package com.example.book.repository

import com.example.book.model.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface BookRepository : JpaRepository<Book, Long> {
    fun findByAuthor(author: String, pageable: Pageable): Page<Book>

    @Modifying
    @Transactional
    @Query("DELETE FROM Book b WHERE b.author = :author")
    fun deleteByAuthor(author: String): Int
}