package ru.sug4chy.uni_cast.telegram.scenario.registration

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.entity.TelegramChat
import ru.sug4chy.uni_cast.repository.AcademicGroupRepository
import ru.sug4chy.uni_cast.repository.StudentRepository
import ru.sug4chy.uni_cast.repository.TelegramChatRepository
import ru.sug4chy.uni_cast.service.TelegramService
import ru.sug4chy.uni_cast.telegram.scenario.Scenario
import ru.sug4chy.uni_cast.telegram.scenario.StateMachine
import ru.sug4chy.uni_cast.telegram.scenario.registration.states.*

@Component
class RegistrationStateMachine(
    private val telegramChatRepository: TelegramChatRepository,
    private val telegramService: TelegramService,
    private val academicGroupRepository: AcademicGroupRepository,
    private val studentRepository: StudentRepository
) : StateMachine<RegistrationStateBase>() {

    override fun changeStateTo(
        chat: TelegramChat,
        newState: RegistrationStateBase,
        update: Update
    ) {
        chat.currentState = getState(newState)
        telegramChatRepository.save(chat)
        newState.onStateChanged(chat, update)
    }

    override fun getState(state: RegistrationStateBase): Int =
        when (state) {
            is RegistrationStartedState -> RegistrationScenarioState.STARTED
            is FullNameEnteredState -> RegistrationScenarioState.FULL_NAME_ENTERED
            is GroupNameEnteredState -> RegistrationScenarioState.GROUP_NAME_ENTERED
            is RegistrationCompletedState -> RegistrationScenarioState.COMPLETED
            else -> throw IllegalArgumentException("Unknown state: ${state::class.simpleName}")
        }.ordinal

    override fun canStartScenario(update: Update): Boolean =
        update.hasMessage() &&
                update.message.hasText() &&
                update.message.text == "/start"

    override fun scenario(): Scenario =
        Scenario.REGISTRATION

    override fun start(chat: TelegramChat, update: Update) {
        getState(RegistrationScenarioState.STARTED)
            .onStateChanged(chat, update)
    }

    override fun currentStateFromOrdinal(state: Int): RegistrationStateBase =
        getState(RegistrationScenarioState.entries[state])

    fun getState(state: RegistrationScenarioState): RegistrationStateBase =
        when (state) {
            RegistrationScenarioState.STARTED -> RegistrationStartedState(
                telegramService = telegramService,
                registrationStateMachine = this
            )

            RegistrationScenarioState.FULL_NAME_ENTERED -> FullNameEnteredState(
                telegramService = telegramService,
                registrationStateMachine = this
            )

            RegistrationScenarioState.GROUP_NAME_ENTERED -> GroupNameEnteredState(
                telegramService = telegramService,
                registrationStateMachine = this,
                academicGroupRepository = academicGroupRepository
            )

            RegistrationScenarioState.COMPLETED -> RegistrationCompletedState(
                telegramService = telegramService,
                registrationStateMachine = this,
                studentRepository = studentRepository,
                academicGroupRepository = academicGroupRepository
            )
        }

    fun clearScenario(chat: TelegramChat) {
        chat.apply {
            currentScenario = null
            currentState = null
            currentScenarioArgs.clear()
        }
        telegramChatRepository.save(chat)
    }
}