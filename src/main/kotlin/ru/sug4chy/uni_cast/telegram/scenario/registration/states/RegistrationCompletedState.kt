package ru.sug4chy.uni_cast.telegram.scenario.registration.states

import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.entity.Student
import ru.sug4chy.uni_cast.entity.TelegramChat
import ru.sug4chy.uni_cast.repository.AcademicGroupRepository
import ru.sug4chy.uni_cast.repository.StudentRepository
import ru.sug4chy.uni_cast.service.TelegramService
import ru.sug4chy.uni_cast.telegram.SELF_SENDER
import ru.sug4chy.uni_cast.telegram.scenario.registration.RegistrationStateMachine

class RegistrationCompletedState(
    registrationStateMachine: RegistrationStateMachine,
    private val telegramService: TelegramService,
    private val studentRepository: StudentRepository,
    private val academicGroupRepository: AcademicGroupRepository,
) : RegistrationStateBase(registrationStateMachine) {

    override fun onStateChanged(chat: TelegramChat, update: Update) {
        Student.createNew(
            name = chat.currentScenarioArgs["fullName"] as String,
            group = academicGroupRepository
                .findByName(chat.currentScenarioArgs["groupName"] as String)!!
        ).also {
            it.telegramChat = chat
            studentRepository.save(it)
        }

        telegramService.sendAndSaveMessage(
            chat = chat,
            messageText = "Поздравляю вас с успешным завершением регистрации!",
            from = SELF_SENDER,
            withReactions = false
        )

        registrationStateMachine.clearScenario(chat)
    }

    override fun handleUserInput(chat: TelegramChat, update: Update) = Unit
}