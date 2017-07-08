package de.kevcodez.semver.semvervalidator.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.github.zafarkhaja.semver.Version;
import de.kevcodez.semver.semvervalidator.dto.VersionDto;

@Mapper(componentModel = "spring")
public interface VersionConverter {

    @Mapping(source = "majorVersion", target = "major")
    @Mapping(source = "minorVersion", target = "minor")
    @Mapping(source = "patchVersion", target = "patch")
    @Mapping(source = "normalVersion", target = "normal")
    @Mapping(source = "preReleaseVersion", target = "preRelease")
    VersionDto convertToDto(Version version);

}
