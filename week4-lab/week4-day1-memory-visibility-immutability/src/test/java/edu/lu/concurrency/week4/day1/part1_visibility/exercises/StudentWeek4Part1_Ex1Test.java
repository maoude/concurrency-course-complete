/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part1_visibility.exercises;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentWeek4Part1_Ex1Test {

    @Test
    void stop_flag_is_declared_volatile() throws Exception {
        Field f = Ex1_StopFlagVolatile.class.getDeclaredField("running");
        assertTrue(Modifier.isVolatile(f.getModifiers()), "running must be volatile");
    }

    @Test
    void worker_stops_after_stop_signal() throws Exception {
        Ex1_StopFlagVolatile ex = new Ex1_StopFlagVolatile();

        Thread worker = new Thread(ex::runUntilStopped, "w4-ex1-worker");
        worker.start();

        Thread.sleep(50);
        ex.stop();
        worker.join(1000);

        assertFalse(worker.isAlive(), "worker should observe stop flag and terminate");
    }
}
