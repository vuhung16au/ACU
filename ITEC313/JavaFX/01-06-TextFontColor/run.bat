@echo off
REM JavaFX Text Font Color Demo - Windows Execution Script
REM This script builds and runs the JavaFX application

echo JavaFX Text Font Color Demo
echo ============================

REM Check if Maven is installed
mvn -version >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 24 or later and try again
    pause
    exit /b 1
)

echo Building and running the application...
echo.

REM Clean and compile
echo Cleaning previous build...
call mvn clean

REM Compile and run
echo Compiling and running...
call mvn javafx:run

echo.
echo Application finished.
pause 