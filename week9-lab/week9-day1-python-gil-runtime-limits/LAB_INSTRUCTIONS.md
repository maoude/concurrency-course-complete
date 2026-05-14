# Lab Instructions - Week 9

## Goal

Understand why Python CPU-bound threads do not scale like Java CPU-bound
threads, and why multiprocessing changes the resource model.

## Workflow

1. Run `.\run-tests.ps1`.
2. Run `.\run-all-demos.ps1`.
3. Run `.\student-check.ps1` to see expected TODO failures.
4. Implement one exercise at a time.
5. Complete `VISUAL_PROOF_GUIDE.md` and `LANGUAGE_CONTRAST.md`.
6. Discuss `case-studies/W9_Knight-Capital-2012.md`.

## Required Explanation

Be ready to explain:

- why CPU-bound Python threads do not use multiple cores effectively
- why multiprocessing can use multiple cores
- what overhead multiprocessing introduces
- what visual evidence you captured
- how Go and Erlang use different concurrency models
