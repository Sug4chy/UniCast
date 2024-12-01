package ru.sug4chy.uni_cast.telegram.implementation

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramWebhookBot
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updates.SetWebhook
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.sug4chy.uni_cast.configuration.properties.telegram.TelegramBotProperties
import ru.sug4chy.uni_cast.telegram.TelegramApiClient

@Component
class TelegramBotImpl(
    private val telegramBotProperties: TelegramBotProperties,
) : TelegramWebhookBot(telegramBotProperties.token), TelegramApiClient {

    override fun getBotUsername(): String =
        telegramBotProperties.botUsername

    @Deprecated(
        "Этот метод не используется, остался лишним от библиотекиу",
        ReplaceWith("UpdateHandler.handle(update)")
    )
    override fun onWebhookUpdateReceived(update: Update): Nothing =
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
        inlineButtons: List<List<Pair<String, String>>>
    ): Int {
        val sendMessageApiMethod = SendMessage.builder()
            .chatId(chatId)
            .text(text)
            .parseMode(parseMode)
            .replyMarkup(
                InlineKeyboardMarkup(inlineButtons.map { row ->
                    row.map { rowItem ->
                        InlineKeyboardButton().apply {
                            this.text = rowItem.first
                            this.callbackData = rowItem.second
                        }
                    }
                })
            )
            .build()
        val message: Message = this.execute(sendMessageApiMethod)

        return message.messageId
    }

    override fun setCommands(vararg commands: BotCommand) {
        SetMyCommands.builder()
            .commands(commands.toMutableList())
            .build()
            .let { this@TelegramBotImpl.execute(it) }
    }

    @PostConstruct
    fun init() {
        this.setWebhook()
        this.setCommands(
            BotCommand.builder()
                .command("/faq")
                .description("Ответы на вопросы о факультете, которые могут волновать студентов")
                .build()
        )
    }
}