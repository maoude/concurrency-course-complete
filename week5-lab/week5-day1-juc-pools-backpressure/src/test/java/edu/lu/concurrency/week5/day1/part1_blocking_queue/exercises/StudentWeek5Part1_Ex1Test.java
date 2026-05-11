package edu.lu.concurrency.week5.day1.part1_blocking_queue.exercises;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

class StudentWeek5Part1_Ex1Test {
    @Test
    void storesAndReturnsItemsInFifoOrder() throws Exception {
        Ex1_BoundedBuffer<String> buffer = new Ex1_BoundedBuffer<>(2);

        buffer.put("a");
        buffer.put("b");

        assertEquals(2, buffer.size());
        assertEquals(0, buffer.remainingCapacity());
        assertEquals("a", buffer.take());
        assertEquals("b", buffer.take());
        assertEquals(0, buffer.size());
    }

    @Test
    @Timeout(2)
    void putBlocksUntilSpaceIsAvailable() throws Exception {
        Ex1_BoundedBuffer<Integer> buffer = new Ex1_BoundedBuffer<>(1);
        buffer.put(1);
        CountDownLatch attemptingPut = new CountDownLatch(1);
        AtomicBoolean completed = new AtomicBoolean(false);

        Thread producer = new Thread(() -> {
            try {
                attemptingPut.countDown();
                buffer.put(2);
                completed.set(true);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        assertTrue(attemptingPut.await(500, TimeUnit.MILLISECONDS));
        Thread.sleep(100);
        assertFalse(completed.get(), "put should block while the bounded buffer is full");

        assertEquals(1, buffer.take());
        producer.join(1_000);
        assertTrue(completed.get(), "put should complete after space is available");
        assertEquals(2, buffer.take());
    }
}
