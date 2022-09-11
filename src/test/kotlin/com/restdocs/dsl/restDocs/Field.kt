package com.restdocs.dsl.restDocs

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.snippet.Attributes.key

class Field(
    val descriptor: FieldDescriptor
) {
    init {
        descriptor.attributes(key("constraints").value(""))
        descriptor.attributes(key("format").value(""))
    }

    infix fun means(value: String): Field = Field(descriptor.description(value))

    infix fun isOptional(value: Boolean): Field {
        if (value) descriptor.optional()
        return this
    }

    infix fun addConstraint(value: String): Field {
        descriptor.attributes(key("constraints").value(value))
        return this
    }

    infix fun addFormat(value: String): Field {
        descriptor.attributes(key("format").value(value))
        return this
    }
}