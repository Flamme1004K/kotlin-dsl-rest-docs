package com.restdocs.dsl.restDocs

import org.springframework.restdocs.payload.FieldDescriptor

class Field(
    val descriptor: FieldDescriptor
) {
    val isIgnored: Boolean = descriptor.isIgnored
    val isOptional: Boolean = descriptor.isOptional

    infix fun means(value: String): Field = Field(descriptor.description(value))

    infix fun Field.attributes(block: Field.() -> Unit): Field {
        block()
        return this
    }
}