@echo off
REM Selection Sort Visualizer - JavaFX Application Runner for Windows
REM This script runs the Selection Sort visualization application

echo ========================================
echo Selection Sort Visualizer - JavaFX
echo ========================================

REM Check if Maven is installed
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven to run this application
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 11 or higher to run this application
    pause
    exit /b 1
)

echo Compiling and running Selection Sort Visualizer...
echo This may take a moment on first run...
echo.

REM Run the JavaFX application using Maven
mvn clean javafx:run

echo.
echo Application finished.
pause
