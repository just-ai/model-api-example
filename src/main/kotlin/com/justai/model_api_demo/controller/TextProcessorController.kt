package com.justai.model_api_demo.controller

import com.justai.model_api_demo.service.TextProcessorService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TextProcessorController(private val textProcessorService: TextProcessorService) {
    @PostMapping("/process-text")
    fun processText(@RequestBody text:String):String {
        return textProcessorService.preprocessText(text)
    }
}