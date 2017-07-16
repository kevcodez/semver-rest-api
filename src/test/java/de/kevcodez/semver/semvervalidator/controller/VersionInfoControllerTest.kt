package de.kevcodez.semver.semvervalidator.controller

import com.github.zafarkhaja.semver.Version
import de.kevcodez.semver.semvervalidator.converter.VersionConverter
import de.kevcodez.semver.semvervalidator.dto.VersionDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Matchers.any
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class VersionInfoControllerTest {

    @Autowired
    private val restTemplate: TestRestTemplate? = null

    @MockBean
    private val versionConverter: VersionConverter? = null

    @Test
    fun getInformationSuccess() {
        `when`(versionConverter!!.convertToDto(any(Version::class.java))).thenReturn(VersionDto())

        val response = restTemplate!!.getForEntity("/info/1.0.0", VersionDto::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(response.body).isNotNull()
    }

    @Test
    fun getInformationBadRequest() {
        val response = restTemplate!!.getForEntity("/info/invalid", VersionDto::class.java)
        assertThat(response.statusCode).isEqualTo(HttpStatus.BAD_REQUEST)
        assertThat(response.body).isNull()
        verify(versionConverter!!, never()).convertToDto(any<Version>())
    }

}
