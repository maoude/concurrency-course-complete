/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 1
 * Lab Title: Lab 1 - Foundations and Amdahl Performance Modeling
 * ================================================================
 */

package edu.lu.concurrency.week1.lab1.part4_amdahl.exercises;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek1Part4_Ex2Test {

    private static final double EPS = 1e-6;

    @Test
    void perfect_linear_speedup_means_zero_serial_fraction() {
        // S = N exactly => e = 0, P = 1.
        assertEquals(0.0, Ex2_KarpFlatt.serialFraction(8.0, 8), EPS);
        assertEquals(1.0, Ex2_KarpFlatt.parallelFraction(8.0, 8), EPS);
    }

    @Test
    void no_speedup_means_full_serial_fraction() {
        // S = 1 on N cores => e = 1, P = 0.
        assertEquals(1.0, Ex2_KarpFlatt.serialFraction(1.0, 8), EPS);
        assertEquals(0.0, Ex2_KarpFlatt.parallelFraction(1.0, 8), EPS);
    }

    @Test
    void recovers_p_from_amdahl_speedup() {
        // If P = 0.9 and N = 4, Amdahl gives S = 1 / (0.1 + 0.225) ~ 3.0769.
        double S = 1.0 / (0.1 + 0.9 / 4.0);
        double pHat = Ex2_KarpFlatt.parallelFraction(S, 4);
        assertEquals(0.9, pHat, 1e-9);
    }

    @Test
    void invalid_inputs_throw() {
        assertThrows(IllegalArgumentException.class,
                () -> Ex2_KarpFlatt.serialFraction(0.0, 4));
        assertThrows(IllegalArgumentException.class,
                () -> Ex2_KarpFlatt.serialFraction(-1.0, 4));
        assertThrows(IllegalArgumentException.class,
                () -> Ex2_KarpFlatt.serialFraction(2.0, 1)); // N<2
    }
}
