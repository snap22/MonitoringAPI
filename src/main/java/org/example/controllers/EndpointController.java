package org.example.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.EndpointRequest;
import org.example.dto.EndpointResponse;
import org.example.dto.MonitoringResultResponse;
import org.example.services.IEndpointService;
import org.example.services.IMonitoringResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EndpointController {

    private final IEndpointService endpointService;

    /**
     * Retrieves the list of endpoints for the current user.
     *
     * @return a ResponseEntity containing the list of EndpointResponse objects and HTTP status 200 (OK)
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/users/me/endpoints",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<EndpointResponse>> endpointsGet(
    ) {
        return ResponseEntity.ok(endpointService.getCurrentUserEndpoints());
    }


    /**
     * Retrieves a specific endpoint for the current user.
     *
     * @param endpointId the ID of the endpoint to retrieve
     * @return a ResponseEntity containing the EndpointResponse object and HTTP status 200 (OK)
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/users/me/endpoints/{endpointId}",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EndpointResponse> endpointGet(
            @PathVariable("endpointId") long endpointId
    ) {
        return ResponseEntity.ok(endpointService.getCurrentUserEndpoint(endpointId));
    }


    /**
     * Creates a new endpoint for the current user.
     *
     * @param request the EndpointRequest object containing the endpoint details
     * @return a ResponseEntity containing the created EndpointResponse and HTTP status 201 (Created)
     */
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/users/me/endpoints",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EndpointResponse> endpointPost(
            @Valid @RequestBody EndpointRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(endpointService.createCurrentUserEndpoint(request));
    }


    /**
     * Updates an existing endpoint for the current user.
     *
     * @param endpointId the ID of the endpoint to update
     * @param request the EndpointRequest object containing the updated endpoint details
     * @return a ResponseEntity containing the updated EndpointResponse and HTTP status 200 (OK)
     */
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/users/me/endpoints/{endpointId}",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<EndpointResponse> endpointPost(
            @PathVariable("endpointId") long endpointId,
            @Valid @RequestBody EndpointRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(endpointService.updateCurrentUserEndpoint(endpointId, request));
    }


    /**
     * Deletes an existing endpoint for the current user.
     *
     * @param endpointId the ID of the endpoint to delete
     * @return a ResponseEntity with HTTP status 204 (No Content)
     */
    @RequestMapping(
            method = RequestMethod.DELETE,
            value = "/users/me/endpoints/{endpointId}"
    )
    public ResponseEntity<Void> endpointPost(
            @PathVariable("endpointId") long endpointId
    ) {
        endpointService.deleteCurrentUserEndpoint(endpointId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
