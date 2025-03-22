package com.example.book.service

import com.example.book.model.Book
import com.example.book.model.request.BookRequest
import com.example.book.model.response.BasePageResponse
import com.example.book.repository.BookRepository
import com.example.book.service.exception.PublishedDateInvalidException
import java.time.LocalDate
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookServiceImpl(private val bookRepository: BookRepository) : BookService {
    override fun saveBook(request: BookRequest): Book {
        val currentYear = LocalDate.now().year
        if (request.publishedDate.year !in 1000..currentYear) {
            throw PublishedDateInvalidException("Published date must be between 1000 and current year")
        }

        val book = Book(title = request.title, author = request.author, publishedDate = request.publishedDate)
        return bookRepository.save(book)
    }

    override fun getBooksByAuthor(author: String, pageable: Pageable): BasePageResponse<Book> {
        val page = bookRepository.findByAuthor(author, pageable)
        return BasePageResponse(
            content = page.content,
            totalElements = page.totalElements,
            totalPages = page.totalPages,
            currentPage = page.number,
            pageSize = page.size
        )
    }
}