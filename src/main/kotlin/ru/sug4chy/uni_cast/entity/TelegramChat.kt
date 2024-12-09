package ru.sug4chy.uni_cast.entity

import jakarta.persistence.*
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.entity.base.EntityBase
import ru.sug4chy.uni_cast.hibernate.MapJsonbAttributeConverter
import ru.sug4chy.uni_cast.telegram.scenario.Scenario
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

    @Column(name = "current_scenario", nullable = true, unique = false)
    @Enumerated(EnumType.ORDINAL)
    val currentScenario: Scenario? = null,

    @Column(name = "current_state", nullable = true, unique = false)
    val currentState: Int? = null,

    @Convert(converter = MapJsonbAttributeConverter::class)
    @Column(name = "current_scenario_args", nullable = true, unique = false, columnDefinition = "jsonb")
    val currentScenarioArgs: Map<String, Any> = emptyMap(),
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