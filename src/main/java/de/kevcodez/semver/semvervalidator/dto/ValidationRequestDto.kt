package de.kevcodez.semver.semvervalidator.dto

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@NoArgsConstructor
@AllArgsConstructor
class ValidationRequestDto(var versionRange: String, var versionsToValidate: List<String>) {

}
