#!/bin/bash

# Test script for Vigenère Cipher implementation
# This script tests both vigenere_cipher.py and vigenere_decrypt.py

echo "=== Vigenère Cipher Test Script ==="
echo

# Set up test files
KEY_FILE="vigenere.txt"
INPUT_FILE="inputs-plaintext/test_message.txt"
ENCRYPTED_FILE="output-ciphertext/encrypted_message.txt"
DECRYPTED_FILE="output-ciphertext/decrypted_message.txt"

# Create output directory if it doesn't exist
mkdir -p output-ciphertext

echo "1. Testing vigenere_cipher.py (Encryption)"
echo "============================================"
echo "Input file: $INPUT_FILE"
echo "Key file: $KEY_FILE"
echo "Output file: $ENCRYPTED_FILE"
echo

# Display original message
echo "Original message:"
cat "$INPUT_FILE"
echo

# Run encryption
echo "Running: python3 vigenere_cipher.py --key $KEY_FILE --encrypt -f $INPUT_FILE -o $ENCRYPTED_FILE"
python3 vigenere_cipher.py --key "$KEY_FILE" --encrypt -f "$INPUT_FILE" -o "$ENCRYPTED_FILE"

if [ $? -eq 0 ]; then
    echo "✓ Encryption completed successfully"
    echo "Encrypted message:"
    cat "$ENCRYPTED_FILE"
    echo
else
    echo "✗ Encryption failed"
    exit 1
fi

echo
echo "2. Testing vigenere_decrypt.py (Decryption)"
echo "============================================"
echo "Input file: $ENCRYPTED_FILE"
echo "Key file: $KEY_FILE"
echo "Output file: $DECRYPTED_FILE"
echo

# Run decryption
echo "Running: python3 vigenere_decrypt.py --key $KEY_FILE -f $ENCRYPTED_FILE -o $DECRYPTED_FILE"
python3 vigenere_decrypt.py --key "$KEY_FILE" -f "$ENCRYPTED_FILE" -o "$DECRYPTED_FILE"

if [ $? -eq 0 ]; then
    echo "✓ Decryption completed successfully"
    echo "Decrypted message:"
    cat "$DECRYPTED_FILE"
    echo
else
    echo "✗ Decryption failed"
    exit 1
fi

echo
echo "3. Verification"
echo "==============="
echo "Comparing original and decrypted messages..."

# Compare original and decrypted files (case-insensitive)
# Convert both to uppercase for comparison
ORIGINAL_UPPER=$(cat "$INPUT_FILE" | tr '[:lower:]' '[:upper:]')
DECRYPTED_UPPER=$(cat "$DECRYPTED_FILE" | tr '[:lower:]' '[:upper:]')

if [ "$ORIGINAL_UPPER" = "$DECRYPTED_UPPER" ]; then
    echo "✓ SUCCESS: Original and decrypted messages are identical (case-insensitive)!"
else
    echo "✗ FAILURE: Original and decrypted messages differ!"
    echo
    echo "Original (uppercase):"
    echo "$ORIGINAL_UPPER"
    echo "Decrypted (uppercase):"
    echo "$DECRYPTED_UPPER"
    exit 1
fi

echo
echo "4. Additional Tests"
echo "==================="

