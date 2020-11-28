package com.justai.model_api_demo.dto.prediction

data class PredictionResponseData(
        val result: List<List<PredictedClassData>>
)