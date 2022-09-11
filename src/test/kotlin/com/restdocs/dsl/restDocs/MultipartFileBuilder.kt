package com.restdocs.dsl.restDocs

import org.springframework.restdocs.request.RequestDocumentation.partWithName
import org.springframework.restdocs.request.RequestDocumentation.requestParts
import org.springframework.restdocs.request.RequestPartDescriptor
import org.springframework.restdocs.request.RequestPartsSnippet

class MultipartFileBuilder {
    private val fields = mutableListOf<RequestPartDescriptor>()

    infix fun String.means(
        description: Any
    ) {
        fields.add(partWithName(this).description(description))
    }

    fun build() = fields
}

fun multipartFile(lists: MultipartFileBuilder.() -> Unit): RequestPartsSnippet =
    requestParts(
        MultipartFileBuilder().apply(lists).build()
    )