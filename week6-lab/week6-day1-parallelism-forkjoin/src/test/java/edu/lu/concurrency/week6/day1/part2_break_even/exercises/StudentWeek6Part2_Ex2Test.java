package edu.lu.concurrency.week6.day1.part2_break_even.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentWeek6Part2_Ex2Test {

    @Test
    void reportContainsOneRowPerInputSizeInOrder() {
        int[] sizes = {256, 1_024, 4_096};

        Ex2_BreakEvenReport.Report report = Ex2_BreakEvenReport.measure(sizes, 256);

        assertEquals(3, report.rows().size());
        assertEquals(256, report.rows().get(0).size());
        assertEquals(1_024, report.rows().get(1).size());
        assertEquals(4_096, report.rows().get(2).size());
        assertTrue(report.rows().stream().allMatch(Ex2_BreakEvenReport.Row::sameResult));
    }

    @Test
    void reportExplainsCoordinationOverhead() {
        Ex2_BreakEvenReport.Report report = Ex2_BreakEvenReport.measure(new int[] {512, 2_048}, 256);

        assertFalse(report.explanation().isBlank());
        assertTrue(report.explanation().toLowerCase().contains("overhead"));
    }
}
