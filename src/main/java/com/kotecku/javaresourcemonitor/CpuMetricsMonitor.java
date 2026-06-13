package com.kotecku.javaresourcemonitor;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import oshi.hardware.CentralProcessor;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CpuMetricsMonitor {

    private final CentralProcessor centralProcessor;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.SECONDS)
    public void countCpuLoadOverall() {
        long[] prevTicks = centralProcessor.getSystemCpuLoadTicks();
        try {
            TimeUnit.SECONDS.sleep(1);
            double cpuLoad = centralProcessor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
            System.out.printf("CPU Load: %.0f%%%n", cpuLoad);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
