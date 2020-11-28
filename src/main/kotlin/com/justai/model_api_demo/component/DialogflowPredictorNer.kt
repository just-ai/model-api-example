package com.justai.model_api_demo.component

import com.google.cloud.dialogflow.v2beta1.*
import com.justai.model_api_demo.component.interfaces.NamedEntityRecognizer
import com.justai.model_api_demo.component.interfaces.Predictor
import com.justai.model_api_demo.config.DialogflowConfig
import com.justai.model_api_demo.dto.ner.NerMarkupItems
import com.justai.model_api_demo.dto.ner.NerRequestData
import com.justai.model_api_demo.dto.ner.NerResponseData
import com.justai.model_api_demo.dto.prediction.PredictedClassData
import com.justai.model_api_demo.dto.prediction.PredictionRequestData
import com.justai.model_api_demo.dto.prediction.PredictionResponseData
import com.justai.model_api_demo.util.converters.knowledgeToPredictedClassDataList
import com.justai.model_api_demo.util.converters.toNerMarkupItems
import com.justai.model_api_demo.util.converters.toPredictedClassData
import com.justai.model_api_demo.util.exceptions.WrongClassifierException
import com.justai.model_api_demo.util.exceptions.WrongNerProjectIdException
import org.springframework.stereotype.Component
import java.util.*

@Component
class DialogflowPredictorNer
(private val dialogflowSessionsClientProvider: DialogflowSessionsClientProvider,
 private val dialogflowConfig: DialogflowConfig) : Predictor, NamedEntityRecognizer {
    override fun predictIntents(texts: List<String>, classifier: String, modelName: String): Map<String, List<PredictedClassData>> {
        /*Dialogflow requires to supply language code at all times, but uses it only for audio
        recognition; Thus, it's safe to hardcode any language code here*/
        val detectedIntents = detectIntentTexts(
                projectId = classifier,
                texts = texts,
                languageCode = "en"
        )
        if (detectedIntents.isNullOrEmpty()) throw WrongClassifierException(classifier)
        return detectedIntents.mapValues {
            if (it.value.knowledgeAnswers.answersCount == 0) {
                listOf(it.value.toPredictedClassData())
            } else {
                it.value.knowledgeToPredictedClassDataList()
            }
        }
    }

    override fun fulfillPredictionRequest(request: PredictionRequestData, classifier: String, modelName: String): PredictionResponseData {
        val texts = request.data.map { it.text }
        val predictions = predictIntents(texts, classifier, modelName)
        val listOfLists = predictions.map { it.value }
        return PredictionResponseData(listOfLists)
    }

    private fun detectIntentTexts(
            projectId: String, texts: List<String>, sessionId: String = UUID.randomUUID().toString(), languageCode: String? = null): Map<String, QueryResult>? {
        val queryResults: MutableMap<String, QueryResult> = linkedMapOf()
        val sessionsClient = dialogflowSessionsClientProvider.getSessionsClient(projectId)
        val session: SessionName = SessionName.of(projectId, sessionId)
        texts.forEach { text ->
            val textInput: TextInput.Builder = TextInput.newBuilder().setText(text)
            languageCode?.let { textInput.setLanguageCode(it) }
            val queryInput: QueryInput = QueryInput.newBuilder().setText(textInput).build()
            val response: DetectIntentResponse = sessionsClient.detectIntent(session, queryInput)
            val queryResult: QueryResult = response.queryResult
            queryResults[text] = queryResult
        }
        return queryResults
    }

    override fun nerTexts(texts: List<String>, language: String): List<NerMarkupItems> {
        val detectedIntents = detectIntentTexts(
                projectId = dialogflowConfig.nerProjectId,
                texts = texts,
                languageCode = language
        )
                ?: throw WrongNerProjectIdException(dialogflowConfig.nerProjectId)
        return detectedIntents.map { (_, v) -> v.toNerMarkupItems() }
    }

    override fun fulfillNerRequest(request: NerRequestData): NerResponseData {
        return NerResponseData(
                nerTexts(request.texts, request.language)
        )
    }
}