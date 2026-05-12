package edu.lu.concurrency.week6.day1.part1_fork_join.exercises;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StudentWeek6Part1_Ex1Test {

    @Test
    void forkJoinSumMatchesSequentialForSmallInput() {
        int[] values = {5, -2, 7, 10};
        assertEquals(20, Ex1_ForkJoinSum.parallelSum(values, 2));
    }

    @RepeatedTest(5)
    void forkJoinSumMatchesSequentialUnderLargerInput() {
        int[] values = new int[20_000];
        Arrays.fill(values, 3);

        assertEquals(60_000, Ex1_ForkJoinSum.parallelSum(values, 512));
    }
}
