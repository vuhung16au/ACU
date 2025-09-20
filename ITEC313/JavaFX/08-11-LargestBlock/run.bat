@echo off
REM Run script for Largest Block Demo
REM This script compiles and runs the JavaFX application

echo Starting Largest Block Demo...

REM Check if Maven is available
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 24 or later and try again
    pause
    exit /b 1
)

REM Run the application
echo Running Largest Block Demo with Maven...
mvn clean javafx:run

echo Application finished.
pause
