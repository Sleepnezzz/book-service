package com.example.book.model.request

import com.fasterxml.jackson.annotation.JsonFormat
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import java.time.LocalDate

@Schema(description = "Request payload for create book")
data class BookRequest(
    @Schema(description = "book title", example = "title")
    @field:NotBlank
    @field:Size(max = 255)
    val title: String,
    @Schema(description = "book author", example = "author")
    @field:NotBlank
    @field:Size(max = 255)
    val author: String,
    @Schema(description = "book publishedDate", example = "2025-03-20")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "en_US", timezone = "GMT+7")
    val publishedDate: LocalDate
)