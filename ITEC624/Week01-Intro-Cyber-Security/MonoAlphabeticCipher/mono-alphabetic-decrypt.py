#!/usr/bin/env python3
"""
Mono-alphabetic Cipher Decryption Implementation

This script implements decryption for a mono-alphabetic cipher that maps each letter
of the cipher alphabet back to the original plaintext alphabet using a substitution key.
"""

import argparse
import os
import sys


def load_cipher_key(key_file='random-shuffle.txt'):
    """
    Load the cipher key from the specified file and create reverse mapping for decryption.
    
    Args:
        key_file (str): Path to the file containing the cipher alphabet
        
    Returns:
        dict: Mapping from cipher letters to plaintext letters
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
    
    # Create the reverse mapping (cipher to plain)
    plain_alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
    decrypt_mapping = {}
    
    for i, cipher_char in enumerate(cipher_alphabet):
        plain_char = plain_alphabet[i]
        decrypt_mapping[cipher_char] = plain_char
        # Also map lowercase letters
        decrypt_mapping[cipher_char.lower()] = plain_char.lower()
    
    return decrypt_mapping


def decrypt_text(ciphertext, decrypt_mapping):
    """
    Decrypt the given ciphertext using the decryption mapping.
    
    Args:
        ciphertext (str): The text to decrypt
        decrypt_mapping (dict): Mapping from cipher letters to plaintext letters
        
    Returns:
        str: The decrypted plaintext
    """
    plaintext = []
    
    for char in ciphertext:
        if char.isalpha():
            # Decrypt alphabetic characters
            decrypted_char = decrypt_mapping.get(char, char)
            plaintext.append(decrypted_char)
        else:
            # Keep non-alphabetic characters as they are
            plaintext.append(char)
    
    return ''.join(plaintext)


def main():
    """Main function to handle command line arguments and execute the decryption."""
    parser = argparse.ArgumentParser(description='Mono-alphabetic Cipher Decryption')
    parser.add_argument('text', nargs='?', help='Ciphertext to decrypt (if not using file input)')
    parser.add_argument('-f', '--file', help='Input file containing ciphertext to decrypt')
    parser.add_argument('-k', '--key', default='random-shuffle.txt', 
                       help='Key file containing cipher alphabet (default: random-shuffle.txt)')
    
    args = parser.parse_args()
    
    # Check if we have input
    if not args.text and not args.file:
        parser.print_help()
        print("\nError: Please provide either ciphertext to decrypt or specify an input file.")
        sys.exit(1)
    
    # Load the cipher key (reverse mapping for decryption)
    decrypt_mapping = load_cipher_key(args.key)
    
    # Get the ciphertext
    if args.file:
        # Read from file
        if not os.path.exists(args.file):
            print(f"Error: Input file '{args.file}' not found.")
            sys.exit(1)
        
        with open(args.file, 'r') as f:
            ciphertext = f.read()
    else:
        # Use command line argument
        ciphertext = args.text
    
    # Decrypt the text
    plaintext = decrypt_text(ciphertext, decrypt_mapping)
    
    # Output the result
    print(plaintext)


if __name__ == '__main__':
    main()
