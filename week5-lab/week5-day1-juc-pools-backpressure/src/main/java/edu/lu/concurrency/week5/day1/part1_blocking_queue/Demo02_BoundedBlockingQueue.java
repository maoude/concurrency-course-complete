package edu.lu.concurrency.week5.day1.part1_blocking_queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Demo02_BoundedBlockingQueue {
    public static boolean secondOfferIsRejectedWhenFull() {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        return queue.offer("first") && !queue.offer("second");
    }

    public static void main(String[] args) {
        System.out.println("Bounded queue rejected second item: " + secondOfferIsRejectedWhenFull());
    }
}
