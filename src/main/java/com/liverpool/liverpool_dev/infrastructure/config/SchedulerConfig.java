package com.liverpool.liverpool_dev.infrastructure.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.liverpool.liverpool_dev.domain.port.in.SyncDataUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfig {
    private final SyncDataUseCase syncDataUseCase;

    @EventListener(ApplicationReadyEvent.class)
    public void syncOnStartup() {
        log.info("Application ready - triggering initial data synchronization");
        syncDataUseCase.syncAll();
    }

    @Scheduled(fixedRateString = "${scheduling.sync.rate:18000}")
    public void scheduledSync() {
        log.info("Running scheduled data synchronization");
        syncDataUseCase.syncAll();
    }
}
