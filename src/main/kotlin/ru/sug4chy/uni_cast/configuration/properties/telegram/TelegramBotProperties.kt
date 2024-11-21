package ru.sug4chy.uni_cast.configuration.properties.telegram

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding

@ConfigurationProperties(prefix = "telegram.bot")
data class TelegramBotProperties @ConstructorBinding constructor(
    val token: String,
    val webhookUrl: String,
    val botUsername: String
)