package com.domain.command.command.entity

class Command(
    val command: String,
    val parameter: Map<String, Object> = emptyMap()
)