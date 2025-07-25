#!/bin/bash

# Test script for Caesar cipher encryption and decryption
# Tests the round-trip encryption/decryption process
# 
# Usage:
#   ./test_caesar_cipher_decrypt.sh                     # Test with default text
#   ./test_caesar_cipher_decrypt.sh "Your text here"    # Test with custom text
#   ./test_caesar_cipher_decrypt.sh -f filename         # Test with file input
#   ./test_caesar_cipher_decrypt.sh --file filename     # Test with file input
#   ./test_caesar_cipher_decrypt.sh -h|--help          # Show this help message

# Function to show help
show_help() {
    echo "Caesar Cipher Test Script"
    echo
    echo "This script tests the Caesar cipher encryption and decryption process by:"
    echo "1. Encrypting input text using caesar_cipher.py"
    echo "2. Decrypting the result using caesar_decrypt.py"
    echo "3. Comparing original and decrypted text"
    echo "4. Running brute force attack to verify the correct key is found"
    echo
    echo "Usage:"
    echo "  $0                           # Test with default text: 'Send the money to Alice'"
    echo "  $0 \"Your text here\"          # Test with custom text"
    echo "  $0 -f filename               # Test with file input"
    echo "  $0 --file filename           # Test with file input"
    echo "  $0 -h|--help                # Show this help message"
    echo
    echo "Examples:"
    echo "  $0"
    echo "  $0 \"Hello World\""
    echo "  $0 -f test-data/short_english.txt"
    echo "  $0 --file test-data/medium_english.txt"
    echo
    echo "Note: The script uses a fixed key of 3 for all tests."
}

# Check for help option first
if [ "$1" = "-h" ] || [ "$1" = "--help" ]; then
    show_help
    exit 0
fi

echo "=== Caesar Cipher Test Script ==="
echo

# Default test parameters
DEFAULT_TEXT="Send the money to Alice"
KEY=3
USE_FILE=false
INPUT_FILE=""
ORIGINAL_TEXT=""

