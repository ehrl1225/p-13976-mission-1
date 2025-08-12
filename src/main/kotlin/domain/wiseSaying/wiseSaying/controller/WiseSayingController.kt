package com.domain.wiseSaying.wiseSaying.controller

import com.domain.command.command.entity.Command
import com.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.domain.wiseSaying.wiseSaying.service.WiseSayingService
import com.global.Ut.page.Page
import com.global.Ut.page.Pageable
import com.global.Ut.text.ReadText
import com.global.Ut.text.WriteText
import com.global.cotext.ContextManager

class WiseSayingController {
    val wiseSayingService by lazy{
        ContextManager.get<WiseSayingService>("wiseSayingService")!!
    }
    val readText by lazy { ContextManager.get<ReadText>("readText")!! }
    val writeText by lazy{ ContextManager.get<WriteText>("writeText")!! }

    fun createWiseSaying(command: Command){
        val content = command.parameter["content"] as String
        val author = command.parameter["author"] as String
        val wiseSaying = wiseSayingService.createWiseSaying(content, author)
        writeText.writeln("${wiseSaying.id}번 명언이 등록되었습니다.")
    }

    fun showWiseSaying(wiseSayings: Page<WiseSaying>){
        writeText.writeln("번호 / 작가 / 명언")
        writeText.writeln("----------------------")
        for (wiseSaying in wiseSayings.content.reversed()) {
            writeText.writeln("${wiseSaying.id} / ${wiseSaying.author} / ${wiseSaying.content}")
        }
        writeText.writeln("----------------------")
        writeText.write("페이지 : ")
        for (i in 1..wiseSayings.totalPages) {
            if (i == wiseSayings.pageNumber) {
                writeText.write("[$i] ")
            } else {
                writeText.write("$i ")
            }
            if (i < wiseSayings.totalPages) {
                writeText.write("/ ")
            }
        }
        writeText.writeln()
    }


    fun getWiseSayings(command: Command){
        val page = command.parameter["page"] as Int? ?: 1
        val size = command.parameter["size"] as Int? ?: 5
        val pageable = Pageable(page, size)
        if (!command.parameter.containsKey("keyword")){
            val wiseSayings = wiseSayingService.getWiseSayings(pageable)
            showWiseSaying(wiseSayings)
            return
        }
        if (command.parameter.containsKey("keywordType")){
            val keywordType = command.parameter["keywordType"] as String
            val keyword = command.parameter["keyword"] as String
            val wiseSayings = wiseSayingService.search(keywordType, keyword, pageable)
            if (wiseSayings.content.isEmpty()) {
                writeText.writeln("검색 결과가 없습니다.")
            } else {
                writeText.writeln("----------------------")
                writeText.writeln("검색타입 : ${keywordType}")
                writeText.writeln("검색어 : ${keyword}")
                writeText.writeln("----------------------")
                showWiseSaying(wiseSayings)
            }
        } else {
            writeText.writeln("검색어를 입력해주세요.")
        }
    }

    fun deleteWiseSaying(command: Command){
        val id = command.parameter["id"] as Int
        if (wiseSayingService.deleteWiseSaying(id.toLong())){
            writeText.writeln("${id}번 명언이 삭제되었습니다.")
        }else{
            writeText.writeln("${id}번 명언은 존재하지 않습니다.")
        }
    }

    fun modifyWiseSaying(command: Command){
        val id = command.parameter["id"] as Int
        val wiseSaying = wiseSayingService.findById(id.toLong())
        if (wiseSaying == null) {
            writeText.writeln("${id}번 명언은 존재하지 않습니다.")
            return
        }
        writeText.writeln("명언(기존) : ${wiseSaying.content}")
        writeText.write("명언 : ")
        val content = readText.read()
        if (content == null) return
        writeText.writeln("작가(기존) : ${wiseSaying.author}")
        writeText.write("작가 : ")
        val author = readText.read()
        if (author == null) return
        wiseSaying.content = content
        wiseSaying.author = author
    }

    fun finalizeDb(){
        wiseSayingService.finalizeDb()
    }

    fun build(){
        wiseSayingService.build()
        writeText.writeln("data.json 파일의 내용이 갱신되었습니다.")
    }
}