package ru.sug4chy.uni_cast.telegram.scenario

import ru.sug4chy.uni_cast.entity.TelegramChat

abstract class StateMachine<TState : State> {
    abstract fun changeStateTo(chat: TelegramChat, newState: TState)
}