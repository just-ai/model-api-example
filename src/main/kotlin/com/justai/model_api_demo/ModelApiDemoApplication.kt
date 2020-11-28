package com.justai.model_api_demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@ConfigurationPropertiesScan()
@PropertySource("classpath:preprocessor.properties", "classpath:dialogflow.properties", "classpath:exceptions.properties")
class ModelApiDemoApplication

fun main(args: Array<String>) {
    runApplication<ModelApiDemoApplication>(*args)
}
