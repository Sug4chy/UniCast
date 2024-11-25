package ru.sug4chy.uni_cast.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.sug4chy.uni_cast.entity.ChannelChat
import java.util.*

@Repository
interface ChannelChatRepository : CrudRepository<ChannelChat, UUID>