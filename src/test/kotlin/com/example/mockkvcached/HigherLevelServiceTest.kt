package com.example.mockkvcached

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
