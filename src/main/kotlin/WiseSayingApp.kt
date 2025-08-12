package com

import com.domain.command.command.controller.CommandController
import com.domain.command.command.entity.Command
import com.global.Ut.text.ReadText
import com.global.Ut.text.WriteText
import com.global.config.RunMode
import com.global.cotext.ContextManager
import com.global.request.Request

class WiseSayingApp(
    private val runMode: RunMode = RunMode.DEV
) {
    init{
        ContextManager.set("runMode", runMode)
    }

    private val commandController by lazy{
        ContextManager.get<CommandController>("commandController")!!
    }
    private val request by lazy{
        ContextManager.get<Request>("request")!!
    }

    private val readText by lazy{
        ContextManager.get<ReadText>("readText")!!
    }

    private val writeText by lazy{
        ContextManager.get<WriteText>("writeText")!!
    }

    fun run(){
        writeText.writeln("== 명언 앱 ==")
        while (true){
            val command = getCommand()

            if (command == null)
                continue
            request.work(command)
            if (command.command == "종료"){
                break
            }
        }
    }

    fun getCommand(): Command?{
        writeText.write("명령) ")
        val input = readText.read()
        if (input == null)
            return null
        if (input.isBlank()) {
            writeText.writeln("명령어를 입력해주세요.")
            return null;
        }
        val command = commandController.getCommand(input)
        return command;
    }
}