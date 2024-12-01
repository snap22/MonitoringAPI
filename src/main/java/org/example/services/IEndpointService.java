package org.example.services;

import jakarta.transaction.Transactional;
import org.example.dto.EndpointRequest;
import org.example.dto.EndpointResponse;
import org.example.exceptions.ResourceNotFoundException;

import java.util.List;

public interface IEndpointService {
    /**
     * Retrieves the list of endpoints for the current user.
     *
     * @return a list of EndpointResponse objects representing the user's endpoints
     */
    List<EndpointResponse> getCurrentUserEndpoints();

    /**
     * Retrieves a specific endpoint for the current user.
     *
     * @param endpointId the ID of the endpoint to retrieve
     * @return an EndpointResponse object representing the endpoint
     * @throws ResourceNotFoundException if the endpoint is not found
     */
    EndpointResponse getCurrentUserEndpoint(long endpointId);

    /**
     * Creates a new endpoint for the current user.
     *
     * @param request the request object containing the endpoint details
     * @return an EndpointResponse object representing the created endpoint
     */
    @Transactional
    EndpointResponse createCurrentUserEndpoint(EndpointRequest request);

    /**
     * Updates an existing endpoint for the current user.
     *
     * @param endpointId the ID of the endpoint to update
     * @param request the request object containing the updated endpoint details
     * @return an EndpointResponse object representing the updated endpoint
     * @throws ResourceNotFoundException if the endpoint is not found
     */
    @Transactional
    EndpointResponse updateCurrentUserEndpoint(long endpointId, EndpointRequest request);

    /**
     * Deletes an existing endpoint for the current user.
     *
     * @param endpointId the ID of the endpoint to delete
     * @throws ResourceNotFoundException if the endpoint is not found
     */
    @Transactional
    void deleteCurrentUserEndpoint(long endpointId);
}
