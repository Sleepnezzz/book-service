package com.example.book.cucumber.steps.book

import com.fasterxml.jackson.databind.ObjectMapper
import io.cucumber.datatable.DataTable
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.Assert
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

class BookCreationWithTableSteps {

    @LocalServerPort
    private var port: Int = 8080

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private lateinit var response: ResponseEntity<String>

    private var invalidBookDetails: Map<String, String?>? = null

    @Given("I have the following invalid book details:")
    fun givenInvalidBookDetails(dataTable: DataTable) {
        val rows = dataTable.asMaps(String::class.java, String::class.java)
        invalidBookDetails = rows[0]
        println("[givenInvalidBookDetails] Invalid book details: $invalidBookDetails")
    }

    @When("I send a POST request to {string} with the invalid book details")
    fun whenISendPOSTRequest(url: String) {
        val book = invalidBookDetails?.let { invalidDetails ->
            invalidDetails.mapValues { (_, value) ->
                when (value) {
                    null -> ""
                    else -> value
                }
            }
        } ?: mapOf()

        response = sendPostRequest(url, book)
    }

    private fun sendPostRequest(url: String, requestBody: Map<String, Any>): ResponseEntity<String> {
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON

        val jsonRequestBody = objectMapper.writeValueAsString(requestBody)

        val entity = HttpEntity(jsonRequestBody, headers)

        return restTemplate.postForEntity("http://localhost:$port$url", entity, String::class.java)
    }

    @Then("the response status code should be {int}")
    fun thenTheResponseStatusShouldBe(expectedStatus: Int) {
        Assert.assertEquals(expectedStatus, response.statusCode.value())
        println("Response status: ${response.statusCode.value()}")
    }

    @Then("the response should contain error messages")
    fun thenTheResponseShouldContainErrorMessages() {
        println(response.body)
        Assert.assertNotNull(response.body)
        Assert.assertTrue(response.body!!.contains("Invalid request body format"))
        println("Response body contains error messages.")
    }
}