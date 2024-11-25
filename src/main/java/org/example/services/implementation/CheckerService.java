package org.example.services.implementation;

import lombok.extern.slf4j.Slf4j;
import org.example.entities.EndpointEntity;
import org.example.entities.MonitoringResultEntity;
import org.example.repositories.IEndpointRepository;
import org.example.repositories.IMonitoringResultRepository;
import org.example.services.ICheckerService;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Service
public class CheckerService implements ICheckerService {
    private final WebClient webClient;
    private final IEndpointRepository endpointRepository;
    private final IMonitoringResultRepository monitoringResultRepository;
    private final ConcurrentSkipListSet<Long> currentlyCheckedEndpoints;

    public CheckerService(WebClient.Builder webClientBuilder, IEndpointRepository endpointRepository, IMonitoringResultRepository monitoringResultRepository) {
        this.webClient = webClientBuilder.build();
        this.endpointRepository = endpointRepository;
        this.monitoringResultRepository = monitoringResultRepository;
        this.currentlyCheckedEndpoints = new ConcurrentSkipListSet<>();
    }

    @Override
    public void checkEndpoints() {
        List<EndpointEntity> endpoints = endpointRepository.findAll();
        log.info("Checking {} endpoints", endpoints.size());

        for (EndpointEntity endpoint : endpoints) {
            if (!shouldCheck(endpoint))
                continue;

            if (isEndpointBeingChecked(endpoint))
                continue;

            checkEndpoint(endpoint);

        }
    }

    public boolean shouldCheck(EndpointEntity endpoint) {
        if (endpoint.getLastCheckedAt() == null)
            return true;

        // now >= last checked + interval
        LocalDateTime now = LocalDateTime.now();
        return !now.isBefore(
                endpoint.getLastCheckedAt().plusSeconds(endpoint.getCheckInterval())
        );
    }

    public boolean isEndpointBeingChecked(EndpointEntity endpoint) {
        return currentlyCheckedEndpoints.contains(endpoint.getId());
    }

    public void checkEndpoint(EndpointEntity endpoint) {
        currentlyCheckedEndpoints.add(endpoint.getId());

        log.info("Checking {}", endpoint.getUrl());

        webClient.get()
                .uri(endpoint.getUrl())
                .retrieve()
                .onStatus(
                        HttpStatusCode::isError,
                        clientResponse -> clientResponse.bodyToMono(String.class).flatMap(errorBody -> {
                            // Log and save the error status and response
                            int statusCode = clientResponse.statusCode().value();

                            MonitoringResultEntity result = new MonitoringResultEntity();
                            result.setEndpoint(endpoint);
                            result.setStatusCode(String.valueOf(statusCode));
                            result.setPayload(errorBody); // Store error response body

                            monitoringResultRepository.save(result);

                            log.warn("HTTP error [{}] at {}: {}", statusCode, endpoint.getUrl(), errorBody);

                            // Pass the error downstream for further handling if needed
                            return Mono.error(new RuntimeException("HTTP error: " + statusCode));
                        })
                )
                .toEntity(String.class)
                .timeout(Duration.ofSeconds(endpoint.getCheckInterval())) // Handle timeout
                .doOnTerminate(() -> {
                    // Remove from the set on completion
                    currentlyCheckedEndpoints.remove(endpoint.getId());
                })
                .subscribe(response -> {
                    // Success: Store response in DB
                    int statusCode = response.getStatusCode().value();

                    MonitoringResultEntity result = new MonitoringResultEntity();
                    result.setEndpoint(endpoint);
                    result.setStatusCode(String.valueOf(statusCode));
                    result.setPayload(response.getBody());

                    monitoringResultRepository.save(result);

                    log.info("Checked [{}] {}", statusCode, endpoint.getUrl());
                }, error -> {
                    // Exception (e.g., timeout, DNS issue): Log and store as exception
                    MonitoringResultEntity result = new MonitoringResultEntity();
                    result.setEndpoint(endpoint);
                    result.setStatusCode(null);
                    result.setPayload(error.getMessage());

                    monitoringResultRepository.save(result);

                    log.error("Exception while checking {} - {}", endpoint.getUrl(), error.getLocalizedMessage());
                });
    }

}
