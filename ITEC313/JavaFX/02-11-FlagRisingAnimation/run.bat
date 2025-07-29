@echo off
setlocal enabledelayedexpansion

REM FlagRisingAnimation - JavaFX Flag Rising Animation Demo
REM Cross-platform execution script for Windows

echo 🚩 FlagRisingAnimation - JavaFX Flag Rising Animation Demo
echo ==================================================

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Error: Maven is not installed or not in PATH
    echo Please install Maven and try again.
    echo.
    echo Installation: Download from https://maven.apache.org/download.cgi
    pause
    exit /b 1
)

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Error: Java is not installed or not in PATH
    echo Please install Java 24+ and try again.
    pause
    exit /b 1
)

REM Check Java version
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
    set JAVA_VERSION=!JAVA_VERSION:"=!
    for /f "tokens=1 delims=." %%a in ("!JAVA_VERSION!") do set JAVA_MAJOR=%%a
)

if %JAVA_MAJOR% lss 24 (
    echo ❌ Error: Java 24 or higher is required. Current version: %JAVA_VERSION%
    echo Please upgrade Java and try again.
    pause
    exit /b 1
)

echo ✅ Java version: %JAVA_VERSION%
echo ✅ Maven version: 
mvn -version 2>&1 | findstr "Apache Maven"
echo.

REM Clean and compile
echo 🔨 Building project...
mvn clean compile

if %errorlevel% neq 0 (
    echo ❌ Build failed. Please check the error messages above.
    pause
    exit /b 1
)

echo ✅ Build successful!
echo.

REM Run the application
echo 🚀 Starting FlagRisingAnimation...
echo 📝 Animation Details:
echo    - Flag rises from bottom to top
echo    - Animation duration: 10 seconds per cycle
echo    - Total cycles: 5
echo    - Path: Vertical line (100, 200) to (100, 0)
echo.

mvn javafx:run

echo.
echo ✅ Application completed successfully!
pause 