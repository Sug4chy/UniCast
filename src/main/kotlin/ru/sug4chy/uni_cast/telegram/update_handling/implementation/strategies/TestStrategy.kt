package ru.sug4chy.uni_cast.telegram.update_handling.implementation.strategies

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.telegram.TelegramApiClient
import ru.sug4chy.uni_cast.telegram.update_handling.UpdateHandlingStrategy

@Component
class TestStrategy(
    private val telegramApiClient: TelegramApiClient
) : UpdateHandlingStrategy {

    override fun canHandle(update: Update): Boolean =
        update.message?.text != null &&
                update.message.text.isNotEmpty()

    override fun handle(update: Update) {
        telegramApiClient.sendMessage(
            chatId = update.message.chat.id,
            text = "Вы написал: *${update.message.text}*",
        )
    }
}