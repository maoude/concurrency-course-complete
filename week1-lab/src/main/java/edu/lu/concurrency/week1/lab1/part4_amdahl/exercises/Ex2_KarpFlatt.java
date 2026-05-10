/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 1
 * Lab Title: Lab 1 - Foundations and Amdahl Performance Modeling
 * ================================================================
 */

/*
 * ================================================================
 * EXERCISE W1.P4.Ex2 - Karp-Flatt: Estimate the Serial Fraction
 * ----------------------------------------------------------------
 * Goal:        Given a measured speedup S on N processors, recover the
 *              "experimentally determined serial fraction" e:
 *
 *                  e = (1/S - 1/N) / (1 - 1/N)
 *
 *              and from that the parallel fraction P = 1 - e.
 *
 * Given:       measuredSpeedup (S, double > 0) and cores (N, int >= 2).
 *
 * Your task:
 *   1) Validate the inputs.
 *   2) Implement serialFraction(S, N) returning e in [0, 1].
 *   3) Implement parallelFraction(S, N) returning 1 - e.
 *   4) When N == 1, the formula degenerates - throw
 *      IllegalArgumentException("N must be >= 2").
 *
 * Pass when:   StudentWeek1Part4_Ex2Test is green.
 * Hint:        e tells you how *bad* a parallel implementation is. If e
 *              grows with N, you are losing efficiency to overhead, not
 *              to Amdahl's Law.
 * ================================================================
 */
package edu.lu.concurrency.week1.lab1.part4_amdahl.exercises;

public final class Ex2_KarpFlatt {

    /** Experimentally determined serial fraction e. */
    public static double serialFraction(double measuredSpeedup, int cores) {
        if (!(measuredSpeedup > 0.0)) {
            throw new IllegalArgumentException("measuredSpeedup must be > 0");
        }
        if (cores < 2) {
            throw new IllegalArgumentException("N must be >= 2");
        }

        double invS = 1.0 / measuredSpeedup;
        double invN = 1.0 / cores;
        return (invS - invN) / (1.0 - invN);
    }

    /** Parallel fraction P = 1 - e. */
    public static double parallelFraction(double measuredSpeedup, int cores) {
        return 1.0 - serialFraction(measuredSpeedup, cores);
    }

    private Ex2_KarpFlatt() {}
}
