package edu.lu.concurrency.week7.day1.part5_nio_counterpoint.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentWeek7Part5_Ex5Test {

    @Test
    void explainsNioCounterpoint() {
        Ex5_NioCounterpoint.Comparison comparison = Ex5_NioCounterpoint.compare(500);

        assertEquals(500, comparison.blockingWorkerThreads());
        assertEquals(1, comparison.nioSelectorThreads());
        assertTrue(comparison.explanation().toLowerCase().contains("async"));
        assertTrue(comparison.explanation().toLowerCase().contains("threads"));
    }
}
