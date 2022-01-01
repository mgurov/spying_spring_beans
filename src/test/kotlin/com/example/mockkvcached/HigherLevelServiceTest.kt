package com.example.mockkvcached

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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