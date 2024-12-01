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

    // Executes every second
    @Scheduled(cron = "*/1 * * * * *")
    public void runMonitoring() {
        checkerService.checkEndpoints();
    }

    // Execute every 15 min
    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void clearResults() {
        monitoringResultService.clearOutdatedResults();
    }
}
