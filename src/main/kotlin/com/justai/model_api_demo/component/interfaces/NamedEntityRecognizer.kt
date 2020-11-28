package com.justai.model_api_demo.component.interfaces

import com.justai.model_api_demo.dto.ner.NerMarkupItems
import com.justai.model_api_demo.dto.ner.NerRequestData
import com.justai.model_api_demo.dto.ner.NerResponseData

interface NamedEntityRecognizer {
    fun nerTexts(texts: List<String>, language: String): List<NerMarkupItems>
    fun fulfillNerRequest(request: NerRequestData): NerResponseData
}