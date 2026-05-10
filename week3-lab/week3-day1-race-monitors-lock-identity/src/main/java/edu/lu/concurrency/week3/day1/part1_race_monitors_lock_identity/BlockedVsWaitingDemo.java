package main.java.edu.lu.concurrency.week3.day1.part1_race_monitors_lock_identity;

public class BlockedVsWaitingDemo {

    private static final Object LOCK = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread lockHolder = new Thread(() -> {
            synchronized (LOCK) {
                System.out.println(Thread.currentThread().getName() + " acquired LOCK.");

                try {
                    // This thread keeps the monitor for a while.
                    // Any other thread trying synchronized(LOCK) becomes BLOCKED.
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                System.out.println(Thread.currentThread().getName() + " releasing LOCK.");
            }
        }, "lock-holder");

        Thread blockedThread = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " trying to enter synchronized block...");
            synchronized (LOCK) {
                System.out.println(Thread.currentThread().getName() + " finally entered synchronized block.");
            }
        }, "blocked-thread");

        Thread waitingThread = new Thread(() -> {
            synchronized (LOCK) {
                try {
                    System.out.println(Thread.currentThread().getName() + " calling wait() and entering WAITING state.");

                    // wait() releases the monitor and places the thread in WAITING state.
                    LOCK.wait();

                    System.out.println(Thread.currentThread().getName() + " resumed after notify.");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }, "waiting-thread");

        // Start waiting thread first so it can acquire lock and call wait().
        waitingThread.start();
        Thread.sleep(500);

        System.out.println("State of waiting-thread after wait call: " + waitingThread.getState());

        // Start lock holder so it acquires lock and sleeps while holding it.
        lockHolder.start();
        Thread.sleep(500);

        // Start blocked thread; it will try to get the same lock and become BLOCKED.
        blockedThread.start();
        Thread.sleep(500);

        System.out.println("State of lock-holder    : " + lockHolder.getState());
        System.out.println("State of blocked-thread : " + blockedThread.getState());
        System.out.println("State of waiting-thread : " + waitingThread.getState());

        // After lockHolder releases the lock, main acquires it and wakes waitingThread.
        lockHolder.join();

        synchronized (LOCK) {
            System.out.println("main thread calling notifyAll()");
            LOCK.notifyAll();
        }

        blockedThread.join();
        waitingThread.join();

        System.out.println("=== BlockedVsWaitingDemo ===");
        System.out.println("BLOCKED  = waiting to acquire a monitor already held by another thread.");
        System.out.println("WAITING  = voluntarily suspended, often via wait().");
    }
}