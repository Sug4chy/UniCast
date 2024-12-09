package ru.sug4chy.uni_cast.use_case.messages

import ru.sug4chy.uni_cast.dto.request.messages.IndividualRequest

interface IndividualUseCase {
    operator fun invoke(request: IndividualRequest)
}