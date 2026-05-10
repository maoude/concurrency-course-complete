/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week 3 – Locks, Monitors & Reentrancy
 * ================================================================
 * EXERCISE W3.P5.Ex2 — Rewrite SafeCounter using ReentrantLock
 * ================================================================
 * Goal:     Replace the intrinsic (synchronized) lock with an explicit
 *           java.util.concurrent.locks.ReentrantLock. The correctness
 *           guarantee must be identical: one thread at a time, exact count.
 *
 * Your task:
 *   1) Declare:  private final ReentrantLock lock = new ReentrantLock();
 *   2) increment():
 *        lock.lock();
 *        try   { count++; }
 *        finally { lock.unlock(); }
 *   3) getCount(): same lock/finally pattern.
 *
 * Pass when: StudentWeek3Part5_Ex2Test is green.
 *
 * Why ReentrantLock over synchronized?
 *   • tryLock(timeout, unit) — bounded waiting without deadlock
 *   • lockInterruptibly()    — cancellable lock acquisition
 *   • Multiple Condition objects — fine-grained wait/signal queues
 *   • Fair-mode scheduling
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part5_reentrancy.exercises;

import java.util.concurrent.locks.ReentrantLock;

public class Ex2_ReentrantLockCounter {

    // TODO 1: private final ReentrantLock lock = new ReentrantLock();
    private int count = 0;

    public void increment() {
        // TODO 2: lock.lock();
        //         try   { count++; }
        //         finally { lock.unlock(); }
        throw new UnsupportedOperationException("TODO – implement increment");
    }

    public int getCount() {
        // TODO 3: lock.lock();
        //         try   { return count; }
        //         finally { lock.unlock(); }
        throw new UnsupportedOperationException("TODO – implement getCount");
    }
}
