package com.domain.wiseSaying.wiseSaying.repository

import com.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.global.Ut.page.Page
import com.global.Ut.page.Pageable
import com.global.cotext.ContextManager

class WiseSayingRepositoryMemory: WiseSayingRepository {
    private var wiseSayingNumber:Long = 0L
    private val wiseSayings: MutableList<WiseSaying> = mutableListOf()
    private val wiseSayingRepositoryFile by lazy{
        ContextManager.get<WiseSayingRepositoryFile>("wiseSayingRepositoryFile")!!
    }

    init{
//         Load existing wise sayings from file
        val loadedWiseSayings = wiseSayingRepositoryFile.loadAll()
        if (loadedWiseSayings.isNotEmpty()) {
            wiseSayings.addAll(loadedWiseSayings)
            // Set the last used ID based on the loaded data
            wiseSayingNumber = loadedWiseSayings.maxOfOrNull { it.id } ?: 0L
        } else {
            // If no data is loaded, reset the ID counter
            wiseSayingNumber = 0L
        }
        wiseSayingNumber = wiseSayingRepositoryFile.loadLastId()

    }

    override fun save(wiseSaying: WiseSaying): WiseSaying{
        wiseSaying.id = ++wiseSayingNumber
        wiseSayings.add(wiseSaying)
        wiseSayingRepositoryFile.save(wiseSaying)
        return wiseSaying
    }

    override fun findAll(pageable: Pageable): Page<WiseSaying> {
        return Page.of(wiseSayings, pageable)
    }

    override fun deleteById(id: Long): Boolean {
        val iterator = wiseSayings.iterator()
        while (iterator.hasNext()) {
            val wiseSaying = iterator.next()
            if (wiseSaying.id == id) {
                iterator.remove()
                return true
            }
        }
        return false
    }

    override fun reset() {
        wiseSayingNumber = 0L
        wiseSayings.clear()
        wiseSayingRepositoryFile.reset()
    }

    override fun findById(id: Long): WiseSaying? {
        return wiseSayings.find { it.id == id }
    }

    override fun finalizeDb() {
        wiseSayingRepositoryFile.saveIndex(wiseSayings)
        wiseSayingRepositoryFile.saveLastId(wiseSayingNumber)
    }

    override fun build() {
        wiseSayingRepositoryFile.build(wiseSayings)
    }

    override fun findByContent(content: String, pageable: Pageable): Page<WiseSaying> {
        return Page.of(wiseSayings.filter { it.content.contains(content, ignoreCase = true) }, pageable)

    }

    override fun findByAuthor(author: String, pageable: Pageable): Page<WiseSaying> {
        return Page.of(wiseSayings.filter { it.author.contains(author, ignoreCase = true) }, pageable)
    }

    override fun findByAuthorAndContent(
        author: String,
        content: String,
        pageable: Pageable
    ): Page<WiseSaying> {
        return Page.of(wiseSayings.filter {
            it.author.contains(author, ignoreCase = true) &&
            it.content.contains(content, ignoreCase = true)
        }, pageable)
    }
}