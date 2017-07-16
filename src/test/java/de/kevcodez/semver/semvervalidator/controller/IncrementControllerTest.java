package de.kevcodez.semver.semvervalidator.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.github.zafarkhaja.semver.Version;
import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import de.kevcodez.semver.semvervalidator.DataProviderRunnerWithSpring;
import de.kevcodez.semver.semvervalidator.converter.VersionConverter;
import de.kevcodez.semver.semvervalidator.converter.VersionConverterImpl;
import de.kevcodez.semver.semvervalidator.dto.VersionDto;

@RunWith(DataProviderRunnerWithSpring.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IncrementControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private VersionConverter versionConverter;

    private VersionConverterImpl versionConverterImpl = new VersionConverterImpl();

    @Before
    public void setup() {
        when(versionConverter.convertToDto(any()))
            .thenAnswer(i -> versionConverterImpl.convertToDto(i.getArgumentAt(0, Version.class)));
    }

    @Test
    @UseDataProvider("dataIncrement")
    public void increment(String url, String expectedVersion) {
        ResponseEntity<VersionDto> responseEntity = restTemplate
            .getForEntity("/increment/" + url, VersionDto.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getNormal()).isEqualTo(expectedVersion);
    }

    @Test
    public void incrementPreRelease() {
        ResponseEntity<VersionDto> responseEntity = restTemplate.getForEntity("/increment/preRelease/1.0.0-rc1", VersionDto.class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().getPreRelease()).isEqualTo("rc1.1");
    }

    @DataProvider
    public static Object[][] dataIncrement() {
        return new Object[][] {
            { "major/1.0.0", "2.0.0" },
            { "minor/1.0.0", "1.1.0" },
            { "patch/1.0.0", "1.0.1" }
        };
    }

}
