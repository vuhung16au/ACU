#!/bin/bash

# JavaFX List Stack Queue PriorityQueue Demo
# Cross-platform execution script for Unix/Linux/macOS

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}JavaFX List Stack Queue PriorityQueue Demo${NC}"
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
    echo "Please install Java and try again."
    exit 1
fi

# Get Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
echo -e "${YELLOW}Java version: $JAVA_VERSION${NC}"

# Clean and compile
echo -e "${YELLOW}Cleaning and compiling...${NC}"
mvn clean compile

# Run the application
echo -e "${YELLOW}Running JavaFX application...${NC}"
mvn javafx:run

echo -e "${GREEN}Application finished.${NC}" 