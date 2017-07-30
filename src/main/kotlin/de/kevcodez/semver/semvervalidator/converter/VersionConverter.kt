package de.kevcodez.semver.semvervalidator.converter

import com.github.zafarkhaja.semver.Version
import de.kevcodez.semver.semvervalidator.dto.VersionDto
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface VersionConverter {

    @Mappings(
            Mapping(source = "majorVersion", target = "major"),
            Mapping(source = "minorVersion", target = "minor"),
            Mapping(source = "patchVersion", target = "patch"),
            Mapping(source = "normalVersion", target = "normal"),
            Mapping(source = "preReleaseVersion", target = "preRelease")
    )
    fun convertToDto(version: Version): VersionDto

}
