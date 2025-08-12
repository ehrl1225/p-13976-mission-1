package com.global.Ut.page

class Page<T> (
    val content: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalElements: Int,
    val totalPages: Int
){
    companion object{
        fun <T> empty(): Page<T> {
            return Page(emptyList(), 0, 0, 0, 0)
        }

        fun <T> of(content: List<T>, pageable: Pageable): Page<T> {
            val startIndex = maxOf(0, content.size - pageable.pageNumber * pageable.pageSize)
            val entIndex = startIndex + pageable.pageSize
            val subContent = if (startIndex < content.size){
                content.subList(startIndex, minOf(entIndex, content.size))
            }else{
                emptyList()
            }
            return Page(subContent, pageNumber = pageable.pageNumber, pageSize = pageable.pageSize,totalElements = content.size, totalPages = (content.size + pageable.pageSize - 1) / pageable.pageSize)
        }
    }
}