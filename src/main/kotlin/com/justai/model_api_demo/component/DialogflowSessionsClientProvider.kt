package com.justai.model_api_demo.component

import com.google.api.gax.core.FixedCredentialsProvider
import com.google.auth.oauth2.GoogleCredentials
import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.dialogflow.v2beta1.SessionsClient
import com.google.cloud.dialogflow.v2beta1.SessionsSettings
import com.justai.model_api_demo.config.DialogflowConfig
import com.justai.model_api_demo.util.exceptions.WrongClassifierException
import com.justai.model_api_demo.util.exceptions.WrongCredentialsDirectoryException
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileInputStream


@Component
class DialogflowSessionsClientProvider(dialogflowConfig: DialogflowConfig) {
    final val credentials = File(dialogflowConfig.credentialsDirectory).listFiles()?.toList()?.map {
        val cred = GoogleCredentials.fromStream(
                FileInputStream(it)
        )
        (cred as ServiceAccountCredentials).projectId to cred
    }?.toMap()

    init {
        if (credentials.isNullOrEmpty()) throw WrongCredentialsDirectoryException()
    }

    fun getSessionsClient(projectId: String): SessionsClient {
        val settingsBuilder = SessionsSettings.newBuilder()
        val creds = credentials?.get(projectId) ?: throw WrongClassifierException(projectId)
        val sessionsSettings = settingsBuilder.setCredentialsProvider(FixedCredentialsProvider.create(creds)).build()
        return SessionsClient.create(sessionsSettings)
    }
}