# Week 3 Checklist

- [ ] I can run `.\gradlew.bat test`.
- [ ] I can run `.\gradlew.bat studentCheck`.
- [ ] I can explain a race condition as an invariant violation under an interleaving.
- [ ] I can fix a critical section with one consistent lock discipline.
- [ ] I can explain why lock identity matters.
- [ ] I can distinguish BLOCKED from WAITING.
- [ ] I can explain why `wait()` must be guarded by a `while` condition.
- [ ] I can explain intrinsic lock reentrancy.
- [ ] I can use `ReentrantLock` with `try/finally`.
- [ ] I completed `CONCURRENCY_SCORECARD.md` with evidence.

## Student Exercise Coverage

- [ ] W3.P1.Ex1 - Fix bank account race
- [ ] W3.P1.Ex2 - Deadlock-free transfer by lock ordering
- [ ] W3.P2.Ex1 - Convert synchronized method to synchronized block
- [ ] W3.P2.Ex2 - Lock splitting
- [ ] W3.P3.Ex1 - Fix method-local lock identity bug
- [ ] W3.P3.Ex2 - Static shared lock
- [ ] W3.P4.Ex1 - BLOCKED state assertion
- [ ] W3.P4.Ex2 - Bounded buffer with `wait()` / `notifyAll()`
- [ ] W3.P5.Ex1 - Reentrant depth
- [ ] W3.P5.Ex2 - ReentrantLock counter
