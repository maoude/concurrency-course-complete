/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * ================================================================
 */

package edu.lu.concurrency.week3.day1.part3_lock_identity.exercises;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part3_Ex2Test {

    @Test
    void safe_lock_field_exists_and_is_not_a_string() throws Exception {
        // The student must add some private final field that is the lock.
        // We accept any private final field whose runtime type is NOT
        // String. There must be at least one such field.
        boolean foundNonStringPrivateFinalLock = false;
        for (Field f : Ex2_StringLiteralTrap.class.getDeclaredFields()) {
            int mods = f.getModifiers();
            if (Modifier.isPrivate(mods)
                    && Modifier.isFinal(mods)
                    && !f.getType().equals(String.class)
                    && !f.getType().isPrimitive()) {
                foundNonStringPrivateFinalLock = true;
            }
        }
        assertTrue(foundNonStringPrivateFinalLock,
                "Add a `private final Object` (or similar) field as the safe lock. " +
                "Locking on a String literal is the trap this exercise is about.");
    }

    @RepeatedTest(3)
    void safe_increment_is_correct() throws Exception {
        Ex2_StringLiteralTrap c = new Ex2_StringLiteralTrap();
        int threads    = 8;
        int iterations = 100_000;

        Thread[] ts = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            ts[i] = new Thread(() -> {
                for (int j = 0; j < iterations; j++) c.safeIncrement();
            });
        }
        for (Thread t : ts) t.start();
        for (Thread t : ts) t.join();

        assertEquals(threads * iterations, c.getCount());
    }
}
