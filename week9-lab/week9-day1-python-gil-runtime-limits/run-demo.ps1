param(
    [string]$Demo = "all"
)

$ErrorActionPreference = "Stop"
$env:PYTHONPATH = "src"
python -m week9.demo_runner $Demo
exit $LASTEXITCODE
