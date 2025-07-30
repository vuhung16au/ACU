@echo off
REM JavaFX Sorting Algorithm Visualizer - Windows Execution Script
REM This script builds and runs the JavaFX sorting application

setlocal enabledelayedexpansion

echo JavaFX Sorting Algorithm Visualizer
echo =====================================

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    echo Please install OpenJDK 24 or later
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven 3.9+
    pause
    exit /b 1
)

echo Building project...
call mvn clean compile
if errorlevel 1 (
    echo Error: Build failed
    pause
    exit /b 1
)

echo Running JavaFX application...
call mvn javafx:run
if errorlevel 1 (
    echo Error: Application failed to start
    pause
    exit /b 1
)

echo Application finished.
pause 