$ErrorActionPreference = "Stop"
$env:PYTHONPATH = "src"
python -m week9.demo_runner all
exit $LASTEXITCODE
