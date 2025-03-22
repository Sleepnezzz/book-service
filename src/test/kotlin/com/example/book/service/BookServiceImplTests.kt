package com.example.book.service

import com.example.book.model.Book
import com.example.book.model.request.BookRequest
import com.example.book.repository.BookRepository
import com.example.book.service.exception.PublishedDateInvalidException
import java.time.LocalDate
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable

@ExtendWith(MockitoExtension::class)
class BookServiceImplTests {

    @Mock
    private lateinit var bookRepository: BookRepository

    @InjectMocks
    private lateinit var subject: BookServiceImpl

    private lateinit var pageable: Pageable

    @BeforeEach
    fun setUp() {
        pageable = Mockito.mock(Pageable::class.java)
    }

    @Nested
    inner class SaveBookTest {
        @Test
        fun `should save book success`() {
            val request = BookRequest("Title", "Author", LocalDate.of(2020, 1, 1))
            val book = Book(title = "Title", author = "Author", publishedDate = LocalDate.of(2020, 1, 1))
            `when`(bookRepository.save(Mockito.any())).thenReturn(book)

            val actual = subject.saveBook(request)

            assertEquals(book, actual)
        }

        @Test
        fun `should throw PublishedDateInvalidException when published date is invalid`() {
            val request = BookRequest("Title", "Author", LocalDate.of(999, 1, 1))

            assertThrows(PublishedDateInvalidException::class.java) {
                subject.saveBook(request)
            }

            val request2 = BookRequest("Title", "Author", LocalDate.now().plusYears(1))

            assertThrows(PublishedDateInvalidException::class.java) {
                subject.saveBook(request2)
            }
        }
    }

    @Nested
    inner class FindByAuthorTest {
        @Test
        fun `should return when get Books By Author`() {
            val author = "Author"
            val books = listOf(
                Book(title = "Title1", author = author, publishedDate = LocalDate.of(2020, 1, 1)),
                Book(title = "Title2", author = author, publishedDate = LocalDate.of(2021, 1, 1))
            )
            val page: Page<Book> = PageImpl(books, pageable, books.size.toLong())
            `when`(bookRepository.findByAuthor(author, pageable)).thenReturn(page)

            val actual = subject.getBooksByAuthor(author, pageable)

            assertEquals(books, actual.content)
            assertEquals(books.size.toLong(), actual.totalElements)
            assertEquals(1, actual.totalPages)
            assertEquals(0, actual.currentPage)
            assertEquals(books.size, actual.pageSize)
        }
    }
}