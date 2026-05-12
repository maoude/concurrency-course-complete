/*
 * Course: Concurrency and Distributed Systems
 * Week:   6 - Parallelism and Fork/Join
 * Author: Dr. Mohamad AOUDE
 *
 * Fork/Join array sum with an explicit threshold.
 */
package edu.lu.concurrency.week6.day1.part1_fork_join;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public final class Demo02_ForkJoinArraySum {

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
            int length = endExclusive - startInclusive;
            if (length <= threshold) {
                long total = 0;
                for (int i = startInclusive; i < endExclusive; i++) {
                    total += values[i];
                }
                return total;
            }

            int middle = startInclusive + length / 2;
            SumTask left = new SumTask(values, startInclusive, middle, threshold);
            SumTask right = new SumTask(values, middle, endExclusive, threshold);
            left.fork();
            long rightResult = right.compute();
            long leftResult = left.join();
            return leftResult + rightResult;
        }
    }

    private Demo02_ForkJoinArraySum() {
    }
}
