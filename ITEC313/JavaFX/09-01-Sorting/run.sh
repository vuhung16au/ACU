#!/bin/bash

# JavaFX Sorting Algorithm Visualizer - Unix/Linux/macOS Execution Script
# This script builds and runs the JavaFX sorting application

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}JavaFX Sorting Algorithm Visualizer${NC}"
echo -e "${BLUE}=====================================${NC}"

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo -e "${RED}Error: Java is not installed or not in PATH${NC}"
    echo "Please install OpenJDK 24 or later"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 24 ]; then
    echo -e "${YELLOW}Warning: Java version $JAVA_VERSION detected. OpenJDK 24+ is recommended.${NC}"
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}Error: Maven is not installed or not in PATH${NC}"
    echo "Please install Maven 3.9+"
    exit 1
fi

echo -e "${GREEN}Building project...${NC}"
mvn clean compile

echo -e "${GREEN}Running JavaFX application...${NC}"
mvn javafx:run

echo -e "${GREEN}Application finished.${NC}" 