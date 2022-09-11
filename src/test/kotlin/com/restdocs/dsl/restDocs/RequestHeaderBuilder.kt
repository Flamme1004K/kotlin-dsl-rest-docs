package com.restdocs.dsl.restDocs

import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.headers.RequestHeadersSnippet

class RequestHeaderBuilder {
    private val fields = mutableListOf<HeaderDescriptor>()

    infix fun String.means(
        description: Any
    ) {
        fields.add(headerWithName(this).description(description))
    }

    fun build() = fields
}

fun requestHeader(lists: RequestHeaderBuilder.() -> Unit): RequestHeadersSnippet =
    requestHeaders(
        RequestHeaderBuilder().apply(lists).build()
    )
