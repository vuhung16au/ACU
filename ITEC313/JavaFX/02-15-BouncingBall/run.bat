@echo off
REM JavaFX Bouncing Ball Demo - Windows Execution Script
REM This script builds and runs the JavaFX bouncing ball application

echo === JavaFX Bouncing Ball Demo ===
echo Building and running the application...

REM Check if Maven is available
mvn -version >nul 2>&1
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
    echo Please install Java 24 or later and try again
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
echo Starting the bouncing ball application...
echo Controls:
echo   - Mouse press: Pause animation
echo   - Mouse release: Resume animation
echo   - Up arrow: Increase speed
echo   - Down arrow: Decrease speed
echo   - Close window to exit
echo.

mvn javafx:run

echo Application finished.
pause 