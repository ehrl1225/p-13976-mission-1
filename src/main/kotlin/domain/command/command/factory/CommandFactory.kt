package com.domain.command.command.factory

import com.domain.command.command.entity.Command
import com.domain.command.command.service.CommandService
import com.global.Ut.text.ReadText
import com.global.Ut.text.WriteText
import com.global.cotext.ContextManager

class CommandFactory {
    val readText by lazy { ContextManager.get<ReadText>("readText")!! }
    val writeText by lazy { ContextManager.get<WriteText>("writeText")!! }
    val commandService by lazy{ ContextManager.get<CommandService>("commandService")!! }


    fun getCommand(command: String): Command?{
        return when (command){
            "등록"->{
                writeText.write("명언 : ")
                val content = readText.read()
                writeText.write("작가 : ")
                val author = readText.read()
                val parameter = mapOf(
                    "content" to content as Object,
                    "author" to author as Object
                )
                Command(command, parameter)
            }
            else -> {
                if (command.contains("?")){
                    return commandService.analyzeCommand(command)
                }
                return Command(command)
            }
        }
    }
}