package ru.sug4chy.uni_cast.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.sug4chy.uni_cast.entity.TelegramChat
import java.util.*

@Repository
interface TelegramChatRepository : CrudRepository<TelegramChat, UUID> {
    fun findByExtId(extId: Long): TelegramChat?
    fun existsByExtId(extId: Long): Boolean
}