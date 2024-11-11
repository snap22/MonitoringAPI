package org.example.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MonitoringResultResponse {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("statusCode")
    private String statusCode;

    @JsonProperty("payload")
    private String payload;

    @JsonProperty("checkedAt")
    private LocalDateTime checkedAt;
}
