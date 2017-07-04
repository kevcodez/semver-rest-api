package de.kevcodez.semver.semvervalidator.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zafarkhaja.semver.ParseException;
import com.github.zafarkhaja.semver.Version;
import de.kevcodez.semver.semvervalidator.dto.VersionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/info")
@Api("Endpoint to retrieve information about a version.")
public class VersionInfoController {

    @GetMapping("{version:.+}")
    @ApiOperation("Gets information about the given version.")
    ResponseEntity<?> getInformation(@PathVariable String version) {
        try {
            Version semanticVersion = Version.valueOf(version);

            VersionDto versionDto = VersionDto.builder()
                .major(semanticVersion.getMajorVersion())
                .minor(semanticVersion.getMinorVersion())
                .patch(semanticVersion.getPatchVersion())
                .preRelease(semanticVersion.getPreReleaseVersion())
                .normal(semanticVersion.getNormalVersion())
                .buildMetadata(semanticVersion.getBuildMetadata())
                .build();

            return new ResponseEntity<>(versionDto, HttpStatus.OK);
        }
        catch (ParseException exc) {
            return new ResponseEntity<>(exc.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
