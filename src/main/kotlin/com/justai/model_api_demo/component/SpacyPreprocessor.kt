package com.justai.model_api_demo.component

import com.google.gson.Gson
import com.justai.model_api_demo.component.interfaces.Preprocessor
import com.justai.model_api_demo.config.PreprocessorConfig
import com.justai.model_api_demo.dto.preprocessor.MarkupData
import com.justai.model_api_demo.dto.preprocessor.ScriptMessage
import com.justai.model_api_demo.dto.preprocessor.TextPreprocessorRequestData
import com.justai.model_api_demo.dto.preprocessor.TextPreprocessorResponseData
import com.justai.model_api_demo.util.converters.toMarkupData
import com.justai.model_api_demo.util.exceptions.UnsupportedLanguageException
import com.justai.model_api_demo.util.readInputStream
import org.springframework.stereotype.Component
import java.io.BufferedWriter
import java.io.OutputStreamWriter

@Component
class SpacyPreprocessor(val preprocessorConfig: PreprocessorConfig, val gson: Gson) : Preprocessor {
    override fun preprocessTexts(texts: List<String>, modelName: String, markPatterns: Boolean, fixSpelling: Boolean): List<MarkupData> {
        val processedTexts = preprocessTextsWithScript(texts,
                preprocessorConfig.spacyPath, modelName, markPatterns, fixSpelling)
        val messages = processedTexts.map { it -> gson.fromJson(it, ScriptMessage::class.java) }
        return messages.map { it.toMarkupData() }
    }

    override fun fulfillPreprocessRequest(request: TextPreprocessorRequestData): TextPreprocessorResponseData {
        val modelName = preprocessorConfig.modelByLanguage[request.language]
        if (modelName.isNullOrEmpty()) throw UnsupportedLanguageException(request.language)
        val markups = preprocessTexts(request.documents, modelName, request.markPatterns, request.fixSpells)
        return TextPreprocessorResponseData(markups)
    }

    private fun preprocessTextsWithScript(texts: List<String>, scriptPath: String, modelName: String, checkPatterns: Boolean, checkSpelling: Boolean): List<String> {
        val processBuilder = ProcessBuilder("python", scriptPath, modelName, checkPatterns.toString(), checkSpelling.toString())
        processBuilder.redirectErrorStream(true)
        val process = processBuilder.start()
        val out = BufferedWriter(OutputStreamWriter(process.outputStream))
        texts.forEach {
            out.write(it)
            out.newLine()
        }
        out.newLine()
        out.flush()
        val result = readInputStream(process.inputStream)
        return result
    }
}