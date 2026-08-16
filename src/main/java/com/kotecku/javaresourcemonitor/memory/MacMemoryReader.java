package com.kotecku.javaresourcemonitor.memory;

import com.sun.jna.Memory;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.ptr.LongByReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import oshi.hardware.GlobalMemory;

@Component
@RequiredArgsConstructor
public class MacMemoryReader {
    private final GlobalMemory memory;

    private SystemB.VMStatistics64 callVmStatistics64() {
        SystemB.VMStatistics64 stats = new SystemB.VMStatistics64();
        IntByReference count = new IntByReference(stats.size() / 4);

        int result = SystemB.INSTANCE.host_statistics64(
                SystemB.INSTANCE.mach_host_self(),
                SystemB.HOST_VM_INFO64,
                stats,
                count
        );

        if (result != SystemB.KERN_SUCCESS) {
            throw new IllegalStateException("host_statistics64(HOST_VM_INFO64) returned an error, code: " + result);
        }
        return stats;
    }

    private long readPageSizeFromSysctl() {
        Memory sizeBuffer = new Memory(8);
        LongByReference sizeLength = new LongByReference(8L);

        int result = SystemB.INSTANCE.sysctlbyname(
                "hw.pagesize",
                sizeBuffer,
                sizeLength,
                null,
                0L
        );

        if (result != SystemB.KERN_SUCCESS) {
            throw new IllegalStateException(
                    "sysctlbyname(\"hw.pagesize\") returned an error, code: " + result);
        }

        return sizeBuffer.getLong(0);
    }

    public double getAvailableMemoryBytes() {
        SystemB.VMStatistics64 stats = callVmStatistics64();

        long pageSize = readPageSizeFromSysctl();
        long used = (stats.active_count + (long) stats.wire_count) * pageSize;

        return (memory.getTotal() - used);
    }

    public double getTotalMemoryBytes() {
        return memory.getTotal();
    }

    public double getFreeMemoryBytes() {
        SystemB.VMStatistics64 stats = callVmStatistics64();
        return (stats.free_count * readPageSizeFromSysctl());

    }

    public double getCachedMemoryBytes() {
        SystemB.VMStatistics64 stats = callVmStatistics64();
        return (stats.external_page_count * readPageSizeFromSysctl());
    }

    public double getUsedMemoryBytes() {
        SystemB.VMStatistics64 stats = callVmStatistics64();
        return (stats.active_count + (long) stats.wire_count) * readPageSizeFromSysctl();
    }
}