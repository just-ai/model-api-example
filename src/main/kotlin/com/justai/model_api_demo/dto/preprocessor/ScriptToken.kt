package com.justai.model_api_demo.dto.preprocessor

data class ScriptToken(
        var text:String,
        var idx:Int,
        var lemma:String,
        var lower:String,
        var pos:String,
        var isPunct:Boolean,
        var isPattern:Boolean,
        var corrected: String
)