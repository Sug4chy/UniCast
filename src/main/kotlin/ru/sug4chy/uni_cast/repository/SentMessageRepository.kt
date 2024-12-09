package ru.sug4chy.uni_cast.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.sug4chy.uni_cast.entity.SentMessage
import java.util.*

@Repository
interface SentMessageRepository : CrudRepository<SentMessage, UUID> {

    @Query("SELECT m FROM SentMessage m WHERE m.extId = :extId AND m.telegramChat.id = :channelId")
    fun findByExtIdAndChatId(
        @Param("extId") extId: Int,
        @Param("channelId") channelId: UUID
    ): SentMessage?
}