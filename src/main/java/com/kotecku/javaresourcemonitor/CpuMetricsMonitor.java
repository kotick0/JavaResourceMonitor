package com.kotecku.javaresourcemonitor;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Sensors;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CpuMetricsMonitor {

    private final CentralProcessor centralProcessor;
    private final Sensors sensors;
    private final MacOsCpuTemperatureReader macOsCpuTemperatureReader;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.SECONDS)
    public double getCpuLoadOverall() {
        long[] prevTicks = centralProcessor.getSystemCpuLoadTicks();
        try {
            TimeUnit.SECONDS.sleep(1);
            return centralProcessor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.SECONDS)
    public double getCpuTemperatureOverall() {
        if (System.getProperty("os.arch").contains("aarch64") && System.getProperty("os.name").contains("Mac")) {
            return macOsCpuTemperatureReader.readOverallCpuTemperature();
        } else {
            return sensors.getCpuTemperature();
        }
    }
}
