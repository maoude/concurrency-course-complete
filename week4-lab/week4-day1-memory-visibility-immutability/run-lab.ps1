$ErrorActionPreference = "Stop"

$proj = $PSScriptRoot
Push-Location $proj
try {
  Write-Host "`n=== WEEK 4 LAB: DEMO + TEST CHECK ===`n"

  Write-Host "`nRunning Demo01_StopFlagVisibility...`n"
  .\gradlew.bat run --console=plain --no-daemon | Out-Host

  Write-Host "`nRunning student grading checks...`n"
  .\gradlew.bat test --tests "*StudentWeek*" --console=plain --no-daemon | Out-Host

  Write-Host "`nWeek 4 run-lab flow finished.`n"
}
finally {
  Pop-Location
}
