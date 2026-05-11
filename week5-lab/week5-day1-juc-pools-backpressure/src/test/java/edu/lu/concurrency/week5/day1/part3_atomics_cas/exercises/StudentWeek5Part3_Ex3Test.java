package edu.lu.concurrency.week5.day1.part3_atomics_cas.exercises;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentWeek5Part3_Ex3Test {
    @Test
    void emptyMetricsHaveZeroRate() {
        Ex3_AtomicMetrics metrics = new Ex3_AtomicMetrics();

        assertEquals(0, metrics.successes());
        assertEquals(0, metrics.failures());
        assertEquals(0, metrics.total());
        assertEquals(0.0, metrics.successRatePercent(), 0.0001);
    }

    @Test
    @Timeout(4)
    void concurrentUpdatesAreNotLost() throws Exception {
        Ex3_AtomicMetrics metrics = new Ex3_AtomicMetrics();
        int threads = 8;
        int iterations = 2_000;
        List<Thread> workers = new ArrayList<>();

        for (int t = 0; t < threads; t++) {
            Thread worker = new Thread(() -> {
                for (int i = 0; i < iterations; i++) {
                    metrics.recordSuccess();
                    if (i % 4 == 0) {
                        metrics.recordFailure();
                    }
                }
            });
            workers.add(worker);
            worker.start();
        }

        for (Thread worker : workers) {
            worker.join();
        }

        assertEquals(threads * iterations, metrics.successes());
        assertEquals(threads * (iterations / 4), metrics.failures());
        assertEquals(metrics.successes() + metrics.failures(), metrics.total());
        assertEquals(80.0, metrics.successRatePercent(), 0.0001);
    }
}
