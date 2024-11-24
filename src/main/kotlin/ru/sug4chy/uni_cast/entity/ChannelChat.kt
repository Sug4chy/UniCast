package ru.sug4chy.uni_cast.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.entity.base.EntityBase
import java.time.LocalDateTime
import java.util.*

@Entity
@Table(name = "channel_chat")
class ChannelChat(
    id: UUID? = null,

    @Column(name = "ext_id", nullable = false, unique = true)
    val extId: Long,

    @Column(nullable = false, unique = true)
    val name: String,

    @Column(name = "added_at", nullable = false)
    val addedAt: LocalDateTime,
) : EntityBase(id) {

    companion object {

        fun fromUpdate(update: Update): ChannelChat {
            require(update.hasMyChatMember()) {
                "Некорректный тип обновления для создания сущности ChannelChat"
            }

            return ChannelChat(
                extId = update.myChatMember.chat.id,
                name = update.myChatMember.chat.title,
                addedAt = LocalDateTime.now()
            )
        }
    }
}