# Test with alternative key file if it exists
if [ -f "alternative_key.txt" ]; then
    echo "Testing with alternative key file..."
    ALT_ENCRYPTED_FILE="output-ciphertext/alt_encrypted_message.txt"
    ALT_DECRYPTED_FILE="output-ciphertext/alt_decrypted_message.txt"
    
    echo "Encrypting with alternative key:"
    python3 vigenere_cipher.py --key "alternative_key.txt" --encrypt -f "$INPUT_FILE" -o "$ALT_ENCRYPTED_FILE"
    
    if [ "$?" -eq 0 ]; then
        echo "Alternative key encryption completed."
        echo "Decrypting with alternative key:"
        python3 vigenere_decrypt.py --key "alternative_key.txt" -f "$ALT_ENCRYPTED_FILE" -o "$ALT_DECRYPTED_FILE"
        
        if [ "$?" -eq 0 ]; then
            echo "Alternative key decryption completed."
            # Case-insensitive comparison for alternative key test
            ALT_ORIGINAL_UPPER=$(cat "$INPUT_FILE" | tr '[:lower:]' '[:upper:]')
            ALT_DECRYPTED_UPPER=$(cat "$ALT_DECRYPTED_FILE" | tr '[:lower:]' '[:upper:]')
            
            if [ "$ALT_ORIGINAL_UPPER" = "$ALT_DECRYPTED_UPPER" ]; then
                echo "✓ Alternative key test passed!"
            else
                echo "✗ Alternative key test failed!"
            fi
        else
            echo "✗ Alternative key decryption failed!"
        fi
    else
        echo "✗ Alternative key encryption failed!"
    fi
    echo
fi

# Test standard input/output
echo "Testing standard input/output..."
echo "Original message:" | python3 vigenere_cipher.py --key "$KEY_FILE" --encrypt | python3 vigenere_decrypt.py --key "$KEY_FILE"
echo

echo

echo "5.1. Testing crack_vigenere.py with Brute-Force"
echo "==============================================="
echo "Attempting to crack with brute-force method (for short keys)..."
echo "Input file: $ENCRYPTED_FILE"
echo

# Try brute-force with a reasonable key length limit
echo "Running: python3 crack_vigenere.py -f $ENCRYPTED_FILE --brute-force --key-length 8 --verbose"
BRUTE_FORCE_OUTPUT=$(python3 crack_vigenere.py -f "$ENCRYPTED_FILE" --brute-force --key-length 8 --verbose 2>&1)
echo "$BRUTE_FORCE_OUTPUT"
echo

# Extract just the key for comparison  
BRUTE_FORCE_KEY=$(python3 crack_vigenere.py -f "$ENCRYPTED_FILE" --brute-force --key-length 8 --key-only 2>/dev/null)
ORIGINAL_KEY=$(cat "$KEY_FILE" | tr '[:lower:]' '[:upper:]')

echo "Brute-force key comparison:"
echo "Original key: $ORIGINAL_KEY"
echo "Brute-force key: $BRUTE_FORCE_KEY"

if [ "$ORIGINAL_KEY" = "$BRUTE_FORCE_KEY" ]; then
    echo "✓ SUCCESS: Brute-force found the exact key!"
else
    echo "⚠ Different result from brute-force method"
    echo "  (This may indicate the key is too long for efficient brute-force)"
    
    # Test if the brute-force result still produces correct decryption
    BRUTE_FORCE_DECRYPTED_FILE="output-ciphertext/brute_force_decrypted.txt"
    python3 crack_vigenere.py -f "$ENCRYPTED_FILE" --brute-force --key-length 8 -o "$BRUTE_FORCE_DECRYPTED_FILE" 2>/dev/null
    
    if [ -f "$BRUTE_FORCE_DECRYPTED_FILE" ]; then
        BRUTE_FORCE_UPPER=$(cat "$BRUTE_FORCE_DECRYPTED_FILE" | tr '[:lower:]' '[:upper:]')
        ORIGINAL_UPPER=$(cat "$INPUT_FILE" | tr '[:lower:]' '[:upper:]')
        
        if [ "$ORIGINAL_UPPER" = "$BRUTE_FORCE_UPPER" ]; then
            echo "✓ However, brute-force still produced correct decryption!"
        else
            echo "ℹ Brute-force produced different decryption (expected for complex keys)"
        fi
        rm -f "$BRUTE_FORCE_DECRYPTED_FILE"
    fi
fi

echo

echo "5.2. Comprehensive Brute-Force Testing (Key Lengths 1-5)"
echo "=========================================================="
echo "Testing brute-force cracking with systematically designed short keys..."
echo

