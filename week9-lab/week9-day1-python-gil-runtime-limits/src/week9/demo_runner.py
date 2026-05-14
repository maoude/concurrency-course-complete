from __future__ import annotations

import sys

from week9.part1_threads_vs_processes.demo import compare_small_cpu_workload
from week9.part2_visual_proof.demo import visual_proof_steps
from week9.part3_language_contrast.demo import language_contrast
from week9.part4_case_study.demo import knight_capital_lessons


def main(argv: list[str] | None = None) -> None:
    args = sys.argv[1:] if argv is None else argv
    demo = args[0].lower() if args else "all"
    demos = {
        "01": demo01,
        "demo01": demo01,
        "02": demo02,
        "demo02": demo02,
        "03": demo03,
        "demo03": demo03,
        "04": demo04,
        "demo04": demo04,
    }
    if demo == "all":
        for fn in (demo01, demo02, demo03, demo04):
            fn()
        return
    if demo not in demos:
        raise SystemExit(f"Unknown Week 9 demo: {demo}")
    demos[demo]()


def demo01() -> None:
    print("\n=== Demo01 ===")
    print(compare_small_cpu_workload())


def demo02() -> None:
    print("\n=== Demo02 ===")
    print(visual_proof_steps())


def demo03() -> None:
    print("\n=== Demo03 ===")
    print(language_contrast())


def demo04() -> None:
    print("\n=== Demo04 ===")
    print(knight_capital_lessons())


if __name__ == "__main__":
    main()
