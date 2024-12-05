package ru.sug4chy.uni_cast.telegram

import org.telegram.telegrambots.meta.api.methods.ParseMode
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand

interface TelegramApiClient {
    fun setWebhook()
    fun sendMessage(
        chatId: Long,
        text: String,
        parseMode: String = ParseMode.MARKDOWN,
        inlineButtons: List<List<Pair<String, String>>> = emptyList()
    ): Int
    fun setCommands(vararg commands: BotCommand)
}