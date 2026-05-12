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
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Measures ThreadLocal retained bytes in a reused worker with and without cleanup.
 */
/**
 * Measures retained ThreadLocal payloads to make pool-based cleanup hazards concrete.
 */
public final class Demo19_ThreadLocalMemoryRetention {
    private static final ThreadLocal<byte[]> SCRATCH = new ThreadLocal<>();

    // Important concurrency point: The second task reads the same worker ThreadLocal slot to show what remains retained.
    public record RetentionResult(int bytesPerTask, boolean cleanup, int retainedBytes) {
    }

    public static RetentionResult measureRetainedBytes(int bytesPerTask, boolean cleanup)
            throws ExecutionException, InterruptedException {
        if (bytesPerTask <= 0) {
            throw new IllegalArgumentException("bytesPerTask must be positive");
        }

        ExecutorService singleWorker = Executors.newSingleThreadExecutor();
        try {
            // Concurrency note: Submit schedules work asynchronously onto pool threads instead of running on caller thread.
            singleWorker.submit(() -> {
                SCRATCH.set(new byte[bytesPerTask]);
                if (cleanup) {
                    SCRATCH.remove();
                }
            }).get();

            // Concurrency note: Submit schedules work asynchronously onto pool threads instead of running on caller thread.
            int retainedBytes = singleWorker.submit(() -> {
                byte[] retained = SCRATCH.get();
                return retained == null ? 0 : retained.length;
            }).get();
            return new RetentionResult(bytesPerTask, cleanup, retainedBytes);
        } finally {
            singleWorker.shutdownNow();
        }
    }

    private Demo19_ThreadLocalMemoryRetention() {
    }
    // Expected behavior: Long-lived pooled threads retain ThreadLocal values until explicit cleanup occurs.
}