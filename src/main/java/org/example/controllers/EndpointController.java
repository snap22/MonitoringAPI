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

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/users/me/endpoints",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<EndpointResponse>> endpointsGet(
    ) {
        return ResponseEntity.ok(endpointService.getCurrentUserEndpoints());
    }


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
