package com.justai.model_api_demo.component.interfaces

import com.justai.model_api_demo.dto.preprocessor.MarkupData
import com.justai.model_api_demo.dto.preprocessor.TextPreprocessorRequestData
import com.justai.model_api_demo.dto.preprocessor.TextPreprocessorResponseData

interface Preprocessor {
    fun preprocessTexts(texts: List<String>, modelName: String, markPatterns: Boolean, fixSpelling: Boolean): List<MarkupData>
    fun fulfillPreprocessRequest(request: TextPreprocessorRequestData): TextPreprocessorResponseData
}