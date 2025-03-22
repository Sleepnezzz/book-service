package com.example.book.repository

import com.example.book.model.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    fun findByAuthor(author: String, pageable: Pageable): Page<Book>
}