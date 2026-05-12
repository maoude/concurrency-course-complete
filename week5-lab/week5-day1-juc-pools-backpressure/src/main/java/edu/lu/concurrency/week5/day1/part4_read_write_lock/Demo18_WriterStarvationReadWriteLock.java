/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Evidence demo for read-write lock liveness: readers scale, but writers can wait under read pressure.
 */
/**
 * Measures writer delay under read pressure to discuss fairness and starvation risks.
 */
public final class Demo18_WriterStarvationReadWriteLock {

    // Important concurrency point: Fairness changes admission order and can reduce writer starvation under read pressure.
    public record Observation(boolean fair, boolean writerCompleted, long writerWaitMillis, String note) {
    }

    public static Observation observeWriterDelay(boolean fair) throws InterruptedException {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock(fair);
        AtomicBoolean keepReading = new AtomicBoolean(true);
        // Concurrency note: Latch coordinates thread start/finish points to make concurrency behavior testable.
        CountDownLatch readersStarted = new CountDownLatch(3);
        // Concurrency note: Latch coordinates thread start/finish points to make concurrency behavior testable.
        CountDownLatch writerDone = new CountDownLatch(1);
        ExecutorService pool = Executors.newFixedThreadPool(4);
        List<Runnable> readers = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            readers.add(() -> {
                // Concurrency note: countDown() signals one completed participant toward releasing awaiters.
                readersStarted.countDown();
                while (keepReading.get()) {
                    // Concurrency note: Read lock allows concurrent readers while excluding writers for read-mostly workloads.
                    lock.readLock().lock();
                    try {
                        sleep(2);
                    } finally {
                        // Concurrency note: Unlock in finally to avoid deadlocks and leaked lock ownership after exceptions.
                        lock.readLock().unlock();
                    }
                }
            });
        }

        try {
            readers.forEach(pool::submit);
            readersStarted.await(1, TimeUnit.SECONDS);

            long start = System.nanoTime();
            // Concurrency note: Submit schedules work asynchronously onto pool threads instead of running on caller thread.
            pool.submit(() -> {
                // Concurrency note: Write lock provides exclusive mutation to preserve map/cache invariants.
                lock.writeLock().lock();
                try {
                    // Concurrency note: countDown() signals one completed participant toward releasing awaiters.
                    writerDone.countDown();
                } finally {
                    // Concurrency note: Unlock in finally to avoid deadlocks and leaked lock ownership after exceptions.
                    lock.writeLock().unlock();
                }
            });

            sleep(40);
            keepReading.set(false);
            boolean completed = writerDone.await(1, TimeUnit.SECONDS);
            long waitMillis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - start);
            String note = fair
                    ? "Fair locks reduce barging but can cost throughput."
                    : "Unfair locks are faster on average but can delay writers under read pressure.";
            return new Observation(fair, completed, waitMillis, note);
        } finally {
            keepReading.set(false);
            pool.shutdownNow();
            // Concurrency note: awaitTermination() blocks until workers finish or timeout, enabling controlled shutdown.
            pool.awaitTermination(1, TimeUnit.SECONDS);
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private Demo18_WriterStarvationReadWriteLock() {
    }
    // Expected behavior: Heavy reader traffic can delay writer progress, exposing starvation risk depending on fairness.
}