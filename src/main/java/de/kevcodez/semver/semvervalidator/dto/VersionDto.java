package de.kevcodez.semver.semvervalidator.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class VersionDto {

    private Integer major;
    private Integer minor;
    private Integer patch;
    private String preRelease;

    private String normal;
    private String buildMetadata;

}
