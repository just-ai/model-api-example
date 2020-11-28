package com.justai.model_api_demo.util

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

fun readInputStream(inputStream: InputStream): List<String> {
    val reader = BufferedReader(InputStreamReader(inputStream, StandardCharsets.UTF_8))
    val linesList = mutableListOf<String>()
    var line: String?
    while (reader.readLine().also { line = it } != null) {
        linesList.add(line!!)
    }
    return linesList
}

