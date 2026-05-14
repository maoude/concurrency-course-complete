$ErrorActionPreference = "Stop"

$projectRoot = $PSScriptRoot
Push-Location $projectRoot
try {
    Write-Host "Running all Week 6 demos"
    .\gradlew.bat run --console=plain --args="all"
}
finally {
    Pop-Location
}
