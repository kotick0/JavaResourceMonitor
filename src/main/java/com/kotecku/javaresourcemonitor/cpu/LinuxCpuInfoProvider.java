package com.kotecku.javaresourcemonitor.cpu;

import com.kotecku.javaresourcemonitor.OnLinuxCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Component;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Sensors;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Conditional(OnLinuxCondition.class)
public class LinuxCpuInfoProvider implements CpuInfoProvider {

    private final CentralProcessor centralProcessor;
    private final Sensors sensors;

    @Override
    public double[] getCpuLoadPerCore() {
        long[][] prevTicks = centralProcessor.getProcessorCpuLoadTicks();
        try {
            TimeUnit.SECONDS.sleep(1);
            double[] loadPerCore = centralProcessor.getProcessorCpuLoadBetweenTicks(prevTicks);
            for (int i = 0; i < loadPerCore.length; i++) {
                loadPerCore[i] *= 100;
            }
            return loadPerCore;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double[] getCpuTemperaturePerCore() {
        LinkedHashMap<String, Double> cores = new LinkedHashMap<>();
        try (DirectoryStream<Path> hwmons = Files.newDirectoryStream(Paths.get("/sys/class/hwmon"), "hwmon*")) {
            for (Path hw : hwmons) {
                try (DirectoryStream<Path> labels = Files.newDirectoryStream(hw, "temp*_label")) {
                    for (Path label : labels) {
                        String name = Files.readString(label).trim();
                        Path input = hw.resolve(label.getFileName().toString().replace("_label", "_input"));
                        double celsius = Long.parseLong(Files.readString(input).trim()) / 1000.0;
                        cores.putIfAbsent(name, celsius);
                    }
                }
            }
            //TODO Do zweryfikowania na bare metal Linux
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return cores.values().stream().mapToDouble(Double::doubleValue).toArray();
    }

    @Override
    public double getCpuLoadPercent() {
        long[] prevTicks = centralProcessor.getSystemCpuLoadTicks();
        try {
            TimeUnit.SECONDS.sleep(1);
            return centralProcessor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public double getCpuTemperatureMax() {
        return sensors.getCpuTemperature();
    }
}
