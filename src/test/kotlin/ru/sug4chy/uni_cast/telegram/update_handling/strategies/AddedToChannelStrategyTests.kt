package ru.sug4chy.uni_cast.telegram.update_handling.strategies

import io.mockk.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.telegram.telegrambots.meta.api.objects.Chat
import org.telegram.telegrambots.meta.api.objects.ChatMemberUpdated
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.User
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberAdministrator
import org.telegram.telegrambots.meta.api.objects.chatmember.ChatMemberBanned
import ru.sug4chy.uni_cast.configuration.properties.telegram.TelegramBotProperties
import ru.sug4chy.uni_cast.entity.TelegramChat
import ru.sug4chy.uni_cast.repository.TelegramChatRepository
import ru.sug4chy.uni_cast.telegram.update_handling.implementation.strategies.AddedToChannelStrategy
import java.util.stream.Stream

@SpringBootTest
@ActiveProfiles("test")
class AddedToChannelStrategyTests {

    @Autowired
    private lateinit var telegramBotProperties: TelegramBotProperties
    private lateinit var addedToChannelStrategy: AddedToChannelStrategy
    private lateinit var repoMock: TelegramChatRepository

    @BeforeEach
    fun setUp() {
        repoMock = mockk<TelegramChatRepository>()
        addedToChannelStrategy = AddedToChannelStrategy(repoMock, telegramBotProperties)
    }

    @ParameterizedTest
    @MethodSource("provideCombinationsForCanHandleTests")
    fun `can't handle when update is invalid`(
        update: Update,
        expected: Boolean
    ) {
        // arrange

        // act
        val result = addedToChannelStrategy.canHandle(update)

        // assert
        assertEquals(expected, result)
    }

    @Test
    fun handle() {
        // arrange
        val update = Update().apply {
            myChatMember = ChatMemberUpdated().apply {
                chat = Chat().apply {
                    id = 1
                    title = "Test title"
                }
            }
        }
        every { repoMock.save(any()) } returns TelegramChat.fromMyChatMemberUpdate(update)

        // act
        addedToChannelStrategy.handle(update)

        // assert
        verify { repoMock.save(any()) }
    }

    companion object {

        @JvmStatic
        fun provideCombinationsForCanHandleTests(): Stream<Arguments> =
            Stream.of(
                Arguments.of(Update().apply { myChatMember = null }, false),
                Arguments.of(Update().apply {
                    myChatMember = ChatMemberUpdated().apply {
                        chat = Chat().apply { type = "not channel" }
                    }
                }, false),
                Arguments.of(Update().apply {
                    myChatMember = ChatMemberUpdated().apply {
                        chat = Chat().apply {
                            type = "channel"
                        }
                        newChatMember = ChatMemberBanned()
                    }
                }, false),
                Arguments.of(Update().apply {
                    myChatMember = ChatMemberUpdated().apply {
                        chat = Chat().apply {
                            type = "channel"
                        }
                        newChatMember = ChatMemberAdministrator().apply {
                            user = User().apply {
                                userName = "not bot's name"
                            }
                        }
                    }
                }, false),
                Arguments.of(Update().apply {
                    myChatMember = ChatMemberUpdated().apply {
                        chat = Chat().apply {
                            type = "channel"
                        }
                        newChatMember = ChatMemberAdministrator().apply {
                            user = User().apply {
                                userName = "test-bot-username"
                            }
                        }
                    }
                }, true)
            )
    }
}