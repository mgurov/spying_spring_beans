package com.example.mockkvcached

import org.springframework.stereotype.Component

@Component
class LowLevelService {
    fun perform(input: String): String {
        return "input($input)"
    }
}

@Component
class HigherLevelService(
    private val lowLevelService: LowLevelService,
) {
    fun performHigher(input: String): String {
        return resiliently { lowLevelService.perform(input + "_1") } +
                " - " +
                resiliently { lowLevelService.perform(input + "_2") }
    }

    private fun resiliently(function: () -> String): String {
        return try {
            function()
        } catch (e: Exception) {
            return "failed(${e.message})"
        }
    }
}