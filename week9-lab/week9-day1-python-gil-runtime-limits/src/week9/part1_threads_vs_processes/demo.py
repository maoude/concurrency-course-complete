from __future__ import annotations

import concurrent.futures
import os
import time


def cpu_bound_work(iterations: int) -> int:
    total = 0
    for i in range(iterations):
        total += (i * i) % 97
    return total


def run_threads(workers: int, iterations: int) -> float:
    start = time.perf_counter()
    with concurrent.futures.ThreadPoolExecutor(max_workers=workers) as executor:
        list(executor.map(cpu_bound_work, [iterations] * workers))
    return time.perf_counter() - start


def run_processes(workers: int, iterations: int) -> float:
    start = time.perf_counter()
    with concurrent.futures.ProcessPoolExecutor(max_workers=workers) as executor:
        list(executor.map(cpu_bound_work, [iterations] * workers))
    return time.perf_counter() - start


def compare_small_cpu_workload() -> dict[str, object]:
    workers = min(4, os.cpu_count() or 2)
    iterations = 150_000
    return {
        "workers": workers,
        "thread_seconds": round(run_threads(workers, iterations), 4),
        "process_seconds": round(run_processes(workers, iterations), 4),
        "lesson": "CPU-bound Python threads are limited by the GIL; processes use separate interpreters.",
    }
