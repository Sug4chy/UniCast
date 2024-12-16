package ru.sug4chy.uni_cast.use_case.messages.implementation

import ru.sug4chy.uni_cast.annotation.UseCase
import ru.sug4chy.uni_cast.dto.request.messages.MulticastRequest
import ru.sug4chy.uni_cast.repository.TelegramChatRepository
import ru.sug4chy.uni_cast.service.TelegramService
import ru.sug4chy.uni_cast.use_case.messages.MulticastUseCase

@UseCase
class MulticastUseCaseImpl(
    private val telegramChatRepository: TelegramChatRepository,
    private val telegramService: TelegramService
) : MulticastUseCase {

    override fun invoke(request: MulticastRequest) =
        telegramChatRepository
            .findAll()
            .forEach { chat ->
                telegramService.sendAndSaveMessageWithReactions(
                    chat = chat,
                    messageText = request.text,
                    from = request.from
                )
            }
}