# Function to test brute-force with specific key length
test_brute_force_key_length() {
    local key_length="$1"
    local test_key="$2"
    local test_description="$3"
    
    echo "Testing brute-force for $test_description (length: $key_length, key: '$test_key')"
    echo "================================================================"
    
    # Create temporary files
    local temp_key_file="output-ciphertext/temp_bf_key_${key_length}.txt"
    local temp_encrypted_file="output-ciphertext/temp_bf_encrypted_${key_length}.txt"
    local temp_decrypted_file="output-ciphertext/temp_bf_decrypted_${key_length}.txt"
    
    # Create key file
    echo "# Brute-force test key (length: $key_length)" > "$temp_key_file"
    echo "K = \"$test_key\"" >> "$temp_key_file"
    
    # Encrypt the test message
    echo "Encrypting with key '$test_key'..."
    python3 vigenere_cipher.py --key "$temp_key_file" --encrypt -f "$INPUT_FILE" -o "$temp_encrypted_file" 2>/dev/null
    
    if [ $? -eq 0 ]; then
        echo "✓ Encryption successful"
        
        # Show encrypted text (first 50 chars)
        ENCRYPTED_PREVIEW=$(head -c 50 "$temp_encrypted_file")
        echo "Encrypted text preview: $ENCRYPTED_PREVIEW..."
        
        # Attempt brute-force crack
        echo "Attempting brute-force crack (max length: $key_length)..."
        CRACK_START_TIME=$(date +%s)
        
        # Run brute-force with verbose output
        BRUTE_FORCE_RESULT=$(python3 crack_vigenere.py -f "$temp_encrypted_file" --brute-force --key-length "$key_length" --verbose 2>&1)
        CRACK_END_TIME=$(date +%s)
        CRACK_DURATION=$((CRACK_END_TIME - CRACK_START_TIME))
        
        echo "Brute-force output:"
        echo "$BRUTE_FORCE_RESULT"
        
        # Get the cracked key
        CRACKED_KEY=$(python3 crack_vigenere.py -f "$temp_encrypted_file" --brute-force --key-length "$key_length" --key-only 2>/dev/null)
        
        if [ -n "$CRACKED_KEY" ]; then
            echo "✓ Brute-force completed in ${CRACK_DURATION} seconds"
            echo "Original key: $test_key"
            echo "Cracked key:  $CRACKED_KEY"
            
            # Normalize keys for comparison
            NORMALIZED_ORIGINAL=$(echo "$test_key" | tr '[:lower:]' '[:upper:]')
            
            if [ "$NORMALIZED_ORIGINAL" = "$CRACKED_KEY" ]; then
                echo "✓ SUCCESS: Exact key match!"
            else
                echo "⚠ Different key found - testing decryption quality..."
                
                # Test decryption quality
                python3 crack_vigenere.py -f "$temp_encrypted_file" --brute-force --key-length "$key_length" -o "$temp_decrypted_file" 2>/dev/null
                
                if [ -f "$temp_decrypted_file" ]; then
                    # Compare decrypted text with original
                    ORIGINAL_UPPER=$(cat "$INPUT_FILE" | tr '[:lower:]' '[:upper:]')
                    CRACKED_UPPER=$(cat "$temp_decrypted_file" | tr '[:lower:]' '[:upper:]')
                    
                    if [ "$ORIGINAL_UPPER" = "$CRACKED_UPPER" ]; then
                        echo "✓ SUCCESS: Different key but perfect decryption!"
                    else
                        echo "⚠ PARTIAL: Decryption differs from original"
                        echo "Original: $ORIGINAL_UPPER"
                        echo "Cracked:  $CRACKED_UPPER"
                    fi
                fi
            fi
        else
            echo "✗ FAILURE: Brute-force could not find key in ${CRACK_DURATION} seconds"
            echo "  This may indicate the key is not in the search space or timeout occurred"
        fi
    else
        echo "✗ Encryption failed for key '$test_key'"
    fi
    
    # Clean up temporary files
    rm -f "$temp_key_file" "$temp_encrypted_file" "$temp_decrypted_file"
    echo
}

# Test key length 1
test_brute_force_key_length 1 "X" "single character key"

# Test key length 2
test_brute_force_key_length 2 "AB" "two character key"

