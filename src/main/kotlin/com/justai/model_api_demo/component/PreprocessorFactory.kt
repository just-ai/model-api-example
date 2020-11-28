package com.justai.model_api_demo.component

import com.justai.model_api_demo.component.interfaces.Preprocessor
import com.justai.model_api_demo.util.exceptions.WrongPreprocessingEngineException
import org.springframework.stereotype.Component

@Component
class PreprocessorFactory(
        val spacyPreprocessor: SpacyPreprocessor
) {
    fun getInstance(name: String): Preprocessor {
        return when (name) {
            "spacy" -> spacyPreprocessor
            else -> throw WrongPreprocessingEngineException(name)
        }
    }
}
