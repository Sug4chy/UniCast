package ru.sug4chy.uni_cast.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.CONFLICT)
class StudentAlreadyExistsException : Exception()