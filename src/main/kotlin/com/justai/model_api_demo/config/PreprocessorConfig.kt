package com.justai.model_api_demo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "preprocessor")
data class PreprocessorConfig (
        var spacyPath: String,
        var modelByLanguage: Map<String, String>
)