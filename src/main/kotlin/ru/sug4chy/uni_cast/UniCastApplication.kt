package ru.sug4chy.uni_cast

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [JacksonAutoConfiguration::class])
class UniCastApplication

fun main(args: Array<String>) {
    runApplication<UniCastApplication>(*args)
}