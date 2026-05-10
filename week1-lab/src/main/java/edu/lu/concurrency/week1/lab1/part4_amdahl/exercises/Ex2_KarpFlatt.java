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
        // TODO 1: validate measuredSpeedup > 0 and cores >= 2.
        // TODO 2: return (1.0/S - 1.0/N) / (1.0 - 1.0/N).
        return 0.0;
    }

    /** Parallel fraction P = 1 - e. */
    public static double parallelFraction(double measuredSpeedup, int cores) {
        // TODO: implement using serialFraction.
        return 0.0;
    }

    private Ex2_KarpFlatt() {}
}
