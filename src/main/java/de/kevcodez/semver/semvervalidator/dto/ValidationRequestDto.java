package de.kevcodez.semver.semvervalidator.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationRequestDto {

    @NotNull
    private String versionRange;

    @NotNull
    private List<String> versionsToValidate = new ArrayList<>();

}
