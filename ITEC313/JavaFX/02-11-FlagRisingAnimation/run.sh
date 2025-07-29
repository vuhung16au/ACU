#!/bin/bash

# FlagRisingAnimation - JavaFX Flag Rising Animation Demo
# Cross-platform execution script for Unix/Linux/macOS

echo "🚩 FlagRisingAnimation - JavaFX Flag Rising Animation Demo"
echo "=================================================="

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo "❌ Error: Maven is not installed or not in PATH"
    echo "Please install Maven and try again."
    echo ""
    echo "Installation commands:"
    echo "  macOS: brew install maven"
    echo "  Ubuntu/Debian: sudo apt install maven"
    echo "  CentOS/RHEL: sudo yum install maven"
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed or not in PATH"
    echo "Please install Java 24+ and try again."
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 24 ]; then
    echo "❌ Error: Java 24 or higher is required. Current version: $JAVA_VERSION"
    echo "Please upgrade Java and try again."
    exit 1
fi

echo "✅ Java version: $(java -version 2>&1 | head -n 1)"
echo "✅ Maven version: $(mvn -version 2>&1 | head -n 1)"
echo ""

# Clean and compile
echo "🔨 Building project..."
mvn clean compile

if [ $? -ne 0 ]; then
    echo "❌ Build failed. Please check the error messages above."
    exit 1
fi

echo "✅ Build successful!"
echo ""

# Run the application
echo "🚀 Starting FlagRisingAnimation..."
echo "📝 Animation Details:"
echo "   - Flag rises from bottom to top"
echo "   - Animation duration: 10 seconds per cycle"
echo "   - Total cycles: 5"
echo "   - Path: Vertical line (100, 200) to (100, 0)"
echo ""

mvn javafx:run

echo ""
echo "✅ Application completed successfully!" 