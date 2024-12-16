package ru.sug4chy.uni_cast.telegram.scenario.registration.states

import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.entity.TelegramChat
import ru.sug4chy.uni_cast.service.TelegramService
import ru.sug4chy.uni_cast.telegram.SELF_SENDER
import ru.sug4chy.uni_cast.telegram.scenario.registration.RegistrationScenarioState
import ru.sug4chy.uni_cast.telegram.scenario.registration.RegistrationStateMachine

class RegistrationStartedState(
    private val telegramService: TelegramService,
    registrationStateMachine: RegistrationStateMachine
) : RegistrationStateBase(registrationStateMachine) {

    private val nextState = RegistrationScenarioState.FULL_NAME_ENTERED

    override fun onStateChanged(chat: TelegramChat, update: Update) {
        telegramService.sendAndSaveMessage(
            chat = chat,
            messageText = "Здравствуйте! Я - бот, цель жизни которого, это передавать студентам информацию. " +
                    "Давайте начнём знакомиться!",
            from = SELF_SENDER
        )
        registrationStateMachine.changeStateTo(
            chat = chat,
            newState = registrationStateMachine.getState(nextState),
            update = update
        )
    }

    override fun handleUserInput(chat: TelegramChat, update: Update) = Unit
}