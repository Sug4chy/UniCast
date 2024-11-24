package ru.sug4chy.uni_cast.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.telegram.telegrambots.meta.api.objects.Update
import ru.sug4chy.uni_cast.telegram.update_handling.UpdateHandler

@RestController
@RequestMapping("/updates")
class UpdatesController(
    private val updatesHandler: UpdateHandler
) {

    @PostMapping
    fun handleUpdate(@RequestBody update: Update) {
        updatesHandler.handle(update)
    }
}