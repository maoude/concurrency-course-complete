package edu.lu.concurrency.week5.day1.part2_thread_pools;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;

public class Demo06_RejectionPolicyDemo {
    public static boolean rejectsWhenWorkerAndQueueAreFull() throws InterruptedException {
        ThreadPoolExecutor executor = Demo05_BoundedExecutor.create(1, 1);
        try {
            executor.execute(() -> sleep(300));
            executor.execute(() -> sleep(300));
            executor.execute(() -> { });
            return false;
        } catch (RejectedExecutionException expected) {
            return true;
        } finally {
            executor.shutdownNow();
            executor.awaitTermination(1, java.util.concurrent.TimeUnit.SECONDS);
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
