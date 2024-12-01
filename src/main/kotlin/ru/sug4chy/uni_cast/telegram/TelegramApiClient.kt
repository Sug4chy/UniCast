package ru.sug4chy.uni_cast.telegram

import org.telegram.telegrambots.meta.api.methods.ParseMode

interface TelegramApiClient {
    fun setWebhook()
    fun sendMessage(
        chatId: Long,
        text: String,
        parseMode: String = ParseMode.MARKDOWN,
        inlineButtons: List<List<Pair<String, String>>> = emptyList()
    ): Int
}