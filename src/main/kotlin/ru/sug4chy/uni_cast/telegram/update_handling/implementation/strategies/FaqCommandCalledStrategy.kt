package ru.sug4chy.uni_cast.telegram.update_handling.implementation.strategies

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.telegram.FAQ_MESSAGE
import ru.sug4chy.uni_cast.telegram.TelegramApiClient
import ru.sug4chy.uni_cast.telegram.update_handling.UpdateHandlingStrategy

@Component
class FaqCommandCalledStrategy(
    private val telegramApiClient: TelegramApiClient
) : UpdateHandlingStrategy {

    override fun canHandle(update: Update): Boolean =
        update.hasMessage() &&
                update.message.hasText() &&
                (update.message.text == "/faq" ||
                        update.message.text.startsWith("/faq"))

    override fun handle(update: Update) {
        telegramApiClient.sendMessage(
            chatId = update.message.chat.id,
            text = FAQ_MESSAGE
        )
    }
}