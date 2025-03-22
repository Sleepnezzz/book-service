package com.example.book.model.response

data class BasePageResponse<T>(
    val content: List<T>,
    val totalElements: Long,
    val totalPages: Int,
    val currentPage: Int,
    val pageSize: Int
)