/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 3
 * Lab Title: Day 1 - Locks, Monitors and Reentrancy
 * Week 3 - Part 2: Monitors
 * Demo04 - SynchronizedMethodCounterApp
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part2_monitors;

public class Demo15_SynchronizedMethodCounterApp {

	private int count = 0;

	/*
	 * Synchronized: lets only one thread inside the function at a time.
	 * Avoids: race conditions on the shared count field.
	 */
	public synchronized void increment() {
		// The implicit monitor is `this`, so only one thread can enter here at a time.
		count++;
	}

	public static void main(String[] args) {
		Demo15_SynchronizedMethodCounterApp app = new Demo15_SynchronizedMethodCounterApp();
		app.doWork();
	}

	public void doWork() {
		// Two workers contend on the same synchronized method.
		Thread t1 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				increment();
			}
		}, "counter-1");

		Thread t2 = new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				increment();
			}
		}, "counter-2");

		t1.start();
		t2.start();

		try {
			// Wait for both threads so the final count is stable before printing.
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}

		System.out.println("Final value of count: " + count);
	}
}