package com.justai.model_api_demo.dto.ner

data class NerMarkupItems(
        val text: String,
        val entities: List<NerItemData>
)