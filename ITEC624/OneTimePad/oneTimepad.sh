#!/bin/bash

# One-Time Pad Test Script
# This script runs unit tests and provides interactive testing for the One-Time Pad implementation

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
PURPLE='\033[0;35m'
CYAN='\033[0;36m'
NC='\033[0m' # No Color

# Function to print colored output
print_color() {
    local color=$1
    local message=$2
    echo -e "${color}${message}${NC}"
}

# Function to print section headers
print_section() {
    echo
    print_color $CYAN "=================================="
    print_color $CYAN "$1"
    print_color $CYAN "=================================="
    echo
}

# Function to run unit tests
run_unit_tests() {
    print_section "RUNNING UNIT TESTS"
    
    # Check if we're in the correct directory
    if [[ ! -f "src/oneTimepad.py" ]]; then
        print_color $RED "Error: oneTimepad.py not found in src/ directory"
        print_color $YELLOW "Please run this script from the OneTimePad project root directory"
        exit 1
    fi
    
    # Check if Python is available
    if ! command -v python3 &> /dev/null; then
        print_color $RED "Error: python3 is not installed or not in PATH"
        exit 1
    fi
    
    # Run the unit tests
    print_color $BLUE "Running unit tests with unittest..."
    echo
    
    if python3 -m unittest tests.test_otp -v; then
        print_color $GREEN "✓ All unit tests passed!"
    else
        print_color $RED "✗ Some unit tests failed!"
        exit 1
    fi
}

# Function to test CLI encrypt with generated key
test_cli_encrypt_generated() {
    print_color $YELLOW "=== Test CLI: Encrypt with generated key ==="
    echo
    
    print_color $BLUE "Running: python3 src/oneTimepad.py -e"
    echo
    
    python3 src/oneTimepad.py -e
}

# Function to test CLI encrypt with custom key
test_cli_encrypt_custom() {
    print_color $YELLOW "=== Test CLI: Encrypt with custom key ==="
    echo
    
    print_color $BLUE "Running: python3 src/oneTimepad.py --encrypt-custom"
    echo
    
    python3 src/oneTimepad.py --encrypt-custom
}

# Function to test CLI decrypt
test_cli_decrypt() {
    print_color $YELLOW "=== Test CLI: Decrypt message ==="
    echo
    
    print_color $BLUE "Running: python3 src/oneTimepad.py -d"
    echo
    
    python3 src/oneTimepad.py -d
}

# Function to test CLI help
test_cli_help() {
    print_color $YELLOW "=== Test CLI: Help message ==="
    echo
    
    print_color $BLUE "Running: python3 src/oneTimepad.py --help"
    echo
    
    python3 src/oneTimepad.py --help
}

# Function to test encryption with generated key
test_encrypt_generated_key() {
    print_color $YELLOW "=== Test Option 1: Encrypt with generated key ==="
    echo
    
    read -p "Enter message to encrypt: " message
    
    if [[ -n "$message" ]]; then
        print_color $BLUE "Running encryption with generated key..."
        python3 -c "
import sys
sys.path.insert(0, 'src')
from oneTimepad import OneTimePad

otp = OneTimePad()
ciphertext, key = otp.encrypt_with_generated_key('$message')
prepared = otp._prepare_text('$message')

print(f'Original message: \"$message\"')
print(f'Prepared message: \"{prepared}\"')
print(f'Generated key: \"{key}\"')
print(f'Ciphertext: \"{ciphertext}\"')

# Verify decryption
decrypted = otp.decrypt(ciphertext, key)
print(f'Decrypted: \"{decrypted}\"')
print(f'Verification: {\"PASS\" if decrypted == prepared else \"FAIL\"}')
"
    else
        print_color $RED "No message entered!"
    fi
}

# Function to test encryption with custom key
test_encrypt_custom_key() {
    print_color $YELLOW "=== Test Option 2: Encrypt with custom key ==="
    echo
    
    read -p "Enter message to encrypt: " message
    read -p "Enter custom key: " key
    
    if [[ -n "$message" && -n "$key" ]]; then
        print_color $BLUE "Running encryption with custom key..."
        python3 -c "
import sys
sys.path.insert(0, 'src')
from oneTimepad import OneTimePad

otp = OneTimePad()
try:
    ciphertext = otp.encrypt('$message', '$key')
    prepared = otp._prepare_text('$message')
    
    print(f'Original message: \"$message\"')
    print(f'Prepared message: \"{prepared}\"')
    print(f'Custom key: \"$key\"')
    print(f'Ciphertext: \"{ciphertext}\"')
    
    # Verify decryption
    decrypted = otp.decrypt(ciphertext, '$key')
    print(f'Decrypted: \"{decrypted}\"')
    print(f'Verification: {\"PASS\" if decrypted == prepared else \"FAIL\"}')
    
except ValueError as e:
    print(f'Error: {e}')
"
    else
        print_color $RED "Message and key are required!"
    fi
}

