package edu.lu.concurrency.week5.day1.part2_thread_pools;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrates the fundamental Callable/Future pattern.
 * For convenience when submitting multiple callables, see ExecutorService.invokeAll().
 */
public class Demo14_CallableAndFuture {
    public static int computeSquare(int input) throws Exception {
        ExecutorService pool = Executors.newSingleThreadExecutor();
        try {
            Callable<Integer> work = () -> input * input;
            Future<Integer> result = pool.submit(work);
            return result.get(1, TimeUnit.SECONDS);
        } finally {
            pool.shutdownNow();
        }
    }
}
