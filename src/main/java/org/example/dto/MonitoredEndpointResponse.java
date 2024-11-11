package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonitoredEndpointResponse {
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
