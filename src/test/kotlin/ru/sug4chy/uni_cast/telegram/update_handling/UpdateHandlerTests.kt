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
        val strategy = arrangeExecutorAndStrategy(isUpdateValid = true)

        // act
        handler.handle(Update())

        // assert
        verify { strategy.canHandle(any()) }
        verify { strategy.handle(any()) }
    }

    @Test
    fun `strategy doesn't execute when update isn't valid`() {
        // arrange
        val strategy = arrangeExecutorAndStrategy(isUpdateValid = false)

        // act
        handler.handle(Update())

        // assert
        verify { strategy.canHandle(any()) }
        verify(exactly = 0) { strategy.handle(any()) }
    }

    private fun arrangeExecutorAndStrategy(
        isUpdateValid: Boolean
    ): UpdateHandlingStrategy {
        val strategy = mockk<UpdateHandlingStrategy>()
        every { strategy.canHandle(any()) } returns isUpdateValid
        every { strategy.handle(any()) } just runs
        handler = UpdateHandlerImpl(listOf(strategy))

        return strategy
    }
}