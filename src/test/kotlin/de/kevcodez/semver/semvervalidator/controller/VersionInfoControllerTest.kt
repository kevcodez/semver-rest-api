package de.kevcodez.semver.semvervalidator.controller

import com.tngtech.java.junit.dataprovider.DataProvider
import com.tngtech.java.junit.dataprovider.UseDataProvider
import de.kevcodez.semver.semvervalidator.DataProviderRunnerWithSpring
import de.kevcodez.semver.semvervalidator.dto.VersionDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@RunWith(DataProviderRunnerWithSpring::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VersionInfoControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    @UseDataProvider("dataGetInformation")
    fun getInformation(version: String, expectedStatusCode: HttpStatus) {
        val response = restTemplate.getForEntity("/info/$version", VersionDto::class.java)
        assertThat(response.statusCode).isEqualTo(expectedStatusCode)
    }

    companion object {

        @DataProvider
        @JvmStatic
        fun dataGetInformation(): Array<Array<Any>> {
            return arrayOf(
                    arrayOf("1.0.0", HttpStatus.OK),
                    arrayOf("invalid", HttpStatus.BAD_REQUEST)
            )
        }
    }

}
