package org.example.services;

import org.example.entities.EndpointEntity;

public interface ICheckerService {
    /**
     * Checks all endpoints that are due for checking.
     */
    void checkEndpoints();

    /**
     * Determines if an endpoint is currently being checked.
     *
     * @param endpoint the endpoint to check
     * @return true if the endpoint is being checked, false otherwise
     */
    boolean isEndpointBeingChecked(EndpointEntity endpoint);

    /**
     * Checks a single endpoint.
     *
     * @param endpoint the endpoint to check
     */
    void checkEndpoint(EndpointEntity endpoint);
}
