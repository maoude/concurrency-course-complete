/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 - Part 3: Coordination
 * Demo20 - CallableAndFuture
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * Future is the simplest teaching bridge from raw threads to task-
 * based concurrency. A Callable produces a result, Future lets the
 * caller wait for it explicitly, and ExecutionException preserves
 * the cause when the task fails.
 *
 * The important lesson is not just “a task ran in the background”.
 * It is that completion and failure are both explicit, observable
 * events.
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part3_coordination;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class Demo23_CallableAndFuture {

    public static void main(String[] args) throws InterruptedException {
        new Demo23_CallableAndFuture().runApp();
    }

    private void runApp() throws InterruptedException {
        ExecutorService service = Executors.newSingleThreadExecutor();

        Future<Integer> future = service.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                int value = 3200;
                System.out.println("Sleeping for " + value + " milliseconds");
                Thread.sleep(value);
                System.out.println("Awake");

                if (value < 3000) {
                    throw new IllegalArgumentException("Nice try dude");
                }

                return value;
            }
        });

        service.shutdown();

        try {
            System.out.println("The thread slept for " + future.get() + " milliseconds");
        } catch (ExecutionException e) {
            System.out.println("Task failed: " + e.getCause().getMessage());
        }

        System.out.println("Program exited");
    }

    private Demo23_CallableAndFuture() {}
}