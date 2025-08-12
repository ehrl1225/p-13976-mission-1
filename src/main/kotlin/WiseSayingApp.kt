package com

import com.domain.command.command.entity.Command
import com.domain.command.command.factory.CommandFactory
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

    private val commandFactory by lazy{
        ContextManager.get<CommandFactory>("commandFactory")!!
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
            if (!request.work(command))
                break

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
        val command = commandFactory.getCommand(input)
        return command;
    }
}