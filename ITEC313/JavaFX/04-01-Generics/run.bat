@echo off
REM JavaFX Generics Demo - Windows Execution Script
REM This script builds and runs the JavaFX generics demonstration application

echo JavaFX Generics Demo - Build and Run Script
echo =============================================

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 24 or later and try again
    pause
    exit /b 1
)

echo Building project...
mvn clean compile

if %errorlevel% equ 0 (
    echo Build successful!
    echo Starting JavaFX application...
    mvn javafx:run
) else (
    echo Build failed!
    pause
    exit /b 1
)

pause 