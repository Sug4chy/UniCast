package ru.sug4chy.uni_cast.service.implementation

import org.springframework.stereotype.Service
import ru.sug4chy.uni_cast.entity.SentMessage
import ru.sug4chy.uni_cast.entity.TelegramChat
import ru.sug4chy.uni_cast.repository.SentMessageRepository
import ru.sug4chy.uni_cast.service.TelegramService
import ru.sug4chy.uni_cast.telegram.*

@Service
class TelegramServiceImpl(
    private val telegramApiClient: TelegramApiClient,
    private val sentMessageRepository: SentMessageRepository
) : TelegramService {

    override fun sendAndSaveMessage(
        chat: TelegramChat,
        messageText: String,
        from: String,
        withReactions: Boolean
    ) {
        val messageExtId = if (withReactions) {
            telegramApiClient.sendMessage(
                chatId = chat.extId,
                text = messageText,
                inlineButtons = listOf(
                    listOf(
                        THUMB_UP_EMOJI to POSITIVE_CALLBACK_REACTION,
                        THUMB_DOWN_EMOJI to NEGATIVE_CALLBACK_REACTION
                    )
                )
            )
        } else {
            telegramApiClient.sendMessage(
                chatId = chat.extId,
                text = messageText,
            )
        }

        val message = SentMessage.builder()
            .extId(messageExtId)
            .text(messageText)
            .sender(from)
            .telegramChat(chat)
            .build()

        sentMessageRepository.save(message)
    }
}