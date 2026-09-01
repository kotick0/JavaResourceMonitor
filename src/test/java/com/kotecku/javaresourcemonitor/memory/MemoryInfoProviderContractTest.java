package com.kotecku.javaresourcemonitor.memory;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import oshi.SystemInfo;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
abstract class MemoryInfoProviderContractTest {
    private final SystemInfo systemInfo = new SystemInfo();

    protected final long TOTAL_MEMORY_BYTES = systemInfo.getHardware().getMemory().getTotal();
    protected final long AVAILABLE_MEMORY_BYTES = systemInfo.getHardware().getMemory().getAvailable();

    protected abstract MemoryInfoProvider provider();

    @Test
    void getTotalMemoryBytesValueShouldNotBeNull() {
        assertThat(provider().getTotalMemoryBytes()).isNotNull();
    }

    @Test
    void getTotalMemoryBytesValueShouldEqualTotalMemory() {
        assertThat(provider().getTotalMemoryBytes()).isEqualTo(TOTAL_MEMORY_BYTES);
    }

    @Test
    void getAvailableMemoryBytesValueShouldNotBeNull() {
        assertThat(provider().getAvailableMemoryBytes()).isNotNull();
    }

    @Test
    void getAvailableMemoryBytesValueShouldEqualAvailableMemory() {
        assertThat(provider().getAvailableMemoryBytes()).isEqualTo(AVAILABLE_MEMORY_BYTES);
    }

    @Test
    void getFreeMemoryBytesValueShouldNotBeNull() {
        assertThat(provider().getFreeMemoryBytes()).isNotNull();
    }

    @Test
    void getFreeMemoryBytesShouldNotBeNegative() {
        assertThat(provider().getFreeMemoryBytes()).isNotNegative();
    }

    @Test
    void getCachedMemoryBytesValueShouldNotBeNull() {
        assertThat(provider().getCachedMemoryBytes()).isNotNull();
    }

    @Test
    void getCachedMemoryBytesShouldNotBeNegative() {
        assertThat(provider().getCachedMemoryBytes()).isNotNegative();
    }

    @Test
    void getUsedMemoryBytesValueShouldNotBeNull() {
        assertThat(provider().getUsedMemoryBytes()).isNotNull();
    }

    @Test
    void getUsedMemoryBytesShouldNotBeNegative() {
        assertThat(provider().getUsedMemoryBytes()).isNotNegative();
    }
}
