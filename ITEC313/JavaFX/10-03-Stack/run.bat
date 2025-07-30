@echo off
REM Stack Demo Application Runner Script
REM For Windows systems

echo Building and running Stack Demo Application...

REM Check if Maven is installed
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 24 or later and try again
    pause
    exit /b 1
)

REM Clean and compile
echo Cleaning and compiling...
call mvn clean compile

if %errorlevel% neq 0 (
    echo Error: Compilation failed
    pause
    exit /b 1
)

REM Run the application
echo Starting Stack Demo Application...
call mvn javafx:run

echo Application finished.
pause 