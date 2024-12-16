package ru.sug4chy.uni_cast.telegram.scenario.registration.states

import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.entity.TelegramChat
import ru.sug4chy.uni_cast.repository.AcademicGroupRepository
import ru.sug4chy.uni_cast.service.TelegramService
import ru.sug4chy.uni_cast.telegram.GROUP_CHOSEN_CALLBACK_START
import ru.sug4chy.uni_cast.telegram.SELF_SENDER
import ru.sug4chy.uni_cast.telegram.scenario.registration.RegistrationStateMachine

class GroupNameEnteredState(
    registrationStateMachine: RegistrationStateMachine,
    private val academicGroupRepository: AcademicGroupRepository,
    private val telegramService: TelegramService
) : RegistrationStateBase(registrationStateMachine) {

    override fun onStateChanged(chat: TelegramChat, update: Update) {
        telegramService.sendAndSaveMessage(
            chat = chat,
            messageText = "Принято! Теперь выбери, в какой группе ты состоишь:",
            from = SELF_SENDER,
            inlineMarkup = academicGroupRepository.findAll()
                .map { listOf(it.name to "$GROUP_CHOSEN_CALLBACK_START${it.name}") }
        )
    }

    @Deprecated(
        "Ввод через inline-кнопку обрабатывается в другом месте",
        ReplaceWith("GroupChosenStrategy.handle(update)")
    )
    override fun handleUserInput(chat: TelegramChat, update: Update) =
        Unit
}