#!/usr/bin/env python3
"""
Brute Force Caesar Cipher Decryption

This script attempts to decrypt a Caesar cipher by trying all possible keys
from 1 to 25 and displaying the results. This demonstrates how the Caesar
cipher can be easily broken through brute force attacks.

Usage:
    python brute_force_caesar.py "cipher text to decrypt"
    python brute_force_caesar.py -f /path/to/file/name.ext.cae
    python brute_force_caesar.py --file /path/to/file/name.ext.cae
    
Where:
    - cipher text to decrypt: The encrypted message to be brute-forced
    - -f/--file: Read input from file

The script will display all 25 possible decryptions, making it easy to
identify the correct plaintext by visual inspection.
"""

import sys
import argparse

def caesar_decrypt(text, key):
    """
    Decrypt text using Caesar cipher algorithm with a specific key.
    
    Args:
        text (str): The ciphertext to decrypt
        key (int): The shift value
    
    Returns:
        str: The decrypted plaintext
    """
    result = ""
    
    for char in text:
        if char.isalpha():
            # Determine if the character is uppercase or lowercase
            is_upper = char.isupper()
            
            # Convert to lowercase for calculation
            char = char.lower()
            
            # Convert letter to number (a=0, b=1, ..., z=25)
            char_num = ord(char) - ord('a')
            
            # Apply Caesar cipher decryption formula: (c - k) mod 26
            decrypted_num = (char_num - key) % 26
            
            # Convert back to letter
            decrypted_char = chr(decrypted_num + ord('a'))
            
            # Restore original case
            if is_upper:
                decrypted_char = decrypted_char.upper()
            
            result += decrypted_char
        else:
            # Keep non-alphabetic characters (spaces, punctuation) as they are
            result += char
    
    return result


def brute_force_caesar(cipher_text):
    """
    Attempt to decrypt Caesar cipher by trying all possible keys.
    
    Args:
        cipher_text (str): The encrypted text to brute-force
    """
    print(f"Brute-forcing Caesar cipher for: \"{cipher_text}\"")
    print("=" * 60)
    print(f"{'Key':<4} | {'Decrypted Text'}")
    print("-" * 60)
    
    # Try all possible keys from 1 to 25
    for key in range(1, 26):
        decrypted_text = caesar_decrypt(cipher_text, key)
        print(f"{key:<4} | {decrypted_text}")


def main():
    """Main function to handle command line arguments and execute brute force."""
    parser = argparse.ArgumentParser(
        description="Brute force decrypt Caesar cipher by trying all possible keys",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  python brute_force_caesar.py "Vqg wkh prqh wq Dolfh"
  python brute_force_caesar.py -f encrypted.txt.cae
  python brute_force_caesar.py --file /path/to/document.txt.cae
        """
    )
    
    # Create mutually exclusive group for text input vs file input
    input_group = parser.add_mutually_exclusive_group(required=True)
    input_group.add_argument('text', nargs='?', help='Cipher text to decrypt (use quotes for multiple words)')
    input_group.add_argument('-f', '--file', dest='file_path', help='Path to input file')
    
    # Custom parsing to handle file input
    if len(sys.argv) >= 2 and sys.argv[1] in ['-f', '--file']:
        # File input mode: parse arguments differently
        if len(sys.argv) >= 3:
            # python script.py -f filename
            args = argparse.Namespace(file_path=sys.argv[2], text=None)
        else:
            print("Error: File path required with -f option")
            sys.exit(1)
    else:
        # Text input mode: use standard argument parsing
        args = parser.parse_args()
    
    # Get input text
    if args.file_path:
        # Read from file
        try:
            with open(args.file_path, 'r', encoding='utf-8') as file:
                cipher_text = file.read().strip()
        except FileNotFoundError:
            print(f"Error: File '{args.file_path}' not found")
            sys.exit(1)
        except IOError as e:
            print(f"Error reading file '{args.file_path}': {e}")
            sys.exit(1)
    else:
        # Use text from command line
        cipher_text = args.text
        if cipher_text is None:
            print("Error: Text input required")
            sys.exit(1)
    
    # Perform brute force attack
    brute_force_caesar(cipher_text)


if __name__ == "__main__":
    main()
