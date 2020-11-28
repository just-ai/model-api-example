package com.justai.model_api_demo.dto.errors

data class ErrorResponse(
        val error: ApiError,
        val errors: List<ApiError> = emptyList()
)