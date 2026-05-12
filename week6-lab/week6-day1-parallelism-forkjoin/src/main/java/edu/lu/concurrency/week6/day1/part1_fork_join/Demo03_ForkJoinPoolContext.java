/*
 * Course: Concurrency and Distributed Systems
 * Week:   6 - Parallelism and Fork/Join
 * Author: Dr. Mohamad AOUDE
 *
 * Small observable facts about the common ForkJoinPool.
 */
package edu.lu.concurrency.week6.day1.part1_fork_join;

import java.util.concurrent.ForkJoinPool;

public final class Demo03_ForkJoinPoolContext {

    public record PoolInfo(int parallelism, int availableProcessors) {
    }

    public static PoolInfo commonPoolInfo() {
        return new PoolInfo(
                ForkJoinPool.commonPool().getParallelism(),
                Runtime.getRuntime().availableProcessors());
    }

    private Demo03_ForkJoinPoolContext() {
    }
}
