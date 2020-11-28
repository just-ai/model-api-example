package com.justai.model_api_demo.dto.errors

data class ApiError(
        val code: String,
        val message: ErrorMessage = ErrorMessage()
) {
    data class ErrorMessage(
            val additionalProperties: Map<String, String> = mapOf()
    )
}