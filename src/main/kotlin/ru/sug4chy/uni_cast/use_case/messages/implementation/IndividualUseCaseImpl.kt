package ru.sug4chy.uni_cast.use_case.messages.implementation

import ru.sug4chy.uni_cast.annotation.UseCase
import ru.sug4chy.uni_cast.dto.request.messages.IndividualRequest
import ru.sug4chy.uni_cast.repository.StudentRepository
import ru.sug4chy.uni_cast.service.TelegramService
import ru.sug4chy.uni_cast.use_case.messages.IndividualUseCase

@UseCase
class IndividualUseCaseImpl(
    private val studentRepository: StudentRepository,
    private val telegramService: TelegramService
) : IndividualUseCase {

    override fun invoke(request: IndividualRequest) {
        for (studentDto in request.students) {
            val student = studentRepository.findByFullNameIfTelegramChatExists(studentDto.fullName)
                ?: continue

            telegramService.sendAndSaveMessage(
                chat = student.telegramChat ?: continue,
                messageText = request.message,
                from = request.from,
                withReactions = false
            )
        }
    }
}