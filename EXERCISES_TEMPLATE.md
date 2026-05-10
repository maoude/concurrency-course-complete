# Exercises - Week {W} Day {D} - {TopicName}

> Copy this file into each weekly lab as `EXERCISES.md` and fill in the
> blanks. One row per `Ex{N}_{Name}.java` stub. Keep the table in the
> same order as students should attempt them.

---

## How to work on these

1. Open the file under `src/main/java/.../partN_xxx/exercises/Ex*.java`.
2. Read the header comment (Goal / Given / Your task / Pass when).
3. Replace every `TODO` with your implementation.
4. Run **only your test** while iterating:
   ```
   gradlew test --tests "StudentWeek{W}Part{N}_Ex{M}Test"
   ```
5. When the part is green, run the whole part:
   ```
   gradlew test --tests "StudentWeek{W}Part{N}*"
   ```
6. When the week is green, hand it in.

To run every exercise across the lab: `gradlew studentCheck`.

---

## Exercise list

| ID         | Stub                                | Test                                       | Topic               | Status |
|------------|--------------------------------------|--------------------------------------------|---------------------|--------|
| W{W}.P1.Ex1 | `part1_xxx/exercises/Ex1_Foo.java`  | `StudentWeek{W}Part1_Ex1Test`              | (one-line goal)     | TODO   |
| W{W}.P1.Ex2 | `part1_xxx/exercises/Ex2_Bar.java`  | `StudentWeek{W}Part1_Ex2Test`              | ...                 | TODO   |
| W{W}.P2.Ex1 | `part2_xxx/exercises/Ex1_Baz.java`  | `StudentWeek{W}Part2_Ex1Test`              | ...                 | TODO   |
| ...        | ...                                  | ...                                        | ...                 | ...    |

---

## Grading

- Each test class is worth 1 point.
- Tests using `@RepeatedTest(N)` must pass **all N runs** to count.
- A solution that hard-codes the expected output (e.g. `System.out.println("...")`
  to spoof stdout assertions) is rejected.
- Late policy and weighting are in the course syllabus, not here.
