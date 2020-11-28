package com.justai.model_api_demo.util.converters

import com.google.cloud.dialogflow.v2beta1.QueryResult
import com.justai.model_api_demo.dto.prediction.PredictedClassData

fun QueryResult.toPredictedClassData(): PredictedClassData {
    return PredictedClassData(
            clazz = this.intent.displayName,
            score = this.intentDetectionConfidence.toDouble()
    )
}

fun QueryResult.knowledgeToPredictedClassDataList(): List<PredictedClassData> {
    return this.knowledgeAnswers.answersList.map {
        PredictedClassData(
                clazz = it.answer,
                score = it.matchConfidence.toDouble()
        )
    }.distinctBy { it.clazz }
}