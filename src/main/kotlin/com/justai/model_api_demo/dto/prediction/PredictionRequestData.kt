package com.justai.model_api_demo.dto.prediction

class PredictionRequestData(texts: List<String>) {
    val data = texts.map { Data(it) }

    data class Data(
            val text: String
    )
}