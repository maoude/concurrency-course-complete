package edu.lu.concurrency.week5.day1.part4_read_write_lock.exercises;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.*;

class StudentWeek5Part4_Ex4Test {
    @Test
    void storesReadsAndSnapshotsValues() {
        Ex4_ReadMostlyCache<String, Integer> cache = new Ex4_ReadMostlyCache<>();

        cache.put("a", 1);
        cache.put("b", 2);

        assertEquals(1, cache.get("a"));
        assertEquals(2, cache.get("b"));
        assertEquals(2, cache.size());

        Map<String, Integer> snapshot = cache.snapshot();
        assertEquals(Map.of("a", 1, "b", 2), snapshot);
        assertThrows(UnsupportedOperationException.class, () -> snapshot.put("c", 3));
    }

    @Test
    @Timeout(4)
    void concurrentReadersAndWritersKeepConsistentSize() throws Exception {
        Ex4_ReadMostlyCache<Integer, Integer> cache = new Ex4_ReadMostlyCache<>();
        int threads = 4;
        int iterations = 500;
        CountDownLatch start = new CountDownLatch(1);
        Thread[] workers = new Thread[threads];

        for (int t = 0; t < threads; t++) {
            final int offset = t * iterations;
            workers[t] = new Thread(() -> {
                try {
                    start.await();
                    for (int i = 0; i < iterations; i++) {
                        cache.put(offset + i, i);
                        assertNotNull(cache.snapshot());
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            workers[t].start();
        }

        start.countDown();
        for (Thread worker : workers) {
            worker.join();
        }

        assertEquals(threads * iterations, cache.size());
    }
}
