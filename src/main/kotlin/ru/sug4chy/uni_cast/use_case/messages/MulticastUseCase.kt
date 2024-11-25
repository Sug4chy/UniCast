package ru.sug4chy.uni_cast.use_case.messages

import ru.sug4chy.uni_cast.dto.request.messages.MulticastRequest

interface MulticastUseCase {
    operator fun invoke(request: MulticastRequest)
}