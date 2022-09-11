package com.restdocs.dsl.restDocs

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.payload.RequestFieldsSnippet
import org.springframework.restdocs.payload.ResponseFieldsSnippet

class BodyBuilder {
    private val fields = mutableListOf<FieldDescriptor>()

    infix fun String.means(
        description: String
    ): Field {
        val fieldWithPath = PayloadDocumentation.fieldWithPath(this)
        val field = Field(fieldWithPath).means(description)
        fields.add(field.descriptor)
        return field
    }

    fun build() = fields
}

fun responseBody(lists: BodyBuilder.() -> Unit): ResponseFieldsSnippet =
    responseFields(
        BodyBuilder().apply(lists).build()
    )

fun requestBody(lists: BodyBuilder.() -> Unit): RequestFieldsSnippet =
    requestFields(
        BodyBuilder().apply(lists).build()
    )
