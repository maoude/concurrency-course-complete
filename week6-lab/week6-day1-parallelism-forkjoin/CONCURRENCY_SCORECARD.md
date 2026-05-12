# Concurrency Scorecard - Week 6

- [ ] Computation is deterministic.
- [ ] Subtasks are independent and side-effect free.
- [ ] Threshold avoids excessive tiny tasks.
- [ ] No blocking I/O runs inside the common `ForkJoinPool`.
- [ ] Measurement compares the same work for both implementations.
- [ ] Break-even conclusion includes hardware and input-size caveats.
