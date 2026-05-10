/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 1
 * Lab Title: Lab 1 - Foundations and Amdahl Performance Modeling
 * ================================================================
 */

/*
 * INSTRUCTOR SOLUTION for W1.P4.Ex2.
 * Excluded from the default build. Compile with -Pinstructor=true.
 */
package edu.lu.concurrency.week1.lab1.part4_amdahl.solutions;

public final class Sol2_KarpFlatt {

    public static double serialFraction(double measuredSpeedup, int cores) {
        // Inputs must describe a valid measurement point.
        if (!(measuredSpeedup > 0.0)) {
            throw new IllegalArgumentException("measuredSpeedup must be > 0");
        }
        if (cores < 2) {
            throw new IllegalArgumentException("N must be >= 2");
        }
        // Rearranged Karp-Flatt relation.
        double invS = 1.0 / measuredSpeedup;
        double invN = 1.0 / cores;
        return (invS - invN) / (1.0 - invN);
    }

    public static double parallelFraction(double measuredSpeedup, int cores) {
        // Parallel fraction is the complement of serial fraction.
        return 1.0 - serialFraction(measuredSpeedup, cores);
    }

    private Sol2_KarpFlatt() {}
}
