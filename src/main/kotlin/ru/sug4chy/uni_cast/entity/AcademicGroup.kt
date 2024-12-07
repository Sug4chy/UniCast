package ru.sug4chy.uni_cast.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import ru.sug4chy.uni_cast.entity.base.EntityBase
import java.util.*

@Entity
@Table(name = "academic_group")
class AcademicGroup private constructor(
    id: UUID? = null,

    @Column(nullable = false, unique = true)
    val name: String,

    @OneToMany(mappedBy = "group")
    val students: Set<Student> = emptySet(),
) : EntityBase(id) {

    companion object {

        fun createNew(name: String): AcademicGroup =
            AcademicGroup(
                name = name
            )
    }
}