package ru.sug4chy.uni_cast.telegram.scenario.registration.states

import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.entity.TelegramChat
import ru.sug4chy.uni_cast.service.TelegramService
import ru.sug4chy.uni_cast.telegram.SELF_SENDER
import ru.sug4chy.uni_cast.telegram.scenario.registration.RegistrationScenarioState
import ru.sug4chy.uni_cast.telegram.scenario.registration.RegistrationStateMachine

class FullNameEnteredState(
    private val telegramService: TelegramService,
    registrationStateMachine: RegistrationStateMachine,
) : RegistrationStateBase(registrationStateMachine) {

    private val nextState = RegistrationScenarioState.GROUP_NAME_ENTERED

    override fun onStateChanged(chat: TelegramChat, update: Update) {
        telegramService.sendAndSaveMessage(
            chat = chat,
            messageText = "Пожалуйста, введите свои имя и фамилию (именно в этом порядке):",
            from = SELF_SENDER,
        )
    }

    override fun handleUserInput(chat: TelegramChat, update: Update) {
        require(update.hasMessage() && update.message.hasText())

        val fullName = update.message.text
        chat.currentScenarioArgs["fullName"] = fullName

        registrationStateMachine.changeStateTo(
            chat = chat,
            newState = registrationStateMachine.getState(nextState),
            update = update
        )
    }
}