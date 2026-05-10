/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part3_dcl.exercises;


public final class Ex3_DCLVolatileSingleton {

    private static volatile Ex3_DCLVolatileSingleton instance;
    private final int marker;

    private Ex3_DCLVolatileSingleton() {
        this.marker = 42;
    }

    public static Ex3_DCLVolatileSingleton getInstance() {
        Ex3_DCLVolatileSingleton local = instance;
        if (local == null) {
            synchronized (Ex3_DCLVolatileSingleton.class) {
                local = instance;
                if (local == null) {
                    local = new Ex3_DCLVolatileSingleton();
                    instance = local;
                }
            }
        }
        return local;
    }

    public int marker() {
        return marker;
    }
}
