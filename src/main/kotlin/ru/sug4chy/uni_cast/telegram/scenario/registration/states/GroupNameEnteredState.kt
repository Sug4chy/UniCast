package ru.sug4chy.uni_cast.telegram.scenario.registration.states

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.repository.AcademicGroupRepository
import ru.sug4chy.uni_cast.repository.TelegramChatRepository
import ru.sug4chy.uni_cast.service.TelegramService
import ru.sug4chy.uni_cast.telegram.SELF_SENDER
import ru.sug4chy.uni_cast.telegram.scenario.registration.RegistrationStateMachine

@Component
class GroupNameEnteredState(
    registrationStateMachine: RegistrationStateMachine,
    private val telegramChatRepository: TelegramChatRepository,
    private val academicGroupRepository: AcademicGroupRepository,
    private val telegramService: TelegramService,
    private val nextState: RegistrationCompletedState
) : RegistrationStateBase(registrationStateMachine) {

    override fun onStateChanged(update: Update) = Unit

    override fun handleUserInput(update: Update) {
        require(update.hasMessage() && update.message.hasText())

        val groupName = update.message.text
        val chat = telegramChatRepository.findByExtId(update.message.chatId)
            ?: return
        if (!academicGroupRepository.existsByName(groupName)) {
            telegramService.sendAndSaveMessage(
                chat = chat,
                messageText = "Я не знаю такую группу. Пожалуйста, повторите ввод:",
                from = SELF_SENDER,
                withReactions = false
            )
            return
        }

        // TODO: сохраняем имя группы в аргументы сценария
        registrationStateMachine.changeStateTo(
            chat = chat,
            newState = nextState
        )
    }
}