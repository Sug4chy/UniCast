package ru.sug4chy.uni_cast.telegram.scenario

import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.entity.TelegramChat

abstract class StateMachine<TState : State> {
    abstract fun changeStateTo(
        chat: TelegramChat,
        newState: TState,
        update: Update,
    )

    abstract fun getState(state: TState): Int
    abstract fun canStartScenario(update: Update): Boolean
    abstract fun scenario(): Scenario
    abstract fun start(chat: TelegramChat, update: Update)
    abstract fun currentStateFromOrdinal(state: Int): TState
}