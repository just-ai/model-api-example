package com.justai.model_api_demo.dto.prediction

import com.google.gson.annotations.SerializedName

data class PredictedClassData(
        @SerializedName("class")
        val clazz: String,
        val score: Double
)