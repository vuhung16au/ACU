#!/usr/bin/env python3
"""
Caesar Cipher Implementation

This script implements the Caesar cipher algorithm that shifts each letter
by a fixed number of positions in the alphabet.

Usage:
    python caesar_cipher.py "text to encrypt" [key]
    python caesar_cipher.py -f /path/to/file/name.ext [key]
    python caesar_cipher.py --file /path/to/file/name.ext [key]
    
Where:
    - text to encrypt: The plaintext message to be encrypted
    - -f/--file: Read input from file
    - key: The shift value (default is 3)

When using file input, encrypted text will be saved as /path/to/file/name.ext.cae
The algorithm handles both uppercase and lowercase letters while removing
spaces and non-alphabetic characters from the output.
"""

import sys
import os
import argparse

def caesar_cipher(text, key=3):
    """
    Encrypt text using Caesar cipher algorithm.
    
    Args:
        text (str): The plaintext to encrypt
        key (int): The shift value (default 3)
    
    Returns:
        str: The encrypted ciphertext
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
            
            # Apply Caesar cipher formula: (p + k) mod 26
            encrypted_num = (char_num + key) % 26
            
            # Convert back to letter
            encrypted_char = chr(encrypted_num + ord('a'))
            
            # Restore original case
            if is_upper:
                encrypted_char = encrypted_char.upper()
            
            result += encrypted_char
        # Non-alphabetic characters (including spaces) are now deleted
    
    return result


def main():
    """Main function to handle command line arguments and execute cipher."""
    parser = argparse.ArgumentParser(
        description="Encrypt text using Caesar cipher",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  python caesar_cipher.py "Send the money to Alice" 3
  python caesar_cipher.py -f input.txt 5
  python caesar_cipher.py --file /path/to/document.txt
        """
    )
    
    # Create mutually exclusive group for text input vs file input
    input_group = parser.add_mutually_exclusive_group(required=True)
    input_group.add_argument('text', nargs='?', help='Text to encrypt (use quotes for multiple words)')
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
                text = file.read()
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
    
    # Encrypt the text
    encrypted_text = caesar_cipher(text, args.key)
    
    # Output the result
    if args.file_path:
        # Save to file with .cae extension
        output_file = args.file_path + '.cae'
        try:
            with open(output_file, 'w', encoding='utf-8') as file:
                file.write(encrypted_text)
            print(f"Encrypted text saved to: {output_file}")
            print(f"Encrypted text: {encrypted_text}")
        except IOError as e:
            print(f"Error writing to file '{output_file}': {e}")
            sys.exit(1)
    else:
        # Print to console
        print(encrypted_text)


if __name__ == "__main__":
    main()
