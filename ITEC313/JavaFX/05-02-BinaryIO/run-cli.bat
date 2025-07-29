@echo off
REM Binary I/O CLI Demo Runner Script
REM This script runs the CLI version of the Binary I/O demonstration

echo === Binary I/O CLI Demo ===
echo Running CLI version of Binary I/O demonstration...
echo.

REM Check if Java is available
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    pause
    exit /b 1
)

REM Check if Maven is available
mvn -version >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed or not in PATH
    pause
    exit /b 1
)

REM Compile and run the CLI version
echo Compiling and running BinaryIOCLIDemo...
echo.

REM Run using Maven exec plugin
mvn exec:java -Dexec.mainClass="com.acu.javafx.binaryio.BinaryIOCLIDemo" -Dexec.args=""

echo.
echo CLI demo completed.
pause 