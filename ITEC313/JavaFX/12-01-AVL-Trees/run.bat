@echo off
REM AVL Tree JavaFX Demo - Run Script
REM This script runs the JavaFX application on Windows systems

echo AVL Tree JavaFX Demo
echo ====================

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed or not in PATH
    pause
    exit /b 1
)

echo Building and running the application...
echo.

REM Clean and compile
echo Cleaning previous build...
mvn clean

echo Compiling and running...
mvn javafx:run

echo.
echo Application finished.
pause 