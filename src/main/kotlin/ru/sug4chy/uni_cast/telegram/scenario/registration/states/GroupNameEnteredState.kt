package ru.sug4chy.uni_cast.telegram.scenario.registration.states

import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.entity.TelegramChat
import ru.sug4chy.uni_cast.repository.AcademicGroupRepository
import ru.sug4chy.uni_cast.service.TelegramService
import ru.sug4chy.uni_cast.telegram.SELF_SENDER
import ru.sug4chy.uni_cast.telegram.scenario.registration.RegistrationScenarioState
import ru.sug4chy.uni_cast.telegram.scenario.registration.RegistrationStateMachine

class GroupNameEnteredState(
    registrationStateMachine: RegistrationStateMachine,
    private val academicGroupRepository: AcademicGroupRepository,
    private val telegramService: TelegramService
) : RegistrationStateBase(registrationStateMachine) {

    private val nextState = RegistrationScenarioState.COMPLETED

    override fun onStateChanged(chat: TelegramChat, update: Update) = Unit

    override fun handleUserInput(chat: TelegramChat, update: Update) {
        require(update.hasMessage() && update.message.hasText())

        val groupName = update.message.text
        if (!academicGroupRepository.existsByName(groupName)) {
            telegramService.sendAndSaveMessage(
                chat = chat,
                messageText = "Я не знаю такую группу. Пожалуйста, повторите ввод:",
                from = SELF_SENDER,
                withReactions = false
            )
            return
        }

        chat.currentScenarioArgs["groupName"] = groupName
        registrationStateMachine.changeStateTo(
            chat = chat,
            newState = registrationStateMachine.getState(nextState),
            update = update
        )
    }
}