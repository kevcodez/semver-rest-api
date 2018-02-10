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
@RequestMapping("info")
@Api("Endpoint to retrieve information about a version.")
class VersionInfoController {

    @Autowired
    private lateinit var versionConverter: VersionConverter

    @GetMapping("{version:.+}")
    @ApiOperation("Gets information about the given version.")
    @ApiResponses(ApiResponse(code = 200, message = "Version information.", response = VersionDto::class), ApiResponse(code = 400, message = "Invalid version."))
    internal fun getInformation(@PathVariable version: String): ResponseEntity<*> {
        return try {
            val semanticVersion = Version.valueOf(version)
            val versionDto = versionConverter.convertToDto(semanticVersion)

            ResponseEntity(versionDto, HttpStatus.OK)
        } catch (exc: ParseException) {
            ResponseEntity(exc.localizedMessage, HttpStatus.BAD_REQUEST)
        }
    }

}
