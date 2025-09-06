@echo off
REM Koch Snowflake Fractal Demo - Run Script for Windows
REM This script compiles and runs the JavaFX application

echo Koch Snowflake Fractal Demo
echo ==========================
echo.

REM Check if Maven is available
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is available
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java and try again
    pause
    exit /b 1
)

echo Building and running the Koch Snowflake Fractal Demo...
echo.

REM Clean, compile, and run
mvn clean javafx:run

echo.
echo Application finished.
pause
