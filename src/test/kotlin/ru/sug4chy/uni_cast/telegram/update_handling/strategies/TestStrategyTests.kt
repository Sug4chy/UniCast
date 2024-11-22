package ru.sug4chy.uni_cast.telegram.update_handling.strategies

import io.mockk.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.telegram.TelegramApiClient
import ru.sug4chy.uni_cast.telegram.update_handling.implementation.strategies.TestStrategy

class TestStrategyTests {

    private val apiClientMock = mockk<TelegramApiClient>()
    private val testStrategy: TestStrategy = TestStrategy(apiClientMock)

    @Test
    fun `cannot handle when message is null`() {
        // arrange
        val updateMessageNull = Update().apply {
            message = null
        }

        // act
        val result = testStrategy.canHandle(updateMessageNull)

        // assert
        assertFalse(result)
    }

    @Test
    fun `cannot handle when text is null`() {
        // arrange
        val updateTextNull = Update().apply {
            message = Message().apply {
                text = null
            }
        }

        // act
        val result = testStrategy.canHandle(updateTextNull)

        // assert
        assertFalse(result)
    }

    @Test
    fun `cannot handle when text is empty`() {
        // arrange
        val updateTextEmpty = Update().apply {
            message = Message().apply {
                text = ""
            }
        }

        // act
        val result = testStrategy.canHandle(updateTextEmpty)

        // assert
        assertFalse(result)
    }

    @Test
    fun `can handle when update is valid`() {
        // arrange
        val update = Update().apply {
            message = Message().apply {
                text = "Some valid text"
            }
        }

        // act
        val result = testStrategy.canHandle(update)

        // assert
        assertTrue(result)
    }

    @Test
    fun `handle works correct with given parameters`() {
        // arrange
        every { apiClientMock.sendMessage(any(), any()) } just runs
        val update = Update().apply {
            message = Message().apply {
                text = "Some valid text"
                chat = Chat().apply {
                    id = 1
                }
            }
        }

        // act
        testStrategy.handle(update)

        // assert
        verify { apiClientMock.sendMessage(1, "Вы написал: *Some valid text*") }
    }
}