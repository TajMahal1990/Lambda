import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import ru.netology.Chat
import ru.netology.ChatService
import ru.netology.Message
import ru.netology.NotFoundException
import kotlin.test.assertEquals

class ChatServiceTest {

    @Before
    fun clearBeforeTest() {
        ChatService.clear()
    }

    @Test
    fun addMessage() {
        val message1 = Message(type = "in", text = "Hi")
        val message2 = Message(type = "out", text = "Hi")
        ChatService.addMessage(1, message1)
        ChatService.addMessage(1, message2)

        val result = ChatService.getChats().size
        assertEquals(1, result)
    }

    @Test
    fun lastMessages() {
        val message1 = Message(type = "in", text = "Hi")
        ChatService.addMessage(1, message1)
        val expected = message1.text
        val result = ChatService.lastMessages()
        assertEquals(expected, result)
    }

    @Test(expected = NotFoundException::class)
    fun lastMessages_exception() {
        val message1 = Message(1, type = "in", text = "Hi", true, true)
        ChatService.addMessage(1, message1)
        ChatService.lastMessages()
    }

    @Test
    fun getMessages() {
        val message1 = Message(1, type = "in", text = "Hi", false, true)
        val message2 = Message(2, type = "in", text = "How are you?", false, true)
        ChatService.addMessage(1, message1)
        ChatService.addMessage(1, message2)
        val expected = listOf(message2)
        val result = ChatService.getMessages(1, 1)
        assertEquals(expected, result)
    }

    @Test(expected = NotFoundException::class)
    fun getMessages_exception() {
        ChatService.getMessages(5,1)
    }

    @Test
    fun getUnreadChatsCount() {
        val message1 = Message(type = "in", text = "Hi")
        ChatService.addMessage(1, message1)
        val result = ChatService.getUnreadChatsCount()
        assertEquals(1, result)
    }

    @Test
    fun getChats() {
        val message1 = Message(type = "in", text = "Hi")
        ChatService.addMessage(1, message1)
        val result = ChatService.getChats().size
        assertEquals(1, result)
    }

    @Test
    fun deleteMessage() {
        val message1 = Message(type = "in", text = "Hi")
        ChatService.addMessage(1, message1)
        val result = ChatService.deleteMessage(1, 1)
        assertEquals(true, result)
    }

    @Test
    fun deleteChat() {
        val message1 = Message(type = "in", text = "Hi")
        ChatService.addMessage(1, message1)
        val result = ChatService.deleteChat(1)
        assertEquals(true, result)
    }

    @Test(expected = NotFoundException::class)
    fun deleteChat_exception() {
        ChatService.deleteChat(5)
    }

    @Test
    fun editMessage() {
        val message1 = Message(type = "in", text = "Hi")
        ChatService.addMessage(1, message1)
        val result = ChatService.editMessage(1, 1, "new text")
        assertEquals(true, result)
    }

    @Test(expected = NotFoundException::class)
    fun editMessage_exception() {
        ChatService.editMessage(1, 1, "new text")
    }
}