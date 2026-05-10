# Troubleshooting - Week 4 Lab

- If tests are flaky, rerun specific class with `--tests` and inspect race windows.
- If stop-flag tests fail, check that the flag field is exactly `volatile`.
- If DCL test fails, ensure singleton reference is `volatile` and checked twice.
- If snapshot cache test fails, never mutate published maps in place.
