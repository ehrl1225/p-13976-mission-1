package com.domain.wiseSaying.wiseSaying.service

import com.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.domain.wiseSaying.wiseSaying.repository.WiseSayingRepository
import com.global.Ut.page.Page
import com.global.Ut.page.Pageable
import com.global.cotext.ContextManager

class WiseSayingService {
    val wiseSayingRepository by lazy{
        ContextManager.get<WiseSayingRepository>("wiseSayingRepository")!!
    }

    fun createWiseSaying(content: String, author: String): WiseSaying{
        var wiseSaying = WiseSaying(content, author)
        return wiseSayingRepository.save(wiseSaying)
    }

    fun getWiseSayings(pageable: Pageable): Page<WiseSaying> {
        return wiseSayingRepository.findAll(pageable)
    }

    fun deleteWiseSaying(id: Long):Boolean{
        return wiseSayingRepository.deleteById(id)
    }

    fun findById(id: Long): WiseSaying? {
        return wiseSayingRepository.findById(id)
    }

    fun finalizeDb(){
        wiseSayingRepository.finalizeDb()
    }

    fun build(){
        wiseSayingRepository.build()
    }

    fun search(keywordType:String, keyword:String, pageable: Pageable): Page<WiseSaying> {
        return when (keywordType){
            "author" -> wiseSayingRepository.findByAuthor(keyword, pageable)
            "content" -> wiseSayingRepository.findByContent(keyword, pageable)
            "authorAndContent" -> {
                val parts = keyword.split(",", limit = 2)
                if (parts.size == 2) {
                    wiseSayingRepository.findByAuthorAndContent(parts[0], parts[1], pageable)
                } else {
                    Page.empty()
                }
            }
            else -> Page.empty()
        }
    }
}