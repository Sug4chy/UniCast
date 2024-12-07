package ru.sug4chy.uni_cast.controller

import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import ru.sug4chy.uni_cast.dto.request.students.SaveStudentRequest
import ru.sug4chy.uni_cast.use_case.students.SaveStudentUseCase

@RestController
@RequestMapping("/students")
class StudentsController(
    private val saveStudentUseCase: SaveStudentUseCase
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun saveStudent(
        @Valid @RequestBody request: SaveStudentRequest
    ) {
        saveStudentUseCase(
            studentName = request.fullName,
            groupName = request.groupName,
        )
    }
}