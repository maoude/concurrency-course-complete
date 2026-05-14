$ErrorActionPreference = "Stop"
$env:PYTHONPATH = "src"
python -m unittest tests.test_instructor_solutions
exit $LASTEXITCODE
