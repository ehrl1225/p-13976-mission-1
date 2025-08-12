package com.global.request

import com.domain.command.command.entity.Command
import com.domain.wiseSaying.wiseSaying.controller.WiseSayingController
import com.global.cotext.ContextManager

class Request {
    private val wiseSayingController by lazy{
        ContextManager.get< WiseSayingController>("wiseSayingController")!!
    }

    fun work(command: Command){
        when (command.command){
            "등록"->{
                wiseSayingController.createWiseSaying(command)
            }
            "목록"->{
                wiseSayingController.getWiseSayings(command)
            }
            "삭제"->{
                wiseSayingController.deleteWiseSaying(command)
            }
            "수정"->{
                wiseSayingController.modifyWiseSaying(command)
            }
            "종료"->{
                wiseSayingController.finalizeDb()
            }
            "빌드"->{
                wiseSayingController.build()
            }
        }
    }
}