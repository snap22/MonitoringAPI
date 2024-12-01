package org.example.services.implementation;

import org.example.dto.MonitoringResultResponse;
import org.example.entities.MonitoringResultEntity;
import org.example.entities.UserEntity;
import org.example.exceptions.ResourceNotFoundException;
import org.example.mappers.IMonitoringResultMapper;
import org.example.repositories.IEndpointRepository;
import org.example.repositories.IMonitoringResultRepository;
import org.example.services.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MonitoringResultServiceTest {

    @Mock
    private IEndpointRepository endpointRepository;

    @Mock
    private IMonitoringResultRepository monitoringResultRepository;

    @Mock
    private IMonitoringResultMapper monitoringResultMapper;

    @Mock
    private IUserService userService;

    @InjectMocks
    private MonitoringResultService monitoringResultService;

    private MonitoringResultEntity monitoringResult;
    private MonitoringResultResponse monitoringResultResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        monitoringResult = new MonitoringResultEntity();
        monitoringResult.setId(1L);

        monitoringResultResponse = new MonitoringResultResponse();
        monitoringResultResponse.setId(1L);

        UserEntity user = new UserEntity();
        user.setId(1L);

        when(userService.getCurrentUserEntity()).thenReturn(user);
    }

    @Test
    void getResultsForCurrentUserEndpoint_shouldReturnResults() {
        long endpointId = 1L;
        long userId = 1L;

        when(endpointRepository.existsByIdAndUserId(endpointId, userId)).thenReturn(true);
        when(monitoringResultRepository.getMostRecentResultsForUserAndEndpoint(userId, endpointId, PageRequest.of(0, 10)))
                .thenReturn(List.of(monitoringResult));
        when(monitoringResultMapper.toResponse(monitoringResult)).thenReturn(monitoringResultResponse);

        List<MonitoringResultResponse> responses = monitoringResultService.getResultsForCurrentUserEndpoint(endpointId);

        assertEquals(1, responses.size());
        assertEquals(monitoringResultResponse, responses.get(0));
    }

    @Test
    void getResultsForCurrentUserEndpoint_shouldThrowResourceNotFoundException() {
        long endpointId = 1L;
        long userId = 1L;

        when(endpointRepository.existsByIdAndUserId(endpointId, userId)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> monitoringResultService.getResultsForCurrentUserEndpoint(endpointId));
    }

    @Test
    void clearOutdatedResults_shouldDeleteOutdatedResults() {
        List<Long> endpointIds = List.of(1L, 2L, 3L);

        when(endpointRepository.getAllIds()).thenReturn(endpointIds);

        monitoringResultService.clearOutdatedResults();

        for (Long endpointId : endpointIds) {
            verify(monitoringResultRepository).deleteOutdatedResultsForEndpointId(endpointId);
        }
    }
}