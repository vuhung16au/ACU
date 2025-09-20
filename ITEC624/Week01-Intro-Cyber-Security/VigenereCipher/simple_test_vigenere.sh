#!/bin/bash

# Simple test script for Vigenère Cipher implementation
# This script runs vigenere_cipher.py and then vigenere_decrypt.py
# as requested in the prompt

echo "=== Simple Vigenère Cipher Test ==="

# Test using main options for both scripts
echo "1. Running vigenere_cipher.py to encrypt test message..."
python3 vigenere_cipher.py --key vigenere.txt --encrypt -f inputs-plaintext/test_message.txt -o output-ciphertext/test_encrypted.txt

echo "2. Running vigenere_decrypt.py to decrypt the message..."
python3 vigenere_decrypt.py --key vigenere.txt -f output-ciphertext/test_encrypted.txt -o output-ciphertext/test_decrypted.txt

echo "3. Results:"
echo "Original message:"
cat inputs-plaintext/test_message.txt
echo
echo "Encrypted message:"
cat output-ciphertext/test_encrypted.txt
echo
echo "Decrypted message:"
cat output-ciphertext/test_decrypted.txt

echo "=== Test completed ==="
