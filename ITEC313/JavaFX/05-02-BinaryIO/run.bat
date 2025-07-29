@echo off
setlocal enabledelayedexpansion

REM Binary I/O Demo - JavaFX Application
REM Cross-platform execution script for Windows

echo ==========================================
echo   Binary I/O Demo - JavaFX Application
echo ==========================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Java is not installed or not in PATH
    echo [INFO] Please install Java 24 or later
    pause
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [ERROR] Maven is not installed or not in PATH
    echo [INFO] Please install Maven 3.9+ or later
    pause
    exit /b 1
)

REM Detect platform
for /f "tokens=2 delims==" %%a in ('wmic os get osarchitecture /value ^| find "="') do set ARCH=%%a
if "%ARCH%"=="64-bit" (
    set PLATFORM=win
) else (
    set PLATFORM=win
)

echo [INFO] Detected platform: %PLATFORM% (Windows %ARCH%)
echo.

REM Clean previous builds
echo [INFO] Cleaning previous builds...
call mvn clean
if %errorlevel% neq 0 (
    echo [ERROR] Clean failed
    pause
    exit /b 1
)
echo [SUCCESS] Clean completed
echo.

REM Build the project
echo [INFO] Building project with Maven...
call mvn compile
if %errorlevel% neq 0 (
    echo [ERROR] Build failed
    pause
    exit /b 1
)
echo [SUCCESS] Build completed
echo.

REM Run the application
echo [INFO] Starting Binary I/O Demo application...
echo [INFO] Platform: %PLATFORM%
echo.

REM Run with Maven JavaFX plugin
call mvn javafx:run
if %errorlevel% neq 0 (
    echo [ERROR] Application failed to start
    pause
    exit /b 1
)

echo [SUCCESS] Application completed
echo.
pause 