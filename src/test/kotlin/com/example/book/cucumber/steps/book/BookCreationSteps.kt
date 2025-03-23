package com.example.book.cucumber.steps.book

import com.example.book.repository.BookRepository
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import java.time.LocalDate
import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

class BookCreationSteps {

    @LocalServerPort
    private var port: Int = 8080

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    private lateinit var response: ResponseEntity<String>

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Before
    fun cleanDatabase() {
        bookRepository.deleteAll()
        println("Database cleaned before scenario.")
    }

    @Given("a book with title {string}, author {string}, and a valid published date")
    fun givenABookWithDetails(title: String, author: String) {
        println("[givenABookWithDetails] Book title: $title, author: $author")
    }

    @When("I send a POST request to {string}")
    fun whenISendPOSTRequest(url: String) {
        val book = mapOf("title" to "The Hobbit", "author" to "J.R.R. Tolkien", "publishedDate" to LocalDate.now())
        response = sendPostRequest(url, book)
    }

    @Then("the response status should be {int}")
    fun thenTheResponseStatusShouldBe(expectedStatus: Int) {
        Assert.assertEquals(HttpStatus.valueOf(expectedStatus), response.statusCode)
        println("Response status: ${response.statusCode}")
    }

    @Then("the response should contain {string}")
    fun thenTheResponseShouldContain(expectedContent: String) {
        Assert.assertTrue(response.body?.contains(expectedContent) == true)
        println("Response body contains: $expectedContent")
    }

    private fun sendPostRequest(url: String, requestBody: Any): ResponseEntity<String> {
        return restTemplate.postForEntity("http://localhost:$port$url", requestBody, String::class.java)
    }
}
