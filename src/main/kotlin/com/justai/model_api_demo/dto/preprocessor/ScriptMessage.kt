package com.justai.model_api_demo.dto.preprocessor

data class ScriptMessage(
        var doc: ScriptDoc,
        var tokens: List<ScriptToken>
)