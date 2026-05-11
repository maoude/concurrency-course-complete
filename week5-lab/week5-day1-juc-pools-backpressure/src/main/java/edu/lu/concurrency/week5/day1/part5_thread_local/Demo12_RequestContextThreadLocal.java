package edu.lu.concurrency.week5.day1.part5_thread_local;

public class Demo12_RequestContextThreadLocal {
    private static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();

    public static String runWithRequestId(String requestId) {
        REQUEST_ID.set(requestId);
        try {
            return REQUEST_ID.get();
        } finally {
            REQUEST_ID.remove();
        }
    }
}
