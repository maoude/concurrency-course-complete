# Lab Instructions - Week 7

## Goal

Build async pipelines that have explicit completion, timeout, fallback, and
failure behavior. Test them with strategies that make race windows visible.

## Workflow

1. Read `EXERCISES.md`.
2. Run `.\gradlew.bat test` to verify demos.
3. Run `.\gradlew.bat studentCheck` to see the expected TODO failures.
4. Implement one exercise at a time.
5. Rerun the relevant student test.
6. Finish with `.\gradlew.bat test` and `.\gradlew.bat studentCheck`.
7. Review `QUIZ_QUESTIONS.md` before the exit ticket.

## Required Explanation

For each exercise, be ready to explain:

- where the async boundary is
- what timeout protects the caller
- what fallback value is returned
- how failures propagate or are translated
- how the test amplifies timing-sensitive behavior
- why async is a pipeline model, not fire-and-forget chaos

## Required Sequence Diagram

For Part 4, draw a sequence diagram showing:

- request enters pipeline
- independent tasks fan out
- `allOf()` coordinates completion
- results are aggregated
- slow/failing tasks trigger timeout or fallback behavior

## Checkpoint Bridge

Week 7 prepares the Week 8 async server checkpoint. The key design move is
not "use more threads"; it is explicit completion, failure, timeout, and
fallback semantics.
