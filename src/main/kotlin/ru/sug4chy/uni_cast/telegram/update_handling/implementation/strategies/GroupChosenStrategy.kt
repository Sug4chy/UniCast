package ru.sug4chy.uni_cast.telegram.update_handling.implementation.strategies

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.repository.AcademicGroupRepository
import ru.sug4chy.uni_cast.repository.TelegramChatRepository
import ru.sug4chy.uni_cast.service.TelegramService
import ru.sug4chy.uni_cast.telegram.GROUP_CHOSEN_CALLBACK_START
import ru.sug4chy.uni_cast.telegram.SELF_SENDER
import ru.sug4chy.uni_cast.telegram.scenario.registration.RegistrationScenarioState
import ru.sug4chy.uni_cast.telegram.scenario.registration.RegistrationStateMachine
import ru.sug4chy.uni_cast.telegram.update_handling.UpdateHandlingStrategy

@Component
class GroupChosenStrategy(
    private val telegramChatRepository: TelegramChatRepository,
    private val registrationStateMachine: RegistrationStateMachine,
    private val telegramService: TelegramService,
    private val academicGroupRepository: AcademicGroupRepository
) : UpdateHandlingStrategy {

    override fun canHandle(update: Update): Boolean =
        update.hasCallbackQuery() &&
                update.callbackQuery.data != null &&
                update.callbackQuery.data.startsWith(GROUP_CHOSEN_CALLBACK_START)

    override fun handle(update: Update) {
        val chat = telegramChatRepository.findByExtId(update.callbackQuery.message.chatId)
            ?: return

        val groupName = update.callbackQuery.data.substringAfter(":")
        if (!academicGroupRepository.existsByName(groupName)) {
            telegramService.sendAndSaveMessage(
                chat = chat,
                messageText = "Я не знаю такую группу. Пожалуйста, повторите ввод:",
                from = SELF_SENDER,
            )
            return
        }

        chat.currentScenarioArgs["groupName"] = groupName
        registrationStateMachine.changeStateTo(
            chat = chat,
            newState = registrationStateMachine.getState(RegistrationScenarioState.COMPLETED),
            update = update
        )
    }
}