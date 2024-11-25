package org.example.services;

import jakarta.transaction.Transactional;
import org.example.dto.EndpointRequest;
import org.example.dto.EndpointResponse;

import java.util.List;

public interface IEndpointService {
    List<EndpointResponse> getCurrentUserEndpoints();

    EndpointResponse getCurrentUserEndpoint(long endpointId);

    @Transactional
    EndpointResponse createCurrentUserEndpoint(EndpointRequest request);

    @Transactional
    EndpointResponse updateCurrentUserEndpoint(long endpointId, EndpointRequest request);

    @Transactional
    void deleteCurrentUserEndpoint(long endpointId);
}
