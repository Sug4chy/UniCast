package ru.sug4chy.uni_cast.configuration.properties.telegram

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "telegram.api")
data class TelegramApiProperties @ConstructorBinding constructor(
    val url: String,
)