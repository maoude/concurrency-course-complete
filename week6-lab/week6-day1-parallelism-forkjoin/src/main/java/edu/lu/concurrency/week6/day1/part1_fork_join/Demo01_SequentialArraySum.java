/*
 * Course: Concurrency and Distributed Systems
 * Week:   6 - Parallelism and Fork/Join
 * Author: Dr. Mohamad AOUDE
 *
 * Sequential baseline for array computations.
 */
package edu.lu.concurrency.week6.day1.part1_fork_join;

public final class Demo01_SequentialArraySum {

    public static long sum(int[] values) {
        long total = 0;
        for (int value : values) {
            total += value;
        }
        return total;
    }

    public static void main(String[] args) {
        int[] values = {1, 2, 3, 4, 5};
        System.out.println("Sequential sum = " + sum(values));
    }

    private Demo01_SequentialArraySum() {
    }
}
