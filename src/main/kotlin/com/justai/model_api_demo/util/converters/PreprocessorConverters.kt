package com.justai.model_api_demo.util.converters

import com.justai.model_api_demo.dto.preprocessor.MarkupData
import com.justai.model_api_demo.dto.preprocessor.ScriptMessage
import com.justai.model_api_demo.dto.preprocessor.ScriptToken
import com.justai.model_api_demo.dto.preprocessor.WordMarkupData

fun ScriptMessage.toMarkupData(): MarkupData {
    return MarkupData(
            source = this.doc.text,
            correctedText = this.doc.corrected,
            words = this.tokens.map { it.toWordMarkupData() }
    )
}

fun ScriptToken.toWordMarkupData(): WordMarkupData {
    return WordMarkupData(
            annotations = WordMarkupData.Annotations(
                    this.lemma,
                    this.pos
            ),
            startPos = this.idx,
            endPos = this.idx + this.text.length,
            pattern = this.isPattern,
            punctuation = this.isPunct,
            source = this.text,
            word = this.corrected
    )
}