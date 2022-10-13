package com.letters.to.auth.domain

import com.fasterxml.jackson.databind.ObjectMapper
import javax.persistence.AttributeConverter

class RegisterInformationConverter(
    private val objectMapper: ObjectMapper
) : AttributeConverter<RegisterInformation, String> {
    override fun convertToDatabaseColumn(attribute: RegisterInformation?): String {
        return objectMapper.writeValueAsString(attribute)
    }

    override fun convertToEntityAttribute(dbData: String?): RegisterInformation {
        return objectMapper.readValue(dbData, RegisterInformation::class.java)
    }
}
