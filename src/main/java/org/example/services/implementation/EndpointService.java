package org.example.services.implementation;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.EndpointRequest;
import org.example.dto.EndpointResponse;
import org.example.entities.EndpointEntity;
import org.example.exceptions.ResourceNotFoundException;
import org.example.mappers.IEndpointMapper;
import org.example.repositories.IEndpointRepository;
import org.example.services.IEndpointService;
import org.example.services.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EndpointService implements IEndpointService {
    private final IEndpointRepository endpointRepository;
    private final IEndpointMapper endpointMapper;
    private final IUserService userService;

    @Override
    public List<EndpointResponse> getCurrentUserEndpoints() {
        long currentUserId = userService.getCurrentUser().getId();
        List<EndpointEntity> endpoints = endpointRepository.findByUserId(currentUserId);

        return endpoints
                .stream()
                .map(endpointMapper::toResponse)
                .toList();
    }

    @Override
    public EndpointResponse getCurrentUserEndpoint(long endpointId) {
        EndpointEntity endpoint = findEndpointOrThrowError(endpointId);

        return endpointMapper.toResponse(endpoint);
    }

    @Override
    @Transactional
    public EndpointResponse createCurrentUserEndpoint(EndpointRequest request) {
        EndpointEntity endpoint = endpointMapper.toEntity(request);
        endpoint.setUser(userService.getCurrentUserEntity());
        endpointRepository.save(endpoint);

        return endpointMapper.toResponse(endpoint);
    }

    @Override
    @Transactional
    public EndpointResponse updateCurrentUserEndpoint(long endpointId, EndpointRequest request) {
        EndpointEntity endpoint = findEndpointOrThrowError(endpointId);
        endpointMapper.updateFromRequest(request, endpoint);
        endpointRepository.save(endpoint);

        return endpointMapper.toResponse(endpoint);
    }

    @Override
    @Transactional
    public void deleteCurrentUserEndpoint(long endpointId) {
        EndpointEntity endpoint = findEndpointOrThrowError(endpointId);
        endpointRepository.delete(endpoint);
    }

    private EndpointEntity findEndpointOrThrowError(long endpointId) {
        long currentUserId = userService.getCurrentUser().getId();

        return endpointRepository.findByIdAndUserId(endpointId, currentUserId)
                .stream().findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Endpoint for user not found"));
    }
}
