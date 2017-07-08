package de.kevcodez.semver.semvervalidator.controller;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zafarkhaja.semver.ParseException;
import com.github.zafarkhaja.semver.Version;
import de.kevcodez.semver.semvervalidator.converter.VersionConverter;
import de.kevcodez.semver.semvervalidator.dto.VersionDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/increment")
@Api("Endpoint to increment versions.")
public class IncrementController {

    private VersionConverter versionConverter;

    @Autowired
    IncrementController(VersionConverter versionConverter) {
        this.versionConverter = versionConverter;
    }

    @GetMapping("/major/{version}/")
    @ApiOperation("Increments major version of the given version.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Incremented version.", response = VersionDto.class),
        @ApiResponse(code = 400, message = "Invalid version.")
    })
    ResponseEntity<?> incrementMajorVersion(String version) {
        return incrementVersion(Version::incrementMajorVersion, version);
    }

    @GetMapping("/minor/{version}/")
    @ApiOperation("Increments minor version of the given version.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Incremented version.", response = VersionDto.class),
        @ApiResponse(code = 400, message = "Invalid version.")
    })
    ResponseEntity<?> incrementMinorVersion(String version) {
        return incrementVersion(Version::incrementMinorVersion, version);
    }

    @GetMapping("/patch/{version}/")
    @ApiOperation("Increments patch version of the given version.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Incremented version.", response = VersionDto.class),
        @ApiResponse(code = 400, message = "Invalid version.")
    })
    ResponseEntity<?> incrementPatchVersion(String version) {
        return incrementVersion(Version::incrementPatchVersion, version);
    }

    @GetMapping("/buildMetadata/{version}/")
    @ApiOperation("Increments build metadata version of the given version.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Incremented version.", response = VersionDto.class),
        @ApiResponse(code = 400, message = "Invalid version.")
    })
    ResponseEntity<?> incrementBuildMetadataVersion(String version) {
        return incrementVersion(Version::incrementBuildMetadata, version);
    }

    @GetMapping("/preRelease/{version}/")
    @ApiOperation("Increments pre release version of the given version.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Incremented version.", response = VersionDto.class),
        @ApiResponse(code = 400, message = "Invalid version.")
    })
    ResponseEntity<?> incrementPreReleaseVersion(String version) {
        return incrementVersion(Version::incrementPreReleaseVersion, version);
    }

    private ResponseEntity<?> incrementVersion(Function<Version, Version> function, String version) {
        try {
            Version semanticVersion = Version.valueOf(version);
            Version incrementedVersion = function.apply(semanticVersion);
            VersionDto versionDto = versionConverter.convertToDto(incrementedVersion);

            return ResponseEntity.ok(versionDto);
        }
        catch (ParseException exc) {
            return new ResponseEntity<>(exc.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
