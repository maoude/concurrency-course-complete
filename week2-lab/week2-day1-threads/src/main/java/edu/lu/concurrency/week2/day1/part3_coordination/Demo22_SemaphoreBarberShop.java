/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 - Part 3: Coordination
 * Demo19 - SemaphoreBarberShop
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * This demo uses Semaphore to model a bounded waiting room and a
 * single barber chair. The key lesson is that coordination can be
 * expressed with higher-level primitives instead of timing guesses.
 *
 * The waiting-room semaphore bounds how many customers may wait.
 * The barber-chair semaphore ensures only one haircut happens at a
 * time. Both are explicit, visible coordination rules.
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part3_coordination;

import edu.lu.concurrency.week2.day1.common.Console;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public final class Demo22_SemaphoreBarberShop {

    private static final class BarberShop {

        private final Semaphore waitingRoomSemaphore;
        private final Semaphore barberChairSemaphore = new Semaphore(1, true);
        private final Random random = new Random();

        private final int waitingRoomCapacity;
        private int servedCustomers = 0;

        private BarberShop(int waitingRoomCapacity) {
            this.waitingRoomCapacity = waitingRoomCapacity;
            this.waitingRoomSemaphore = new Semaphore(waitingRoomCapacity, true);
        }

        public void customerHaveAHairCut(int customerId) throws InterruptedException {
            enterTheQueue(customerId);
            enterTheRoom(customerId);
            haveHairCut(customerId);
        }

        private void enterTheQueue(int customerId) {
            System.out.println(customerId + " entered the queue");
        }

        private void enterTheRoom(int customerId) throws InterruptedException {
            waitingRoomSemaphore.acquire();
            System.out.println(customerId + " entered the waitingRoom. Available seats: "
                    + waitingRoomSemaphore.availablePermits() + "/" + waitingRoomCapacity);
        }

        private void haveHairCut(int customerId) throws InterruptedException {
            barberChairSemaphore.acquire();
            try {
                waitingRoomSemaphore.release();
                System.out.println(customerId + " entered the barber room");
                System.out.println(customerId + " having a hair cut");
                Thread.sleep(random.nextInt(3) * 1000L);
                System.out.println(customerId + " finished having cut");
                servedCustomers++;
            } finally {
                barberChairSemaphore.release();
            }
        }

        public int getServedCustomers() {
            return servedCustomers;
        }

        public int getWaitingRoomAvailableSeats() {
            return waitingRoomSemaphore.availablePermits();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Demo22_SemaphoreBarberShop().runApp();
    }

    private void runApp() throws InterruptedException {
        Console.hr("Demo19  SemaphoreBarberShop");

        final int customerCount = 20;
        final BarberShop barberShop = new BarberShop(10);

        List<Thread> customers = new ArrayList<>();
        for (int i = 0; i < customerCount; i++) {
            final int customerId = i;
            Thread customer = new Thread(() -> {
                try {
                    barberShop.customerHaveAHairCut(customerId);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }, "customer-" + customerId);
            customers.add(customer);
        }

        for (Thread customer : customers) {
            customer.start();
            Thread.sleep(200L);
        }

        for (Thread customer : customers) {
            customer.join();
        }

        System.out.println("[SUMMARY] servedCustomers=" + barberShop.getServedCustomers()
                + " waitingRoomAvailableSeats=" + barberShop.getWaitingRoomAvailableSeats());
    }

    private Demo22_SemaphoreBarberShop() {}
}