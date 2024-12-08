package ru.sug4chy.uni_cast.dto.request.messages

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import ru.sug4chy.uni_cast.dto.StudentDto

data class IndividualRequest(

    @NotEmpty
    val students: List<StudentDto>,

    @NotBlank
    val message: String,

    @NotBlank
    val from: String
)