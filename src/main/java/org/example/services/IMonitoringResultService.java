package org.example.services;

import jakarta.transaction.Transactional;
import org.example.dto.MonitoringResultResponse;

import java.util.List;

public interface IMonitoringResultService {
    List<MonitoringResultResponse> getResultsForCurrentUserEndpoint(long endpointId);

    @Transactional
    void clearOutdatedResults();
}
