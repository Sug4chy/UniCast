package ru.sug4chy.uni_cast.entity

import jakarta.persistence.*
import ru.sug4chy.uni_cast.entity.base.EntityBase
import java.util.*

@Entity
@Table(name = "sent_message")
class SentMessage private constructor(
    id: UUID? = null,

    @Column(name = "ext_id", nullable = false)
    val extId: Int,

    @Column(nullable = false)
    val text: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", nullable = false)
    val telegramChat: TelegramChat,

    @Column(nullable = false)
    val sender: String,

    @OneToMany(mappedBy = "message")
    val reactions: Set<MessageReaction> = emptySet()
) : EntityBase(id) {

    class MessageBuilder {

        private var extId: Int = -1
        private var text: String = ""
        private var telegramChat: TelegramChat? = null
        private var sender: String = ""

        fun extId(extId: Int): MessageBuilder =
            apply { this.extId = extId }

        fun text(text: String): MessageBuilder =
            apply { this.text = text }

        fun channelChat(telegramChat: TelegramChat): MessageBuilder =
            apply { this.telegramChat = telegramChat }

        fun sender(sender: String): MessageBuilder =
            apply { this.sender = sender }

        fun build(): SentMessage {
            check(this.extId != -1) { "extId must be initialized" }
            check(this.text != "") { "text must be initialized" }
            check(this.telegramChat != null) { "channelChat must be initialized" }
            check(this.sender != "") { "sender must be initialized" }

            return SentMessage(
                extId = this.extId,
                text = this.text,
                telegramChat = this.telegramChat ?: throw IllegalStateException("channelChat must be initialized"),
                sender = this.sender
            )
        }
    }

    companion object {

        fun builder(): MessageBuilder =
            MessageBuilder()
    }
}