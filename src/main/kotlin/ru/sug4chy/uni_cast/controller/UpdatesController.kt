package ru.sug4chy.uni_cast.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.telegram.telegrambots.meta.api.objects.Update

@RestController
@RequestMapping("/updates")
class UpdatesController {

    @PostMapping
    fun handleUpdate(@RequestBody update: Update) {
    }
}