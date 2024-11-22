package ru.sug4chy.uni_cast.telegram.update_handling

import io.mockk.*
import org.junit.jupiter.api.Test
import org.springframework.test.context.ActiveProfiles
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.telegram.update_handling.implementation.UpdateHandlingStrategyExecutorImpl

@ActiveProfiles("test")
class UpdateHandlingStrategyExecutorTests {

    private lateinit var executor: UpdateHandlingStrategyExecutor

    @Test
    fun `strategy executes when update is valid`() {
        // arrange
        val testStrategy = arrangeExecutorAndStrategy(isUpdateValid = true)

        // act
        executor.handle(Update())

        // assert
        verify { testStrategy.canHandle(any()) }
        verify { testStrategy.handle(any()) }
    }

    @Test
    fun `strategy doesn't execute when update isn't valid`() {
        // arrange
        val testStrategy = arrangeExecutorAndStrategy(isUpdateValid = false)

        // act
        executor.handle(Update())

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
        executor = UpdateHandlingStrategyExecutorImpl(listOf(testStrategy))

        return testStrategy
    }
}
