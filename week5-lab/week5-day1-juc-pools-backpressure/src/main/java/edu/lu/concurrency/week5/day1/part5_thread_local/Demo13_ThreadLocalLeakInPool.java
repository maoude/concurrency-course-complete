package edu.lu.concurrency.week5.day1.part5_thread_local;

public class Demo13_ThreadLocalLeakInPool {
    private static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();

    public static String unsafeHandle(String requestId, boolean setValue) {
        if (setValue) {
            REQUEST_ID.set(requestId);
        }
        return REQUEST_ID.get();
    }

    public static void clear() {
        REQUEST_ID.remove();
    }
}
