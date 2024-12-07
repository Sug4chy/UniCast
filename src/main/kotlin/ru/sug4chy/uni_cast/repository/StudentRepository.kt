package ru.sug4chy.uni_cast.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.sug4chy.uni_cast.entity.Student
import java.util.*

@Repository
interface StudentRepository : CrudRepository<Student, UUID> {
    fun existsByFullName(fullName: String): Boolean
}