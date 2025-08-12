package com.domain.wiseSaying.wiseSaying.repository

import com.domain.wiseSaying.wiseSaying.entity.WiseSaying
import com.global.cotext.ContextManager
import java.io.File

class WiseSayingRepositoryFile {
    val wiseSayingRepositoryJson by lazy{ ContextManager.get<WiseSayingRepositoryJson>("wiseSayingRepositoryJson")!! }
    val writeText by lazy{ ContextManager.get<com.global.Ut.text.WriteText>("writeText")!! }
    private val wiseSayingPath = "db/wiseSaying"
    private val indexPath = "$wiseSayingPath/index.txt"
    private val lastIdPath = "$wiseSayingPath/lastId.txt"
    private val dataPath = "$wiseSayingPath/data.json"

    fun save(wiseSaying: WiseSaying){
        val id = wiseSaying.id
        val path = "$wiseSayingPath/$id.json"
        val file = File(path)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs() // Create the directory if it doesn't exist
        }
        val text = wiseSayingRepositoryJson.wiseSayingToJson(wiseSaying)
        file.writeText(text)
        writeText.writeFile(path, text)
    }

    fun load(id: Long): WiseSaying?{
        val file = File("$wiseSayingPath/$id.json")
        if (!file.exists()) return null
        val content = file.readText()
        return wiseSayingRepositoryJson.jsonToWiseSaying(content)
    }

    fun loadAll():List<WiseSaying>{
        val indexFile = File(indexPath)
        if (!indexFile.exists()) {
            return emptyList() // Return an empty list if the index file does not exist
        }
        val ids = indexFile.readLines().map { it.toLong() }
        val wiseSayings = mutableListOf<WiseSaying>()
        for (id in ids) {
            load(id)?.let { wiseSayings.add(it) }
        }
        return wiseSayings
    }

    fun saveLastId(lastId: Long) {
        // Save the last ID to a file or database
        val file = File(lastIdPath)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs() // Create the directory if it doesn't exist
        }
        val text = "$lastId"
        file.writeText(text)
        writeText.writeFile(lastIdPath, text)
    }

    fun loadLastId(): Long {
        val file = File(lastIdPath)
        if (!file.exists()) return 0L
        val content = file.readText()
        return content.toLongOrNull() ?: 0L
    }

    fun saveIndex(wiseSayings:List<WiseSaying>) {
        val indexFile = File(indexPath)
        if (!indexFile.parentFile.exists()) {
            indexFile.parentFile.mkdirs() // Create the directory if it doesn't exist
        }
        val iterator = wiseSayings.iterator()
        indexFile.bufferedWriter().use { writer ->
            while (iterator.hasNext()) {
                val wiseSaying = iterator.next()
                writer.write("${wiseSaying.id}\n")
            }
        }
    }

    fun reset(){
        val wiseSayingDir = File(wiseSayingPath)
        if (wiseSayingDir.exists()) {
            wiseSayingDir.deleteRecursively() // Delete the entire directory and its contents
        }
        val indexFile = File(indexPath)
        if (indexFile.exists()) {
            indexFile.delete() // Delete the index file
        }
        val lastIdFile = File(lastIdPath)
        if (lastIdFile.exists()) {
            lastIdFile.delete() // Delete the last ID file
        }
    }

    fun build(wiseSayings:List<WiseSaying>){
        val file = File(dataPath)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs() // Create the directory if it doesn't exist
        }
        val text = wiseSayingRepositoryJson.wiseSayingsToJson(wiseSayings)
        file.writeText(text)
        writeText.writeFile(dataPath, text)
    }
}
