package org.example.services.implementation;

import org.example.dto.EndpointRequest;
import org.example.dto.EndpointResponse;
import org.example.entities.EndpointEntity;
import org.example.entities.UserEntity;
import org.example.exceptions.ResourceNotFoundException;
import org.example.mappers.IEndpointMapper;
import org.example.repositories.IEndpointRepository;
import org.example.services.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EndpointServiceTest {

    @Mock
    private IEndpointRepository endpointRepository;

    @Mock
    private IEndpointMapper endpointMapper;

    @Mock
    private IUserService userService;

    @InjectMocks
    private EndpointService endpointService;

    private UserEntity user;
    private EndpointEntity endpoint;
    private EndpointRequest endpointRequest;
    private EndpointResponse endpointResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new UserEntity();
        user.setId(1L);

        endpoint = new EndpointEntity();
        endpoint.setId(1L);
        endpoint.setUser(user);
        endpoint.setResults(new ArrayList<>());

        endpointRequest = new EndpointRequest();
        endpointRequest.setUrl("http://localhost:8080");

        endpointResponse = new EndpointResponse();
        endpointResponse.setId(1L);

        when(userService.getCurrentUserEntity()).thenReturn(user);
    }

    @Test
    void getCurrentUserEndpoints_shouldReturnEndpoints() {
        when(endpointRepository.findByUserId(user.getId())).thenReturn(List.of(endpoint));
        when(endpointMapper.toResponse(endpoint)).thenReturn(endpointResponse);

        List<EndpointResponse> responses = endpointService.getCurrentUserEndpoints();

        assertEquals(1, responses.size());
        assertEquals(endpointResponse, responses.get(0));
    }

    @Test
    void getCurrentUserEndpoint_shouldReturnEndpoint() {
        when(endpointRepository.findByIdAndUserId(endpoint.getId(), user.getId())).thenReturn(Optional.of(endpoint));
        when(endpointMapper.toResponse(endpoint)).thenReturn(endpointResponse);

        EndpointResponse response = endpointService.getCurrentUserEndpoint(endpoint.getId());

        assertEquals(endpointResponse, response);
    }

    @Test
    void getCurrentUserEndpoint_shouldThrowResourceNotFoundException() {
        when(endpointRepository.findByIdAndUserId(endpoint.getId(), user.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> endpointService.getCurrentUserEndpoint(endpoint.getId()));
    }

    @Test
    void createCurrentUserEndpoint_shouldCreateAndReturnEndpoint() {
        when(endpointMapper.toEntity(endpointRequest)).thenReturn(endpoint);
        when(endpointRepository.save(endpoint)).thenReturn(endpoint);
        when(endpointMapper.toResponse(endpoint)).thenReturn(endpointResponse);

        EndpointResponse response = endpointService.createCurrentUserEndpoint(endpointRequest);

        assertEquals(endpointResponse, response);
        verify(endpointRepository).save(endpoint);
    }

    @Test
    void updateCurrentUserEndpoint_shouldUpdateAndReturnEndpoint() {
        when(endpointRepository.findByIdAndUserId(endpoint.getId(), user.getId())).thenReturn(Optional.of(endpoint));
        when(endpointMapper.toResponse(endpoint)).thenReturn(endpointResponse);

        EndpointResponse response = endpointService.updateCurrentUserEndpoint(endpoint.getId(), endpointRequest);

        assertEquals(endpointResponse, response);
        verify(endpointRepository).save(endpoint);
    }

    @Test
    void updateCurrentUserEndpoint_shouldClearResultsForUpdatedUrl() {
        endpoint.setUrl("http://old-url.com");
        when(endpointRepository.findByIdAndUserId(endpoint.getId(), user.getId())).thenReturn(Optional.of(endpoint));
        when(endpointMapper.toResponse(endpoint)).thenReturn(endpointResponse);

        endpointRequest.setUrl("http://new-url.com");
        EndpointResponse response = endpointService.updateCurrentUserEndpoint(endpoint.getId(), endpointRequest);

        assertEquals(endpointResponse, response);
        assertNull(endpoint.getLastCheckedAt());
        assertTrue(endpoint.getResults().isEmpty());
        verify(endpointRepository).save(endpoint);
    }

    @Test
    void deleteCurrentUserEndpoint_shouldDeleteEndpoint() {
        when(endpointRepository.findByIdAndUserId(endpoint.getId(), user.getId())).thenReturn(Optional.of(endpoint));

        endpointService.deleteCurrentUserEndpoint(endpoint.getId());

        verify(endpointRepository).delete(endpoint);
    }

    @Test
    void deleteCurrentUserEndpoint_shouldThrowResourceNotFoundException() {
        when(endpointRepository.findByIdAndUserId(endpoint.getId(), user.getId())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> endpointService.deleteCurrentUserEndpoint(endpoint.getId()));
    }
}