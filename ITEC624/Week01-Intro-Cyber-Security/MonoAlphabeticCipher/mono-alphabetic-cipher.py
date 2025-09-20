#!/usr/bin/env python3
"""
Mono-alphabetic Cipher Implementation

This script implements a mono-alphabetic cipher that maps each letter
of the alphabet to a randomly selected letter using a substitution key.
"""

import argparse
import os
import sys


def load_cipher_key(key_file='random-shuffle.txt'):
    """
    Load the cipher key from the specified file.
    
    Args:
        key_file (str): Path to the file containing the cipher alphabet
        
    Returns:
        dict: Mapping from plaintext letters to cipher letters
    """
    # Check if key file exists
    if not os.path.exists(key_file):
        print(f"Error: Key file '{key_file}' not found.")
        sys.exit(1)
    
    # Load the cipher alphabet
    with open(key_file, 'r') as f:
        cipher_alphabet = f.read().strip().upper()
    
    # Validate the cipher alphabet
    if len(cipher_alphabet) != 26:
        print(f"Error: Cipher alphabet must be exactly 26 characters. Found {len(cipher_alphabet)}.")
        sys.exit(1)
    
    if len(set(cipher_alphabet)) != 26:
        print("Error: Cipher alphabet must contain all unique letters.")
        sys.exit(1)
    
    # Create the mapping
    plain_alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
    cipher_mapping = {}
    
    for i, plain_char in enumerate(plain_alphabet):
        cipher_mapping[plain_char] = cipher_alphabet[i]
        # Also map lowercase letters
        cipher_mapping[plain_char.lower()] = cipher_alphabet[i]
    
    return cipher_mapping


def encrypt_text(plaintext, cipher_mapping):
    """
    Encrypt the given plaintext using the cipher mapping.
    
    Args:
        plaintext (str): The text to encrypt
        cipher_mapping (dict): Mapping from plaintext to cipher letters
        
    Returns:
        str: The encrypted ciphertext
    """
    ciphertext = []
    
    for char in plaintext:
        if char.isalpha():
            # Encrypt alphabetic characters
            encrypted_char = cipher_mapping.get(char, char)
            ciphertext.append(encrypted_char)
        else:
            # Keep non-alphabetic characters as they are
            ciphertext.append(char)
    
    return ''.join(ciphertext)


def main():
    """Main function to handle command line arguments and execute the cipher."""
    parser = argparse.ArgumentParser(description='Mono-alphabetic Cipher Encryption')
    parser.add_argument('text', nargs='?', help='Text to encrypt (if not using file input)')
    parser.add_argument('-f', '--file', help='Input file containing plaintext to encrypt')
    parser.add_argument('-k', '--key', default='random-shuffle.txt', 
                       help='Key file containing cipher alphabet (default: random-shuffle.txt)')
    
    args = parser.parse_args()
    
    # Check if we have input
    if not args.text and not args.file:
        parser.print_help()
        print("\nError: Please provide either text to encrypt or specify an input file.")
        sys.exit(1)
    
    # Load the cipher key
    cipher_mapping = load_cipher_key(args.key)
    
    # Get the plaintext
    if args.file:
        # Read from file
        if not os.path.exists(args.file):
            print(f"Error: Input file '{args.file}' not found.")
            sys.exit(1)
        
        with open(args.file, 'r') as f:
            plaintext = f.read()
    else:
        # Use command line argument
        plaintext = args.text
    
    # Encrypt the text
    ciphertext = encrypt_text(plaintext, cipher_mapping)
    
    # Output the result
    print(ciphertext)


if __name__ == '__main__':
    main()
