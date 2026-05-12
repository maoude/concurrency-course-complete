/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 5
 * Lab Title: Day 1 - java.util.concurrent, Pools, and Backpressure
 * ================================================================
 */
package edu.lu.concurrency.week5.day1.part2_thread_pools;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
/**
 * Instructor contrast demo showing why production code should prefer ThreadPoolExecutor over hand-rolled executors.
 */
public class Demo17_HandRolledExecutorContrast {
    // Important concurrency point: This toy implementation is intentionally minimal and omits production executor guarantees.
    public record ContrastResult(String firstResult, String secondResult, boolean rejectedAfterShutdown) {
    }

    public static ContrastResult runDemo() throws Exception {
        ToyExecutor<String> executor = new ToyExecutor<>(2);
        try {
            // Concurrency note: submit() dispatches asynchronous work to executor threads.
            ToyFuture<String> first = executor.submit(new CallableWithID<String>(101L) {
                @Override
                public String call() {
                    return "task-" + getId();
                }
            });
            ToyFuture<String> second = executor.submit(new CallableWithID<String>(202L) {
                @Override
                public String call() {
                    return "task-" + getId();
                }
            });

            String firstResult = first.get();
            String secondResult = second.get();

            executor.shutdownAndAwait(1, TimeUnit.SECONDS);

            boolean rejectedAfterShutdown;
            try {
                executor.submit(new CallableWithID<String>(303L) {
                    @Override
                    public String call() {
                        return "task-" + getId();
                    }
                });
                rejectedAfterShutdown = false;
            } catch (IllegalStateException expected) {
                rejectedAfterShutdown = true;
            }

            return new ContrastResult(firstResult, secondResult, rejectedAfterShutdown);
        } finally {
            executor.close();
        }
    }

    private abstract static class CallableWithID<T> implements Callable<T> {
        private final Long id;

        protected CallableWithID(Long id) {
            this.id = Objects.requireNonNull(id, "id");
        }

        public Long getId() {
            return id;
        }
    }

    private static final class CallableRecord<T> {
        private final CallableWithID<T> callable;
        private final ToyFuture<T> future;

        private CallableRecord(CallableWithID<T> callable, ToyFuture<T> future) {
            this.callable = callable;
            this.future = future;
        }
    }

    private static final class ToyFuture<T> {
        private final Semaphore ready = new Semaphore(0);
        private volatile T value;
        private volatile Exception failure;

        private void complete(T value) {
            this.value = value;
            ready.release();
        }

        private void fail(Exception failure) {
            this.failure = failure;
            ready.release();
        }

        private T get() throws Exception {
            ready.acquire();
            if (failure != null) {
                throw failure;
            }
            return value;
        }
    }

    private static final class ToyExecutor<T> implements AutoCloseable {
        private final BlockingQueue<CallableRecord<T>> queue = new LinkedBlockingQueue<>();
        private final List<Thread> workers = new ArrayList<>();
        private volatile boolean accepting = true;

        private ToyExecutor(int workerCount) {
            if (workerCount <= 0) {
                throw new IllegalArgumentException("workerCount must be positive");
            }
            for (int i = 0; i < workerCount; i++) {
                Thread worker = new Thread(this::runWorker, "toy-executor-" + i);
                workers.add(worker);
                worker.start();
            }
        }

        private ToyFuture<T> submit(CallableWithID<T> callable) {
            if (!accepting) {
                throw new IllegalStateException("executor already shut down");
            }
            ToyFuture<T> future = new ToyFuture<>();
            queue.add(new CallableRecord<>(callable, future));
            return future;
        }

        private void shutdownAndAwait(long timeout, TimeUnit unit) throws InterruptedException {
            accepting = false;
            long deadlineNanos = System.nanoTime() + unit.toNanos(timeout);
            for (Thread worker : workers) {
                long remaining = deadlineNanos - System.nanoTime();
                if (remaining <= 0) {
                    break;
                }
                worker.join(TimeUnit.NANOSECONDS.toMillis(remaining));
            }
        }

        private void runWorker() {
            while (accepting || !queue.isEmpty()) {
                try {
                    CallableRecord<T> record = queue.poll(100, TimeUnit.MILLISECONDS);
                    if (record == null) {
                        continue;
                    }
                    try {
                        record.future.complete(record.callable.call());
                    } catch (Exception ex) {
                        record.future.fail(ex);
                    }
                } catch (InterruptedException interrupted) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }

        @Override
        public void close() {
            accepting = false;
            for (Thread worker : workers) {
                worker.interrupt();
            }
        }
    }
    // Expected behavior: Hand-rolled execution is fragile versus ThreadPoolExecutor lifecycle and safety guarantees.
}