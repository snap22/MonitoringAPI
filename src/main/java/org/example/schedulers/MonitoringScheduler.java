package org.example.schedulers;

import lombok.RequiredArgsConstructor;
import org.example.services.ICheckerService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MonitoringScheduler {

    private final ICheckerService checkerService;

    // Executes every second
    @Scheduled(cron = "*/1 * * * * *")
    public void runMonitoring() {
        checkerService.checkEndpoints();
    }
}
