package de.kevcodez.semver.semvervalidator.controller

import com.github.zafarkhaja.semver.Version
import com.tngtech.java.junit.dataprovider.DataProvider
import com.tngtech.java.junit.dataprovider.UseDataProvider
import de.kevcodez.semver.semvervalidator.DataProviderRunnerWithSpring
import de.kevcodez.semver.semvervalidator.converter.VersionConverter
import de.kevcodez.semver.semvervalidator.converter.VersionConverterImpl
import de.kevcodez.semver.semvervalidator.dto.VersionDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
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
class IncrementControllerTest {

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @MockBean
    private lateinit var versionConverter: VersionConverter

    private val versionConverterImpl = VersionConverterImpl()

    @Before
    fun setup() {
        `when`(versionConverter.convertToDto(any<Version>()))
                .thenAnswer { versionConverterImpl.convertToDto(it.getArgumentAt(0, Version::class.java)) }
    }

    @Test
    @UseDataProvider("dataIncrement")
    fun increment(url: String, expectedVersion: String) {
        val responseEntity = restTemplate.getForEntity("/increment/${url}", VersionDto::class.java)

        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).isNotNull()
        assertThat(responseEntity.body.normal).isEqualTo(expectedVersion)
    }

    @Test
    fun incrementInvalidVersion() {
        val responseEntity = restTemplate.getForEntity("/increment/major/invalid", VersionDto::class.java)

        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(responseEntity.body).isNull()
    }

    @Test
    fun incrementPreRelease() {
        val responseEntity = restTemplate.getForEntity("/increment/preRelease/1.0.0-rc1", VersionDto::class.java)

        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).isNotNull()
        assertThat(responseEntity.body.preRelease).isEqualTo("rc1.1")
    }

    companion object {

        @DataProvider
        @JvmStatic
        fun dataIncrement(): Array<Array<Any>> {
            return arrayOf(
                    arrayOf<Any>("major/1.0.0", "2.0.0"),
                    arrayOf<Any>("minor/1.0.0", "1.1.0"),
                    arrayOf<Any>("patch/1.0.0", "1.0.1")
            )
        }
    }

}
