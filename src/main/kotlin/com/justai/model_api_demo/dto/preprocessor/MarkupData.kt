package com.justai.model_api_demo.dto.preprocessor

data class MarkupData(
        val source:String,
        val correctedText:String?,
        val words:List<WordMarkupData>
)