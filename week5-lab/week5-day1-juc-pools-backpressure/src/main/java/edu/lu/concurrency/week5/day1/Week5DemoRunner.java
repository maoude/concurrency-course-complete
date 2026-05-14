/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1;

import edu.lu.concurrency.week5.day1.part1_blocking_queue.Demo01_UnboundedQueueRisk;
import edu.lu.concurrency.week5.day1.part1_blocking_queue.Demo02_BoundedBlockingQueue;
import edu.lu.concurrency.week5.day1.part1_blocking_queue.Demo03_ProducerConsumerBackpressure;
import edu.lu.concurrency.week5.day1.part2_thread_pools.Demo04_FixedThreadPool;
import edu.lu.concurrency.week5.day1.part2_thread_pools.Demo06_RejectionPolicyDemo;
import edu.lu.concurrency.week5.day1.part2_thread_pools.Demo14_CallableAndFuture;
import edu.lu.concurrency.week5.day1.part2_thread_pools.Demo15_CachedThreadPool;
import edu.lu.concurrency.week5.day1.part2_thread_pools.Demo16_FixedVsCachedThroughput;
import edu.lu.concurrency.week5.day1.part2_thread_pools.Demo17_HandRolledExecutorContrast;
import edu.lu.concurrency.week5.day1.part3_atomics_cas.Demo07_SynchronizedCounter;
import edu.lu.concurrency.week5.day1.part3_atomics_cas.Demo08_AtomicCounter;
import edu.lu.concurrency.week5.day1.part3_atomics_cas.Demo09_CASRetryLoop;
import edu.lu.concurrency.week5.day1.part4_read_write_lock.Demo10_SynchronizedReadMostlyCache;
import edu.lu.concurrency.week5.day1.part4_read_write_lock.Demo11_ReadWriteLockCache;
import edu.lu.concurrency.week5.day1.part4_read_write_lock.Demo18_WriterStarvationReadWriteLock;
import edu.lu.concurrency.week5.day1.part5_thread_local.Demo12_RequestContextThreadLocal;
import edu.lu.concurrency.week5.day1.part5_thread_local.Demo13_ThreadLocalLeakInPool;
import edu.lu.concurrency.week5.day1.part5_thread_local.Demo19_ThreadLocalMemoryRetention;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Command-line runner for Week 5 demos so students can run one demo or the full sequence.
 */
public final class Week5DemoRunner {
    private static final Map<String, DemoAction> DEMOS = new LinkedHashMap<>();

    static {
        DEMOS.put("01", Week5DemoRunner::demo01);
        DEMOS.put("02", Week5DemoRunner::demo02);
        DEMOS.put("03", Week5DemoRunner::demo03);
        DEMOS.put("04", Week5DemoRunner::demo04);
        DEMOS.put("06", Week5DemoRunner::demo06);
        DEMOS.put("07", Week5DemoRunner::demo07);
        DEMOS.put("08", Week5DemoRunner::demo08);
        DEMOS.put("09", Week5DemoRunner::demo09);
        DEMOS.put("10", Week5DemoRunner::demo10);
        DEMOS.put("11", Week5DemoRunner::demo11);
        DEMOS.put("12", Week5DemoRunner::demo12);
        DEMOS.put("13", Week5DemoRunner::demo13);
        DEMOS.put("14", Week5DemoRunner::demo14);
        DEMOS.put("15", Week5DemoRunner::demo15);
        DEMOS.put("16", Week5DemoRunner::demo16);
        DEMOS.put("17", Week5DemoRunner::demo17);
        DEMOS.put("18", Week5DemoRunner::demo18);
        DEMOS.put("19", Week5DemoRunner::demo19);
    }

    // Important concurrency point: Each action calls the demo API directly so demos without main methods are still runnable.
    public static void main(String[] args) throws Exception {
        if (args.length == 0 || "all".equalsIgnoreCase(args[0])) {
            runAll();
            return;
        }

        String key = normalize(args[0]);
        DemoAction action = DEMOS.get(key);
        if (action == null) {
            printUsage();
            throw new IllegalArgumentException("Unknown Week 5 demo: " + args[0]);
        }
        runOne(key, action);
    }

    private static void runAll() throws Exception {
        for (Map.Entry<String, DemoAction> entry : DEMOS.entrySet()) {
            runOne(entry.getKey(), entry.getValue());
        }
    }

    private static void runOne(String key, DemoAction action) throws Exception {
        System.out.println();
        System.out.println("=== Demo" + key + " ===");
        action.run();
    }

