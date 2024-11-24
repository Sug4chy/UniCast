package ru.sug4chy.uni_cast.telegram.update_handling

import io.mockk.*
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.telegram.update_handling.implementation.UpdateHandlerImpl

@ActiveProfiles("test")
class UpdateHandlerTests {

    private lateinit var handler: UpdateHandler

    @Test
    fun `strategy executes when update is valid`() {
        // arrange
        val testStrategy = arrangeExecutorAndStrategy(isUpdateValid = true)

        // act
        handler.handle(Update())

        // assert
        verify { testStrategy.canHandle(any()) }
        verify { testStrategy.handle(any()) }
    }

    @Test
    fun `strategy doesn't execute when update isn't valid`() {
        // arrange
        val testStrategy = arrangeExecutorAndStrategy(isUpdateValid = false)

        // act
        handler.handle(Update())

        // assert
        verify { testStrategy.canHandle(any()) }
        verify(exactly = 0) { testStrategy.handle(any()) }
    }

    private fun arrangeExecutorAndStrategy(
        isUpdateValid: Boolean
    ): UpdateHandlingStrategy {
        val testStrategy = mockk<UpdateHandlingStrategy>()
        every { testStrategy.canHandle(any()) } returns isUpdateValid
        every { testStrategy.handle(any()) } just runs
        handler = UpdateHandlerImpl(listOf(testStrategy))

        return testStrategy
    }
}