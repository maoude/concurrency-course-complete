from __future__ import annotations


def explain_cpu_bound_choice() -> dict[str, str]:
    return {
        "threads": "CPU-bound Python threads do not scale across cores effectively because of the GIL.",
        "multiprocessing": "multiprocessing can use multiple cores because each process has its own interpreter.",
        "cost": "processes add startup, memory, and serialization overhead.",
    }
