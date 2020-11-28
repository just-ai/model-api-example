package com.justai.model_api_demo.service

import com.google.gson.Gson
import com.justai.model_api_demo.component.interfaces.Predictor
import com.justai.model_api_demo.dto.prediction.PredictionRequestData
import org.springframework.stereotype.Service

@Service
class PredictionService(val gson: Gson, val predictor: Predictor) {
    fun predictIntents(requestText: String, classifier: String, modelName: String): String {
        val request = gson.fromJson(requestText, PredictionRequestData::class.java)
        return gson.toJson(
                predictor.fulfillPredictionRequest(request, classifier, modelName)
        )
    }
}