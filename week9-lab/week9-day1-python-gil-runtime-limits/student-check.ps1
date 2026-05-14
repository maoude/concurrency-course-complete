$ErrorActionPreference = "Stop"
$env:PYTHONPATH = "src"
python -m unittest discover -s tests -p "student_*.py"
exit $LASTEXITCODE
