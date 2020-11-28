package com.justai.model_api_demo.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("model.api.auth")
data class AuthCredentialsConfigProperties(
        val login: String,
        val password: String
)
