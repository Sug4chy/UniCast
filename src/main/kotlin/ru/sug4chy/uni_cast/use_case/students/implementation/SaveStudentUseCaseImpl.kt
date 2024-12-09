package ru.sug4chy.uni_cast.use_case.students.implementation

import ru.sug4chy.uni_cast.annotation.UseCase
import ru.sug4chy.uni_cast.entity.AcademicGroup
import ru.sug4chy.uni_cast.entity.Student
import ru.sug4chy.uni_cast.exception.StudentAlreadyExistsException
import ru.sug4chy.uni_cast.repository.AcademicGroupRepository
import ru.sug4chy.uni_cast.repository.StudentRepository
import ru.sug4chy.uni_cast.use_case.students.SaveStudentUseCase

@UseCase
class SaveStudentUseCaseImpl(
    private val academicGroupRepository: AcademicGroupRepository,
    private val studentRepository: StudentRepository
) : SaveStudentUseCase {

    override fun invoke(studentName: String, groupName: String) {
        val group = academicGroupRepository.findByName(groupName) ?: run {
            AcademicGroup.createNew(groupName)
                .also { academicGroupRepository.save(it) }
        }

        if (studentRepository.existsByFullName(studentName)) {
            throw StudentAlreadyExistsException()
        }

        val student = Student.createNew(studentName, group)
        studentRepository.save(student)
    }
}