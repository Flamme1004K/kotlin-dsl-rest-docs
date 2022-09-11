package com.restdocs.dsl.restDocs

import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.PathParametersSnippet
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters

class PathVariableBuilder {
    private val fields = mutableListOf<ParameterDescriptor>()

    infix fun String.means(
        description: Any
    ) {
        fields.add(parameterWithName(this).description(description))
    }

    fun build() = fields
}

fun pathVariable(lists: PathVariableBuilder.() -> Unit): PathParametersSnippet =
    pathParameters(
        PathVariableBuilder().apply(lists).build()
    )