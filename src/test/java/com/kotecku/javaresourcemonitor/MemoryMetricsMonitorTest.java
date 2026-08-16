package com.kotecku.javaresourcemonitor;

import com.kotecku.javaresourcemonitor.memory.MacMemoryReader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemoryMetricsMonitorTest {

    @Autowired
    private MacMemoryReader macMemoryReader;

    @Test
    public void getMemoryAvailable() {
        double memoryAvailable = macMemoryReader.getAvailableMemoryBytes();
        System.out.println("Available Memory: " +  memoryAvailable);
    }
    @Test
    public void getMemoryTotal() {
        double memoryTotal = macMemoryReader.getTotalMemoryBytes();
        System.out.println("Total Memory: " + memoryTotal);
    }

    @Test
    public void getMemoryFree() {
        double memoryFree = macMemoryReader.getFreeMemoryBytes();
        System.out.println("Free Memory: " + memoryFree);
    }

    @Test
    public void getMemoryCached() {
        double memoryCached = macMemoryReader.getCachedMemoryBytes();
        System.out.println("Cached Memory: " + memoryCached);
    }

    @Test
    public void getMemoryUsed() {
        double memoryUsed = macMemoryReader.getUsedMemoryBytes();
        System.out.println("Used Memory: " + memoryUsed);
    }

}
