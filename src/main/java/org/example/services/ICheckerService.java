package org.example.services;

import org.example.entities.EndpointEntity;

public interface ICheckerService {
    void checkEndpoints();

    boolean isEndpointBeingChecked(EndpointEntity endpoint);

    void checkEndpoint(EndpointEntity endpoint);
}
