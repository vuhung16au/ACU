@echo off
REM Hilbert Curve Demo - Run Script for Windows
REM This script compiles and runs the Hilbert Curve JavaFX application

echo Hilbert Curve Demo - Starting Application
echo ========================================

REM Check if Maven is available
where mvn >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is available
where java >nul 2>nul
if %ERRORLEVEL% NEQ 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 24 or later and try again
    pause
    exit /b 1
)

echo Building and running Hilbert Curve Demo...
echo.

REM Clean, compile, and run the application
mvn clean javafx:run

echo.
echo Application finished.
pause
