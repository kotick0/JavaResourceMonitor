package com.kotecku.javaresourcemonitor.memory;

import org.springframework.stereotype.Component;

@Component
public class LinuxMemoryInfoProvider implements MemoryInfoProvider { // TODO DO zmiany zupelnie!!!
    @Override
    public long getTotalMemoryBytes() {
        return Long.parseLong(null);
    }

    @Override
    public long getAvailableMemoryBytes() {
        return Long.parseLong(null);
    }

    @Override
    public long getFreeMemoryBytes() {
        return Long.parseLong(null);
    }

    @Override
    public long getCachedMemoryBytes() {
        return Long.parseLong(null);
    }

    @Override
    public long getUsedMemoryBytes() {
        return Long.parseLong(null);
    }
}