# Test key length 3
test_brute_force_key_length 3 "KEY" "three character dictionary key"

# Test key length 4
test_brute_force_key_length 4 "CODE" "four character dictionary key"

# Test key length 5 - using our pre-generated test file
echo "Testing brute-force for 5-character key (length: 5, key: 'CRACK')"
echo "================================================================"
echo "Using pre-generated cipher file: output-ciphertext/test_brute_force-key-len-5.txt"

# Show the encrypted content
echo "Encrypted text:"
cat "output-ciphertext/test_brute_force-key-len-5.txt"
echo

# Attempt brute-force crack on the 5-character test
echo "Attempting brute-force crack (max length: 5)..."
CRACK_START_TIME=$(date +%s)

# Run brute-force with verbose output
BRUTE_FORCE_5_RESULT=$(python3 crack_vigenere.py -f "output-ciphertext/test_brute_force-key-len-5.txt" --brute-force --key-length 5 --verbose 2>&1)
CRACK_END_TIME=$(date +%s)
CRACK_DURATION=$((CRACK_END_TIME - CRACK_START_TIME))

echo "Brute-force output:"
echo "$BRUTE_FORCE_5_RESULT"

# Get the cracked key for 5-character test
CRACKED_5_KEY=$(python3 crack_vigenere.py -f "output-ciphertext/test_brute_force-key-len-5.txt" --brute-force --key-length 5 --key-only 2>/dev/null)

if [ -n "$CRACKED_5_KEY" ]; then
    echo "✓ Brute-force completed in ${CRACK_DURATION} seconds"
    echo "Original key: CRACK"
    echo "Cracked key:  $CRACKED_5_KEY"
    
    if [ "CRACK" = "$CRACKED_5_KEY" ]; then
        echo "✓ SUCCESS: Exact 5-character key match!"
    else
        echo "⚠ Different key found - testing decryption quality..."
        
        # Test decryption quality
        BF_5_DECRYPTED_FILE="output-ciphertext/bf_5_decrypted_test.txt"
        python3 crack_vigenere.py -f "output-ciphertext/test_brute_force-key-len-5.txt" --brute-force --key-length 5 -o "$BF_5_DECRYPTED_FILE" 2>/dev/null
        
        if [ -f "$BF_5_DECRYPTED_FILE" ]; then
            # Compare decrypted text with original
            ORIGINAL_UPPER=$(cat "$INPUT_FILE" | tr '[:lower:]' '[:upper:]')
            CRACKED_5_UPPER=$(cat "$BF_5_DECRYPTED_FILE" | tr '[:lower:]' '[:upper:]')
            
            if [ "$ORIGINAL_UPPER" = "$CRACKED_5_UPPER" ]; then
                echo "✓ SUCCESS: Different key but perfect decryption!"
            else
                echo "⚠ PARTIAL: Decryption differs from original"
                echo "Original: $ORIGINAL_UPPER"
                echo "Cracked:  $CRACKED_5_UPPER"
            fi
            rm -f "$BF_5_DECRYPTED_FILE"
        fi
    fi
else
    echo "✗ FAILURE: Brute-force could not find 5-character key in ${CRACK_DURATION} seconds"
    echo "  This indicates 5-character brute-force is computationally intensive"
fi

echo
echo "Brute-Force Performance Summary (Key Lengths 1-5):"
echo "===================================================="
echo "• Length 1: Should crack almost instantly (26 possibilities)"
echo "• Length 2: Should crack very quickly (676 possibilities)"  
echo "• Length 3: Should crack in seconds (17,576 possibilities)"
echo "• Length 4: May take several seconds (456,976 possibilities)"
echo "• Length 5: May take minutes or timeout (11,881,376 possibilities)"
echo "• Performance depends on hardware and implementation efficiency"
echo

echo

echo "5. Testing crack_vigenere.py (Statistical Analysis)"
echo "==================================================="
echo "Attempting to crack the encrypted message without knowing the key..."
echo "Input file: $ENCRYPTED_FILE"
echo

