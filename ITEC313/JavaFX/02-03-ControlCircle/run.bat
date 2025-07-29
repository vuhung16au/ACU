@echo off
REM ControlCircle - JavaFX Application Runner
REM This script runs the JavaFX application using Maven

echo === ControlCircle JavaFX Application ===
echo Running the application...

REM Check if Maven is available
mvn --version >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven and try again
    pause
    exit /b 1
)

REM Check if Java is available
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 24 and try again
    pause
    exit /b 1
)

REM Display versions
echo Maven version:
mvn --version | findstr "Apache Maven"

echo Java version:
java -version

echo.
echo Building and running the application...
echo.

REM Clean and run the application
mvn clean javafx:run

echo.
echo Application finished.
pause 