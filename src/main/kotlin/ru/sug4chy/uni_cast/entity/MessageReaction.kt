package ru.sug4chy.uni_cast.entity

import jakarta.persistence.*
import ru.sug4chy.uni_cast.entity.base.EntityBase
import java.util.*

@Entity
@Table(name = "message_reaction")
class MessageReaction private constructor(
    id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_id", nullable = false)
    val message: SentMessage,

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    val reaction: MessageReactionType,

    @Column(name = "from_user", nullable = false)
    val fromUser: String
) : EntityBase(id) {

    companion object {

        fun createNew(
            message: SentMessage,
            reaction: MessageReactionType,
            from: String
        ): MessageReaction =
            MessageReaction(
                message = message,
                reaction = reaction,
                fromUser = from
            )
    }
}