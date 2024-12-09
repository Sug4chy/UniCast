package ru.sug4chy.uni_cast.telegram.scenario.registration.states

import ru.sug4chy.uni_cast.telegram.scenario.State
import ru.sug4chy.uni_cast.telegram.scenario.registration.RegistrationStateMachine

abstract class RegistrationStateBase(
    protected val registrationStateMachine: RegistrationStateMachine
) : State()