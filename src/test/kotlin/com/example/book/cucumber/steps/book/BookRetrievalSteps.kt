package com.example.book.cucumber.steps.book

import com.example.book.model.Book
import com.example.book.repository.BookRepository
import io.cucumber.java.Before
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import java.time.LocalDate
import org.junit.jupiter.api.Assertions.assertTrue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.ResponseEntity

class BookRetrievalSteps {
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

    @Given("books exist in the database for author {string}")
    fun givenBooksExistForAuthor(author: String) {
        val book = Book(title = "Sample Book", author = author, publishedDate = LocalDate.now())
        bookRepository.save(book)
        println("[givenBooksExistForAuthor] Book saved for author: $author")
    }

    @When("I send a GET request to {string}")
    fun whenISendGETRequest(url: String) {
        response = sendGetRequest(url)
    }

    @Then("the response should contain books for {string}")
    fun thenTheResponseShouldContainBooksFor(author: String) {
        assertTrue(response.body?.contains(author) == true)
        println("Response body contains books for: $author")
    }

    private fun sendGetRequest(url: String): ResponseEntity<String> {
        return restTemplate.getForEntity("http://localhost:$port$url", String::class.java)
    }
}
