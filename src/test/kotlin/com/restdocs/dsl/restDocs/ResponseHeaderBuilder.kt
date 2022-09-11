package com.restdocs.dsl.restDocs

import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders
import org.springframework.restdocs.headers.ResponseHeadersSnippet

class ResponseHeaderBuilder {
    private val fields = mutableListOf<HeaderDescriptor>()

    infix fun String.means(
        description: Any
    ) {
        fields.add(headerWithName(this).description(description))
    }

    fun build() = fields
}

fun responseHeader(lists: ResponseHeaderBuilder.() -> Unit): ResponseHeadersSnippet =
    responseHeaders(
        ResponseHeaderBuilder().apply(lists).build()
    )
