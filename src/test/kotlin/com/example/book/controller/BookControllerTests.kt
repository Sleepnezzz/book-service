package com.example.book.controller

import com.example.book.model.Book
import com.example.book.model.request.BookRequest
import com.example.book.model.response.BasePageResponse
import com.example.book.service.BookService
import java.time.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

@ExtendWith(MockitoExtension::class)
class BookControllerTests {

    @Mock
    private lateinit var bookService: BookService

    @InjectMocks
    private lateinit var subject: BookController

    @Test
    fun `getBooksByAuthor should return books`() {
        val page = 0
        val size = 10
        val author = "Author Name"
        val books = listOf(
            Book(title = "Title1", author = author, publishedDate = LocalDate.of(2020, 1, 1)),
            Book(title = "Title2", author = author, publishedDate = LocalDate.of(2021, 1, 1))
        )

        val pageResponse = BasePageResponse(
            content = books,
            totalElements = 1,
            totalPages = 1,
            currentPage = page,
            pageSize = size
        )

        `when`(bookService.getBooksByAuthor(author, PageRequest.of(page, size))).thenReturn(pageResponse)

        val actual: ResponseEntity<BasePageResponse<Book>> = subject.getBooksByAuthor(author, page, size)

        assertEquals(HttpStatus.OK, actual.statusCode)
        assertEquals(books, actual.body?.content)
        verify(bookService, times(1)).getBooksByAuthor(author, PageRequest.of(page, size))
    }

    @Test
    fun `saveBook should return created book`() {
        val request =
            BookRequest(title = "Book 1", author = "Author Name", publishedDate = LocalDate.parse("2023-01-01"))
        val savedBook =
            Book(id = 1, title = "Book 1", author = "Author Name", publishedDate = LocalDate.parse("2023-01-01"))
        `when`(bookService.saveBook(request)).thenReturn(savedBook)

        val actual: ResponseEntity<Book> = subject.saveBook(request)

        assertEquals(HttpStatus.CREATED, actual.statusCode)
        assertEquals(savedBook, actual.body)
        verify(bookService, times(1)).saveBook(request)
    }
}