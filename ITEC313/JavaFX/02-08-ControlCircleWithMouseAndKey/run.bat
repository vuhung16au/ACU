@echo off
REM ControlCircleWithMouseAndKey - JavaFX Application Runner
REM Cross-platform script for Windows

echo ControlCircleWithMouseAndKey - JavaFX Application
echo ==================================================

REM Check if Maven is installed
where mvn >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again.
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>&1
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 24 or later and try again.
    pause
    exit /b 1
)

echo Building and running the application...

REM Clean and compile
echo Cleaning previous build...
call mvn clean

REM Compile and run
echo Compiling and running...
call mvn javafx:run

echo Application completed.
pause 