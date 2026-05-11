package edu.lu.concurrency.week5.day1.part2_thread_pools.exercises;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class StudentWeek5Part2_Ex2Test {
    @Test
    @Timeout(3)
    void acceptedTasksRunToCompletion() throws Exception {
        try (Ex2_BoundedExecutor executor = new Ex2_BoundedExecutor(2, 2)) {
            AtomicInteger completed = new AtomicInteger();

            assertTrue(executor.submit(completed::incrementAndGet));
            assertTrue(executor.submit(completed::incrementAndGet));

            executor.shutdownAndAwait(2, TimeUnit.SECONDS);
            assertEquals(2, completed.get());
        }
    }

    @Test
    @Timeout(3)
    void rejectsWhenWorkersAndQueueAreFull() throws Exception {
        try (Ex2_BoundedExecutor executor = new Ex2_BoundedExecutor(1, 1)) {
            CountDownLatch release = new CountDownLatch(1);
            Runnable blockingTask = () -> {
                try {
                    release.await();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            };

            assertTrue(executor.submit(blockingTask));
            assertTrue(executor.submit(blockingTask));
            assertFalse(executor.submit(() -> { }), "third task should be rejected when worker and queue are full");

            release.countDown();
            executor.shutdownAndAwait(2, TimeUnit.SECONDS);
        }
    }
}
