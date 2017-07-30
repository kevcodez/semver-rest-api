package de.kevcodez.semver.semvervalidator.controller


import com.tngtech.java.junit.dataprovider.DataProvider
import com.tngtech.java.junit.dataprovider.UseDataProvider
import de.kevcodez.semver.semvervalidator.DataProviderRunnerWithSpring
import de.kevcodez.semver.semvervalidator.dto.ValidationResponseDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType


@RunWith(DataProviderRunnerWithSpring::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ValidationControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    @UseDataProvider("dataIsVersionInRange")
    fun isVersionInRange(version: String, versionRange: String, expectedStatusCode: HttpStatus) {
        val responseEntity = restTemplate.getForEntity("/validate/${version}/inRange/${versionRange}", Boolean::class.java)

        assertThat(responseEntity.statusCode).isEqualTo(expectedStatusCode)
    }

    @Test
    fun versionsInRange() {
        val requestJson = """
        {
          "versionRange": "^1.0.0",
          "versionsToValidate": [
            "1.0.0",
            "1.1.0",
            "2.0.0",
            "invalid"
          ]
        }"""

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val entity = HttpEntity<String>(requestJson, headers)

        val responseEntity = restTemplate.postForEntity("/validate", entity, ValidationResponseDto::class.java)
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)

        val validationResponseDto: ValidationResponseDto = responseEntity.getBody()
        assertThat(validationResponseDto).isNotNull()
        assertThat(validationResponseDto.versionRange).isEqualTo("^1.0.0")

        val validatedVersions = validationResponseDto.validatedVersions
        assertThat(validatedVersions).hasSize(4)
        assertThat(validatedVersions).containsEntry("1.0.0", true)
        assertThat(validatedVersions).containsEntry("1.1.0", true)
        assertThat(validatedVersions).containsEntry("2.0.0", false)
        assertThat(validatedVersions).containsEntry("invalid", false)

    }

    companion object {

        @DataProvider
        @JvmStatic
        fun dataIsVersionInRange(): Array<Array<Any>> {
            return arrayOf(
                    arrayOf<Any>("1.0.0", "^1.0.0", HttpStatus.OK),
                    arrayOf<Any>("invalid", "^1.0.0", HttpStatus.BAD_REQUEST),
                    arrayOf<Any>("1.0.0", "invalid", HttpStatus.BAD_REQUEST)
            )
        }
    }


}
