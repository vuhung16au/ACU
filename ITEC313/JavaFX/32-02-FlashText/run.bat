@echo off
REM FlashText JavaFX Application - Windows Run Script
REM This script builds and runs the FlashText JavaFX application

echo === FlashText JavaFX Application ===
echo Building and running the application...

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
    echo Please install Java 24 and try again
    pause
    exit /b 1
)

REM Display Java version
echo Java version:
java -version

REM Display Maven version
echo Maven version:
mvn -version

REM Clean and compile
echo Cleaning and compiling...
mvn clean compile

REM Run the application
echo Running the FlashText application...
mvn javafx:run

echo Application finished.
pause
