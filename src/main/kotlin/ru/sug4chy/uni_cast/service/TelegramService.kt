package ru.sug4chy.uni_cast.service

import ru.sug4chy.uni_cast.entity.TelegramChat

interface TelegramService {
    fun sendAndSaveMessage(
        chat: TelegramChat,
        messageText: String,
        from: String,
        inlineMarkup: List<List<Pair<String, String>>> = emptyList()
    )

    fun sendAndSaveMessageWithReactions(
        chat: TelegramChat,
        messageText: String,
        from: String
    )
}