/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 4
 * Lab Title: Day 1 - Memory Visibility and Immutability
 * ================================================================
 */
package edu.lu.concurrency.week4.day1.part3_dcl.solutions;

public final class Sol3_DCLVolatileSingleton {

    private static volatile Sol3_DCLVolatileSingleton instance;
    private final int marker;

    private Sol3_DCLVolatileSingleton() {
        this.marker = 42;
    }

    public static Sol3_DCLVolatileSingleton getInstance() {
        Sol3_DCLVolatileSingleton local = instance;
        if (local == null) {
            synchronized (Sol3_DCLVolatileSingleton.class) {
                local = instance;
                if (local == null) {
                    local = new Sol3_DCLVolatileSingleton();
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
