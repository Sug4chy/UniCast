package ru.sug4chy.uni_cast.telegram.implementation

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.configuration.properties.telegram.TelegramBotProperties
import ru.sug4chy.uni_cast.telegram.TelegramApiClient

@Component
internal class TelegramBotImpl(
    private val telegramBotProperties: TelegramBotProperties,
) : TelegramWebhookBot(telegramBotProperties.token), TelegramApiClient {

    override fun getBotUsername(): String =
        telegramBotProperties.botUsername

    // TODO: добавить текст в аннотацию @Deprecated
    @Deprecated("", ReplaceWith("throw UnsupportedOperationException()"))
    override fun onWebhookUpdateReceived(update: Update?): Nothing =
        throw UnsupportedOperationException()

    override fun getBotPath(): String =
        telegramBotProperties.webhookUrl

    override fun setWebhook() {
        SetWebhook.builder()
            .url(telegramBotProperties.webhookUrl)
            .build()
            .let { this@TelegramBotImpl.execute(it) }
    }

    override fun sendMessage(
        chatId: Long,
        text: String,
        parseMode: String,
    ) {
        SendMessage.builder()
            .chatId(chatId)
            .text(text)
            .parseMode(parseMode)
            .build()
            .let { this@TelegramBotImpl.execute(it) }
    }

    @PostConstruct
    fun init() {
        this.setWebhook()
    }
}