# Run the crack tool
echo "Running: python3 crack_vigenere.py -f $ENCRYPTED_FILE --verbose"
CRACKED_OUTPUT=$(python3 crack_vigenere.py -f "$ENCRYPTED_FILE" --verbose)
echo "$CRACKED_OUTPUT"
echo

# Extract just the key for comparison
CRACKED_KEY=$(python3 crack_vigenere.py -f "$ENCRYPTED_FILE" --key-only)
ORIGINAL_KEY=$(cat "$KEY_FILE" | tr '[:lower:]' '[:upper:]')

echo "Key comparison:"
echo "Original key: $ORIGINAL_KEY"
echo "Cracked key:  $CRACKED_KEY"

if [ "$ORIGINAL_KEY" = "$CRACKED_KEY" ]; then
    echo "✓ SUCCESS: Cracked key matches original key!"
else
    echo "⚠ PARTIAL: Key differs but decryption may still be correct"
    echo "  (This can happen with equivalent keys or educational scenarios)"
fi

# Test crack with decrypted output
CRACK_OUTPUT_FILE="output-ciphertext/crack_decrypted.txt"
python3 crack_vigenere.py -f "$ENCRYPTED_FILE" -o "$CRACK_OUTPUT_FILE"

if [ -f "$CRACK_OUTPUT_FILE" ]; then
    echo "Cracked decryption saved to: $CRACK_OUTPUT_FILE"
    echo "Cracked message:"
    cat "$CRACK_OUTPUT_FILE"
    echo
    
    # Compare cracked result with original
    CRACK_UPPER=$(cat "$CRACK_OUTPUT_FILE" | tr '[:lower:]' '[:upper:]')
    ORIGINAL_UPPER=$(cat "$INPUT_FILE" | tr '[:lower:]' '[:upper:]')
    
    if [ "$ORIGINAL_UPPER" = "$CRACK_UPPER" ]; then
        echo "✓ SUCCESS: Cracked message matches original!"
    else
        echo "⚠ Cracked message differs from original"
        echo "This may indicate the cipher was cracked to a different but valid plaintext"
    fi
else
    echo "✗ Failed to create crack output file"
fi

echo

# Test with alternative encrypted file if it exists
if [ -f "output-ciphertext/alt_encrypted_message.txt" ]; then
    echo "Testing crack on alternative key encryption..."
    ALT_CRACKED_KEY=$(python3 crack_vigenere.py -f "output-ciphertext/alt_encrypted_message.txt" --key-only)
    ALT_ORIGINAL_KEY=$(cat "alternative_key.txt" | tr '[:lower:]' '[:upper:]')
    
    echo "Alternative key comparison:"
    echo "Original key: $ALT_ORIGINAL_KEY"
    echo "Cracked key:  $ALT_CRACKED_KEY"
    
    if [ "$ALT_ORIGINAL_KEY" = "$ALT_CRACKED_KEY" ]; then
        echo "✓ Alternative key crack successful!"
    else
        echo "⚠ Alternative key crack produced different result"
    fi
    echo
fi

echo "6. Advanced Crack Tests - Edge Cases"
echo "===================================="

