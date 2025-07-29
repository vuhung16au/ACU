@echo off
REM JavaFX Recursion Demonstrations - Windows Execution Script
REM This script builds and runs the JavaFX application

echo JavaFX Recursion Demonstrations
echo ================================

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

echo Java version:
java -version

echo Maven version:
mvn -version

echo.
echo Building project...
mvn clean compile

echo.
echo Running JavaFX application...
mvn javafx:run

echo.
echo Application finished.
pause 