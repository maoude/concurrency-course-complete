/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part3_dcl.exercises;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class StudentWeek4Part3_Ex3Test {

    @Test
    void dcl_returns_single_published_instance() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(8);
        try {
            List<Callable<Ex3_DCLVolatileSingleton>> calls = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                calls.add(Ex3_DCLVolatileSingleton::getInstance);
            }

            List<Future<Ex3_DCLVolatileSingleton>> futures = pool.invokeAll(calls);
            Ex3_DCLVolatileSingleton first = value(futures.get(0));
            for (Future<Ex3_DCLVolatileSingleton> f : futures) {
                assertSame(first, value(f));
                assertEquals(42, value(f).marker());
            }
        } finally {
            pool.shutdownNow();
        }
    }

    private static Ex3_DCLVolatileSingleton value(Future<Ex3_DCLVolatileSingleton> f)
            throws InterruptedException, ExecutionException {
        return f.get();
    }
}
