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
        cpuMetricsMonitor.getCpuLoadOverall();
    }

    @Test
    public void getCpuTemperatureOverallTest() {
        cpuMetricsMonitor.getCpuTemperatureOverall();
    }
}
