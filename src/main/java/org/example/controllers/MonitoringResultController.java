package org.example.controllers;

import lombok.RequiredArgsConstructor;
import org.example.dto.MonitoringResultResponse;
import org.example.dto.UserResponse;
import org.example.services.IMonitoringResultService;
import org.example.services.IUserService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MonitoringResultController {

    private final IMonitoringResultService monitoringResultService;

    /**
     * Retrieves the list of monitoring results for a specific endpoint of the current user.
     *
     * @param endpointId the ID of the endpoint to retrieve results for
     * @return a ResponseEntity containing the list of MonitoringResultResponse objects and HTTP status 200 (OK)
     */
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/users/me/endpoints/{endpointId}/results",
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<List<MonitoringResultResponse>> resultsGet(
            @PathVariable("endpointId") long endpointId
    ) {
        return ResponseEntity.ok(monitoringResultService.getResultsForCurrentUserEndpoint(endpointId));
    }


}
