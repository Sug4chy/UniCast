package ru.sug4chy.uni_cast.telegram.scenario.registration.states

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.repository.TelegramChatRepository
import ru.sug4chy.uni_cast.service.TelegramService
import ru.sug4chy.uni_cast.telegram.SELF_SENDER
import ru.sug4chy.uni_cast.telegram.scenario.registration.RegistrationStateMachine

@Component
class RegistrationCompletedState(
    registrationStateMachine: RegistrationStateMachine,
    private val telegramService: TelegramService,
    private val telegramChatRepository: TelegramChatRepository
) : RegistrationStateBase(registrationStateMachine) {

    override fun onStateChanged(update: Update) {
        // TODO: достаём все аргументы сценария
        // TODO: создаём и сохраняем студента

        val chat = telegramChatRepository.findByExtId(update.message.chatId)
            ?: return
        telegramService.sendAndSaveMessage(
            chat = chat,
            messageText = "Поздравляю вас с успешным завершением регистрации!",
            from = SELF_SENDER,
            withReactions = false
        )
    }

    override fun handleUserInput(update: Update) = Unit
}