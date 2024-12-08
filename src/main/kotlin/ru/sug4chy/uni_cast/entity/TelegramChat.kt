package ru.sug4chy.uni_cast.entity

import jakarta.persistence.*
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.entity.base.EntityBase
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "telegram_chat")
class TelegramChat private constructor(
    id: UUID? = null,

    @Column(name = "ext_id", nullable = false, unique = true)
    val extId: Long,

    @Column(nullable = false, unique = true)
    val name: String,

    @Column(name = "added_at", nullable = false)
    val addedAt: LocalDateTime,

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "telegramChat")
    val messages: Set<SentMessage> = emptySet(),

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    val type: TelegramChatType,

    @OneToOne(mappedBy = "telegramChat")
    val student: Student? = null,
) : EntityBase(id) {

    companion object {

        fun fromMyChatMemberUpdate(update: Update): TelegramChat {
            require(update.hasMyChatMember()) {
                "Некорректный тип обновления для создания сущности TelegramChat"
            }

            return TelegramChat(
                extId = update.myChatMember.chat.id,
                name = update.myChatMember.chat.title,
                addedAt = LocalDateTime.now(),
                type = TelegramChatType.CHANNEL
            )
        }

        fun fromMessageUpdate(update: Update): TelegramChat {
            require(update.hasMessage()) {
                "Некорректный тип обновления для создания сущности TelegramChat"
            }

            return TelegramChat(
                extId = update.message.chat.id,
                name = update.message.from.userName,
                addedAt = LocalDateTime.now(),
                type = TelegramChatType.PRIVATE_CHAT
            )
        }
    }
}