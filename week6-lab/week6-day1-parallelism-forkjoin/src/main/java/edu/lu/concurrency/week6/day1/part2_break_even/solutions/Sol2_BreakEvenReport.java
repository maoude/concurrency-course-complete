package edu.lu.concurrency.week6.day1.part2_break_even.solutions;

import edu.lu.concurrency.week6.day1.part1_fork_join.Demo01_SequentialArraySum;
import edu.lu.concurrency.week6.day1.part1_fork_join.Demo02_ForkJoinArraySum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class Sol2_BreakEvenReport {

    public record Row(int size, long sequentialNanos, long forkJoinNanos, boolean sameResult) {
    }

    public record Report(List<Row> rows, int firstForkJoinWinSize, String explanation) {
    }

    public static Report measure(int[] sizes, int threshold) {
        List<Row> rows = new ArrayList<>();
        int firstWin = -1;

        for (int size : sizes) {
            int[] values = new int[size];
            Arrays.fill(values, 1);

            long sequentialStart = System.nanoTime();
            long sequential = Demo01_SequentialArraySum.sum(values);
            long sequentialNanos = System.nanoTime() - sequentialStart;

            long forkJoinStart = System.nanoTime();
            long forkJoin = Demo02_ForkJoinArraySum.parallelSum(values, threshold);
            long forkJoinNanos = System.nanoTime() - forkJoinStart;

            if (firstWin == -1 && forkJoinNanos < sequentialNanos) {
                firstWin = size;
            }
            rows.add(new Row(size, sequentialNanos, forkJoinNanos, sequential == forkJoin));
        }

        String explanation = "Fork/Join only wins after the work is large enough to repay split, scheduling, and join overhead.";
        return new Report(List.copyOf(rows), firstWin, explanation);
    }

    private Sol2_BreakEvenReport() {
    }
}
