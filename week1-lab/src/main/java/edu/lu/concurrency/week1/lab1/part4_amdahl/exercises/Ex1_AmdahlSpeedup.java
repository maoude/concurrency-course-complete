/*
 * ================================================================
 * EXERCISE W1.P4.Ex1 - Amdahl Speedup From Scratch
 * ----------------------------------------------------------------
 * Goal:        Implement Amdahl's Law without copying the existing
 *              AmdahlCalculator. You must derive the formula yourself.
 * Given:       Two parameters - parallelFraction P in [0,1] and
 *              cores N >= 1.
 * Your task:
 *   1) Validate the inputs, throw IllegalArgumentException on bad input.
 *   2) Return the theoretical speedup S = 1 / ((1-P) + P/N).
 *   3) Make sure speedup(P=1, N=1) == 1.0 and speedup(P=0, N=anything) == 1.0.
 * Pass when:   StudentWeek1Part4_Ex1Test is green.
 * Hint:        Use double arithmetic, not int division.
 * ================================================================
 */
package edu.lu.concurrency.week1.lab1.part4_amdahl.exercises;

public final class Ex1_AmdahlSpeedup {

    /**
     * Theoretical speedup from Amdahl's Law.
     *
     * @param parallelFraction fraction of the program that can be parallelised, in [0, 1]
     * @param cores            number of parallel workers, must be >= 1
     * @return theoretical speedup factor (>= 1.0)
     */
    public static double speedup(double parallelFraction, int cores) {
        // TODO 1: validate parallelFraction is between 0 and 1 (inclusive),
        //         throw IllegalArgumentException otherwise.
        // TODO 2: validate cores >= 1, throw IllegalArgumentException otherwise.
        // TODO 3: return 1 / ((1 - parallelFraction) + parallelFraction / cores).
        return 0.0; // placeholder so the project still compiles
    }

    private Ex1_AmdahlSpeedup() {}
}
