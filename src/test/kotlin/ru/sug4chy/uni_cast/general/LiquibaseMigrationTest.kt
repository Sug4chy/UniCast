package ru.sug4chy.uni_cast.general

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import kotlin.test.assertEquals

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LiquibaseMigrationTest {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Test
    fun `migrations work`() {
        // arrange

        // act
        val tableExists = entityManager.createNativeQuery(
            "SELECT COUNT(*)\n" +
               "FROM information_schema.tables\n" +
               "WHERE table_name = 'example_table'",
        ).singleResult as Long

        // assert
        assertEquals(0, tableExists)
    }
}