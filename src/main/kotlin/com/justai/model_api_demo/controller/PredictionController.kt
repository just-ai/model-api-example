package com.justai.model_api_demo.controller

import com.justai.model_api_demo.service.PredictionService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PredictionController(private val predictionService: PredictionService) {
    @PostMapping("/classifiers/{classifier}/predictnbest/{modelId}")
    fun processText(@RequestBody text: String, @PathVariable("classifier") classifier: String, @PathVariable("modelId") modelName: String): String {
        return predictionService.predictIntents(text, classifier, modelName)
    }
}