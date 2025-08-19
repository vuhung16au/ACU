#!/bin/bash

# JavaFX Database Application Runner Script
# This script compiles and runs the JavaFX database application

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}JavaFX Database Application Runner${NC}"
echo "=========================================="

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo -e "${RED}Error: Java is not installed or not in PATH${NC}"
    exit 1
fi

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}Error: Maven is not installed or not in PATH${NC}"
    exit 1
fi

echo -e "${YELLOW}Java version:${NC}"
java -version

echo -e "${YELLOW}Maven version:${NC}"
mvn -version

echo -e "\n${GREEN}Building the project...${NC}"
mvn clean compile

echo -e "\n${GREEN}Running the application...${NC}"
echo -e "${YELLOW}Note: The application will create a SQLite database file 'employee.sqlite' in the current directory${NC}"
echo ""

# Run with Maven
mvn javafx:run

echo -e "\n${GREEN}Application finished.${NC}"
