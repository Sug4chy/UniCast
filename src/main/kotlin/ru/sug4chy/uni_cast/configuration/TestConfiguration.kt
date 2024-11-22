package ru.sug4chy.uni_cast.configuration

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import ru.sug4chy.uni_cast.configuration.properties.telegram.TelegramBotProperties
import ru.sug4chy.uni_cast.telegram.implementation.TelegramBotImpl
import ru.sug4chy.uni_cast.telegram.update_handling.UpdateHandlingStrategy
import ru.sug4chy.uni_cast.telegram.update_handling.implementation.strategies.TestStrategy

@Configuration
@Profile("test")
class TestConfiguration {

    @Bean
    fun telegramBotImpl(
        telegramBotProperties: TelegramBotProperties
    ): TelegramBotImpl =
        object : TelegramBotImpl(telegramBotProperties) {

            @PostConstruct
            override fun init() {

            }
        }

    @Bean
    internal fun testStrategy(telegramBotProperties: TelegramBotProperties): UpdateHandlingStrategy =
        TestStrategy(telegramBotImpl(telegramBotProperties))
}