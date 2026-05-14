param(
    [string]$Demo = "all"
)

$ErrorActionPreference = "Stop"
.\gradlew.bat run --console=plain --args="$Demo"
