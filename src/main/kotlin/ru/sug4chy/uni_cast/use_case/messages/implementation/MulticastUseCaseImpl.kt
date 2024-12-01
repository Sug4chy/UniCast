package ru.sug4chy.uni_cast.use_case.messages.implementation

import mu.KLogger
import ru.sug4chy.uni_cast.annotation.UseCase
import ru.sug4chy.uni_cast.dto.request.messages.MulticastRequest
import ru.sug4chy.uni_cast.entity.SentMessage
import ru.sug4chy.uni_cast.repository.ChannelChatRepository
import ru.sug4chy.uni_cast.repository.SentMessageRepository
import ru.sug4chy.uni_cast.telegram.TelegramApiClient
import ru.sug4chy.uni_cast.use_case.messages.MulticastUseCase
import ru.sug4chy.uni_cast.utils.*

@UseCase
class MulticastUseCaseImpl(
    private val telegramApiClient: TelegramApiClient,
    private val channelChatRepository: ChannelChatRepository,
    private val sentMessageRepository: SentMessageRepository
) : MulticastUseCase {

    override fun invoke(request: MulticastRequest) =
        channelChatRepository
            .findAll()
            .forEach { channel ->
                val messageExtId = telegramApiClient.sendMessage(
                    chatId = channel.extId,
                    text = "Новое сообщение:\n${request.text}\n\nОтправитель: ${request.from}",
                    inlineButtons = listOf(
                        listOf(
                            THUMB_UP_EMOJI to POSITIVE_CALLBACK_REACTION,
                            THUMB_DOWN_EMOJI to NEGATIVE_CALLBACK_REACTION
                        )
                    )
                )
                val message = SentMessage.builder()
                    .extId(messageExtId)
                    .text(request.text)
                    .sender(request.from)
                    .channelChat(channel)
                    .build()
                runCatching { sentMessageRepository.save(message) }
                    .onFailure { exception -> logger.error { exception.message } }
            }

    companion object {
        private val logger: KLogger by logger()
    }
}