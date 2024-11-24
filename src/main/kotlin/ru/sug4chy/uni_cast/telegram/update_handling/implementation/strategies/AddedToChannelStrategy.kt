package ru.sug4chy.uni_cast.telegram.update_handling.implementation.strategies

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.configuration.properties.telegram.TelegramBotProperties
import ru.sug4chy.uni_cast.entity.ChannelChat
import ru.sug4chy.uni_cast.repository.ChannelChatRepository
import ru.sug4chy.uni_cast.telegram.update_handling.UpdateHandlingStrategy
import ru.sug4chy.uni_cast.utils.logger

@Component
class AddedToChannelStrategy(
    private val channelChatRepository: ChannelChatRepository,
    private val telegramBotProperties: TelegramBotProperties
) : UpdateHandlingStrategy {

    private val logger by logger()

    override fun canHandle(update: Update): Boolean =
        update.hasMyChatMember() &&
                update.myChatMember.chat.isChannelChat &&
                update.myChatMember.newChatMember.status == "administrator" &&
                update.myChatMember.newChatMember.user.userName ==
                telegramBotProperties.botUsername.removePrefix("@")

    override fun handle(update: Update) {
        val channelChat = ChannelChat.fromUpdate(update)
        runCatching { channelChatRepository.save(channelChat) }
            .onFailure { exception -> logger.error { exception.message } }
    }
}