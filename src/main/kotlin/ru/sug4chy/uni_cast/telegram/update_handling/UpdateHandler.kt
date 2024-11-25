package ru.sug4chy.uni_cast.telegram.update_handling

import org.telegram.telegrambots.meta.api.objects.Update

interface UpdateHandler {
    fun handle(update: Update)
}