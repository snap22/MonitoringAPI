package org.example.services.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.MonitoringResultResponse;
import org.example.entities.MonitoringResultEntity;
import org.example.exceptions.ResourceNotFoundException;
import org.example.mappers.IMonitoringResultMapper;
import org.example.repositories.IEndpointRepository;
import org.example.repositories.IMonitoringResultRepository;
import org.example.services.IMonitoringResultService;
import org.example.services.IUserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MonitoringResultService implements IMonitoringResultService {
    private final IEndpointRepository endpointRepository;
    private final IMonitoringResultRepository monitoringResultRepository;
    private final IMonitoringResultMapper monitoringResultMapper;
    private final IUserService userService;

    @Override
    public List<MonitoringResultResponse> getResultsForCurrentUserEndpoint(long endpointId) {
        long currentUserId = userService.getCurrentUser().getId();

        if (!endpointRepository.existsByIdAndUserId(endpointId, currentUserId))
            throw new ResourceNotFoundException("Endpoint does not exist for given user");

        List<MonitoringResultEntity> results = monitoringResultRepository
                .getMostRecentResultsForUserAndEndpoint(
                        currentUserId,
                        endpointId,
                        PageRequest.of(0, 10)
                );

        return results
                .stream()
                .map(monitoringResultMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public void clearOutdatedResults() {
        List<Long> endpointIds = endpointRepository.getAllIds();

        for (Long endpointId: endpointIds) {
            monitoringResultRepository.deleteOutdatedResultsForEndpointId(endpointId);
        }
    }

}
