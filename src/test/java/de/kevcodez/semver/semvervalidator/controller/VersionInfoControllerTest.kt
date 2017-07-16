package de.kevcodez.semver.semvervalidator.controller

import com.github.zafarkhaja.semver.Version
import com.tngtech.java.junit.dataprovider.DataProvider
import com.tngtech.java.junit.dataprovider.UseDataProvider
import de.kevcodez.semver.semvervalidator.DataProviderRunnerWithSpring
import de.kevcodez.semver.semvervalidator.converter.VersionConverter
import de.kevcodez.semver.semvervalidator.dto.VersionDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

@RunWith(DataProviderRunnerWithSpring::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VersionInfoControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @MockBean
    private lateinit var versionConverter: VersionConverter

    @Test
    @UseDataProvider("dataGetInformation")
    fun getInformation(version: String, expectedStatusCode: HttpStatus) {
        `when`(versionConverter.convertToDto(any(Version::class.java))).thenReturn(VersionDto())

        val response = restTemplate.getForEntity("/info/${version}", VersionDto::class.java)
        assertThat(response.statusCode).isEqualTo(expectedStatusCode)
    }

    companion object {

        @DataProvider
        @JvmStatic
        fun dataGetInformation(): Array<Array<Any>> {
            return arrayOf(
                    arrayOf<Any>("1.0.0", HttpStatus.OK),
                    arrayOf<Any>("invalid", HttpStatus.BAD_REQUEST)
            )
        }
    }

}
