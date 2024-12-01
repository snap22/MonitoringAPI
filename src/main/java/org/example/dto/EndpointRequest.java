package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointRequest {
    @NotNull
    @NotBlank
    @Length(min = 2, max = 50)
    @JsonProperty("name")
    private String name;

    @NotNull
    @NotBlank
    @Length(min = 10, max = 2048) // min = http:// (7 characters) + 3 characters at least
    @Pattern(regexp = "^(http|https)://.*$")
    @JsonProperty("url")
    private String url;

    @Min(1)
    @Max(86400) // max 1 day
    @JsonProperty("interval")
    private int interval;
}
