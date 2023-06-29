package ru.netology

fun main() {
    WallService.add(Post(1, 1, 1, date = 1))
    WallService.add(Post(2, 1, 1, date = 1))
    WallService.print()
    println(WallService.update(Post(6, 1, 1, date = 1, likes = Likes(100))))
    WallService.print()
    println(WallService.createComment(1, Comment()))
    println(WallService.createComment(5, Comment()))
}

class PostNotFoundException(message: String) : RuntimeException(message)
data class Post(
    val id: Int,
    val ownerId: Int,
    val fromID: Int,
    val createdBy: Int? = null,
    val date: Int,
    val text: String = "text",
    val replyOwnerId: Int? = null,
    val replyPostId: Int? = null,
    val views: Int = 0,
    val postType: String = "post",
    val signerId: Int? = null,
    val friendsOnly: Boolean = false,
    val canPin: Boolean = false,
    val canDelete: Boolean = false,
    val canEdit: Boolean = false,
    val isPinned: Boolean = false,
    val markedAsAds: Boolean = false,
    val isFavorite: Boolean = false,
    val postponedId: Int? = null,
    val likes: Likes = Likes(),
    val comments: Comments = Comments(),
    val copyright: Copyright? = null,
    val reposts: Reposts = Reposts(),
    val geo: Geo = Geo(),
    val donut: Donut = Donut(),
    val attachments: Array<Attachment> = arrayOf(
        PhotoAttachment(Photo()),
        VideoAttachment(Video()),
        AudioAttachment(Audio()),
        DocAttachment(Doc()),
        GraffitiAttachment(Graffiti())
    )
)

data class Likes(
    val count: Int = 0,
    val userLikes: Boolean = false,
    val canLike: Boolean = true,
    val canPublish: Boolean = true
)

data class Comments(
    val count: Int = 0,
    val canPost: Boolean = true,
    val groupsCanPost: Boolean = true,
    val canClose: Boolean = true,
    val canOpen: Boolean = true
)

data class Copyright(
    val id: Int,
    val link: Int,
    val name: String,
    val type: String
)

data class Reposts(
    val count: Int = 0,
    val userReposted: Boolean = false
)

data class Geo(
    val type: String = "",
    val coordinates: String = ""
)

data class Donut(
    val isDonut: Boolean = false,
    val paidDuration: Int? = null,
    val placeholder: String = "",
    val canPublishFreeCopy: Boolean = false,
    val editMode: String = ""
)

interface Attachment {
    val type: String
}

data class Photo(
    val id: Int = 1,
    val albumId: Int = 1,
    val ownerId: Int = 1,
    val userId: Int = 1,
    val text: String = "text",
    val date: Int = 1,
    val width: Int = 1,
    val height: Int = 1
)

data class PhotoAttachment(val photo: Photo) : Attachment {
    override val type = "photo"
}

data class Video(
    val id: Int = 1,
    val ownerId: Int = 1,
    val title: String = "text",
    val description: String = "text",
    val duration: Int = 1,
    val date: Int = 1,
    val addingDate: Int = 1,
    val views: Int = 1,
    val comments: Int = 1,
    val player: String = "text",
    val platform: String = "text",
    val canAdd: Boolean = true,
    val isPrivate: Boolean = true,
    val accessKey: String = "text",
    val processing: Boolean = true,
    val isFavorite: Boolean = true,
    val canComment: Boolean = true,
    val canEdit: Boolean = true,
    val canLike: Boolean = true,
    val canRepost: Boolean = true,
    val canSubscribe: Boolean = true,
    val canAddToFaves: Boolean = true,
    val canAttachLink: Boolean = true,
    val width: Int = 1,
    val height: Int = 1,
    val userId: Int = 1,
    val converting: Boolean = true,
    val added: Boolean = true,
    val isSubscribed: Boolean = true,
    val repeat: Boolean = true,
    val type: String = "text",
    val balance: Int = 1,
    val liveStatus: String = "text",
    val live: Boolean = true,
    val upcoming: Boolean = true,
    val spectators: Int = 1
)

data class VideoAttachment(val video: Video) : Attachment {
    override val type = "video"
}

data class Audio(
    val id: Int = 1,
    val ownerId: Int = 1,
    val artist: String = "text",
    val title: String = "text",
    val duration: Int = 1,
    val url: String = "text",
    val lyricsId: Int = 1,
    val albumId: Int = 1,
    val genreId: Int = 1,
    val date: Int = 1,
    val noSearch: Boolean = true,
    val isHq: Boolean = true
)

data class AudioAttachment(val audio: Audio) : Attachment {
    override val type = "audio"
}

data class Doc(
    val id: Int = 1,
    val ownerId: Int = 1,
    val title: String = "text",
    val size: Int = 1,
    val ext: String = "text",
    val url: String = "text",
    val date: Int = 1,
    val type: Int = 1
)

data class DocAttachment(val doc: Doc) : Attachment {
    override val type = "doc"
}

data class Graffiti(
    val id: Int = 1,
    val ownerId: Int = 1,
    val photo130: String = "text",
    val photo604: String = "text"
)

data class GraffitiAttachment(val graffiti: Graffiti) : Attachment {
    override val type = "graffiti"
}

data class Comment(
    val id: Int = 1,
    val fromId: Int = 1,
    val date: Int = 1,
    val text: String = "text",
    val donut: Donut = Donut(),
    val replyToUser: Int? = null,
    val replyToComment: Int? = null,
    val attachments: Array<Attachment> = arrayOf(
        PhotoAttachment(Photo()),
        VideoAttachment(Video()),
        AudioAttachment(Audio()),
        DocAttachment(Doc()),
        GraffitiAttachment(Graffiti())
    )
)

object WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var lastId = 0
    fun add(post: Post): Post {
        posts += post.copy(id = ++lastId, likes = post.likes.copy())
        return posts.last()
    }

    fun update(newPost: Post): Boolean {
        for ((index, post) in posts.withIndex()) {
            if (post.id == newPost.id) {
                posts[index] = newPost.copy(likes = newPost.likes.copy())
                return true
            }
        }
        return false
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        for (post in posts)
            if (post.id == postId) {
                comments += comment
                return comment
            }
        throw PostNotFoundException("no post with id $postId")
    }

    fun clear() {
        posts = emptyArray()
        lastId = 0
    }

    fun print() {
        for (post in posts) {
            print(post)
            println()
        }
    }
}