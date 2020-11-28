package com.justai.model_api_demo.util.converters

import com.google.cloud.dialogflow.v2beta1.QueryResult
import com.google.protobuf.Value
import com.justai.model_api_demo.dto.ner.NerItemData
import com.justai.model_api_demo.dto.ner.NerMarkupItems

fun QueryResult.toNerMarkupItems(): NerMarkupItems {
    /*Extracting parameters from query itself and zipping them with corresponding
    _name_.original parameters from the first outputContext*/
    val mapValueOrig = this.parameters.fieldsMap.map { (k, v) ->
        val newVal = this.outputContextsList[0].parameters.fieldsMap["$k.original"]
                ?.getAsStrings()?.let {
                    v.getAsStrings()?.zip(it)
                }
        k to newVal
    }.toMap().filter { (_, v) -> !v.isNullOrEmpty() }
    /*Finding all the matches of "original value" substring in queryText and extracting their ranges
    to further map them to list of NerItemData*/
    val items = mapValueOrig.flatMap { (k, v) ->
        v!!.distinct().flatMap { pair ->
            val matches = pair.second.toRegex().findAll(this.queryText)
            matches.map {
                NerItemData(
                        startPos = it.range.first,
                        endPos = it.range.last + 1,
                        entity = k,
                        text = pair.second,
                        value = pair.first
                )
            }.toList()
        }
    }
    return NerMarkupItems(
            text = this.queryText,
            entities = items
    )
}

fun Value.getAsStrings(): List<String>? {
    return when (this.kindCase) {
        Value.KindCase.LIST_VALUE -> this.listValue.valuesList.mapNotNull { it.getAsStrings() }.flatMap { it.toList() }
        Value.KindCase.NUMBER_VALUE -> listOf(this.numberValue.toString())
        Value.KindCase.BOOL_VALUE -> listOf(this.boolValue.toString())
        Value.KindCase.STRING_VALUE -> if (this.stringValue.isBlank()) null else listOf(this.stringValue)
        else -> null
    }
}




