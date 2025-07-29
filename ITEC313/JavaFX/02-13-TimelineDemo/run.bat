@echo off
REM JavaFX Timeline Demo - Windows Execution Script
REM This script builds and runs the JavaFX application

echo === JavaFX Timeline Demo ===
echo Building and running the application...

REM Check if Maven is available
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is available
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 24 and try again
    pause
    exit /b 1
)

REM Clean and compile
echo Cleaning previous build...
call mvn clean

echo Compiling and running...
call mvn javafx:run

echo Application completed.
pause 