package com.restdocs.dsl.restDocs

import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestDocumentation.requestParameters
import org.springframework.restdocs.request.RequestParametersSnippet

class RequestParamBuilder {
    private val fields = mutableListOf<ParameterDescriptor>()

    infix fun String.means(
        description: Any
    ) {
        fields.add(RequestDocumentation.parameterWithName(this).description(description))
    }

    fun build() = fields
}

fun requestParam(lists: RequestParamBuilder.() -> Unit): RequestParametersSnippet =
    requestParameters(
        RequestParamBuilder().apply(lists).build()
    )
