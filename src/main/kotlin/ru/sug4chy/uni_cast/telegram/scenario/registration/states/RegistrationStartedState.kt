package ru.sug4chy.uni_cast.telegram.scenario.registration.states

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.entity.TelegramChat
import ru.sug4chy.uni_cast.repository.TelegramChatRepository
import ru.sug4chy.uni_cast.service.TelegramService
import ru.sug4chy.uni_cast.telegram.SELF_SENDER
import ru.sug4chy.uni_cast.telegram.scenario.registration.RegistrationStateMachine

@Component
class RegistrationStartedState(
    private val telegramService: TelegramService,
    private val telegramChatRepository: TelegramChatRepository,
    private val nextState: FullNameEnteredState,
    registrationStateMachine: RegistrationStateMachine
) : RegistrationStateBase(registrationStateMachine) {

    override fun onStateChanged(update: Update) {
        with(telegramChatRepository.findByExtId(update.message.chatId)) {
            if (this != null && this.student != null) {
                telegramService.sendAndSaveMessage(
                    chat = telegramChatRepository.findByExtId(update.message.chatId)!!,
                    messageText = "Кажется, вы уже зарегистрированы в боте. Да, ${this.student?.fullName}?",
                    from = SELF_SENDER,
                    withReactions = false
                )
                return
            }
        }

        val chat = TelegramChat.fromMessageUpdate(update)
            .also { telegramChatRepository.save(it) }
        telegramService.sendAndSaveMessage(
            chat = chat,
            messageText = "Здравствуйте! Я - бот, цель жизни которого, это передавать студентам информацию. Давайте начнём знакомиться!\n\nВведите своё имя:",
            from = SELF_SENDER,
            withReactions = false
        )
        registrationStateMachine.changeStateTo(
            chat = chat,
            newState = nextState
        )
    }

    override fun handleUserInput(update: Update) = Unit
}