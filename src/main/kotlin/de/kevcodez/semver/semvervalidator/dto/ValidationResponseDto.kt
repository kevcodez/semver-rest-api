package de.kevcodez.semver.semvervalidator.dto

class ValidationResponseDto(var versionRange: String, var validatedVersions: MutableMap<String, Boolean>) {

    constructor() : this("", HashMap())

    fun addValidation(version: String, isValid: Boolean) {
        validatedVersions[version] = isValid
    }

}
