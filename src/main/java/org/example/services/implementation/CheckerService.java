package org.example.services.implementation;

import lombok.extern.slf4j.Slf4j;
import org.example.entities.EndpointEntity;
import org.example.entities.MonitoringResultEntity;
import org.example.mappers.IMonitoringResultMapper;
import org.example.repositories.IEndpointRepository;
import org.example.repositories.IMonitoringResultRepository;
import org.example.services.ICheckerService;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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
    private final IMonitoringResultMapper monitoringResultMapper;

    public CheckerService(WebClient.Builder webClientBuilder, IEndpointRepository endpointRepository, IMonitoringResultRepository monitoringResultRepository, IMonitoringResultMapper monitoringResultMapper) {
        this.webClient = webClientBuilder.build();
        this.endpointRepository = endpointRepository;
        this.monitoringResultRepository = monitoringResultRepository;
        this.monitoringResultMapper = monitoringResultMapper;
        this.currentlyCheckedEndpoints = new ConcurrentSkipListSet<>();
    }

    @Override
    public void checkEndpoints() {
        List<EndpointEntity> endpoints = endpointRepository.findCheckableEndpoints(LocalDateTime.now());

        log.info("Checking {} endpoints", endpoints.size());

        for (EndpointEntity endpoint : endpoints) {
            if (isEndpointBeingChecked(endpoint))
                continue;

            checkEndpoint(endpoint);
        }
    }


    public boolean isEndpointBeingChecked(EndpointEntity endpoint) {
        return currentlyCheckedEndpoints.contains(endpoint.getId());
    }

    public void checkEndpoint(EndpointEntity endpoint) {
        currentlyCheckedEndpoints.add(endpoint.getId());

        log.info("Checking {}", endpoint.getUrl());

        webClient.get()
                .uri(endpoint.getUrl())
                .exchangeToMono(response -> response.bodyToMono(String.class)
                        .map(body -> monitoringResultMapper.toResultEntity(response, body, endpoint)))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(result -> {
                    monitoringResultRepository.save(result);

                    // Because of currentlyCheckedEndpoints structure the same endpoint cannot be checked simultaneously
                    endpoint.setLastCheckedAt(result.getCheckedAt());
                    endpointRepository.save(endpoint);

                    currentlyCheckedEndpoints.remove(endpoint.getId());
                })
                .onErrorResume(error -> {
                    log.error("Error while checking {} - {}", endpoint.getUrl(), error.getLocalizedMessage());
                    return Mono.empty();
                })
                .subscribe();
    }
}
