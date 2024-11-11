package org.example.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "monitoring_result")
public class MonitoringResultEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "payload")
    private String payload;

    @Column(name = "checked_at")
    private LocalDateTime checkedAt;
}
