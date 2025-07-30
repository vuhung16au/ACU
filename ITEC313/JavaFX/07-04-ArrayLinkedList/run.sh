#!/bin/bash

# Array and LinkedList Demo - Run Script
# Cross-platform JavaFX application runner

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${BLUE}================================${NC}"
echo -e "${BLUE}  Array and LinkedList Demo     ${NC}"
echo -e "${BLUE}================================${NC}"

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

echo -e "${GREEN}Building and running the application...${NC}"

# Clean and compile
echo -e "${YELLOW}Cleaning previous build...${NC}"
mvn clean

# Compile
echo -e "${YELLOW}Compiling...${NC}"
mvn compile

# Run with direct Java command
echo -e "${YELLOW}Running application...${NC}"
java --module-path "$(mvn dependency:build-classpath -Dmdep.outputFile=/dev/stdout -q)" --add-modules javafx.controls,javafx.fxml,javafx.graphics,javafx.base -cp target/classes com.acu.javafx.arraylinkedlist.ArrayLinkedListDemo

echo -e "${GREEN}Application finished.${NC}" 