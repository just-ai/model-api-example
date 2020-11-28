package com.justai.model_api_demo.dto.preprocessor

data class WordMarkupData(
        val annotations: Annotations?,
        val startPos: Int,
        val endPos: Int,
        val pattern: Boolean? = null,
        val punctuation: Boolean? = null,
        val source: String,
        val word: String
) {
    data class Annotations(
            val lemma: String?,
            val pos: String?
    )
}