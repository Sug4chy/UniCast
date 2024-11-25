package ru.sug4chy.uni_cast.dto.request.messages

data class MulticastRequest(
    val text: String,
    val from: String
)