# Function to test crack with custom key
test_crack_with_key() {
    local key="$1"
    local key_description="$2"
    local test_encrypted_file="output-ciphertext/test_${key}_encrypted.txt"
    local test_key_file="output-ciphertext/test_${key}_key.txt"
    local test_crack_file="output-ciphertext/test_${key}_cracked.txt"
    
    echo "Testing crack with $key_description (key: '$key')"
    echo "================================================="
    
    # Create temporary key file
    echo "$key" > "$test_key_file"
    
    # Encrypt with the test key
    echo "Encrypting with test key..."
    python3 vigenere_cipher.py --key "$test_key_file" --encrypt -f "$INPUT_FILE" -o "$test_encrypted_file"
    
    if [ $? -eq 0 ]; then
        echo "✓ Test encryption completed"
        
        # Attempt to crack
        echo "Attempting to crack..."
        CRACK_START_TIME=$(date +%s)
        CRACKED_TEST_KEY=$(python3 crack_vigenere.py -f "$test_encrypted_file" --key-only 2>/dev/null)
        CRACK_END_TIME=$(date +%s)
        CRACK_DURATION=$((CRACK_END_TIME - CRACK_START_TIME))
        
        if [ -n "$CRACKED_TEST_KEY" ]; then
            echo "Cracked key: '$CRACKED_TEST_KEY' (took ${CRACK_DURATION}s)"
            
            # Try to decrypt with cracked key
            python3 crack_vigenere.py -f "$test_encrypted_file" -o "$test_crack_file" 2>/dev/null
            
            if [ -f "$test_crack_file" ]; then
                # Compare results
                CRACK_TEST_UPPER=$(cat "$test_crack_file" | tr '[:lower:]' '[:upper:]')
                ORIGINAL_UPPER=$(cat "$INPUT_FILE" | tr '[:lower:]' '[:upper:]')
                
                if [ "$ORIGINAL_UPPER" = "$CRACK_TEST_UPPER" ]; then
                    echo "✓ SUCCESS: Crack produced correct plaintext!"
                    
                    # Check if keys match (normalize case)
                    KEY_UPPER=$(echo "$key" | tr '[:lower:]' '[:upper:]')
                    if [ "$KEY_UPPER" = "$CRACKED_TEST_KEY" ]; then
                        echo "✓ PERFECT: Cracked key exactly matches original!"
                    else
                        echo "⚠ GOOD: Different key but correct decryption (equivalent key)"
                    fi
                else
                    echo "✗ FAILURE: Crack produced incorrect plaintext"
                    echo "Expected: $ORIGINAL_UPPER"
                    echo "Got: $CRACK_TEST_UPPER"
                fi
            else
                echo "✗ Failed to generate cracked output file"
            fi
        else
            echo "✗ FAILURE: Could not crack the cipher (took ${CRACK_DURATION}s)"
            echo "  This is expected for non-dictionary keys or very long keys"
        fi
    else
        echo "✗ Test encryption failed"
    fi
    
    # Cleanup temporary files
    rm -f "$test_key_file" "$test_encrypted_file" "$test_crack_file"
    echo
}

# Test Case 1: Non-dictionary key (random characters)
test_crack_with_key "XQZJWMPL" "non-dictionary 8-character key"

# Test Case 2: 8-character dictionary-like key
test_crack_with_key "COMPUTER" "8-character dictionary key"

# Test Case 3: 10-character key (mixed)
test_crack_with_key "TECHNOLOGY" "10-character dictionary key"

# Test Case 4: 10-character non-dictionary key
test_crack_with_key "QXZJWMPLKY" "non-dictionary 10-character key"

# Test Case 5: 12-character dictionary key
test_crack_with_key "MATHEMATICS" "12-character dictionary key"

# Test Case 6: 12-character non-dictionary key
test_crack_with_key "QXZJWMPLKYBV" "non-dictionary 12-character key"

# Test Case 7: Repeated pattern key
test_crack_with_key "ABCABCABC" "9-character repeating pattern key"

# Test Case 8: Single character repeated (edge case)
test_crack_with_key "AAAAAAAA" "8-character single-letter key"

echo "7. Crack Performance Summary"
echo "============================"
echo "The crack tests above demonstrate:"
echo "• Dictionary keys are typically easier to crack"
echo "• Longer keys (10+ characters) are more challenging"
echo "• Non-dictionary keys may be impossible to crack without brute force"
echo "• The crack tool's effectiveness depends on the key being in its dictionary"
echo "• Some keys may have equivalent alternatives that produce the same result"
echo

echo

echo "7. Testing Brute-Force Cracking"
echo "==============================="
echo "Testing brute-force capability with short keys..."

