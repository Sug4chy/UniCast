package ru.sug4chy.uni_cast.telegram.scenario

import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.entity.TelegramChat

abstract class State {
    abstract fun onStateChanged(chat: TelegramChat, update: Update)
    abstract fun handleUserInput(chat: TelegramChat, update: Update)
}