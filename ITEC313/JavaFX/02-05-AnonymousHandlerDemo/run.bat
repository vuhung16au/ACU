@echo off
REM JavaFX AnonymousHandlerDemo - Run Script for Windows
REM This script runs the JavaFX application using Maven

echo JavaFX AnonymousHandlerDemo
echo ===========================

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

echo Starting JavaFX application...
echo.

REM Run the application using Maven
mvn clean javafx:run -Djavafx.mainClass=com.acu.javafx.anonymoushandlerdemo.Launcher

echo.
echo Application finished.
pause 