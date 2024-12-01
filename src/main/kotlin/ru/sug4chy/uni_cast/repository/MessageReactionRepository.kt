package ru.sug4chy.uni_cast.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.sug4chy.uni_cast.entity.MessageReaction
import java.util.*

@Repository
interface MessageReactionRepository : CrudRepository<MessageReaction, UUID> {
    @Query("SELECT CASE WHEN (COUNT(*) > 0) THEN TRUE ELSE FALSE END FROM MessageReaction mr " +
            "WHERE mr.message.id = :messageId AND mr.fromUser = :from")
    fun existsByMessageIdAndFromUser(
        @Param("messageId") messageId: UUID,
        @Param("from") from: String,
    ): Boolean
}