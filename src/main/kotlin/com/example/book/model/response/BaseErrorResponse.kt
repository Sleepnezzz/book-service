package com.example.book.model.response

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class BaseErrorResponse(
    val status: Int,
    val error: String,
    val message: String?,
    val path: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val errors: List<String>? = null
)