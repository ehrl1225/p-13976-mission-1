package com.global.cotext

import com.domain.command.command.controller.CommandController
import com.domain.command.command.service.CommandService
import com.domain.wiseSaying.wiseSaying.controller.WiseSayingController
import com.domain.wiseSaying.wiseSaying.repository.WiseSayingRepositoryFile
import com.domain.wiseSaying.wiseSaying.repository.WiseSayingRepositoryJson
import com.domain.wiseSaying.wiseSaying.repository.WiseSayingRepositoryMemory
import com.domain.wiseSaying.wiseSaying.service.WiseSayingService
import com.global.Ut.text.ReadText
import com.global.Ut.text.WriteText
import com.global.request.Request

class ContextManager{
    companion object{
        val context: MutableMap<String, Object> = mutableMapOf<String, Object>()

        init{
            context.put("readText", ReadText() as Object)
            context.put("writeText", WriteText() as Object)
            context.put("wiseSayingRepositoryJson", WiseSayingRepositoryJson() as Object)
            context.put("wiseSayingRepositoryFile", WiseSayingRepositoryFile() as Object)
            context.put("commandController" , CommandController() as Object)
            context.put("wiseSayingController" , WiseSayingController() as Object)
            context.put("request" , Request() as Object)
            context.put("wiseSayingService", WiseSayingService() as Object)
            context.put("wiseSayingRepository", WiseSayingRepositoryMemory() as Object)
            context.put("commandService", CommandService() as Object)
        }

        fun <T> get(key: String): T? {
            return context[key] as? T
        }

        fun <T> set(key: String, value: T){
            context[key] = value as Object
        }
    }
}