package com.example.mockkvcached

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class RestService(
    private val higherLevelService: HigherLevelService,
) {
    @GetMapping("/hello/")
    fun hello(
        @RequestParam input: String,
    ) = higherLevelService.performHigher(input)
}