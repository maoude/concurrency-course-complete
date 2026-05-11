package edu.lu.concurrency.week5.day1.part5_thread_local.solutions;

import java.util.Objects;

public final class Sol5_RequestContext {
    private static final ThreadLocal<String> REQUEST_ID = new ThreadLocal<>();

    private Sol5_RequestContext() {
    }

    public static void set(String requestId) {
        REQUEST_ID.set(Objects.requireNonNull(requestId, "requestId"));
    }

    public static String get() {
        return REQUEST_ID.get();
    }

    public static void clear() {
        REQUEST_ID.remove();
    }

    public static void runWithContext(String requestId, Runnable task) {
        set(requestId);
        try {
            task.run();
        } finally {
            clear();
        }
    }
}
