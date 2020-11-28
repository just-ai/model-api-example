package com.justai.model_api_demo.dto.ner

data class NerItemData(
        val startPos: Int,
        val endPos: Int,
        val entity: String,
        val text: String,
        val value: String
)