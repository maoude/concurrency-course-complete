from __future__ import annotations

import unittest

from week9.part3_language_contrast.exercises import contrast_models


class StudentWeek9Part3Test(unittest.TestCase):
    def test_contrasts_runtime_models(self) -> None:
        answer = {key: value.lower() for key, value in contrast_models().items()}

        self.assertIn("gil", answer["python"])
        self.assertTrue("goroutine" in answer["go"] and "channel" in answer["go"])
        self.assertTrue("actor" in answer["erlang"] and "message" in answer["erlang"])


if __name__ == "__main__":
    unittest.main()
