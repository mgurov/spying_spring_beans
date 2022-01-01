package com.example.mockkvcached

import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

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
    @Autowired val higherLevelService: HigherLevelService
) {

    @MockkBean
    private lateinit var lowLevelService: LowLevelService

    @Test
    fun `happy times`() {
        //when
        every { lowLevelService.perform("blah_1") } returns "input(blah_1)"
        every { lowLevelService.perform("blah_2") } answers {throw RuntimeException("kaboom")}
        val actual = higherLevelService.performHigher("blah")
        //then
        assertThat(actual).isEqualTo("input(blah_1) - failed(kaboom)")
    }
}
