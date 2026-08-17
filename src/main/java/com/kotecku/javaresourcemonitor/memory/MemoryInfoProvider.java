package com.kotecku.javaresourcemonitor.memory;

public interface MemoryInfoProvider {
    long getTotalMemoryBytes();
    long getAvailableMemoryBytes();
    long getFreeMemoryBytes();
    long getCachedMemoryBytes();
    long getUsedMemoryBytes();

}
