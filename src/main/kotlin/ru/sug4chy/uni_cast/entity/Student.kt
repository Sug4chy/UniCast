package ru.sug4chy.uni_cast.entity

import jakarta.persistence.*
import ru.sug4chy.uni_cast.entity.base.EntityBase
import java.util.*

@Entity
@Table(name = "student")
class Student private constructor(
    id: UUID? = null,

    @Column(name = "full_name", nullable = false, unique = true)
    val fullName: String,

    @JoinColumn(nullable = false, name = "academic_group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val group: AcademicGroup,

    @JoinColumn(nullable = true, name = "telegram_chat_id")
    @OneToOne(fetch = FetchType.LAZY)
    var telegramChat: TelegramChat? = null
) : EntityBase(id) {

    companion object {

        fun createNew(name: String, group: AcademicGroup): Student =
            Student(
                fullName = name,
                group = group
            )
    }
}