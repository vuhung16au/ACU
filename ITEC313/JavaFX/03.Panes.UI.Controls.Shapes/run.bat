@echo off

REM JavaFX Shapes Demo Run Script for Windows
REM This script compiles and runs the JavaFX application

echo JavaFX Panes, UI Controls & Shapes Demo
echo =======================================

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
    echo Please install Java 11 or higher
    pause
    exit /b 1
)

echo Building the application...
mvn clean compile

if %errorlevel% equ 0 (
    echo Build successful! Starting the application...
    mvn javafx:run
) else (
    echo Build failed! Please check the error messages above.
    pause
    exit /b 1
)

pause
