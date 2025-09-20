#!/usr/bin/env python3
"""
Caesar Cipher Decryption Implementation

This script implements the Caesar cipher decryption algorithm that shifts each letter
back by a fixed number of positions in the alphabet to decrypt the ciphertext.

Usage:
    python caesar_decrypt.py "text to decrypt" [key]
    python caesar_decrypt.py -f /path/to/file/name.ext.cae [key]
    python caesar_decrypt.py --file /path/to/file/name.ext.cae [key]
    
Where:
    - text to decrypt: The ciphertext message to be decrypted
    - -f/--file: Read input from file
    - key: The shift value (default is 3)

The algorithm handles both uppercase and lowercase letters while preserving
spaces and non-alphabetic characters.
"""

import sys
import os
import argparse

def caesar_decrypt(text, key=3):
    """
    Decrypt text using Caesar cipher algorithm.
    
    Args:
        text (str): The ciphertext to decrypt
        key (int): The shift value (default 3)
    
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
            # Keep spaces and non-alphabetic characters unchanged
            result += char
    
    return result


def main():
    """Main function to handle command line arguments and execute decryption."""
    parser = argparse.ArgumentParser(
        description="Decrypt text using Caesar cipher",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  python caesar_decrypt.py "Vqg wkh prqh wq Dolfh" 3
  python caesar_decrypt.py -f encrypted.txt.cae 5
  python caesar_decrypt.py --file /path/to/document.txt.cae
        """
    )
    
    # Create mutually exclusive group for text input vs file input
    input_group = parser.add_mutually_exclusive_group(required=True)
    input_group.add_argument('text', nargs='?', help='Text to decrypt (use quotes for multiple words)')
    input_group.add_argument('-f', '--file', dest='file_path', help='Path to input file')
    
    parser.add_argument('key', nargs='?', type=int, default=3, help='Shift key (default: 3)')
    
    # Custom parsing to handle file input with key
    if len(sys.argv) >= 2 and sys.argv[1] in ['-f', '--file']:
        # File input mode: parse arguments differently
        if len(sys.argv) >= 4:
            # python script.py -f filename key
            try:
                key = int(sys.argv[3])
                args = argparse.Namespace(file_path=sys.argv[2], text=None, key=key)
            except ValueError:
                print("Error: Key must be an integer")
                sys.exit(1)
        elif len(sys.argv) == 3:
            # python script.py -f filename (use default key)
            args = argparse.Namespace(file_path=sys.argv[2], text=None, key=3)
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
                text = file.read().strip()
        except FileNotFoundError:
            print(f"Error: File '{args.file_path}' not found")
            sys.exit(1)
        except IOError as e:
            print(f"Error reading file '{args.file_path}': {e}")
            sys.exit(1)
    else:
        # Use text from command line
        text = args.text
        if text is None:
            print("Error: Text input required")
            sys.exit(1)
    
    # Decrypt the text
    decrypted_text = caesar_decrypt(text, args.key)
    
    # Output the result
    print(decrypted_text)


if __name__ == "__main__":
    main()
