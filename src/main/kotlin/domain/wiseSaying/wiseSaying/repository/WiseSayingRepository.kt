package com.domain.wiseSaying.wiseSaying.repository

import com.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.global.Ut.page.Page
import com.global.Ut.page.Pageable

interface WiseSayingRepository {

    fun save(wiseSaying: WiseSaying): WiseSaying

    fun findAll(pageable: Pageable): Page<WiseSaying>

    fun deleteById(id: Long): Boolean

    fun reset()

    fun findById(id: Long): WiseSaying?

    fun finalizeDb()

    fun build()

    fun findByContent(content: String, pageable: Pageable): Page<WiseSaying>

    fun findByAuthor(author: String, pageable: Pageable): Page<WiseSaying>

    fun findByAuthorAndContent(author: String, content: String, pageable: Pageable): Page<WiseSaying>
}