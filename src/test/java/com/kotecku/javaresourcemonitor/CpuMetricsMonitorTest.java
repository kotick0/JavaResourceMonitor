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

    @Test
    public void getCpuLoadPerCoreTest() {
        double[] cpuLoads = cpuMetricsMonitor.getCpuLoadPerCore();
        for (int i = 0; i < cpuLoads.length; i++) {
            System.out.printf("CPU Core %d Load: %.0f%%%n", i, cpuLoads[i]);
        }
    }

    @Test
    public void getCpuTemperaturePerCoreTest() {
        double[] cpuTemperatures = cpuMetricsMonitor.getCpuTemperaturePerCore();
        for (int i = 0; i < cpuTemperatures.length; i++) {
            System.out.printf("CPU Core %d Temperature: %.0f C%n", i, cpuTemperatures[i]);
        }
    }
}
