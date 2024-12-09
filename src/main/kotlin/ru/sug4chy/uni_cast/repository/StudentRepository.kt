package ru.sug4chy.uni_cast.repository

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.sug4chy.uni_cast.entity.Student
import java.util.*

@Repository
interface StudentRepository : CrudRepository<Student, UUID> {
    fun existsByFullName(fullName: String): Boolean

    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.telegramChat t WHERE s.fullName = :fullName AND t IS NOT NULL")
    fun findByFullNameIfTelegramChatExists(
        @Param("fullName") fullName: String
    ): Student?
}