package com.justai.model_api_demo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "dialogflow")
data class DialogflowConfig(
        val nerProjectId: String,
        val credentialsDirectory: String
)