package ru.sug4chy.uni_cast.controller

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import ru.sug4chy.uni_cast.dto.request.messages.IndividualRequest
import ru.sug4chy.uni_cast.dto.request.messages.MulticastRequest
import ru.sug4chy.uni_cast.use_case.messages.IndividualUseCase
import ru.sug4chy.uni_cast.use_case.messages.MulticastUseCase

@RestController
@RequestMapping("/messages")
class MessagesController(
    private val multicastUseCase: MulticastUseCase,
    private val individualUseCase: IndividualUseCase
) {

    @PostMapping("/multicast")
    fun multicast(@RequestBody request: MulticastRequest) {
        multicastUseCase(request)
    }

    @PostMapping("/individual")
    fun individual(
        @Valid @RequestBody request: IndividualRequest,
    ) {
        individualUseCase(request)
    }
}