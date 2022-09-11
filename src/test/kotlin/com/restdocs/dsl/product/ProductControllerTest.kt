package com.restdocs.dsl.product

import com.restdocs.dsl.restDocs.RestDocs
import com.restdocs.dsl.restDocs.makeDocument
import com.restdocs.dsl.restDocs.pathVariable
import com.restdocs.dsl.restDocs.responseBody
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ProductController::class)
class ProductControllerTests : RestDocs() {

    @Test
    fun `GET v1-product 200 ok`() {
        given().perform(
            RestDocumentationRequestBuilders.get(
                "/v1/products/{code}",
                2
            )
        ).makeDocument(
            "product/product-read",
            status().isOk,
            pathVariable {
                "code" means "상품 번호"
            },
            responseBody {
                "code" means "상품 번호" optional true constraints "a" format "0"
                "name" means "상품 이름"
                "price" means "상품 가격"
            }
        )
    }
}