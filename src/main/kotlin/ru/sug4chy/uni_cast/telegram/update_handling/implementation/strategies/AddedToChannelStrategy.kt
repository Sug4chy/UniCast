package ru.sug4chy.uni_cast.telegram.update_handling.implementation.strategies

import mu.KLogger
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.configuration.properties.telegram.TelegramBotProperties
import ru.sug4chy.uni_cast.entity.TelegramChat
import ru.sug4chy.uni_cast.repository.TelegramChatRepository
import ru.sug4chy.uni_cast.telegram.update_handling.UpdateHandlingStrategy
import ru.sug4chy.uni_cast.utils.logger

@Component
class AddedToChannelStrategy(
    private val telegramChatRepository: TelegramChatRepository,
    private val telegramBotProperties: TelegramBotProperties
) : UpdateHandlingStrategy {

    override fun canHandle(update: Update): Boolean =
        update.hasMyChatMember() &&
                update.myChatMember.chat.isChannelChat &&
                update.myChatMember.newChatMember.status == "administrator" &&
                update.myChatMember.newChatMember.user.userName ==
                telegramBotProperties.botUsername.removePrefix("@")

    override fun handle(update: Update) {
        val telegramChat = TelegramChat.fromMyChatMemberUpdate(update)
        runCatching { telegramChatRepository.save(telegramChat) }
            .onFailure { exception -> logger.error { exception.message } }
    }

    companion object {
        private val logger: KLogger by logger()
    }
}