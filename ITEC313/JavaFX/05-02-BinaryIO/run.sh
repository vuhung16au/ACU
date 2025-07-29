#!/bin/bash

# Binary I/O Demo - JavaFX Application
# Cross-platform execution script for Unix/Linux/macOS

set -e

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Check if Java is installed
check_java() {
    if ! command -v java &> /dev/null; then
        print_error "Java is not installed or not in PATH"
        print_status "Please install Java 24 or later"
        exit 1
    fi
    
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
    if [ "$JAVA_VERSION" -lt "24" ]; then
        print_warning "Java version $JAVA_VERSION detected. Java 24+ is recommended."
    else
        print_success "Java version $JAVA_VERSION detected"
    fi
}

# Check if Maven is installed
check_maven() {
    if ! command -v mvn &> /dev/null; then
        print_error "Maven is not installed or not in PATH"
        print_status "Please install Maven 3.9+ or later"
        exit 1
    fi
    
    MAVEN_VERSION=$(mvn -version 2>&1 | head -n 1 | cut -d' ' -f3)
    print_success "Maven version $MAVEN_VERSION detected"
}

# Detect platform
detect_platform() {
    OS=$(uname -s)
    ARCH=$(uname -m)
    
    case $OS in
        "Darwin")
            if [ "$ARCH" = "arm64" ]; then
                PLATFORM="mac-aarch64"
            else
                PLATFORM="mac"
            fi
            ;;
        "Linux")
            if [ "$ARCH" = "aarch64" ]; then
                PLATFORM="linux-aarch64"
            else
                PLATFORM="linux"
            fi
            ;;
        *)
            print_error "Unsupported operating system: $OS"
            exit 1
            ;;
    esac
    
    print_status "Detected platform: $PLATFORM ($OS $ARCH)"
}

# Clean previous builds
clean_build() {
    print_status "Cleaning previous builds..."
    mvn clean
    print_success "Clean completed"
}

# Build the project
build_project() {
    print_status "Building project with Maven..."
    mvn compile
    print_success "Build completed"
}

# Run the application
run_application() {
    print_status "Starting Binary I/O Demo application..."
    print_status "Platform: $PLATFORM"
    
    # Run with Maven JavaFX plugin
    mvn javafx:run
    
    print_success "Application completed"
}

# Main execution
main() {
    echo "=========================================="
    echo "  Binary I/O Demo - JavaFX Application"
    echo "=========================================="
    echo ""
    
    # Check prerequisites
    check_java
    check_maven
    detect_platform
    
    # Build and run
    clean_build
    build_project
    run_application
}

# Handle script interruption
trap 'print_error "Script interrupted"; exit 1' INT TERM

# Run main function
main "$@" 