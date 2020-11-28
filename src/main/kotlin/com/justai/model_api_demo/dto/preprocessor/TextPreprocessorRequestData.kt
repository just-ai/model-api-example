package com.justai.model_api_demo.dto.preprocessor

data class TextPreprocessorRequestData(
        val documents:List<String>,
        val language:String,
        val engine:String,
        val markPatterns:Boolean,
        val fixSpells:Boolean
)