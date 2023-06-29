import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.testng.annotations.Test
import ru.netology.Comment
import ru.netology.Post
import ru.netology.PostNotFoundException
import ru.netology.WallService

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun add_id1() {
        val result = WallService.add(Post(1, 1, 1, date = 1))
        assertEquals(1, result.id)
    }

    @Test
    fun add_id5() {
        val result = WallService.add(Post(5, 1, 1, date = 1))
        assertEquals(1, result.id)
    }

    @Test
    fun update_true() {
        WallService.add(Post(1, 1, 1, date = 1))
        val result = WallService.update(Post(1, 1, 1, date = 1))
        assertEquals(true, result)
    }

    @Test
    fun update_false() {
        WallService.add(Post(1, 1, 1, date = 1))
        val result = WallService.update(Post(10, 1, 1, date = 1))
        assertEquals(false, result)
    }

    @Test
    fun createComment_addTrue() {
        WallService.add(Post(1, 1, 1, date = 1))
        val result = WallService.createComment(1, Comment())
        assertEquals(1, result.id)
    }

    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        WallService.add(Post(1, 1, 1, date = 1))
        WallService.createComment(5, Comment())
    }
}

annotation class Before
