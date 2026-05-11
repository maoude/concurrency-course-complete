/*
 * ================================================================
 * Author: Dr. Mohamad Aoude
 * Course: Concurrency & Distributed Systems
 * Week: Week 2
 * Lab Title: Day 1 - Threads
 * Week 2 - Part 3: Coordination
 * Demo23 - CompletionService Report Processing
 * ================================================================
 *
 * ENGINEERING GLOBAL PERSPECTIVE
 * ---------------------------------------------------------------
 * CompletionService decouples submission order from completion order.
 *
 * Producers submit Callable report jobs to an executor. A dedicated
 * processor consumes finished results as soon as tasks complete via
 * service.take()/poll(), without waiting for slower tasks submitted
 * earlier.
 *
 * This is a common pattern for fan-out/fan-in pipelines:
 *   - many asynchronous producers
 *   - one consumer draining completed work
 *   - no manual Future list polling
 * ================================================================
 */

package edu.lu.concurrency.week2.day1.part3_coordination;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public final class Demo26_CompletionServiceReportProcessing {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        CompletionService<String> service = new ExecutorCompletionService<>(executor);

        // Two requesters submit one report each -> two completions expected.
        int expectedReports = 2;
        ReportRequest faceRequest = new ReportRequest("Face", service, 2);
        ReportRequest onlineRequest = new ReportRequest("Online", service, 1);

        Thread faceThread = new Thread(faceRequest, "face-request");
        Thread onlineThread = new Thread(onlineRequest, "online-request");

        ReportProcessor processor = new ReportProcessor(service, expectedReports);
        Thread senderThread = new Thread(processor, "report-processor");

        System.out.println("Main: Starting the threads");
        senderThread.start();
        faceThread.start();
        onlineThread.start();

        System.out.println("Main: Waiting for the report generators");
        faceThread.join();
        onlineThread.join();

        System.out.println("Main: Shutting down the executor");
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        // Ensure processor stops promptly if all expected results already arrived.
        processor.stopProcessing();
        senderThread.join();

        System.out.println("Main: Ends");
        System.out.println();
        System.out.println("TAKEAWAY:");
        System.out.println("  - CompletionService lets consumers process work by completion time.");
        System.out.println("  - Request threads submit jobs; processor thread drains finished Futures.");
        System.out.println("  - This avoids scanning many Futures to find what is done.");
    }

    static final class ReportGenerator implements Callable<String> {

        private final String sender;
        private final String title;
        private final int durationSeconds;

        ReportGenerator(String sender, String title, int durationSeconds) {
            this.sender = sender;
            this.title = title;
            this.durationSeconds = durationSeconds;
        }

        @Override
        public String call() throws Exception {
            System.out.printf("%s_%s: Generating a report during %d seconds%n",
                    sender, title, durationSeconds);
            TimeUnit.SECONDS.sleep(durationSeconds);
            return sender + ": " + title;
        }
    }

    static final class ReportRequest implements Runnable {

        private final String name;
        private final CompletionService<String> service;
        private final int durationSeconds;

        ReportRequest(String name, CompletionService<String> service, int durationSeconds) {
            this.name = name;
            this.service = service;
            this.durationSeconds = durationSeconds;
        }

        @Override
        public void run() {
            ReportGenerator reportGenerator = new ReportGenerator(name, "Report", durationSeconds);
            service.submit(reportGenerator);
            System.out.printf("%s request: submitted%n", name);
        }
    }

    static final class ReportProcessor implements Runnable {

        private final CompletionService<String> service;
        private final int expectedReports;

        private volatile boolean end;
        private int processed;

        ReportProcessor(CompletionService<String> service, int expectedReports) {
            this.service = service;
            this.expectedReports = expectedReports;
            this.end = false;
            this.processed = 0;
        }

        @Override
        public void run() {
            while (!end && processed < expectedReports) {
                try {
                    Future<String> result = service.poll(200, TimeUnit.MILLISECONDS);
                    if (result != null) {
                        String report = result.get();
                        processed++;
                        System.out.printf("ReportProcessor: Report received: %s%n", report);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                } catch (ExecutionException e) {
                    System.out.printf("ReportProcessor: task failed: %s%n", e.getClass().getSimpleName());
                }
            }
            System.out.println("ReportProcessor: End");
        }

        void stopProcessing() {
            this.end = true;
        }
    }

    private Demo26_CompletionServiceReportProcessing() {}
}
