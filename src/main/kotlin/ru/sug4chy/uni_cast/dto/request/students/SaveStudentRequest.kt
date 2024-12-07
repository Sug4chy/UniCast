package ru.sug4chy.uni_cast.dto.request.students

import jakarta.validation.constraints.NotBlank

data class SaveStudentRequest(

    @NotBlank
    val fullName: String,

    @NotBlank
    val groupName: String
)
