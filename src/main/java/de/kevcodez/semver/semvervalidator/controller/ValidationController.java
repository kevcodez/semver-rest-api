package de.kevcodez.semver.semvervalidator.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.zafarkhaja.semver.ParseException;
import com.github.zafarkhaja.semver.Version;
import de.kevcodez.semver.semvervalidator.dto.ValidationRequestDto;
import de.kevcodez.semver.semvervalidator.dto.ValidationResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/validate")
@Api("Endpoint to validate versions against a version range, according to semver.org specification.")
public class ValidationController {

    @GetMapping("/{version}/inRange/{versionRange}")
    @ApiOperation("Validate a single version against a version range.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Result, whether the given version is in the given version range.", response = Boolean.class),
        @ApiResponse(code = 400, message = "Invalid version.")
    })
    ResponseEntity<?> isVersionInRange(@PathVariable String version, @PathVariable String versionRange) {
        try {
            Version semanticVersion = Version.valueOf(version);

            boolean satisfied = semanticVersion.satisfies(versionRange);

            return new ResponseEntity<>(satisfied, HttpStatus.OK);
        }
        catch (ParseException exc) {
            return new ResponseEntity<>(exc.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    @ApiOperation("Validate a list of versions against a version range.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "List of versions and whether they match or not.", response = ValidationResponseDto.class),
    })
    ResponseEntity<?> versionsInRange(@Valid @RequestBody ValidationRequestDto validationRequestDto) {
        String versionRange = validationRequestDto.getVersionRange();

        ValidationResponseDto validationResponseDto = new ValidationResponseDto();

        for (String version : validationRequestDto.getVersionsToValidate()) {
            try {
                Version semanticVersion = Version.valueOf(version);

                boolean satisfied = semanticVersion.satisfies(versionRange);

                validationResponseDto.addValidation(version, satisfied);
            }
            catch (ParseException exc) {
                validationResponseDto.addValidation(version, false);
            }
        }

        return new ResponseEntity<>(validationResponseDto, HttpStatus.OK);
    }

}
