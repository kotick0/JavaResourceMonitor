package com.kotecku.javaresourcemonitor.cpu;

public interface CpuInfoProvider {
    double[] getCpuLoadPerCore();
    double[] getCpuTemperaturePerCore();
    double getCpuLoadPercent();
    double getCpuTemperatureMax();
}
