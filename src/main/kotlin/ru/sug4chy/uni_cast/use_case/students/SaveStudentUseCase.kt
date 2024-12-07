package ru.sug4chy.uni_cast.use_case.students

interface SaveStudentUseCase {
    operator fun invoke(studentName: String, groupName: String)
}