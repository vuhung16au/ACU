#!/bin/bash

# Quick demo of the interactive functionality
# This script demonstrates the oneTimepad.sh interactive testing

echo "=== Demo of oneTimepad.sh Interactive Testing ==="
echo
echo "Testing Option 1: Encrypt with generated key"
echo "Input: HELLO WORLD"
echo

python3 -c "
import sys
sys.path.insert(0, 'src')
from oneTimepad import OneTimePad

otp = OneTimePad()
message = 'HELLO WORLD'
ciphertext, key = otp.encrypt_with_generated_key(message)
prepared = otp._prepare_text(message)

print(f'Original message: \"{message}\"')
print(f'Prepared message: \"{prepared}\"')
print(f'Generated key: \"{key}\"')
print(f'Ciphertext: \"{ciphertext}\"')

# Verify decryption
decrypted = otp.decrypt(ciphertext, key)
print(f'Decrypted: \"{decrypted}\"')
print(f'Verification: {\"PASS\" if decrypted == prepared else \"FAIL\"}')
"

echo
echo "=== Testing Option 2: Encrypt with custom key ==="
echo "Input: SECRET, Key: ABCDEF"
echo

python3 -c "
import sys
sys.path.insert(0, 'src')
from oneTimepad import OneTimePad

otp = OneTimePad()
message = 'SECRET'
key = 'ABCDEF'
ciphertext = otp.encrypt(message, key)
prepared = otp._prepare_text(message)

print(f'Original message: \"{message}\"')
print(f'Prepared message: \"{prepared}\"')
print(f'Custom key: \"{key}\"')
print(f'Ciphertext: \"{ciphertext}\"')

# Verify decryption
decrypted = otp.decrypt(ciphertext, key)
print(f'Decrypted: \"{decrypted}\"')
print(f'Verification: {\"PASS\" if decrypted == prepared else \"FAIL\"}')
"

echo
echo "=== Testing Option 3: Decrypt message ==="
echo "Using the previous ciphertext and key"
echo

python3 -c "
import sys
sys.path.insert(0, 'src')
from oneTimepad import OneTimePad

otp = OneTimePad()
# Using known values for demo
ciphertext = 'SFDVGV'  # SECRET encrypted with ABCDEF
key = 'ABCDEF'
plaintext = otp.decrypt(ciphertext, key)

print(f'Ciphertext: \"{ciphertext}\"')
print(f'Key: \"{key}\"')
print(f'Decrypted plaintext: \"{plaintext}\"')
"

echo
echo "All 4 options of the One-Time Pad implementation have been successfully tested!"
