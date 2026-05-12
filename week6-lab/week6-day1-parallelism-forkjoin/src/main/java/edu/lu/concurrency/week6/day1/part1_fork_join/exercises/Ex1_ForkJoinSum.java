/*
 * ================================================================
 * EXERCISE W6.P1.Ex1 - Fork/Join Sum
 * ----------------------------------------------------------------
 * Course:      Concurrency and Distributed Systems
 * Week:        6 - Parallelism and Fork/Join
 * Author:      Dr. Mohamad AOUDE
 *
 * Goal:        Implement a Fork/Join sum that matches the sequential result.
 * Given:       Input array, start/end range, and task threshold.
 * Your task:   1. Split ranges larger than the threshold.
 *              2. Compute small ranges sequentially.
 *              3. Join subtasks and return the combined sum.
 * Pass when:   StudentWeek6Part1_Ex1Test is green.
 * Hint:        Fork one side, compute the other side, then join.
 * ================================================================
 */
package edu.lu.concurrency.week6.day1.part1_fork_join.exercises;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public final class Ex1_ForkJoinSum {

    public static long parallelSum(int[] values, int threshold) {
        return ForkJoinPool.commonPool().invoke(new SumTask(values, 0, values.length, threshold));
    }

    static final class SumTask extends RecursiveTask<Long> {
        private final int[] values;
        private final int startInclusive;
        private final int endExclusive;
        private final int threshold;

        SumTask(int[] values, int startInclusive, int endExclusive, int threshold) {
            this.values = values;
            this.startInclusive = startInclusive;
            this.endExclusive = endExclusive;
            this.threshold = threshold;
        }

        @Override
        protected Long compute() {
            /* TODO: implement the sequential base case and recursive split. */
            return 0L;
        }
    }

    private Ex1_ForkJoinSum() {
    }
}
