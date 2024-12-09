package ru.sug4chy.uni_cast.telegram.scenario.registration

import org.springframework.stereotype.Component
import ru.sug4chy.uni_cast.entity.TelegramChat
import ru.sug4chy.uni_cast.repository.TelegramChatRepository
import ru.sug4chy.uni_cast.telegram.scenario.StateMachine
import ru.sug4chy.uni_cast.telegram.scenario.registration.states.RegistrationStateBase

@Component
class RegistrationStateMachine(
    private val telegramChatRepository: TelegramChatRepository
) : StateMachine<RegistrationStateBase>() {

    override fun changeStateTo(chat: TelegramChat, newState: RegistrationStateBase) {
        chat
    }
}