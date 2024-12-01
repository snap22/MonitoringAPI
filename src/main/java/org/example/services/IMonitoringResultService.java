package org.example.services;

import jakarta.transaction.Transactional;
import org.example.dto.MonitoringResultResponse;
import org.example.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IMonitoringResultService {
    /**
     * Retrieves the monitoring results for the current user's endpoint.
     *
     * @param endpointId the ID of the endpoint
     * @return a list of MonitoringResultResponse objects representing the monitoring results
     * @throws ResourceNotFoundException if the endpoint is not found
     */
    List<MonitoringResultResponse> getResultsForCurrentUserEndpoint(long endpointId);

    /**
     * Clears outdated monitoring results for all endpoints.
     */
    @Transactional
    void clearOutdatedResults();
}
