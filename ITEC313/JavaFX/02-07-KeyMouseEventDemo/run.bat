@echo off

REM JavaFX Key and Mouse Event Demo - Run Script
REM This script builds and runs the JavaFX application on Windows systems

echo JavaFX Key and Mouse Event Demo
echo ================================

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

REM Display Java version
echo Java version:
java -version
echo.

REM Clean and compile the project
echo Building the project...
mvn clean compile

if errorlevel 1 (
    echo Error: Build failed
    pause
    exit /b 1
)

REM Run the application
echo Running the application...
mvn javafx:run

echo.
echo Application finished.
pause 