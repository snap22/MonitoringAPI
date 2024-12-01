package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO for representing an endpoint response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("url")
    private String url;

    @JsonProperty("interval")
    private int interval;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    @JsonProperty("lastCheckedAt")
    private LocalDateTime lastCheckedAt;

    @JsonProperty("owner")
    private UserResponse owner;
}
