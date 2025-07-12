@echo off
REM JavaFX Controls Demo - Windows Run Script
REM This script builds and runs the JavaFX application using Maven

echo ======================================
echo JavaFX Controls and Multimedia Demo
echo ======================================

REM Check if Maven is installed
where mvn >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Maven is not installed or not in PATH
    echo Please install Maven 3.9.x or later
    pause
    exit /b 1
)

REM Check if Java is installed
where java >nul 2>nul
if %errorlevel% neq 0 (
    echo Error: Java is not installed or not in PATH
    echo Please install OpenJDK 24 or later
    pause
    exit /b 1
)

REM Display system information
echo System Information:
echo OS: Windows
echo Architecture: %PROCESSOR_ARCHITECTURE%
echo Java Version:
java -version
echo Maven Version:
mvn -version
echo.

REM Clean and compile the project
echo Building project...
mvn clean compile

REM Check if build was successful
if %errorlevel% equ 0 (
    echo Build successful!
    echo.
    echo Starting application...
    echo Note: Close the application window to return to command prompt
    echo.
    
    REM Run the application
    mvn javafx:run
) else (
    echo Build failed!
    pause
    exit /b 1
)

pause
