package ru.sug4chy.uni_cast.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.sug4chy.uni_cast.entity.AcademicGroup
import java.util.*

@Repository
interface AcademicGroupRepository : CrudRepository<AcademicGroup, UUID> {
    fun findByName(name: String): AcademicGroup?
}