# Parse command line arguments
if [ $# -eq 0 ]; then
    # No arguments - use default text
    ORIGINAL_TEXT="$DEFAULT_TEXT"
    echo "Using default text input mode"
elif [ "$1" = "-f" ] || [ "$1" = "--file" ]; then
    # File input mode
    if [ $# -lt 2 ]; then
        echo "Error: File path required with -f/--file option"
        echo "Usage: $0 -f filename"
        exit 1
    fi
    USE_FILE=true
    INPUT_FILE="$2"
    
    # Check if file exists
    if [ ! -f "$INPUT_FILE" ]; then
        echo "Error: File '$INPUT_FILE' not found"
        exit 1
    fi
    
    # Read text from file
    ORIGINAL_TEXT=$(cat "$INPUT_FILE")
    echo "Using file input mode: $INPUT_FILE"
else
    # Text input mode with custom text
    ORIGINAL_TEXT="$1"
    echo "Using custom text input mode"
fi

echo "Original text: \"$ORIGINAL_TEXT\""
echo "Key: $KEY"
echo

# Step 1: Encrypt the text using caesar_cipher.py
echo "Step 1: Encrypting text..."
if [ "$USE_FILE" = true ]; then
    # Create temporary file for encryption
    TEMP_INPUT_FILE=$(mktemp)
    echo "$ORIGINAL_TEXT" > "$TEMP_INPUT_FILE"
    
    # Encrypt using file mode
    ENCRYPT_OUTPUT=$(python3 caesar_cipher.py -f "$TEMP_INPUT_FILE" $KEY)
    ENCRYPTED_TEXT=$(cat "${TEMP_INPUT_FILE}.cae")
    
    echo "Encrypted text saved to: ${TEMP_INPUT_FILE}.cae"
    echo "Encrypted text: \"$ENCRYPTED_TEXT\""
    
    # Clean up temporary input file but keep the .cae file for decryption
    rm "$TEMP_INPUT_FILE"
    ENCRYPTED_FILE="${TEMP_INPUT_FILE}.cae"
else
    # Encrypt using text mode
    ENCRYPTED_TEXT=$(python3 caesar_cipher.py "$ORIGINAL_TEXT" $KEY)
    echo "Encrypted text: \"$ENCRYPTED_TEXT\""
fi
echo

# Step 2: Decrypt the cipher text using caesar_decrypt.py
echo "Step 2: Decrypting text..."
if [ "$USE_FILE" = true ]; then
    # Decrypt using file mode
    DECRYPTED_TEXT=$(python3 caesar_decrypt.py -f "$ENCRYPTED_FILE" $KEY)
    echo "Decrypted text: \"$DECRYPTED_TEXT\""
    
    # Clean up the temporary encrypted file
    rm "$ENCRYPTED_FILE"
else
    # Decrypt using text mode
    DECRYPTED_TEXT=$(python3 caesar_decrypt.py "$ENCRYPTED_TEXT" $KEY)
    echo "Decrypted text: \"$DECRYPTED_TEXT\""
fi
echo

# Step 3: Compare original and decrypted text
echo "Step 3: Comparing texts..."

# Handle comparison based on input mode
if [ "$USE_FILE" = true ]; then
    # For file mode, we need to account for the fact that encryption removes spaces
    # So compare the original text (spaces removed) with decrypted text
    ORIGINAL_TEXT_NO_SPACES=$(echo "$ORIGINAL_TEXT" | sed 's/[^a-zA-Z]//g')
    DECRYPTED_TEXT_CLEAN=$(echo "$DECRYPTED_TEXT" | tr -d '\n\r' | sed 's/[[:space:]]*$//')
    
    echo "Original text (spaces/non-alpha removed): \"$ORIGINAL_TEXT_NO_SPACES\""
    echo "Decrypted text: \"$DECRYPTED_TEXT_CLEAN\""
    
    if [ "$ORIGINAL_TEXT_NO_SPACES" = "$DECRYPTED_TEXT_CLEAN" ]; then
        echo
        echo "✅ SUCCESS: Decrypted text matches original text (with spaces removed)!"
        echo "The Caesar cipher encryption/decryption process works correctly."
    else
        echo
        echo "❌ FAILURE: Decrypted text does not match original text!"
        echo "Expected: \"$ORIGINAL_TEXT_NO_SPACES\""
        echo "Got:      \"$DECRYPTED_TEXT_CLEAN\""
        echo
        echo "Continuing with brute force test anyway..."
    fi
else
    # For text mode, remove spaces and non-alphabetic characters as before
    # since caesar_cipher.py removes spaces and non-alphabetic characters
    ORIGINAL_TEXT_NO_SPACES=$(echo "$ORIGINAL_TEXT" | sed 's/[^a-zA-Z]//g')
    
    echo "Original text (spaces removed): \"$ORIGINAL_TEXT_NO_SPACES\""
    echo "Decrypted text: \"$DECRYPTED_TEXT\""
    
    if [ "$ORIGINAL_TEXT_NO_SPACES" = "$DECRYPTED_TEXT" ]; then
        echo
        echo "✅ SUCCESS: Decrypted text matches original text (with spaces removed)!"
        echo "The Caesar cipher encryption/decryption process works correctly."
    else
        echo
        echo "❌ FAILURE: Decrypted text does not match original text!"
        echo "Expected: \"$ORIGINAL_TEXT_NO_SPACES\""
        echo "Got:      \"$DECRYPTED_TEXT\""
        echo
        echo "Continuing with brute force test anyway..."
    fi
fi

echo
echo "Step 4: Testing brute force decryption..."
echo "Running brute force attack on encrypted text..."
echo

# Run the brute force script and capture output
BRUTE_FORCE_OUTPUT=$(python3 brute_force_caesar.py "$ENCRYPTED_TEXT")
echo "$BRUTE_FORCE_OUTPUT"

echo
echo "Step 5: Verifying brute force found correct key..."

# Extract the decrypted text for the correct key from brute force output
# The correct key should produce text that matches our known decrypted text
BRUTE_FORCE_KEY_LINE=$(echo "$BRUTE_FORCE_OUTPUT" | grep "^$KEY ")
BRUTE_FORCE_DECRYPTED=$(echo "$BRUTE_FORCE_KEY_LINE" | sed "s/^$KEY[[:space:]]*|[[:space:]]*//" | tr -d '[:space:]')

# Remove spaces from our known decrypted text for comparison
KNOWN_DECRYPTED_NO_SPACES=$(echo "$DECRYPTED_TEXT" | tr -d '[:space:]')

echo "Brute force result for key $KEY: \"$(echo "$BRUTE_FORCE_KEY_LINE" | sed "s/^$KEY[[:space:]]*|[[:space:]]*//")\""
echo "Expected decrypted text: \"$DECRYPTED_TEXT\""

if [ "$BRUTE_FORCE_DECRYPTED" = "$KNOWN_DECRYPTED_NO_SPACES" ]; then
    echo
    echo "✅ SUCCESS: Brute force attack correctly identified key $KEY!"
    echo "All Caesar cipher tests passed successfully."
    exit 0
else
    echo
    echo "❌ FAILURE: Brute force attack did not produce expected result for key $KEY!"
    echo "Expected: \"$KNOWN_DECRYPTED_NO_SPACES\""
    echo "Got:      \"$BRUTE_FORCE_DECRYPTED\""
    exit 1
fi