    private static String normalize(String value) {
        String trimmed = value.trim();
        if (trimmed.toLowerCase().startsWith("demo")) {
            trimmed = trimmed.substring(4);
        }
        return trimmed.length() == 1 ? "0" + trimmed : trimmed;
    }

    private static void printUsage() {
        System.out.println("Usage: ./gradlew.bat run --args=\"all\"");
        System.out.println("   or: ./gradlew.bat run --args=\"03\"");
        System.out.println("Available demos: " + String.join(", ", DEMOS.keySet()));
    }

    private static void demo01() {
        System.out.println("Queued without backpressure: " + Demo01_UnboundedQueueRisk.enqueueWithoutLimit(10_000));
    }

    private static void demo02() {
        System.out.println("Second offer rejected when full: " + Demo02_BoundedBlockingQueue.secondOfferIsRejectedWhenFull());
    }

    private static void demo03() throws InterruptedException {
        System.out.println("Producer-consumer sum: " + Demo03_ProducerConsumerBackpressure.runOnce());
    }

    private static void demo04() throws InterruptedException {
        System.out.println("Completed fixed-pool tasks: " + Demo04_FixedThreadPool.runTasks(5));
    }

    private static void demo06() throws InterruptedException {
        System.out.println("Rejected when worker and queue are full: " + Demo06_RejectionPolicyDemo.rejectsWhenWorkerAndQueueAreFull());
    }

    private static void demo07() {
        Demo07_SynchronizedCounter counter = new Demo07_SynchronizedCounter();
        counter.increment();
        counter.increment();
        System.out.println("Synchronized counter: " + counter.get());
    }

    private static void demo08() {
        Demo08_AtomicCounter counter = new Demo08_AtomicCounter();
        counter.increment();
        counter.increment();
        System.out.println("Atomic counter: " + counter.get());
    }

    private static void demo09() {
        Demo09_CASRetryLoop loop = new Demo09_CASRetryLoop();
        System.out.println("CAS add result: " + loop.addPositive(7));
    }

    private static void demo10() {
        Demo10_SynchronizedReadMostlyCache<String, String> cache = new Demo10_SynchronizedReadMostlyCache<>();
        cache.put("course", "concurrency");
        System.out.println("Synchronized cache lookup: " + cache.get("course"));
    }

    private static void demo11() {
        Demo11_ReadWriteLockCache<String, String> cache = new Demo11_ReadWriteLockCache<>();
        cache.put("week", "5");
        System.out.println("Read-write lock cache lookup: " + cache.get("week"));
    }

    private static void demo12() {
        System.out.println("Request context value: " + Demo12_RequestContextThreadLocal.runWithRequestId("req-12"));
    }

    private static void demo13() {
        Demo13_ThreadLocalLeakInPool.unsafeHandle("req-13", true);
        System.out.println("Leaked request context before cleanup: "
                + Demo13_ThreadLocalLeakInPool.unsafeHandle(null, false));
        Demo13_ThreadLocalLeakInPool.clear();
    }

    private static void demo14() throws Exception {
        System.out.println("Callable/Future square: " + Demo14_CallableAndFuture.computeSquare(7));
    }

    private static void demo15() throws InterruptedException {
        System.out.println("Cached-pool worker names used: " + Demo15_CachedThreadPool.workerNamesUsed(4));
    }

    private static void demo16() throws InterruptedException {
        System.out.println("Fixed vs cached measurement: "
                + Demo16_FixedVsCachedThroughput.compareIoLikeTasks(6, 2, 5));
    }

    private static void demo17() throws Exception {
        System.out.println("Hand-rolled executor contrast: " + Demo17_HandRolledExecutorContrast.runDemo());
    }

    private static void demo18() throws InterruptedException {
        System.out.println("Unfair lock observation: " + Demo18_WriterStarvationReadWriteLock.observeWriterDelay(false));
        System.out.println("Fair lock observation: " + Demo18_WriterStarvationReadWriteLock.observeWriterDelay(true));
    }

    private static void demo19() throws Exception {
        System.out.println("Without cleanup: " + Demo19_ThreadLocalMemoryRetention.measureRetainedBytes(1024, false));
        System.out.println("With cleanup: " + Demo19_ThreadLocalMemoryRetention.measureRetainedBytes(1024, true));
    }

    private Week5DemoRunner() {
    }

    @FunctionalInterface
    private interface DemoAction {
        void run() throws Exception;
    }
}
