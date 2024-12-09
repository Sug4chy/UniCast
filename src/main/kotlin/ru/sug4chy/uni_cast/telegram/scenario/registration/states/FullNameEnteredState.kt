package ru.sug4chy.uni_cast.telegram.scenario.registration.states

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.repository.TelegramChatRepository
import ru.sug4chy.uni_cast.service.TelegramService
import ru.sug4chy.uni_cast.telegram.SELF_SENDER
import ru.sug4chy.uni_cast.telegram.scenario.registration.RegistrationStateMachine

@Component
class FullNameEnteredState(
    registrationStateMachine: RegistrationStateMachine,
    private val nextSTate: GroupNameEnteredState,
    private val telegramService: TelegramService,
    private val telegramChatRepository: TelegramChatRepository
) : RegistrationStateBase(registrationStateMachine) {

    override fun onStateChanged(update: Update) = Unit

    override fun handleUserInput(update: Update) {
        require(update.hasMessage() && update.message.hasText())

        val fullName = update.message.text
        val chat = telegramChatRepository.findByExtId(update.message.chatId)
            ?: return
        // TODO: сохраняем в аргументы текущего сценария
        telegramService.sendAndSaveMessage(
            chat = chat,
            messageText = "Принято! Теперь напиши, в какой группе ты состоишь:",
            from = SELF_SENDER,
            withReactions = false
        )
        registrationStateMachine.changeStateTo(
            chat = chat,
            newState = nextSTate
        )
    }
}