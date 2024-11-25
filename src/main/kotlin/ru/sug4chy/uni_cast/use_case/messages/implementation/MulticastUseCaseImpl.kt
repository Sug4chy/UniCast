package ru.sug4chy.uni_cast.use_case.messages.implementation

import ru.sug4chy.uni_cast.annotation.UseCase
import ru.sug4chy.uni_cast.dto.request.messages.MulticastRequest
import ru.sug4chy.uni_cast.repository.ChannelChatRepository
import ru.sug4chy.uni_cast.telegram.TelegramApiClient
import ru.sug4chy.uni_cast.use_case.messages.MulticastUseCase

@UseCase
class MulticastUseCaseImpl(
    private val telegramApiClient: TelegramApiClient,
    private val channelChatRepository: ChannelChatRepository
) : MulticastUseCase {

    override fun invoke(request: MulticastRequest) {
        with(channelChatRepository.findAll()) {
            for (channel in this) {
                telegramApiClient.sendMessage(
                    chatId = channel.extId,
                    text = "Новое сообщение:\n${request.text}\n\nОтправитель: ${request.from}",
                )
            }
        }
    }
}