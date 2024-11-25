package ru.sug4chy.uni_cast.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.sug4chy.uni_cast.entity.SentMessage
import java.util.*

@Repository
interface SentMessageRepository : CrudRepository<SentMessage, UUID>