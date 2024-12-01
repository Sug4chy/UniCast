package ru.sug4chy.uni_cast.telegram.update_handling.implementation.strategies

import mu.KLogger
import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.entity.MessageReaction
import ru.sug4chy.uni_cast.entity.MessageReactionType
import ru.sug4chy.uni_cast.repository.ChannelChatRepository
import ru.sug4chy.uni_cast.repository.MessageReactionRepository
import ru.sug4chy.uni_cast.repository.SentMessageRepository
import ru.sug4chy.uni_cast.telegram.update_handling.UpdateHandlingStrategy
import ru.sug4chy.uni_cast.utils.NEGATIVE_CALLBACK_REACTION
import ru.sug4chy.uni_cast.utils.POSITIVE_CALLBACK_REACTION
import ru.sug4chy.uni_cast.utils.logger

@Component
class ReactedToMessageStrategy(
    private val channelChatRepository: ChannelChatRepository,
    private val messageRepository: SentMessageRepository,
    private val messageReactionRepository: MessageReactionRepository
) : UpdateHandlingStrategy {

    override fun canHandle(update: Update): Boolean =
        update.hasCallbackQuery() &&
                (update.callbackQuery.data == POSITIVE_CALLBACK_REACTION ||
                        update.callbackQuery.data == NEGATIVE_CALLBACK_REACTION)

    override fun handle(update: Update) {
        val chat = channelChatRepository.findByExtId(update.callbackQuery.message.chatId)
            ?: run {
                logger.error { "Chat with ID ${update.callbackQuery.message.chatId} not found" }
                return
            }
        val message = messageRepository.findByExtIdAndChatId(
            extId = update.callbackQuery.message.messageId,
            channelId = chat.id!!
        ) ?: run {
            logger.error { "Message with ID ${update.callbackQuery.message.messageId} not found" }
            return
        }

        if (messageReactionRepository.existsByMessageIdAndFromUser(
                messageId = message.id!!,
                from = update.callbackQuery.from.userName
            )
        ) {
            logger.info {
                "Reaction to message ${message.id} from user ${update.callbackQuery.from.userName} already exists"
            }
            return
        }

        val reaction = MessageReaction.createNew(
            message = message,
            reaction = with(update.callbackQuery.data) {
                when (this) {
                    POSITIVE_CALLBACK_REACTION -> MessageReactionType.POSITIVE

                    NEGATIVE_CALLBACK_REACTION -> MessageReactionType.NEGATIVE

                    else -> return
                }
            },
            from = update.callbackQuery.from.userName
        )
        messageReactionRepository.save(reaction)
    }

    companion object {
        private val logger: KLogger by logger()
    }
}