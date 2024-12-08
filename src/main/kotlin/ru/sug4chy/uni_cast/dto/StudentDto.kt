package ru.sug4chy.uni_cast.dto

import jakarta.validation.constraints.NotBlank

data class StudentDto(

    @NotBlank
    val fullName: String,

    @NotBlank
    val groupName: String
)
