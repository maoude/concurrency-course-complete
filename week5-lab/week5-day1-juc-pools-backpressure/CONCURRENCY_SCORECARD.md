# Concurrency Scorecard - Week 5

- [ ] Thread-safe? No data races in shared state.
- [ ] Visibility guaranteed? Updates are published through concurrent utilities or locks.
- [ ] Deadlock-free? No circular lock acquisition.
- [ ] Liveness considered? Blocking, rejection, and starvation risks are explicit.
- [ ] Bounded resources? Queues, pools, and contexts do not grow without limit.
- [ ] Failure recovery path? Overload behavior is observable and testable.
