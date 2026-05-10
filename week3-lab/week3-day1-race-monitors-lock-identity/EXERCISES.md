# Week 3 — Day 1 Exercises: Locks, Monitors & Reentrancy

**Author:** Dr. Mohamad Aoude  
**Course:** Concurrency & Distributed Systems  
**Week:** 3 – Locks, Monitors & Reentrancy

---

## How to run

```powershell
# From: week3-lab/week3-day1-race-monitors-lock-identity/
./gradlew studentCheck          # run your exercises only
./gradlew test                  # run all tests (instructor mode adds solutions)
```

## Progress tracker

| ID | Stub file | Test file | Topic | Status |
|----|-----------|-----------|-------|--------|
| W3.P1.Ex1 | `part1_race/exercises/Ex1_FixBankAccount.java` | `StudentWeek3Part1_Ex1Test` | Race conditions — synchronized method | ☐ TODO |
| W3.P1.Ex2 | `part1_race/exercises/Ex2_SafeTransfer.java` | `StudentWeek3Part1_Ex2Test` | Deadlock-free transfer via lock ordering | ☐ TODO |
| W3.P2.Ex1 | `part2_monitors/exercises/Ex1_MethodToBlock.java` | `StudentWeek3Part2_Ex1Test` | synchronized method → block (explicit lock obj) | ☐ TODO |
| W3.P2.Ex2 | `part2_monitors/exercises/Ex2_LockSplitting.java` | `StudentWeek3Part2_Ex2Test` | Lock splitting — separate locks per resource | ☐ TODO |
| W3.P3.Ex1 | `part3_lock_identity/exercises/Ex1_FixBrokenCounter.java` | `StudentWeek3Part3_Ex1Test` | Lock-identity bug — local vs instance lock | ☐ TODO |
| W3.P3.Ex2 | `part3_lock_identity/exercises/Ex2_StaticSharedLock.java` | `StudentWeek3Part3_Ex2Test` | Static (class-level) shared lock | ☐ TODO |
| W3.P4.Ex1 | `part4_thread_states/exercises/Ex1_BlockedStateAssertion.java` | `StudentWeek3Part4_Ex1Test` | BLOCKED state — monitor contention | ☐ TODO |
| W3.P4.Ex2 | `part4_thread_states/exercises/Ex2_BoundedBuffer.java` | `StudentWeek3Part4_Ex2Test` | WAITING state — wait()/notifyAll() producer-consumer | ☐ TODO |
| W3.P5.Ex1 | `part5_reentrancy/exercises/Ex1_ReentrantDepth.java` | `StudentWeek3Part5_Ex1Test` | Intrinsic lock reentrancy depth | ☐ TODO |
| W3.P5.Ex2 | `part5_reentrancy/exercises/Ex2_ReentrantLockCounter.java` | `StudentWeek3Part5_Ex2Test` | Explicit ReentrantLock (lock/unlock/finally) | ☐ TODO |

## Exercise descriptions

### Part 1 — Race conditions

**W3.P1.Ex1 `Ex1_FixBankAccount`**
Make `withdrawSafe(int amount)` thread-safe.  
Use `synchronized` on the method signature.  
The check-then-act sequence (`balance >= amount` → `balance -= amount`) must be atomic.

**W3.P1.Ex2 `Ex2_SafeTransfer`**
Implement `transfer(Account from, Account to, int amount)` without deadlock.  
Strategy: always acquire the lock with the _lower_ `id` first.  
Use nested `synchronized` blocks.

---

### Part 2 — Monitors

**W3.P2.Ex1 `Ex1_MethodToBlock`**
Convert `MethodCounter` (synchronized methods on `this`) into `BlockCounter`  
that uses an explicit `private final Object lock` with `synchronized(lock) { … }` blocks.  
Do **not** mark the methods themselves as `synchronized`.

**W3.P2.Ex2 `Ex2_LockSplitting`**
Replace the single `lock` guarding both `counterA` and `counterB` with two  
separate locks — `lockA` and `lockB`. Operations on A must never hold `lockB`.

---

### Part 3 — Lock identity

**W3.P3.Ex1 `Ex1_FixBrokenCounter`**
The `Object lock` is created _inside_ `increment()` — a new object every call,  
so the monitor is never shared. Move `lock` to an **instance field**.  
Keep the `synchronized(lock)` syntax unchanged.

**W3.P3.Ex2 `Ex2_StaticSharedLock`**
Declare `private static final Object CLASS_LOCK = new Object()`.  
Use it in `increment()` and `getCount()` so that all instances  
share one monitor.

---

### Part 4 — Thread states

**W3.P4.Ex1 `Ex1_BlockedStateAssertion`**
Implement `getLockHolder()`, `getContender()`, and `snapshotState(Thread, long)`.  
The holder must keep `SHARED_LOCK` for ~3 s; the contender must attempt the same  
monitor so it enters `Thread.State.BLOCKED`.

**W3.P4.Ex2 `Ex2_BoundedBuffer`**
Implement `put(int)` and `take()` using `wait()` / `notifyAll()`.  
Always use a `while` loop (not `if`) for the condition check.  
`put` must block when `size == capacity`; `take` must block when empty.

---

### Part 5 — Reentrancy

**W3.P5.Ex1 `Ex1_ReentrantDepth`**
Implement `callChain(int depth)` as a `synchronized` recursive method.  
Each level increments `callCount` and calls `callChain(depth - 1)`.  
This exercises reentrant lock acquisition — no deadlock expected at any depth.

**W3.P5.Ex2 `Ex2_ReentrantLockCounter`**
Rewrite a safe counter using `ReentrantLock`.  
Pattern: `lock.lock(); try { … } finally { lock.unlock(); }` in both `increment()` and `getCount()`.

---

## Hints & common mistakes

| Mistake | Symptom | Fix |
|---------|---------|-----|
| Local lock object (`new Object()` inside method) | Count is wrong every run | Declare lock as instance field |
| `if (cond) wait()` instead of `while (cond) wait()` | Spurious wakeup crash | Always use `while` |
| Missing `finally` around `lock.unlock()` | Lock never released on exception | Always use try/finally |
| `synchronized` on `this` _and_ explicit lock | Two separate monitors — no protection | Pick one pattern per class |
| Different lock-acquisition order per transfer | Deadlock under concurrency | Sort accounts by `id` before locking |
