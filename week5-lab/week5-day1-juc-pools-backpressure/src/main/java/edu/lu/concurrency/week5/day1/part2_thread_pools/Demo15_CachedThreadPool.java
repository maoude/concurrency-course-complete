package edu.lu.concurrency.week5.day1.part2_thread_pools;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Demo15_CachedThreadPool {
    public static int workerNamesUsed(int tasks) throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        Set<String> workerNames = Collections.synchronizedSet(new HashSet<>());
        CountDownLatch done = new CountDownLatch(tasks);
        try {
            for (int i = 0; i < tasks; i++) {
                pool.submit(() -> {
                    workerNames.add(Thread.currentThread().getName());
                    sleep(50);
                    done.countDown();
                });
            }
            done.await(2, TimeUnit.SECONDS);
            return workerNames.size();
        } finally {
            pool.shutdownNow();
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
