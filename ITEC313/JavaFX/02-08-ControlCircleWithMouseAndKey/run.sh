#!/bin/bash

# ControlCircleWithMouseAndKey - JavaFX Application Runner
# Cross-platform script for Unix/Linux/macOS

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}ControlCircleWithMouseAndKey - JavaFX Application${NC}"
echo "=================================================="

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}Error: Maven is not installed or not in PATH${NC}"
    echo "Please install Maven and try again."
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo -e "${RED}Error: Java is not installed or not in PATH${NC}"
    echo "Please install Java 24 or later and try again."
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 24 ]; then
    echo -e "${YELLOW}Warning: Java version $JAVA_VERSION detected. Java 24 or later is recommended.${NC}"
fi

echo -e "${GREEN}Building and running the application...${NC}"

# Clean and compile
echo "Cleaning previous build..."
mvn clean

# Compile and run
echo "Compiling and running..."
mvn javafx:run

echo -e "${GREEN}Application completed.${NC}" 