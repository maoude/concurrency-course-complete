# Kafka Mental-Model Preview

Kafka is introduced here only as a mental model for later distributed systems.
This is not a Kafka lab.

## Log as Universal Abstraction

A log is an ordered history of facts. Systems use logs for:

- history: what happened
- replay: rebuild state by reading events again
- recovery: resume after failure
- audit: explain past decisions
- decoupling: producers and consumers do not need to run at the same speed

## Bridge to Concurrency

Earlier weeks focused on shared memory, queues, pools, futures, and async
completion. Distributed logs extend the same question across processes: what
happened, in what order, and how can another component catch up?
