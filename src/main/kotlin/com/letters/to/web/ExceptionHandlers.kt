package com.letters.to.web

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlers {

    @ExceptionHandler(Exception::class)
    fun handle(exception: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.internalServerError().body(ErrorResponse("시스템에 문제가 발생하였습니다. 관리자에게 문의해주세요."))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handle(exception: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        val representError = exception.bindingResult.fieldError

        return ResponseEntity.badRequest()
            .body(ErrorResponse("${representError?.field} ${representError?.defaultMessage}"))
    }

    @ExceptionHandler(RuntimeException::class)
    fun handle(exception: RuntimeException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.badRequest().body(ErrorResponse(exception.message!!))
    }

    @ExceptionHandler(AuthorizationException::class)
    fun handle(exception: AuthorizationException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse(exception.message!!))
    }
}
