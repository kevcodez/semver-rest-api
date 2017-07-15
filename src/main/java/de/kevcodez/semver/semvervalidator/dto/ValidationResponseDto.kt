package de.kevcodez.semver.semvervalidator.dto

class ValidationResponseDto(var versionRange: String, var validatedVersions: MutableMap<String, Boolean>) {

    fun addValidation(version: String, isValid: Boolean) {
        validatedVersions.put(version, isValid);
    }

}
