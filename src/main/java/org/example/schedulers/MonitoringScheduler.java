package org.example.schedulers;

import lombok.RequiredArgsConstructor;
import org.example.services.ICheckerService;
import org.example.services.IMonitoringResultService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonitoringScheduler {

    private final ICheckerService checkerService;
    private final IMonitoringResultService monitoringResultService;

    /**
     * Runs the monitoring process to check endpoints.
     * This method is scheduled to execute every second.
     */
    @Scheduled(cron = "*/1 * * * * *")
    public void runMonitoring() {
        checkerService.checkEndpoints();
    }

    /**
     * Clears outdated monitoring results.
     * This method is scheduled to execute every 5 minutes.
     */
    @Scheduled(fixedRate = 5 * 60 * 1000)
    public void clearResults() {
        monitoringResultService.clearOutdatedResults();
    }
}
