package ru.sug4chy.uni_cast.configuration

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import ru.sug4chy.uni_cast.configuration.properties.telegram.TelegramApiProperties
import ru.sug4chy.uni_cast.configuration.properties.telegram.TelegramBotProperties

@Configuration
@EnableConfigurationProperties(TelegramBotProperties::class, TelegramApiProperties::class)
class TelegramConfiguration