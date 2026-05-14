package edu.lu.concurrency.week8.day1.part1_diagnosis;

public final class Demo01_ThreadDumpClues {

    public record ThreadDumpClue(String threadState, String topFrame, String likelyMechanism) {
    }

    public static ThreadDumpClue clue() {
        return new ThreadDumpClue(
                "WAITING",
                "CompletableFuture.join",
                "request thread waits for an async dependency that has no timeout or fallback");
    }

    private Demo01_ThreadDumpClues() {
    }
}
