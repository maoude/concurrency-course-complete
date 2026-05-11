/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part4_race_conditions;

public final class DemoRunner_Part4 {

    public static void main(String[] args) throws Exception {
        System.out.println("=== Part 4 Runner ===");

        Demo27_RaceCounter_Baseline.main(new String[0]);
        System.out.println();

        Demo28_JoinDoesNotFixRace.main(new String[0]);
        System.out.println();

        Demo31_VolatileIsNotAtomic.main(new String[0]);
        System.out.println();

        Demo29_SynchronizedFix.main(new String[0]);
        System.out.println();

        Demo30_AtomicIntegerFix.main(new String[0]);
        System.out.println();

        Demo33_SharedMutableStateRace.main(new String[0]);
        System.out.println();

        System.out.println("[NOTE] Demo32 is interactive (jstack). Run it manually:");
        System.out.println("  java -cp .\\build\\classes\\java\\main edu.lu.concurrency.week2.day1.part4_race_conditions.Demo32_ThreadDump_Contention");
    }

    private DemoRunner_Part4() {}
}
