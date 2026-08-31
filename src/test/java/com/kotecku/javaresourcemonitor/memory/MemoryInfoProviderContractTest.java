package com.kotecku.javaresourcemonitor.memory;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


abstract class MemoryInfoProviderContractTest {
    protected static final long TOTAL_MEMORY_BYTES = 0; //TODO Do zmiany

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
        assertThat(provider().getAvailableMemoryBytes()).isEqualTo(Runtime.getRuntime().freeMemory());
    }




}
