package edu.lu.concurrency.week5.day1.part1_blocking_queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Demo01_UnboundedQueueRisk {
    public static int enqueueWithoutLimit(int items) {
        Queue<Integer> queue = new ConcurrentLinkedQueue<>();
        for (int i = 0; i < items; i++) {
            queue.add(i);
        }
        return queue.size();
    }

    public static void main(String[] args) {
        System.out.println("Queued items without backpressure: " + enqueueWithoutLimit(10_000));
    }
}
