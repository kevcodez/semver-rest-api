package de.kevcodez.semver.semvervalidator.controller

import com.github.zafarkhaja.semver.ParseException
import com.github.zafarkhaja.semver.Version
import de.kevcodez.semver.semvervalidator.converter.VersionConverter
import de.kevcodez.semver.semvervalidator.dto.VersionDto
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("increment")
@Api("Endpoint to increment versions.")
class IncrementController {

    @Autowired
    private lateinit var versionConverter: VersionConverter

    @GetMapping("major/{version:.+}")
    @ApiOperation("Increments major version of the given version.")
    @ApiResponses(ApiResponse(code = 200, message = "Incremented version.", response = VersionDto::class), ApiResponse(code = 400, message = "Invalid version."))
    internal fun incrementMajorVersion(@PathVariable version: String): ResponseEntity<*> {
        return incrementVersion(version, { it.incrementMajorVersion() })
    }

    @GetMapping("minor/{version:.+}")
    @ApiOperation("Increments minor version of the given version.")
    @ApiResponses(ApiResponse(code = 200, message = "Incremented version.", response = VersionDto::class), ApiResponse(code = 400, message = "Invalid version."))
    internal fun incrementMinorVersion(@PathVariable version: String): ResponseEntity<*> {
        return incrementVersion(version, { it.incrementMinorVersion() })
    }

    @GetMapping("patch/{version:.+}")
    @ApiOperation("Increments patch version of the given version.")
    @ApiResponses(ApiResponse(code = 200, message = "Incremented version.", response = VersionDto::class), ApiResponse(code = 400, message = "Invalid version."))
    internal fun incrementPatchVersion(@PathVariable version: String): ResponseEntity<*> {
        return incrementVersion(version, { it.incrementPatchVersion() })
    }

    @GetMapping("preRelease/{version:.+}")
    @ApiOperation("Increments pre release version of the given version.")
    @ApiResponses(ApiResponse(code = 200, message = "Incremented version.", response = VersionDto::class), ApiResponse(code = 400, message = "Invalid version."))
    internal fun incrementPreReleaseVersion(@PathVariable version: String): ResponseEntity<*> {
        return incrementVersion(version, { it.incrementPreReleaseVersion() })
    }

    private fun incrementVersion(version: String, functionIncrement: (Version) -> Version): ResponseEntity<*> {
        return try {
            val semanticVersion = Version.valueOf(version)
            val incrementedVersion = functionIncrement(semanticVersion)
            val versionDto = versionConverter.convertToDto(incrementedVersion)

            ResponseEntity.ok(versionDto)
        } catch (exc: ParseException) {
            ResponseEntity(exc.localizedMessage, HttpStatus.BAD_REQUEST)
        }

    }


}
