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
import edu.lu.concurrency.week5.day1.part5_thread_local.Demo12_RequestContextThreadLocal;
import edu.lu.concurrency.week5.day1.part5_thread_local.Demo13_ThreadLocalLeakInPool;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Smoke tests for Week 5 demos, keeping examples deterministic and fast enough for CI.
 */
class DemoSmokeTest {
    // Important concurrency point: These assertions stay deterministic so demo regressions fail without timing flakiness.
    @Test
    void demosRun() throws Exception {
        assertEquals(10, Demo01_UnboundedQueueRisk.enqueueWithoutLimit(10));
        assertTrue(Demo02_BoundedBlockingQueue.secondOfferIsRejectedWhenFull());
        assertEquals(3, Demo03_ProducerConsumerBackpressure.runOnce());
        assertEquals(5, Demo04_FixedThreadPool.runTasks(5));
        assertTrue(Demo06_RejectionPolicyDemo.rejectsWhenWorkerAndQueueAreFull());
        assertEquals(49, Demo14_CallableAndFuture.computeSquare(7));
        assertTrue(Demo15_CachedThreadPool.workerNamesUsed(4) >= 1);
        assertTrue(Demo16_FixedVsCachedThroughput.compareIoLikeTasks(6, 2, 5).completed());

        Demo17_HandRolledExecutorContrast.ContrastResult contrast = Demo17_HandRolledExecutorContrast.runDemo();
        assertEquals("task-101", contrast.firstResult());
        assertEquals("task-202", contrast.secondResult());
        // Concurrency note: shutdown() prevents new tasks so the pool can drain safely.
        assertTrue(contrast.rejectedAfterShutdown());

        Demo07_SynchronizedCounter syncCounter = new Demo07_SynchronizedCounter();
        syncCounter.increment();
        assertEquals(1, syncCounter.get());

        Demo08_AtomicCounter counter = new Demo08_AtomicCounter();
        assertEquals(1, counter.increment());
        assertEquals(1, counter.get());

        Demo09_CASRetryLoop cas = new Demo09_CASRetryLoop();
        assertEquals(3, cas.addPositive(3));

        Demo10_SynchronizedReadMostlyCache<String, Integer> syncCache = new Demo10_SynchronizedReadMostlyCache<>();
        syncCache.put("key1", 100);
        assertEquals(100, syncCache.get("key1"));

        Demo11_ReadWriteLockCache<String, Integer> rwCache = new Demo11_ReadWriteLockCache<>();
        rwCache.put("key1", 200);
        assertEquals(200, rwCache.get("key1"));

        assertEquals("r1", Demo12_RequestContextThreadLocal.runWithRequestId("r1"));

        // Demo13: ThreadLocal leak scenario
        String leaked = Demo13_ThreadLocalLeakInPool.unsafeHandle("leak-1", true);
        assertEquals("leak-1", leaked);
        // Intentionally don't clear; verify leak exists
        String stillThere = Demo13_ThreadLocalLeakInPool.unsafeHandle(null, false);
        assertEquals("leak-1", stillThere);
        Demo13_ThreadLocalLeakInPool.clear();
    }
}


