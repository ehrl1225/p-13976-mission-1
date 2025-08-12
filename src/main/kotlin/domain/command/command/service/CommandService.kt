package com.domain.command.command.service

import com.domain.command.command.entity.Command

class CommandService {

    fun isDigitsOnly(s: String): Boolean {
        return s.all { it.isDigit() }
    }


    fun analyzeCommand(command: String): Command{
        val commandParts = command.split("?")
        val mainCommand = commandParts[0]
        val parameters = commandParts[1].split("&")
        val parameterMap = mutableMapOf<String, Object>()
        for (parameter in parameters) {
            val keyValue = parameter.split("=")
            if (keyValue.size == 2) {
                val value = keyValue[1]
                if (isDigitsOnly(value)) {
                    parameterMap[keyValue[0]] = value.toInt() as Object
                }else{
                    parameterMap[keyValue[0]] = keyValue[1] as Object
                }
            }
        }
        return Command(
            command = mainCommand,
            parameter = parameterMap
        )
    }
}