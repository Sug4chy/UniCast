package ru.sug4chy.uni_cast.hibernate

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

@Converter(autoApply = true)
class MapJsonbAttributeConverter : AttributeConverter<Map<*, *>, String> {

    private val objectMapper: ObjectMapper = ObjectMapper()

    override fun convertToDatabaseColumn(attribute: Map<*, *>): String =
        objectMapper.writeValueAsString(attribute)

    override fun convertToEntityAttribute(dbData: String?): Map<String, Any> =
        if (dbData != null) {
            objectMapper.readValue(dbData, Map::class.java) as? Map<String, Any>
                ?: emptyMap()
        } else {
            emptyMap()
        }
}