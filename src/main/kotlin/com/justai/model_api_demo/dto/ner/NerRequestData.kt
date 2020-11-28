package com.justai.model_api_demo.dto.ner

data class NerRequestData(
        val language: String,
        val texts: List<String>
)