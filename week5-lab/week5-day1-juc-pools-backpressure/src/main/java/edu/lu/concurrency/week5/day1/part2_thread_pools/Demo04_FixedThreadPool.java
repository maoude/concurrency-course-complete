package edu.lu.concurrency.week5.day1.part2_thread_pools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Demo04_FixedThreadPool {
    public static int runTasks(int tasks) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        AtomicInteger completed = new AtomicInteger();
        for (int i = 0; i < tasks; i++) {
            pool.submit(completed::incrementAndGet);
        }
        pool.shutdown();
        pool.awaitTermination(2, TimeUnit.SECONDS);
        return completed.get();
    }
}
