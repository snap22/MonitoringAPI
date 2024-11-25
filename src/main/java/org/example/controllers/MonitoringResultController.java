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