# Function to test brute-force with custom key
test_brute_force_with_key() {
    local key="$1"
    local key_description="$2"
    local max_length="$3"
    local test_encrypted_file="output-ciphertext/bf_${key}_encrypted.txt"
    local test_key_file="output-ciphertext/bf_${key}_key.txt"
    
    echo "Testing brute-force with $key_description (key: '$key')"
    echo "====================================================="
    
    # Create temporary key file
    echo "K = \"$key\"" > "$test_key_file"
    
    # Encrypt with the test key
    echo "Encrypting with test key..."
    python3 vigenere_cipher.py --key "$test_key_file" --encrypt -f "$INPUT_FILE" -o "$test_encrypted_file"
    
    if [ $? -eq 0 ]; then
        echo "✓ Test encryption completed"
        
        # Attempt to crack with brute-force
        echo "Attempting brute-force crack (max length: $max_length)..."
        CRACK_START_TIME=$(date +%s)
        CRACKED_BF_KEY=$(python3 crack_vigenere.py -f "$test_encrypted_file" --brute-force --key-length "$max_length" --key-only 2>/dev/null)
        CRACK_END_TIME=$(date +%s)
        CRACK_DURATION=$((CRACK_END_TIME - CRACK_START_TIME))
        
        if [ -n "$CRACKED_BF_KEY" ]; then
            echo "Cracked key: '$CRACKED_BF_KEY' (took ${CRACK_DURATION}s)"
            
            # Check if keys match (normalize case)
            KEY_UPPER=$(echo "$key" | tr '[:lower:]' '[:upper:]')
            if [ "$KEY_UPPER" = "$CRACKED_BF_KEY" ]; then
                echo "✓ SUCCESS: Brute-force found exact key match!"
            else
                echo "⚠ Different key found - testing decryption..."
                
                # Test if the found key produces correct decryption
                BF_DECRYPTED_FILE="output-ciphertext/bf_${key}_decrypted.txt"
                python3 crack_vigenere.py -f "$test_encrypted_file" --brute-force --key-length "$max_length" -o "$BF_DECRYPTED_FILE" 2>/dev/null
                
                if [ -f "$BF_DECRYPTED_FILE" ]; then
                    # Compare results
                    CRACK_BF_UPPER=$(cat "$BF_DECRYPTED_FILE" | tr '[:lower:]' '[:upper:]')
                    ORIGINAL_UPPER=$(cat "$INPUT_FILE" | tr '[:lower:]' '[:upper:]')
                    
                    if [ "$ORIGINAL_UPPER" = "$CRACK_BF_UPPER" ]; then
                        echo "✓ SUCCESS: Different key but correct decryption!"
                    else
                        echo "✗ FAILURE: Incorrect decryption"
                    fi
                    rm -f "$BF_DECRYPTED_FILE"
                fi
            fi
        else
            echo "✗ FAILURE: Brute-force could not crack the cipher (took ${CRACK_DURATION}s)"
            echo "  This is expected for keys longer than the maximum brute-force length"
        fi
    else
        echo "✗ Test encryption failed"
    fi
    
    # Cleanup temporary files
    rm -f "$test_key_file" "$test_encrypted_file"
    echo
}

# Test Case 1: 2-character key (should be very fast)
test_brute_force_with_key "AB" "2-character key" 2

# Test Case 2: 3-character key (should be fast)
test_brute_force_with_key "KEY" "3-character dictionary key" 3

# Test Case 3: 4-character key (moderate time)
test_brute_force_with_key "CODE" "4-character dictionary key" 4

# Test Case 4: 5-character key (longer time - may timeout)
test_brute_force_with_key "CRACK" "5-character dictionary key" 5

# Test Case 5: Mixed case handling
test_brute_force_with_key "Test" "mixed-case key (normalized to TEST)" 4

# Test Case 6: Edge case - single character
test_brute_force_with_key "X" "single-character key" 1

echo "8. Brute-Force Performance Summary"
echo "=================================="
echo "The brute-force tests above demonstrate:"
echo "• Very short keys (1-3 characters) can be cracked almost instantly"
echo "• 4-character keys take a few seconds to a minute"
echo "• 5+ character keys take significantly longer (exponential growth)"
echo "• Keys longer than 6-8 characters are typically impractical for brute-force"
echo "• The --key-length parameter limits the search space for performance"
echo

echo "=== All tests completed! ==="
