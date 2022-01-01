package com.example.mockkvcached

import io.mockk.every
import io.mockk.slot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus


@OurIntegrationTests
class RestServiceTest(
    @Autowired val restTemplate: TestRestTemplate,
    @Autowired val higherLevelService: HigherLevelService,
    ) {
    @Test
    fun `happy REST`() {

        val capturedInput = slot<String>()

        every { higherLevelService.performHigher(capture(capturedInput)) } returns "fooe"

        val response = restTemplate.getForEntity("/hello/?input=blah", String::class.java)

        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isEqualTo("fooe")
        assertThat(capturedInput.captured).isEqualTo("blah")

    }
}