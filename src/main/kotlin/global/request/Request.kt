package com.global.request

import com.domain.command.command.entity.Command
import com.domain.wiseSaying.wiseSaying.controller.WiseSayingController
import com.global.cotext.ContextManager

class Request {
    private val wiseSayingController by lazy{
        ContextManager.get< WiseSayingController>("wiseSayingController")!!
    }

    fun work(command: Command):Boolean{
        when (command.command){
            "등록"->{
                wiseSayingController.createWiseSaying(command)
                return true
            }
            "목록"->{
                wiseSayingController.getWiseSayings(command)
                return true
            }
            "삭제"->{
                wiseSayingController.deleteWiseSaying(command)
                return true
            }
            "수정"->{
                wiseSayingController.modifyWiseSaying(command)
                return true
            }
            "종료"->{
                wiseSayingController.finalizeDb()
                return false
            }
            "빌드"->{
                wiseSayingController.build()
                return true
            }
            else->return true
        }
    }
}