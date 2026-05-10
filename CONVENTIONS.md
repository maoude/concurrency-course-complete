# Project Conventions

This repository hosts the Concurrency & Distributed Systems course labs.
Every week follows the **same** layout. New weeks copy `_template-week/`
and fill in the blanks. Students do not need to learn a new structure
each week.

---

## 1) Lab folder layout

Every weekly lab lives under `weekN-lab/weekN-dayD-<topic>/` and has:

```
weekN-dayD-<topic>/
├── build.gradle               Java 21 + JUnit 5 + studentCheck task
├── settings.gradle
├── README.md                  overview + how-to-run
├── LAB_INSTRUCTIONS.md        what students should observe
├── EXERCISES.md               numbered list of Ex{N}.{M} (student work)
├── CHECKLIST.md               one tickbox per Ex
├── QUIZ_QUESTIONS.md          short oral-quiz items
├── TROUBLESHOOTING.md
├── .gitignore
├── .gitattributes
├── src/main/java/edu/lu/concurrency/weekN/dayD/
│   └── partN_<topic>/
│       ├── Demo*.java                instructor demos shown live
│       ├── exercises/
│       │   └── Ex{N}_{Name}.java     STUDENT STUB - has TODOs
│       └── solutions/                INSTRUCTOR ONLY - see §5
│           └── Sol{N}_{Name}.java
└── src/test/java/edu/lu/concurrency/weekN/dayD/
    ├── TestIO.java                   shared stdout-capture helper
    └── partN_<topic>/
        ├── Demo*Test.java                      tests the live demo runs
        └── exercises/
            └── StudentWeek{W}Part{N}_Ex{M}Test.java   grades student work
```

### Why the names matter

- The `exercises/` package is the only place students should write code.
- The `solutions/` package is the instructor key. By default it is
  excluded from the build (see §5).
- All grading tests start with `StudentWeek` so a single Gradle filter
  selects them: `gradlew test --tests "StudentWeek3Part2*"`.

---

## 2) Numbering

- **Demos** are numbered globally per day: `Demo01_…`, `Demo02_…`, …
- **Exercises** are numbered per part: `Ex1_…`, `Ex2_…`, … inside each
  `partN_xxx/exercises/`. The "external" handle for a student is
  `W{week}.P{part}.Ex{n}` (e.g. *W3.P2.Ex2*).
- **Tests** mirror the exercise number 1-to-1 and use the class name
  `StudentWeek{W}Part{N}_Ex{M}Test`. Inner `@Test` methods can be more
  granular but the class name is the contract.

Example for Week 3, Part 2 (Monitors), Exercise 2:

| What                | Path                                                                            |
|---------------------|----------------------------------------------------------------------------------|
| Stub                | `src/main/java/.../week3/day1/part2_monitors/exercises/Ex2_LockSplitting.java`  |
| Solution (key)      | `src/main/java/.../week3/day1/part2_monitors/solutions/Sol2_LockSplitting.java` |
| Grading test        | `src/test/java/.../part2_monitors/exercises/StudentWeek3Part2_Ex2Test.java`     |
| Student-facing name | **W3.P2.Ex2 - Lock splitting**                                                  |

---

## 3) Exercise stub format

Every `Ex*.java` file has this shape:

```java
/*
 * ================================================================
 * EXERCISE W3.P2.Ex2 - Lock Splitting
 * ----------------------------------------------------------------
 * Goal:        (one sentence)
 * Given:       (what is provided)
 * Your task:   (numbered list of TODOs)
 * Pass when:   StudentWeek3Part2_Ex2Test is green.
 * Hint:        (optional, one line)
 * ================================================================
 */
package edu.lu.concurrency.week3.day1.part2_monitors.exercises;

public final class Ex2_LockSplitting {

    /* TODO: declare two private final Object locks (one per side). */

    public void incrementLeft()  { /* TODO */ }
    public void incrementRight() { /* TODO */ }
    public int  total()          { /* TODO: return left + right */; return 0; }

    private Ex2_LockSplitting() {}
}
```

- Keep the class **compilable** so the rest of the test suite still
  builds. Stub methods may return dummy values; the grading test will
  fail until the student implements the real logic.
- Never put `// solution: ...` hints in the stub. Solutions live in
  `solutions/`.

---

## 4) Grading-test format

```java
package edu.lu.concurrency.week3.day1.part2_monitors.exercises;

import edu.lu.concurrency.week3.day1.TestIO;
import org.junit.jupiter.api.RepeatedTest;
import static org.junit.jupiter.api.Assertions.*;

public class StudentWeek3Part2_Ex2Test {

    @RepeatedTest(5)
    void lock_splitting_preserves_total_under_contention() throws Exception {
        // ...drive Ex2_LockSplitting from many threads, assert correctness.
    }
}
```

Rules:
- One test class per exercise. Test class names are the contract students
  rely on; do not rename them lightly.
- Prefer `@RepeatedTest` for concurrency exercises so flaky-but-wrong
  solutions fail.
- Capture stdout via `TestIO.captureStdout(...)` when the exercise
  outputs a result line.

---

## 5) Instructor solutions (excluded by default)

Solutions are kept in `src/main/java/.../partN_xxx/solutions/`. The build
is configured to **exclude** that folder from the default compile so a
student running `gradlew test` never accidentally pulls in an answer.

To compile and test against the instructor key:

```
gradlew test -Pinstructor=true
```

The flag is wired in each `build.gradle` (see `_template-week/build.gradle`).
Solution test names start with `Solution*` and are also off by default.

---

## 6) Running checks

Three ways to run:

```
gradlew test                            # everything (Demo*Test + Student*Test)
gradlew studentCheck                    # only Student*Test, verbose
gradlew test --tests "StudentWeek3*"    # one week
gradlew test --tests "StudentWeek3Part2*"   # one part of one week
```

`studentCheck` is the recommended student-facing entrypoint. It is
defined identically in every weekly `build.gradle`.

---

## 7) Commit hygiene

- Do not commit `.class` files (already in `.gitignore`).
- Do not commit student answers under `solutions/` until the lab has
  been delivered. Tag those commits `solutions/wN-dD`.
- Each week's `EXERCISES.md` is the source of truth for what is being
  graded. Update it before adding stubs.
