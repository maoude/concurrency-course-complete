package edu.lu.concurrency.week1.lab1.part4_amdahl.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek1Part4_Ex1Test {

    private static final double EPS = 1e-9;

    @Test
    void p_zero_gives_no_speedup() {
        assertEquals(1.0, Ex1_AmdahlSpeedup.speedup(0.0, 1),    EPS);
        assertEquals(1.0, Ex1_AmdahlSpeedup.speedup(0.0, 8),    EPS);
        assertEquals(1.0, Ex1_AmdahlSpeedup.speedup(0.0, 1024), EPS);
    }

    @Test
    void p_one_scales_linearly() {
        assertEquals(1.0, Ex1_AmdahlSpeedup.speedup(1.0, 1), EPS);
        assertEquals(4.0, Ex1_AmdahlSpeedup.speedup(1.0, 4), EPS);
        assertEquals(8.0, Ex1_AmdahlSpeedup.speedup(1.0, 8), EPS);
    }

    @Test
    void textbook_example_50_percent_at_4_cores() {
        // S = 1 / (0.5 + 0.5/4) = 1 / 0.625 = 1.6
        assertEquals(1.6, Ex1_AmdahlSpeedup.speedup(0.5, 4), 1e-9);
    }

    @Test
    void plateau_approaches_1_over_one_minus_p() {
        double atHugeN = Ex1_AmdahlSpeedup.speedup(0.95, 100_000);
        // Theoretical max with P=0.95 is 1/(1-0.95) = 20.0.
        assertTrue(atHugeN > 19.0 && atHugeN < 20.0001,
                "Expected speedup just under 20.0, got " + atHugeN);
    }

    @Test
    void invalid_inputs_throw() {
        assertThrows(IllegalArgumentException.class,
                () -> Ex1_AmdahlSpeedup.speedup(-0.01, 4));
        assertThrows(IllegalArgumentException.class,
                () -> Ex1_AmdahlSpeedup.speedup(1.01, 4));
        assertThrows(IllegalArgumentException.class,
                () -> Ex1_AmdahlSpeedup.speedup(0.5, 0));
        assertThrows(IllegalArgumentException.class,
                () -> Ex1_AmdahlSpeedup.speedup(0.5, -1));
    }
}
