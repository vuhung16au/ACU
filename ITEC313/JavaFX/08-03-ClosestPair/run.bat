@echo off

REM ClosestPair JavaFX Demo - Run Script for Windows
REM This script builds and runs the JavaFX application

echo Building ClosestPair JavaFX Demo...

REM Clean and compile
call mvn clean compile

if %ERRORLEVEL% neq 0 (
    echo Build failed!
    pause
    exit /b %ERRORLEVEL%
)

echo Running ClosestPair JavaFX Demo...

REM Run with JavaFX Maven plugin
call mvn javafx:run

if %ERRORLEVEL% neq 0 (
    echo Application failed to start!
    pause
    exit /b %ERRORLEVEL%
)

echo Application finished.
pause
