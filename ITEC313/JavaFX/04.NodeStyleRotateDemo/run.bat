@echo off
REM JavaFX Node Style Rotate Demo - Windows Execution Script
REM This script builds and runs the JavaFX application using Maven

echo === JavaFX Node Style Rotate Demo - Build ^& Run ===
echo.

REM Check if Maven is installed
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven 3.9.x or later
    pause
    exit /b 1
)

REM Check Java version
echo Checking Java version...
java --version
echo.

REM Check Maven version
echo Checking Maven version...
mvn --version
echo.

REM Clean and compile the project
echo Cleaning and compiling the project...
mvn clean compile
if %errorlevel% neq 0 (
    echo Build failed!
    pause
    exit /b 1
)
echo.

REM Run the application
echo Starting JavaFX application...
echo Note: This will run the enhanced demo version
mvn javafx:run

echo.
echo Application finished.
pause
