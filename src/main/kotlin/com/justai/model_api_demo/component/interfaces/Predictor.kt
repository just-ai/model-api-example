package com.justai.model_api_demo.component.interfaces

import com.justai.model_api_demo.dto.prediction.PredictedClassData
import com.justai.model_api_demo.dto.prediction.PredictionRequestData
import com.justai.model_api_demo.dto.prediction.PredictionResponseData

interface Predictor {
    fun predictIntents(texts: List<String>, classifier: String, modelName: String): Map<String, List<PredictedClassData>>
    fun fulfillPredictionRequest(request: PredictionRequestData, classifier: String, modelName: String): PredictionResponseData
}