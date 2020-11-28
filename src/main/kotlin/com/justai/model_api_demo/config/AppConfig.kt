package com.justai.model_api_demo.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration

class AppConfig {
    @Bean
    fun getGson(): Gson? {
        return GsonBuilder()
                .create()
    }
}