package de.kevcodez.semver.semvervalidator.dto;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResponseDto {

    @NotNull
    private String versionRange;

    @NotNull
    private Map<String, Boolean> validatedVersions = new HashMap<>();

    public void addValidation(String version, boolean isValid) {
        validatedVersions.put(version, isValid);
    }

}
