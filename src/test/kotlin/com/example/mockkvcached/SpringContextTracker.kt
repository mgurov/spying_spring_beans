package com.example.mockkvcached

import org.springframework.boot.SpringApplication
import org.springframework.boot.SpringApplicationRunListener
import org.springframework.context.ConfigurableApplicationContext
import java.time.Duration
import java.time.Instant
import java.util.concurrent.atomic.AtomicInteger

class SpringContextTracker(
    @Suppress("UNUSED_PARAMETER") application: SpringApplication,
    @Suppress("UNUSED_PARAMETER") args: Array<String>,
    ): SpringApplicationRunListener {

    override fun started(context: ConfigurableApplicationContext) {
        val duration = Duration.ofMillis(Instant.now().toEpochMilli() - context.startupDate)
        val contextNumber = counter.incrementAndGet()
        logger().info("$contextNumber spring context started in $duration")
    }

    companion object {
        private val counter = AtomicInteger(0)
    }
}