package de.kevcodez.semver.semvervalidator.converter

import com.github.zafarkhaja.semver.Version
import de.kevcodez.semver.semvervalidator.dto.VersionDto
import org.springframework.stereotype.Component

@Component
class VersionConverter {

    fun convertToDto(version: Version?): VersionDto? {
        if (version == null) {
            return null
        }

        return VersionDto(major = version.majorVersion,
                minor = version.minorVersion,
                patch = version.patchVersion,
                normal = version.normalVersion,
                preRelease = version.preReleaseVersion,
                buildMetadata = version.buildMetadata)
    }
}
