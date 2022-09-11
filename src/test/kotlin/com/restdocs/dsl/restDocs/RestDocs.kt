package com.restdocs.dsl.restDocs

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.generate.RestDocumentationGenerator
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest
import org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.restdocs.snippet.Snippet
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.ResultActionsDsl
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
@AutoConfigureCache
open class RestDocs {

    val mapper: ObjectMapper = ObjectMapper()

    lateinit var mockMvc: MockMvc

    @BeforeEach
    internal fun setUp(
        webApplicationContext: WebApplicationContext,
        restDocumentationContextProvider: RestDocumentationContextProvider
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .alwaysDo<DefaultMockMvcBuilder>(MockMvcResultHandlers.print())
            .apply<DefaultMockMvcBuilder>(
                documentationConfiguration(
                    restDocumentationContextProvider
                ).operationPreprocessors()
                    .withRequestDefaults(prettyPrint())
                    .withResponseDefaults(prettyPrint())
            ).build()
    }

    fun given() = mockMvc
}

fun MockMvc.get(
    urlTemplate: String,
    vararg vars: Any?
): ResultActionsDsl = this.get(urlTemplate, vars) {
    requestAttr(
        RestDocumentationGenerator.ATTRIBUTE_NAME_URL_TEMPLATE,
        urlTemplate
    )
}

fun ResultActions.makeDocument(
    identifier: String,
    result: ResultMatcher,
    vararg snippets: Snippet
): ResultActions = andExpect(result)
    .andDo(
        MockMvcRestDocumentation.document(
            identifier,
            getDocumentRequest(),
            getDocumentResponse(),
            *snippets
        )
    )

fun getDocumentRequest(): OperationRequestPreprocessor = preprocessRequest(prettyPrint())

fun getDocumentResponse(): OperationResponsePreprocessor = preprocessResponse(prettyPrint())
