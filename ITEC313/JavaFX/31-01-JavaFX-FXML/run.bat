@echo off
REM Advanced JavaFX and FXML - Run Script for Windows
REM This script compiles and runs the StyleSheetDemo application

echo === Advanced JavaFX and FXML - StyleSheetDemo ===
echo Building and running the application...

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
    echo Please install Java 24 or higher and try again
    pause
    exit /b 1
)

REM Clean and run with JavaFX Maven plugin
echo Running with JavaFX Maven plugin...
mvn clean javafx:run

echo Application finished.
pause
