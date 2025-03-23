package com.example.book.cucumber.steps.book

import com.example.book.model.Book
import com.example.book.model.response.BasePageResponse
import com.example.book.repository.BookRepository
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.ResponseEntity

class BookRetrievalNotFoundSteps {
    @LocalServerPort
    private var port: Int = 8080

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private lateinit var response: ResponseEntity<BasePageResponse<Book>>

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Given("books Not found in the database for author {string}")
    fun givenBooksNotFoundForAuthor(author: String) {
        println("[givenBooksNotFoundForAuthor] No books will be added for author: $author")
        bookRepository.deleteByAuthor(author)
    }

    @When("I attempt to fetch books from {string}")
    fun whenISendGETRequest(url: String) {
        response = sendGetRequest(url)
    }

    @Then("the response should return empty books")
    fun thenTheResponseShouldReturnEmptyBooks() {
        response.body?.content?.isEmpty()?.let { assertTrue(it) }
        println("Response body is empty or status is OK, indicating no books found.")
    }

    private fun sendGetRequest(url: String): ResponseEntity<BasePageResponse<Book>> {
        val responseType = object : ParameterizedTypeReference<BasePageResponse<Book>>() {}
        return restTemplate.exchange(
            "http://localhost:$port$url",
            org.springframework.http.HttpMethod.GET,
            null,
            responseType
        )
    }
}