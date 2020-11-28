package com.justai.model_api_demo.controller

import com.justai.model_api_demo.service.NerService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class NerController(private val nerService: NerService) {
    @PostMapping("/ner")
    fun processText(@RequestBody text: String): String {
        return nerService.recognizeEntities(text)
    }
}