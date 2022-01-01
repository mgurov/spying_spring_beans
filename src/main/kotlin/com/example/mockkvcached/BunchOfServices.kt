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
        return lowLevelService.perform(input + "_1") +
                " - " +
                lowLevelService.perform(input + "_@")
    }
}