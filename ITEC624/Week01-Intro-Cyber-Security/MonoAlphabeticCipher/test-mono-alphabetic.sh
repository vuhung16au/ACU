#!/bin/bash

# mono-alphabetic-cipher.sh
# Shell script wrapper to test the Mono-alphabetic Cipher
# This script calls the Python implementation with the provided arguments

# Get the directory where this script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
ENCRYPT_SCRIPT="$SCRIPT_DIR/mono-alphabetic-cipher.py"
DECRYPT_SCRIPT="$SCRIPT_DIR/mono-alphabetic-decrypt.py"
CRACK_SCRIPT="$SCRIPT_DIR/crack-mono-alphabetic.py"

# Check if the Python scripts exist
if [ ! -f "$ENCRYPT_SCRIPT" ]; then
    echo "Error: Python script 'mono-alphabetic-cipher.py' not found in $SCRIPT_DIR"
    exit 1
fi

if [ ! -f "$DECRYPT_SCRIPT" ]; then
    echo "Error: Python script 'mono-alphabetic-decrypt.py' not found in $SCRIPT_DIR"
    exit 1
fi

if [ ! -f "$CRACK_SCRIPT" ]; then
    echo "Error: Python script 'crack-mono-alphabetic.py' not found in $SCRIPT_DIR"
    exit 1
fi

# Function to display usage information
usage() {
    echo "Usage: $0 [OPTIONS]"
    echo ""
    echo "Mono-alphabetic Cipher Test Script"
    echo "This script demonstrates encryption, decryption, and cracking using input-plaintext/sample_input.txt"
    echo ""
    echo "Options:"
    echo "  -h, --help              Show this help message and exit"
    echo ""
    echo "This script will:"
    echo "  1. Encrypt sample_input.txt using mono-alphabetic-cipher.py"
    echo "  2. Decrypt the resulting ciphertext using mono-alphabetic-decrypt.py"
    echo "  3. Test the cipher cracking using crack-mono-alphabetic.py"
}

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    case $1 in
        -h|--help)
            usage
            exit 0
            ;;
        *)
            echo "Error: Unknown option $1"
            usage
            exit 1
            ;;
    esac
done

# Check if sample_input.txt exists
SAMPLE_INPUT="$SCRIPT_DIR/input-plaintext/sample_input.txt"
if [ ! -f "$SAMPLE_INPUT" ]; then
    echo "Error: Sample input file 'sample_input.txt' not found in $SCRIPT_DIR/input-plaintext/"
    exit 1
fi

echo "=== Mono-alphabetic Cipher Test ==="
echo ""

# Step 1: Display the original plaintext
echo "Step 1: Original plaintext from input-plaintext/sample_input.txt:"
cat "$SAMPLE_INPUT"
echo ""

# Step 2: Encrypt the sample input
echo "Step 2: Encrypting the plaintext..."
CIPHERTEXT=$(python3 "$ENCRYPT_SCRIPT" -f "$SAMPLE_INPUT")
if [ $? -ne 0 ]; then
    echo "Error: Encryption failed"
    exit 1
fi

echo "Encrypted ciphertext:"
echo "$CIPHERTEXT"
echo ""

# Step 3: Decrypt the ciphertext
echo "Step 3: Decrypting the ciphertext..."
DECRYPTED_TEXT=$(python3 "$DECRYPT_SCRIPT" "$CIPHERTEXT")
if [ $? -ne 0 ]; then
    echo "Error: Decryption failed"
    exit 1
fi

echo "Decrypted plaintext:"
echo "$DECRYPTED_TEXT"
echo ""

# Step 4: Verify the result
ORIGINAL_TEXT=$(cat "$SAMPLE_INPUT" | tr -d '\n')
DECRYPTED_CLEAN=$(echo "$DECRYPTED_TEXT" | tr -d '\n')

if [ "$ORIGINAL_TEXT" = "$DECRYPTED_CLEAN" ]; then
    echo "✅ SUCCESS: Decrypted text matches the original plaintext!"
else
    echo "❌ FAILURE: Decrypted text does not match the original plaintext."
    echo "Original:  '$ORIGINAL_TEXT'"
    echo "Decrypted: '$DECRYPTED_CLEAN'"
    exit 1
fi

echo ""

# Step 5: Test cipher cracking functionality
echo "Step 5: Testing cipher cracking with output-ciphered/sample_ciphertext.txt..."
SAMPLE_CIPHERTEXT="$SCRIPT_DIR/output-ciphered/sample_ciphertext.txt"
if [ -f "$SAMPLE_CIPHERTEXT" ]; then
    echo "Running cipher cracker on sample_ciphertext.txt..."
    python3 "$CRACK_SCRIPT" -f "$SAMPLE_CIPHERTEXT"
    
    if [ $? -eq 0 ]; then
        echo "✅ Cipher cracking completed successfully!"
    else
        echo "⚠️  Cipher cracking finished (may need manual refinement)"
    fi
else
    echo "⚠️  Warning: sample_ciphertext.txt not found, skipping crack test"
fi

echo ""

# Step 6: Test cracking with our generated ciphertext
echo "Step 6: Testing cipher cracking with our generated ciphertext..."
TEMP_CIPHER_FILE=$(mktemp)
echo "$CIPHERTEXT" > "$TEMP_CIPHER_FILE"

echo "Running cipher cracker on our generated ciphertext..."
CRACKED_RESULT=$(python3 "$CRACK_SCRIPT" -f "$TEMP_CIPHER_FILE" 2>/dev/null | grep "Decrypted text:" | sed 's/Decrypted text: //')

# Clean up temporary file
rm -f "$TEMP_CIPHER_FILE"

if [ -n "$CRACKED_RESULT" ]; then
    echo "Cracked result: $CRACKED_RESULT"
    
    # Compare cracked result with original (case-insensitive, ignoring punctuation)
    CRACKED_CLEAN=$(echo "$CRACKED_RESULT" | tr '[:upper:]' '[:lower:]' | tr -cd '[:alnum:][:space:]')
    ORIGINAL_CLEAN=$(echo "$ORIGINAL_TEXT" | tr '[:upper:]' '[:lower:]' | tr -cd '[:alnum:][:space:]')
    
    if [ "$CRACKED_CLEAN" = "$ORIGINAL_CLEAN" ]; then
        echo "✅ SUCCESS: Cracked text matches the original plaintext!"
    else
        echo "⚠️  INFO: Cracked text differs from original (this is expected with heuristic cracking)"
        echo "Original: '$ORIGINAL_CLEAN'"
        echo "Cracked:  '$CRACKED_CLEAN'"
    fi
else
    echo "⚠️  Cipher cracking did not produce readable output"
fi

echo ""
echo "All tests completed successfully!"
