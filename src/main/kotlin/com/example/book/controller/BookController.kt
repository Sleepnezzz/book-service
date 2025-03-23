package com.example.book.controller

import com.example.book.model.request.BookRequest
import com.example.book.model.response.BasePageResponse
import com.example.book.service.BookService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/books")
@Validated
class BookController(private val bookService: BookService) {
    @GetMapping
    @Operation(
        summary = "Get books by author",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Successful operation"
            )
        ]
    )
    fun getBooksByAuthor(
        @RequestParam author: String,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<BasePageResponse<com.example.book.model.Book>> {
        return ResponseEntity.status(HttpStatus.OK)
            .body(bookService.getBooksByAuthor(author, PageRequest.of(page, size)))
    }

    @PostMapping
    @Operation(summary = "Save a book")
    @ApiResponse(responseCode = "201", description = "Book created")
    @ApiResponse(
        responseCode = "400",
        description = "Invalid published date",
        content = [Content(schema = Schema(implementation = String::class))]
    )
    @ApiResponse(
        responseCode = "500",
        description = "Internal server error"
    )
    fun saveBook(
        @Valid
        @RequestBody
        request: BookRequest
    ): ResponseEntity<com.example.book.model.Book> {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.saveBook(request))
    }
}