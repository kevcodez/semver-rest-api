package de.kevcodez.semver.semvervalidator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("/info")
@Api("Endpoint to retrieve information about a version.")
public class VersionInfoController {

    private VersionConverter versionConverter;

    @Autowired
    VersionInfoController(VersionConverter versionConverter) {
        this.versionConverter = versionConverter;
    }

    @GetMapping("{version:.+}")
    @ApiOperation("Gets information about the given version.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Version information.", response = VersionDto.class),
        @ApiResponse(code = 400, message = "Invalid version.")
    })
    ResponseEntity<?> getInformation(@PathVariable String version) {
        try {
            Version semanticVersion = Version.valueOf(version);
            VersionDto versionDto = versionConverter.convertToDto(semanticVersion);

            return new ResponseEntity<>(versionDto, HttpStatus.OK);
        }
        catch (ParseException exc) {
            return new ResponseEntity<>(exc.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
