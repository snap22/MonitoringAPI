package org.example.services;

import org.example.dto.MonitoringResultResponse;

import java.util.List;

public interface IMonitoringResultService {
    List<MonitoringResultResponse> getResultsForCurrentUserEndpoint(long endpointId);
}
