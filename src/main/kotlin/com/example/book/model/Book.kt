package com.example.book.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "books")
data class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
    @Column(name = "title")
    val title: String = "",
    @Column(name = "author")
    val author: String = "",
    @Column(name = "published_date")
    val publishedDate: LocalDate = LocalDate.now()
)