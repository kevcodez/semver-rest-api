package de.kevcodez.semver.semvervalidator.controller

import com.github.zafarkhaja.semver.ParseException
import com.github.zafarkhaja.semver.Version
import de.kevcodez.semver.semvervalidator.dto.ValidationRequestDto
import de.kevcodez.semver.semvervalidator.dto.ValidationResponseDto
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@RestController
@RequestMapping("/validate")
@Api("Endpoint to validate versions against a version range, according to semver.org specification.")
class ValidationController {

    @GetMapping("/{version}/inRange/{versionRange}")
    @ApiOperation("Validate a single version against a version range.")
    @ApiResponses(
            ApiResponse(code = 200, message = "Result, whether the given version is in the given version range.", response = Boolean::class),
            ApiResponse(code = 400, message = "Invalid version.")
    )
    internal fun isVersionInRange(@PathVariable version: String, @PathVariable versionRange: String): ResponseEntity<*> {
        try {
            val semanticVersion = Version.valueOf(version)
            val satisfied = semanticVersion.satisfies(versionRange)

            return ResponseEntity(satisfied, HttpStatus.OK)
        } catch (exc: ParseException) {
            return ResponseEntity(exc.localizedMessage, HttpStatus.BAD_REQUEST)
        }

    }

    @PostMapping
    @ApiOperation("Validate a list of versions against a version range.")
    @ApiResponse(code = 200, message = "List of versions and whether they match or not.", response = ValidationResponseDto::class)
    internal fun versionsInRange(@Valid @RequestBody validationRequestDto: ValidationRequestDto): ResponseEntity<*> {
        val versionRange = validationRequestDto.versionRange

        val validationResponseDto = ValidationResponseDto(validationRequestDto.versionRange, HashMap<String, Boolean>())
        validationResponseDto.versionRange = validationRequestDto.versionRange

        for (version in validationRequestDto.versionsToValidate) {
            try {
                val semanticVersion = Version.valueOf(version)

                val satisfied = semanticVersion.satisfies(versionRange)

                validationResponseDto.addValidation(version, satisfied)
            } catch (exc: ParseException) {
                validationResponseDto.addValidation(version, false)
            }
        }

        return ResponseEntity.ok(validationResponseDto)
    }

}
