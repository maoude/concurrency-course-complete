$ErrorActionPreference = "Stop"
$env:PYTHONPATH = "src"
python -m unittest tests.test_demo_smoke
exit $LASTEXITCODE
