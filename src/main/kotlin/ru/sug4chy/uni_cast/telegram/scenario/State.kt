package ru.sug4chy.uni_cast.telegram.scenario

import org.telegram.telegrambots.meta.api.objects.Update

abstract class State {
    abstract fun onStateChanged(update: Update)
    abstract fun handleUserInput(update: Update)
}