package com.kotecku.javaresourcemonitor;

import com.kotecku.javaresourcemonitor.cpu.CpuMetricsReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CpuMetricsReaderTest {
    @Autowired
    private CpuMetricsReader cpuMetricsReader;

    @Test
    public void getCpuLoadOverallTest() {
        double cpuLoad = cpuMetricsReader.getCpuLoadOverall();
        System.out.printf("CPU Load: %.0f%%%n", cpuLoad);
    }

    @Test
    public void getCpuTemperatureOverallTest() {
        double cpuTemperature = cpuMetricsReader.getCpuTemperatureOverall();
        System.out.printf("CPU Temperature: %.0f C%n", cpuTemperature);
    }

    @Test
    public void getCpuLoadPerCoreTest() {
        double[] cpuLoads = cpuMetricsReader.getCpuLoadPerCore();
        for (int i = 0; i < cpuLoads.length; i++) {
            System.out.printf("CPU Core %d Load: %.0f%%%n", i, cpuLoads[i]);
        }
    }

    @Test
    public void getCpuTemperaturePerCoreTest() {
        double[] cpuTemperatures = cpuMetricsReader.getCpuTemperaturePerCore();
        for (int i = 0; i < cpuTemperatures.length; i++) {
            System.out.printf("CPU Core %d Temperature: %.0f C%n", i, cpuTemperatures[i]);
        }
    }
}
