package com.example.book

import com.example.book.model.response.BaseErrorResponse
import com.example.book.service.exception.PublishedDateInvalidException
import jakarta.servlet.http.HttpServletRequest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val logger: Logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(PublishedDateInvalidException::class)
    fun handlePublishedDateInvalidException(
        ex: PublishedDateInvalidException,
        request: HttpServletRequest
    ): ResponseEntity<BaseErrorResponse> {
        logger.warn("PublishedDateInvalidException: ${ex.message}")

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            BaseErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = "PublishedDateInvalidException",
                message = ex.message,
                path = request.requestURI
            )
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
        ex: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ResponseEntity<BaseErrorResponse> {
        val fieldErrors = ex.bindingResult.fieldErrors.map { "${it.field}: ${it.defaultMessage}" }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            BaseErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = "Validation Failed",
                message = "Validation error occurred",
                path = request.requestURI,
                errors = fieldErrors
            )
        )
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(
        ex: BindException,
        request: HttpServletRequest
    ): ResponseEntity<BaseErrorResponse> {
        val fieldErrors = ex.bindingResult.fieldErrors.map { "${it.field}: ${it.defaultMessage}" }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            BaseErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = "Binding Failed",
                message = "Incorrect data format",
                path = request.requestURI,
                errors = fieldErrors
            )
        )
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingParam(
        ex: MissingServletRequestParameterException,
        request: HttpServletRequest
    ): ResponseEntity<BaseErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            BaseErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = "Missing Parameter",
                message = "Required parameter '${ex.parameterName}' is missing",
                path = request.requestURI
            )
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleJsonParseException(
        ex: HttpMessageNotReadableException,
        request: HttpServletRequest
    ): ResponseEntity<BaseErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            BaseErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = "Malformed JSON",
                message = "Invalid request body format",
                path = request.requestURI
            )
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneralException(
        ex: Exception,
        request: HttpServletRequest
    ): ResponseEntity<BaseErrorResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            BaseErrorResponse(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                error = "Internal Server Error",
                message = ex.message ?: "An unexpected error occurred",
                path = request.requestURI
            )
        )
    }
}