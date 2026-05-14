# Troubleshooting - Week 7

## CompletableFuture never finishes

Check whether every branch returns or completes exceptionally. Avoid
fire-and-forget tasks that the caller cannot observe.

## Timeout test is flaky

Use deterministic delayed tasks and a timeout value that is clearly below the
delay. Avoid depending on exact scheduler timing.

## Race test passes locally

Increase iterations, add `Thread.yield()` in the critical window, and repeat
the test. A small run can hide a broken implementation.

## Fan-out pipeline loses failures

Do not swallow exceptions silently. Convert them into explicit fallback values
or complete the combined future exceptionally.

## NIO seems slower in a small demo

That can be normal. The learning point is that async I/O reduces blocked
worker threads; it does not make the I/O itself faster.
