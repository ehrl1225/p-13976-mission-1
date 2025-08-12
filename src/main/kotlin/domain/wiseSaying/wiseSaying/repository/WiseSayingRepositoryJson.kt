package com.domain.wiseSaying.wiseSaying.repository

import com.domain.wiseSaying.wiseSaying.entity.WiseSaying

class WiseSayingRepositoryJson {

    fun wiseSayingToJson(wiseSaying: WiseSaying):String{
        return """{
    "id": ${wiseSaying.id},
    "content": "${wiseSaying.content}",
    "author": "${wiseSaying.author}"
}""".trimIndent()
    }

    fun wiseSayingsToJson(wiseSayings:List<WiseSaying>):String{
        val stringBuilder = StringBuilder()
        stringBuilder.append("[\n")
        for (i in wiseSayings.indices) {
            val json = wiseSayingToJson(wiseSayings[i])
            for (line in json.lines()) {
                stringBuilder.append("    $line")
                if (i < wiseSayings.size - 1 && line == json.lines().last()) {
                    stringBuilder.append(",\n")
                } else {
                    stringBuilder.append("\n")
                }
            }
        }
        stringBuilder.append("]")
        return stringBuilder.toString()
    }

    fun jsonToWiseSaying(json: String): WiseSaying{
        var id:Long? = null
        var content:String? = null
        var author:String? = null
        val parts = json.substringAfter("{").substringBefore("}").split(",")
        for (part in parts) {
            val keyValue = part.split(":")
            val key = keyValue[0].trim().removeSurrounding("\"")
            val value = keyValue[1].trim().removeSurrounding("\"")
            when (key) {
                "id" -> id = value.toLong()
                "content" -> content = value
                "author" -> author = value
                else -> throw IllegalArgumentException("Unknown key: $key")
            }
        }
        if (id == null || content == null || author == null) {
            throw IllegalArgumentException("Invalid JSON format")
        }
        return WiseSaying(content, author, id)
    }
}