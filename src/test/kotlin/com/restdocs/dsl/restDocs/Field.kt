package com.restdocs.dsl.restDocs

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.snippet.Attributes.key

class Field(
    val descriptor: FieldDescriptor
) {
    val isIgnored: Boolean = descriptor.isIgnored
    val isOptional: Boolean = descriptor.isOptional

    var default: String
        get() = descriptor.attributes.getOrDefault("", "") as String
        set(value) {
            descriptor.attributes(key("default").value(value))
        }

    var format: String
        get() = descriptor.attributes.getOrDefault("", "") as String
        set(value) {
            descriptor.attributes(key("format").value(value))
        }

    var sample: String
        get() = descriptor.attributes.getOrDefault("", "") as String
        set(value) {
            descriptor.attributes(key("sample").value(value))
        }

    infix fun means(value: String): Field = Field(descriptor.description(value))

    infix fun Field.attributes(block: Field.() -> Unit): Field {
        block()
        return this
    }
}