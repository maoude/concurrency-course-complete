from __future__ import annotations

import unittest

from week9.part2_visual_proof.exercises import visual_proof_plan


class StudentWeek9Part2Test(unittest.TestCase):
    def test_visual_proof_plan_is_concrete(self) -> None:
        plan = [step.lower() for step in visual_proof_plan()]

        self.assertGreaterEqual(len(plan), 4)
        self.assertTrue(any("task manager" in step or "htop" in step or "py-spy" in step for step in plan))
        self.assertTrue(any("thread" in step for step in plan))
        self.assertTrue(any("process" in step for step in plan))
        self.assertTrue(any("cpu" in step or "core" in step for step in plan))


if __name__ == "__main__":
    unittest.main()
