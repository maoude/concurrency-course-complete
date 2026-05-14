package edu.lu.concurrency.week8.day1.part1_diagnosis.solutions;

public final class Sol1_DiagnosisReport {

    public record DiagnosisReport(String bug, String evidence, String mechanism, String fix) {
    }

    public static DiagnosisReport report() {
        return new DiagnosisReport(
                "request can wait forever",
                "thread dump shows request worker waiting in CompletableFuture.join",
                "dependency future has no timeout or fallback completion path",
                "apply completeOnTimeout before the caller waits");
    }

    private Sol1_DiagnosisReport() {
    }
}
