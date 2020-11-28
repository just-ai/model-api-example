package com.justai.model_api_demo.service

import com.google.gson.Gson
import com.justai.model_api_demo.component.PreprocessorFactory
import com.justai.model_api_demo.dto.preprocessor.TextPreprocessorRequestData
import org.springframework.stereotype.Service

@Service

class TextProcessorService(val gson: Gson, val preprocessorFactory: PreprocessorFactory) {
    fun preprocessText(text: String): String {
        val requestData: TextPreprocessorRequestData = gson.fromJson(text, TextPreprocessorRequestData::class.java)
        val preprocessor = preprocessorFactory.getInstance(requestData.engine)
        return gson.toJson(
                preprocessor.fulfillPreprocessRequest(requestData)
        )
    }
}