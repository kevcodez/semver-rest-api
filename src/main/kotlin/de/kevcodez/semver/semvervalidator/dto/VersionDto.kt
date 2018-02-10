package de.kevcodez.semver.semvervalidator.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_EMPTY)
class VersionDto(var major: Int, var minor: Int, var patch: Int, var preRelease: String, var normal: String, var buildMetadata: String) {

    constructor() : this(0, 0, 0, "", "", "")

}
