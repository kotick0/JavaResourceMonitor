package com.kotecku.javaresourcemonitor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CpuMetricsMonitorTest {
    @Autowired
    private CpuMetricsMonitor cpuMetricsMonitor;

    @Test
    public void getCpuLoadOverallTest() {
        double cpuLoad = cpuMetricsMonitor.getCpuLoadOverall();
        System.out.printf("CPU Load: %.0f%%%n", cpuLoad);
    }

    @Test
    public void getCpuTemperatureOverallTest() {
        double cpuTemperature = cpuMetricsMonitor.getCpuTemperatureOverall();
        System.out.printf("CPU Temperature: %.0f C%n", cpuTemperature);
    }
}
