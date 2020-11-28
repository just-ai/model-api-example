package com.justai.model_api_demo.service

import com.google.gson.Gson
import com.justai.model_api_demo.component.interfaces.NamedEntityRecognizer
import com.justai.model_api_demo.dto.ner.NerRequestData
import org.springframework.stereotype.Service

@Service
class NerService(val gson: Gson, val namedEntityRecognizer: NamedEntityRecognizer) {
    fun recognizeEntities(requestText: String): String {
        val request = gson.fromJson(requestText, NerRequestData::class.java)
        return gson.toJson(
                namedEntityRecognizer.fulfillNerRequest(request = request)
        )
    }
}