package ru.sug4chy.uni_cast.entity.base

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.io.Serializable
import java.util.*

@MappedSuperclass
abstract class EntityBase(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null
) : Serializable {

    fun isNew(): Boolean =
        id == null
}