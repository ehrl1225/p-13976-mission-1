package com.global.config

enum class RunMode{
    DEV, TEST, PROD;

    companion object {
        fun fromString(value: String): RunMode? {
            return values().find { it.name.equals(value, ignoreCase = true) }
        }
    }
}