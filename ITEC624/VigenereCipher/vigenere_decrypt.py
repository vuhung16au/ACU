#!/usr/bin/env python3
"""
Vigenère Decryption Script

This script implements the Vigenère cipher decryption functionality.
The Vigenère cipher is a polyalphabetic substitution cipher that uses a keyword
to shift letters in the ciphertext back to plaintext.
"""

import argparse
import sys
import re


def load_key_from_file(key_file):
    """
    Load the cipher key from a file.
    
    Args:
        key_file (str): Path to the key file
        
    Returns:
        str: The cipher key
    """
    try:
        with open(key_file, 'r') as f:
            content = f.read()
            # Extract the key value after K = 
            match = re.search(r'K\s*=\s*["\']([^"\']+)["\']', content)
            if match:
                return match.group(1).lower()
            else:
                # If no K = pattern found, try to find any quoted string
                match = re.search(r'["\']([^"\']+)["\']', content)
                if match:
                    return match.group(1).lower()
                else:
                    raise ValueError("No valid key found in file")
    except FileNotFoundError:
        print(f"Error: Key file '{key_file}' not found.", file=sys.stderr)
        sys.exit(1)
    except Exception as e:
        print(f"Error reading key file: {e}", file=sys.stderr)
        sys.exit(1)


def vigenere_encrypt(plaintext, key):
    """
    Encrypt plaintext using the Vigenère cipher.
    
    Args:
        plaintext (str): The text to encrypt
        key (str): The cipher key
        
    Returns:
        str: The encrypted text
    """
    if not key:
        raise ValueError("Key cannot be empty")
    
    ciphertext = []
    key_index = 0
    
    for char in plaintext:
        if char.isalpha():
            # Convert to uppercase for processing
            char = char.upper()
            key_char = key[key_index % len(key)].upper()
            
            # Calculate shift value (A=0, B=1, ..., Z=25)
            char_num = ord(char) - ord('A')
            key_num = ord(key_char) - ord('A')
            
            # Apply Vigenère encryption: (char + key) mod 26
            encrypted_num = (char_num + key_num) % 26
            encrypted_char = chr(encrypted_num + ord('A'))
            
            ciphertext.append(encrypted_char)
            key_index += 1
        else:
            # Non-alphabetic characters are not encrypted
            ciphertext.append(char)
    
    return ''.join(ciphertext)


def vigenere_decrypt(ciphertext, key):
    """
    Decrypt ciphertext using the Vigenère cipher.
    
    Args:
        ciphertext (str): The text to decrypt
        key (str): The cipher key
        
    Returns:
        str: The decrypted text
    """
    if not key:
        raise ValueError("Key cannot be empty")
    
    plaintext = []
    key_index = 0
    
    for char in ciphertext:
        if char.isalpha():
            # Convert to uppercase for processing
            char = char.upper()
            key_char = key[key_index % len(key)].upper()
            
            # Calculate shift value (A=0, B=1, ..., Z=25)
            char_num = ord(char) - ord('A')
            key_num = ord(key_char) - ord('A')
            
            # Apply Vigenère decryption: (char - key) mod 26
            decrypted_num = (char_num - key_num) % 26
            decrypted_char = chr(decrypted_num + ord('A'))
            
            plaintext.append(decrypted_char)
            key_index += 1
        else:
            # Non-alphabetic characters are not decrypted
            plaintext.append(char)
    
    return ''.join(plaintext)


def main():
    """Main function to handle command-line arguments and execute cipher operations."""
    parser = argparse.ArgumentParser(
        description='Vigenère Decryption - Decrypt text using the Vigenère cipher'
    )
    
    parser.add_argument(
        '--key',
        default='vigenere.txt',
        help='Path to the key file (default: vigenere.txt)'
    )
    
    parser.add_argument(
        '--encrypt',
        action='store_true',
        help='Encrypt the input text instead of decrypt'
    )
    
    parser.add_argument(
        '-f', '--file',
        help='Read input from a file (default: standard input)'
    )
    
    parser.add_argument(
        '-o', '--output',
        help='Write output to a file (default: standard output)'
    )
    
    args = parser.parse_args()
    
    # Load the cipher key
    key = load_key_from_file(args.key)
    
    # Read input
    if args.file:
        try:
            with open(args.file, 'r') as f:
                input_text = f.read().strip()
        except FileNotFoundError:
            print(f"Error: Input file '{args.file}' not found.", file=sys.stderr)
            sys.exit(1)
        except Exception as e:
            print(f"Error reading input file: {e}", file=sys.stderr)
            sys.exit(1)
    else:
        # Read from standard input
        input_text = sys.stdin.read().strip()
    
    # Process the text - default is decrypt, unless --encrypt is specified
    if args.encrypt:
        output_text = vigenere_encrypt(input_text, key)
    else:
        output_text = vigenere_decrypt(input_text, key)
    
    # Write output
    if args.output:
        try:
            with open(args.output, 'w') as f:
                f.write(output_text + '\n')
            print(f"Output written to '{args.output}'")
        except Exception as e:
            print(f"Error writing output file: {e}", file=sys.stderr)
            sys.exit(1)
    else:
        # Write to standard output
        print(output_text)


if __name__ == '__main__':
    main()
