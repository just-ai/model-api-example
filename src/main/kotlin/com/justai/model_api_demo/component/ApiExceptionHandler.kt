package com.justai.model_api_demo.component

import com.google.gson.JsonSyntaxException
import com.justai.model_api_demo.dto.errors.ApiError
import com.justai.model_api_demo.dto.errors.ApiError.ErrorMessage
import com.justai.model_api_demo.dto.errors.ErrorResponse
import com.justai.model_api_demo.util.exceptions.UnsupportedLanguageException
import com.justai.model_api_demo.util.exceptions.WrongClassifierException
import com.justai.model_api_demo.util.exceptions.WrongPreprocessingEngineException
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.util.*


@ControllerAdvice
class ApiExceptionHandler {
    @Value(value = "#{\${exception.message.malformedBody}}")
    private lateinit var malformedBodyMessage: Map<String, String>

    @ExceptionHandler(value = [UnsupportedLanguageException::class, WrongPreprocessingEngineException::class])
    fun badRequestException(ex: Exception, request: WebRequest?): ResponseEntity<ErrorResponse>? {
        return ResponseEntity(exceptionToResponse(ex), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [JsonSyntaxException::class, IllegalArgumentException::class])
    fun malformedBodyException(ex: Exception, request: WebRequest?): ResponseEntity<ErrorResponse>? {
        return ResponseEntity(exceptionToResponse(ex, malformedBodyMessage), HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [WrongClassifierException::class])
    fun notFoundException(ex: Exception, request: WebRequest?): ResponseEntity<ErrorResponse>? {
        return ResponseEntity(exceptionToResponse(ex), HttpStatus.NOT_FOUND)
    }

    private fun exceptionToResponse(ex: Exception, message: Map<String, String> = defaultMessage(ex)): ErrorResponse {
        val apiError = ApiError(
                code = ex.javaClass.simpleName,
                message = ErrorMessage(message)
        )
        return ErrorResponse(apiError)
    }

    private fun defaultMessage(ex: Exception): Map<String, String> {
        return mapOf(Locale.getDefault().language to ex.localizedMessage!!)
    }
}