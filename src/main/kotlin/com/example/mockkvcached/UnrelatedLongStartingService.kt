package com.example.mockkvcached

import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class UnrelatedLongStartingService(
    private val slownessLevel: Int = 0
) {
    init {
        for (i in 1..slownessLevel) {
            logger.info("Starting>>> $i")
            TimeUnit.SECONDS.sleep(1)
        }
    }

    companion object {
        private val logger = logger()
    }
}