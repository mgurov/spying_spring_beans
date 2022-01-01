package com.example.mockkvcached

import com.ninjasquad.springmockk.MockkBean
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.spyk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

class HigherLevelServiceHappyTest {
    @Test
    fun `happy times`() {
        val higherLevelService = HigherLevelService(LowLevelService())
        //when
        val actual = higherLevelService.performHigher("blah")
        //then
        assertThat(actual).isEqualTo("input(blah_1) - input(blah_2)")
    }
}

@SpringBootTest
class HigherLevelServiceHappyIT(
    @Autowired val higherLevelService: HigherLevelService
) {
    @Test
    fun `happy times`() {
        //when
        val actual = higherLevelService.performHigher("blah")
        //then
        assertThat(actual).isEqualTo("input(blah_1) - input(blah_2)")
    }
}

@SpringBootTest
class HigherLevelServiceResilienceIT(
    @Autowired val higherLevelService: HigherLevelService,
    @Autowired val lowLevelService: LowLevelService,
) {


    @Test
    fun `happy times`() {
        //when
        every { lowLevelService.perform("blah_2") } answers {throw RuntimeException("kaboom")}
        val actual = higherLevelService.performHigher("blah")
        //then
        assertThat(actual).isEqualTo("input(blah_1) - failed(kaboom)")
    }

    @AfterEach
    fun resetMocks() {
        clearAllMocks()
    }
}

@Configuration
class SpyWrappers {
    @Bean
    @Primary
    fun lowLevelServiceSpy(lowLevelService: LowLevelService): LowLevelService {
        return spyk(lowLevelService)
    }
}