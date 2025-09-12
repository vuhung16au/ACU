@echo off
REM Flat Precedence Calculator - Run Script for Windows
REM This script compiles and runs the JavaFX application

echo ==========================================
echo Flat Precedence Calculator
echo ==========================================
echo.

REM Check if Maven is installed
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java and try again
    pause
    exit /b 1
)

echo Building and running the application...
echo.

REM Clean, compile, and run the JavaFX application
mvn clean javafx:run

echo.
echo Application finished.
pause
