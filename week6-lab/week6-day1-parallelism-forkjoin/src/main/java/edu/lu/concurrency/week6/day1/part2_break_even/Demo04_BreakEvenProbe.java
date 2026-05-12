/*
 * Course: Concurrency and Distributed Systems
 * Week:   6 - Parallelism and Fork/Join
 * Author: Dr. Mohamad AOUDE
 *
 * Measurement probe for sequential vs Fork/Join array sums.
 */
package edu.lu.concurrency.week6.day1.part2_break_even;

import edu.lu.concurrency.week6.day1.part1_fork_join.Demo01_SequentialArraySum;
import edu.lu.concurrency.week6.day1.part1_fork_join.Demo02_ForkJoinArraySum;

import java.util.Arrays;

public final class Demo04_BreakEvenProbe {

    public record Measurement(int size, long sequentialNanos, long forkJoinNanos, boolean sameResult) {
    }

    public static Measurement measure(int size, int threshold) {
        int[] values = new int[size];
        Arrays.fill(values, 1);

        long sequentialStart = System.nanoTime();
        long sequential = Demo01_SequentialArraySum.sum(values);
        long sequentialNanos = System.nanoTime() - sequentialStart;

        long forkJoinStart = System.nanoTime();
        long forkJoin = Demo02_ForkJoinArraySum.parallelSum(values, threshold);
        long forkJoinNanos = System.nanoTime() - forkJoinStart;

        return new Measurement(size, sequentialNanos, forkJoinNanos, sequential == forkJoin);
    }

    private Demo04_BreakEvenProbe() {
    }
}