# Function to test decryption
test_decrypt() {
    print_color $YELLOW "=== Test Option 3: Decrypt message ==="
    echo
    
    read -p "Enter ciphertext to decrypt: " ciphertext
    read -p "Enter key: " key
    
    if [[ -n "$ciphertext" && -n "$key" ]]; then
        print_color $BLUE "Running decryption..."
        python3 -c "
import sys
sys.path.insert(0, 'src')
from oneTimepad import OneTimePad

otp = OneTimePad()
try:
    plaintext = otp.decrypt('$ciphertext', '$key')
    
    print(f'Ciphertext: \"$ciphertext\"')
    print(f'Key: \"$key\"')
    print(f'Decrypted plaintext: \"{plaintext}\"')
    
except ValueError as e:
    print(f'Error: {e}')
"
    else
        print_color $RED "Ciphertext and key are required!"
    fi
}

# Function to run interactive tests
run_interactive_tests() {
    print_section "INTERACTIVE TESTING MODE"
    
    while true; do
        echo
        print_color $PURPLE "Choose an option to test:"
        echo "1. Encrypt with generated key (legacy function)"
        echo "2. Encrypt with custom key (legacy function)"
        echo "3. Decrypt message (legacy function)"
        echo "4. CLI: Encrypt with generated key (-e)"
        echo "5. CLI: Encrypt with custom key (--encrypt-custom)"
        echo "6. CLI: Decrypt message (-d)"
        echo "7. CLI: Show help (--help)"
        echo "8. Exit"
        echo
        
        read -p "Enter choice (1-8): " choice
        echo
        
        case $choice in
            1)
                test_encrypt_generated_key
                ;;
            2)
                test_encrypt_custom_key
                ;;
            3)
                test_decrypt
                ;;
            4)
                test_cli_encrypt_generated
                ;;
            5)
                test_cli_encrypt_custom
                ;;
            6)
                test_cli_decrypt
                ;;
            7)
                test_cli_help
                ;;
            8)
                print_color $GREEN "Exiting interactive mode..."
                break
                ;;
            *)
                print_color $RED "Invalid choice! Please enter 1-8."
                ;;
        esac
        
        echo
        read -p "Press Enter to continue..."
    done
}

# Function to test CLI options
test_cli_options() {
    print_section "TESTING CLI OPTIONS"
    
    print_color $BLUE "Testing all CLI options for oneTimepad.py..."
    echo
    
    test_cli_help
    echo
    read -p "Press Enter to continue to encryption test..."
    echo
    
    test_cli_encrypt_generated
    echo
    read -p "Press Enter to continue to custom encryption test..."
    echo
    
    test_cli_encrypt_custom
    echo
    read -p "Press Enter to continue to decryption test..."
    echo
    
    test_cli_decrypt
}

# Function to run demo
run_demo() {
    print_section "RUNNING DEMONSTRATION"
    
    print_color $BLUE "Running the One-Time Pad demonstration..."
    echo
    
    python3 src/oneTimepad.py
}

# Function to show usage
show_usage() {
    echo "Usage: $0 [OPTIONS]"
    echo
    echo "Options:"
    echo "  -t, --test         Run unit tests only"
    echo "  -i, --interactive  Run interactive testing mode only"
    echo "  -d, --demo        Run demonstration only"
    echo "  -c, --cli         Test CLI options only"
    echo "  -h, --help        Show this help message"
    echo
    echo "With no options, runs unit tests followed by interactive testing mode."
    echo
    echo "CLI Options for oneTimepad.py:"
    echo "  python3 src/oneTimepad.py -e                    # Encrypt with generated key"
    echo "  python3 src/oneTimepad.py --encrypt-custom      # Encrypt with custom key"
    echo "  python3 src/oneTimepad.py -d                    # Decrypt message"
    echo "  python3 src/oneTimepad.py --demo                # Run demonstration"
    echo "  python3 src/oneTimepad.py --help                # Show help"
}

# Main script logic
main() {
    print_color $CYAN "One-Time Pad Cipher Test Script"
    print_color $CYAN "==============================="
    
    # Parse command line arguments
    case ${1:-""} in
        -t|--test)
            run_unit_tests
            ;;
        -i|--interactive)
            run_interactive_tests
            ;;
        -d|--demo)
            run_demo
            ;;
        -c|--cli)
            test_cli_options
            ;;
        -h|--help)
            show_usage
            ;;
        "")
            # Default: run tests then interactive mode
            run_unit_tests
            run_interactive_tests
            ;;
        *)
            print_color $RED "Unknown option: $1"
            show_usage
            exit 1
            ;;
    esac
    
    print_color $GREEN "Script completed successfully!"
}

# Run the main function
main "$@"
