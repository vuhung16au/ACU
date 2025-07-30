@echo off
REM JavaFX List Stack Queue PriorityQueue Demo
REM Cross-platform execution script for Windows

echo JavaFX List Stack Queue PriorityQueue Demo
echo ==================================================

REM Check if Maven is installed
mvn -version >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again.
    pause
    exit /b 1
)

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java and try again.
    pause
    exit /b 1
)

REM Get Java version
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
)
echo Java version: %JAVA_VERSION%

REM Clean and compile
echo Cleaning and compiling...
mvn clean compile

REM Run the application
echo Running JavaFX application...
mvn javafx:run

echo Application finished.
pause 