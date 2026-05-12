/*
 * ================================================================
 * EXERCISE W6.P2.Ex2 - Break-Even Report
 * ----------------------------------------------------------------
 * Course:      Concurrency and Distributed Systems
 * Week:        6 - Parallelism and Fork/Join
 * Author:      Dr. Mohamad AOUDE
 *
 * Goal:        Produce measurements that identify where Fork/Join can win.
 * Given:       A list of input sizes and a Fork/Join threshold.
 * Your task:   1. Measure sequential and Fork/Join execution for each size.
 *              2. Preserve the input order in the returned report.
 *              3. Return the first size where Fork/Join is faster, or -1.
 * Pass when:   StudentWeek6Part2_Ex2Test is green.
 * Hint:        Correctness matters more than a specific machine-dependent time.
 * ================================================================
 */
package edu.lu.concurrency.week6.day1.part2_break_even.exercises;

import java.util.List;

public final class Ex2_BreakEvenReport {

    public record Row(int size, long sequentialNanos, long forkJoinNanos, boolean sameResult) {
    }

    public record Report(List<Row> rows, int firstForkJoinWinSize, String explanation) {
    }

    public static Report measure(int[] sizes, int threshold) {
        /* TODO: run the same computation sequentially and with Fork/Join for each size. */
        return new Report(List.of(), -1, "");
    }

    private Ex2_BreakEvenReport() {
    }
}
