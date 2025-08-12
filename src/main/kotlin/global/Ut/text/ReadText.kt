package com.global.Ut.text

import com.global.config.RunMode
import com.global.cotext.ContextManager

class ReadText {
    val runMode by lazy{
        ContextManager.get<RunMode>("runMode")!!
    }

    val testTexts: MutableList<String> = mutableListOf()
    var testIndex: Int = 0

    fun read():String?{
        when (runMode) {
            RunMode.DEV -> {
                return readDevText()
            }
            RunMode.PROD -> {
                return readProdText()
            }
            RunMode.TEST -> {
                return readTestText()
            }
        }
    }

    fun readDevText():String?{
        return readLine()
    }

    fun readProdText():String?{
        // 프로덕션 환경에서는 파일에서 읽어오는 로직을 구현할 수 있습니다.
        // 예시로, 파일에서 읽어오는 로직을 작성합니다.
        // 실제 파일 경로와 읽기 로직은 환경에 맞게 수정해야 합니다.
        return null // 파일 읽기 로직을 구현해야 합니다.
    }

    fun readTestText():String?{

        if (testIndex < testTexts.size) {
            val testText = testTexts[testIndex++]
            println(testText)
            return testText
        } else {
            return null // 더 이상 읽을 텍스트가 없으면 null 반환
        }
    }


}