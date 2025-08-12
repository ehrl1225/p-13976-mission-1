package com.global.Ut.text

import com.global.config.RunMode
import com.global.cotext.ContextManager

class WriteText {
    val runMode by lazy{
        ContextManager.get<RunMode>("runMode")!!
    }
    val wroteTexts: MutableList<String> = mutableListOf()

    fun write(text:String){
        when (runMode){
            RunMode.DEV -> {
                writeDevText(text)
            }
            RunMode.PROD -> {
                writeProdText(text)
            }
            RunMode.TEST -> {
                writeTestText(text)
            }
        }
    }

    fun writeln(text: String="") {
        write("$text\n")
    }

    fun writeDevText(text: String) {
        print(text)
    }

    fun writeProdText(text: String) {
        // 프로덕션 환경에서는 파일에 쓰는 로직을 구현할 수 있습니다.
        // 예시로, 파일에 쓰는 로직을 작성합니다.
        // 실제 파일 경로와 쓰기 로직은 환경에 맞게 수정해야 합니다.
    }

    fun writeTestText(text: String) {
        print(text)
        if (text.endsWith("\n"))
            wroteTexts.add(text.trimEnd()) // 줄바꿈 문자를 제거하고 저장
        else
            wroteTexts.add(text)
    }

    fun writeFile(path:String, text: String){
        if (runMode == RunMode.TEST) {
            writeln(path)
            for (line in text.lines()) {
                writeln(line)
            }
        }
    }
}