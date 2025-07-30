@echo off
REM JavaFX Probing Demo - Windows Execution Script
REM This script builds and runs the JavaFX application

echo ==========================================
echo JavaFX Probing Techniques Demo
echo ==========================================

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

echo.
echo Maven version:
mvn -version

echo.
echo Building the project...
mvn clean compile

echo.
echo Running the JavaFX application...
mvn javafx:run

echo.
echo Application finished.
pause 