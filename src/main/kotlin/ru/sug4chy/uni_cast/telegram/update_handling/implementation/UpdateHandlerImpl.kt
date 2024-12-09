package ru.sug4chy.uni_cast.telegram.update_handling.implementation

import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.entity.TelegramChat
import ru.sug4chy.uni_cast.repository.TelegramChatRepository
import ru.sug4chy.uni_cast.telegram.scenario.StateMachine
import ru.sug4chy.uni_cast.telegram.update_handling.UpdateHandler
import ru.sug4chy.uni_cast.telegram.update_handling.UpdateHandlingStrategy

@Service
class UpdateHandlerImpl(
    private val existingStrategies: List<UpdateHandlingStrategy>,
    private val telegramChatRepository: TelegramChatRepository,
    private val scenarios: List<StateMachine<*>>
) : UpdateHandler {

    override fun handle(update: Update) {
        val strategy = existingStrategies.find { it.canHandle(update) }
        if (strategy != null) {
            strategy.handle(update)
            return
        }

        if (!update.hasMessage()) {
            return
        }

        val chat = telegramChatRepository.findByExtId(update.message.chatId)
            ?: TelegramChat.fromMessageUpdate(update).also { telegramChatRepository.save(it) }

        if (chat.currentScenario == null) {
            val scenario = scenarios.find { it.canStartScenario(update) }
            chat.currentScenario = scenario?.scenario()
            scenario?.start(chat, update)
        } else {
            val scenario = scenarios.find { it.scenario() == chat.currentScenario }
            scenario?.currentStateFromOrdinal(chat.currentState ?: 0)
                ?.handleUserInput(chat, update)
        }
    }
}