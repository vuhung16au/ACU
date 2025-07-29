@echo off
REM JavaFX Image and ImageView Demo - Run Script
REM This script builds and runs the JavaFX application on Windows systems

echo JavaFX Image and ImageView Demo
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

echo Building project...
call mvn clean compile

if errorlevel 1 (
    echo Build failed. Please check the error messages above.
    pause
    exit /b 1
)

echo Build successful. Running application...
echo.
call mvn javafx:run

pause 