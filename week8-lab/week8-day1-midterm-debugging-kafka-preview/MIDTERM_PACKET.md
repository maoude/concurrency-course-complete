# Week 8 Midterm Packet

## Scenario

The course service has an async request handler. It fans out to a dependency,
waits for the result, and returns a response. Under a slow dependency, some
requests never return.

## Evidence

Simulated thread dump excerpt:

```text
"request-worker-17" WAITING
    at java.util.concurrent.CompletableFuture.join
    at BrokenAsyncServer.handle

"dependency-client-3" TIMED_WAITING
    at java.lang.Thread.sleep
    at SlowDependency.call
```

## Required Midterm Submission

- thread dump or simulated evidence
- diagnosis report
- fixed code
- performance tradeoff analysis
- five-minute recorded explanation

## Rubric

1. Real bug: the submitted explanation identifies an actual concurrency or async failure.
2. Mechanism: the report explains why the failure happens.
3. Cost: the fix includes a realistic performance and design tradeoff.
