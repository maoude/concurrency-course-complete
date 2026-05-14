# Quiz Questions - Week 7

## Short Answer

1. What does a `CompletableFuture` represent?
2. Why is async composition different from CPU parallelism?
3. Where should a service pipeline define its timeout?
4. What is the risk of using `exceptionally(...)` to return a default value for every failure?
5. In fan-out / fan-in, what does the fan-in step coordinate?
6. Why should a combined async response expose that fallback was used?
7. Why can a concurrent test pass many times and still be wrong?
8. What does an "interesting" stress-test outcome mean?
9. Why can NIO handle many waiting connections with fewer blocked threads?
10. Why does async not automatically make CPU-bound work faster?

## Multiple Choice

1. Which method starts an async value-producing task?
   - A. `join`
   - B. `supplyAsync`
   - C. `sleep`
   - D. `wait`

2. Which method can complete a future with a fallback value after a deadline?
   - A. `completeOnTimeout`
   - B. `Thread.yield`
   - C. `notifyAll`
   - D. `parallel`

3. In this lab, which service branch is intentionally slow?
   - A. profile
   - B. pricing
   - C. inventory
   - D. recommendations

4. In this lab, which service branch intentionally fails?
   - A. profile
   - B. pricing
   - C. inventory
   - D. recommendations

5. What should a stress-style test classify as forbidden?
   - A. A result allowed by the contract
   - B. A result that exposes a lost update
   - C. A result that must never occur
   - D. A result produced by a slow service

## Answer Key

1. B
2. A
3. C
4. D
5. C
