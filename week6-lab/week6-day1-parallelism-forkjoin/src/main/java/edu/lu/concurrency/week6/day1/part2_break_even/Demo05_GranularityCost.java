/*
 * Course: Concurrency and Distributed Systems
 * Week:   6 - Parallelism and Fork/Join
 * Author: Dr. Mohamad AOUDE
 *
 * Counts how many leaf tasks a threshold creates for a fixed input size.
 */
package edu.lu.concurrency.week6.day1.part2_break_even;

public final class Demo05_GranularityCost {

    public static int approximateLeafTasks(int size, int threshold) {
        if (size <= 0 || threshold <= 0) {
            throw new IllegalArgumentException("size and threshold must be positive");
        }
        return split(size, threshold);
    }

    private static int split(int size, int threshold) {
        if (size <= threshold) {
            return 1;
        }
        int left = size / 2;
        int right = size - left;
        return split(left, threshold) + split(right, threshold);
    }

    private Demo05_GranularityCost() {
    }
}
