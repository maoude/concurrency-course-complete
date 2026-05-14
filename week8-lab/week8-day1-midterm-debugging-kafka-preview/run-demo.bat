@echo off
set DEMO=%1
if "%DEMO%"=="" set DEMO=all
call gradlew.bat run --console=plain --args="%DEMO%"
