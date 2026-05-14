# Week 7 Anti-Patterns Module

## Thread-Per-Request

Creating one platform thread per request can work for small demos but fails
under sustained load. Threads consume memory, scheduling time, and operational
headroom.

Better question: what is the execution policy, and what resource bounds it?

## Unbounded Queues

An unbounded queue hides overload. Producers keep receiving fast acceptance
while latency and memory pressure grow in the background.

Better question: when the system is full, do we block, reject, drop optional
work, or apply fallback?

## Missing Timeouts

An async pipeline without a timeout can wait forever. This is still a liveness
bug even if no platform thread is blocked.

Better question: what is the caller's time budget, and what happens when the
budget expires?

## Silent Failure Swallowing

`exceptionally(...)` can be useful, but it is dangerous when it converts all
errors into vague defaults without recording that fallback happened.

Better question: does the combined response expose whether a fallback was used?

## Fire-And-Forget

Starting async work without returning, joining, or observing its future loses
completion, failure, and cancellation semantics.

Better question: who owns completion, failure handling, and shutdown?
