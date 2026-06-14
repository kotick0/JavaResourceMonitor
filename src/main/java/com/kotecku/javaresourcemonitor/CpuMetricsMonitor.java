package com.kotecku.javaresourcemonitor;

import com.profesorfalken.jsensors.JSensors;
import com.profesorfalken.jsensors.model.components.Components;
import com.profesorfalken.jsensors.model.components.Cpu;
import com.sun.tools.javac.Main;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Sensors;
import oshi.software.os.OperatingSystem;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class CpuMetricsMonitor { //TODO pozaminieac na zwracane dane zamiast print

    private final CentralProcessor centralProcessor;
    private final OperatingSystem operatingSystem;
    private final Sensors sensors;

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.SECONDS)
    public void getCpuLoadOverall() {
        long[] prevTicks = centralProcessor.getSystemCpuLoadTicks();
        try {
            TimeUnit.SECONDS.sleep(1);
            double cpuLoad = centralProcessor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
            System.out.printf("CPU Load: %.0f%%%n", cpuLoad);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.SECONDS)
    public void getCpuTemperatureOverall() {
        if (operatingSystem.toString().contains("Windows")) {
            String[] groups = new com.sun.security.auth.module.NTSystem().getGroupIDs();
            boolean isAdmin = Arrays.asList(groups).contains("S-1-5-32-544");

            if (!isAdmin) {
                relaunchAsAdmin();
                return; // proces się zaraz zamknie
            }

            Components components = JSensors.get.components();
            List<Cpu> cpus = components.cpus;
            if (cpus != null && !cpus.isEmpty()) {
                cpus.getFirst().sensors.temperatures.stream()
                        .filter(t -> t.name.contains("Package") || t.name.contains("CPU"))
                        .findFirst()
                        .ifPresent(t -> System.out.println("CPU temp: " + t.value + " °C"));
            }
        } else {
            double cpuTemperature = sensors.getCpuTemperature();
            System.out.printf("CPU Temperature: %.0f C%n", cpuTemperature);
        }
    }

    private void relaunchAsAdmin() {
        try {
            String jarPath = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI())
                    .getAbsolutePath();
            String psCommand = "Start-Process -FilePath 'javaw' -ArgumentList '-jar \"%s\"' -Verb RunAs"
                    .formatted(jarPath);
            new ProcessBuilder("powershell.exe", "-NoProfile", "-Command", psCommand).start();
            System.exit(0);
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
