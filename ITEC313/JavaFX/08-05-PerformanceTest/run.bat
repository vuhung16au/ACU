@echo off
REM JavaFX Performance Test Application - Windows Runner
REM This script builds and runs the JavaFX application on Windows

echo === JavaFX Performance Test Application ===
echo Building and running on Windows
echo.

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if errorlevel 1 (
    echo Error: Maven is not installed or not in PATH
    pause
    exit /b 1
)

REM Display Java and Maven versions
echo Java version:
java -version
echo.
echo Maven version:
mvn -version
echo.

REM Clean and compile
echo Cleaning previous build...
mvn clean

echo Compiling project...
mvn compile

echo Running JavaFX application...
echo Note: The application will open in a new window.
echo.

REM Run the application using JavaFX Maven plugin
mvn javafx:run

echo.
echo Application completed.
pause 