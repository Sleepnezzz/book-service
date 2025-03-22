package com.example.book.service

import com.example.book.model.Book
import com.example.book.model.request.BookRequest
import com.example.book.model.response.BasePageResponse
import org.springframework.data.domain.Pageable

interface BookService {
    fun saveBook(request: BookRequest): Book
    fun getBooksByAuthor(author: String, pageable: Pageable): BasePageResponse<Book>
}