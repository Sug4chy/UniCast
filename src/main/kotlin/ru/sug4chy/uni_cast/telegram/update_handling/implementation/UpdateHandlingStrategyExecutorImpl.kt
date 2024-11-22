package ru.sug4chy.uni_cast.telegram.update_handling.implementation

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.telegram.update_handling.UpdateHandlingStrategy
import ru.sug4chy.uni_cast.telegram.update_handling.UpdateHandlingStrategyExecutor

@Service
class UpdateHandlingStrategyExecutorImpl(
    private val existingStrategies: List<UpdateHandlingStrategy>
) : UpdateHandlingStrategyExecutor {

    override fun handle(update: Update) {
        val strategy = existingStrategies.find { it.canHandle(update) } ?: return
        strategy.handle(update)
    }
}