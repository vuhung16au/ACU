#!/usr/bin/env python3
"""
One-Time Pad Cipher Implementation

A Python implementation of the One-Time Pad cipher, a theoretically unbreakable 
encryption method when used correctly.

Author: [Your Name]
Date: July 25, 2025
"""

import secrets
import string
import argparse
import sys
from typing import Optional, Tuple


class OneTimePad:
    """
    Implementation of the One-Time Pad cipher.
    
    The One-Time Pad is a theoretically unbreakable encryption method that requires:
    - A truly random key that is as long as the message
    - The key must be used only once
    - The key must be kept completely secret
    """
    
    def __init__(self, alphabet: Optional[str] = None):
        """
        Initialize the One-Time Pad cipher.
        
        Args:
            alphabet: Custom alphabet to use. Defaults to uppercase letters A-Z.
        """
        self.alphabet = alphabet or string.ascii_uppercase
        self.alphabet_size = len(self.alphabet)
        self.char_to_num = {char: i for i, char in enumerate(self.alphabet)}
        self.num_to_char = {i: char for i, char in enumerate(self.alphabet)}
    
    def generate_key(self, length: int) -> str:
        """
        Generate a truly random key of specified length.
        
        Args:
            length: Length of the key to generate
            
        Returns:
            Random key string of specified length
        """
        if length <= 0:
            raise ValueError("Key length must be positive")
        
        key = ''.join(secrets.choice(self.alphabet) for _ in range(length))
        return key
    
    def _prepare_text(self, text: str) -> str:
        """
        Prepare text for encryption/decryption by converting to uppercase
        and filtering to only include alphabet characters.
        
        Args:
            text: Input text to prepare
            
        Returns:
            Prepared text containing only valid alphabet characters
        """
        prepared = ''.join(char.upper() for char in text if char.upper() in self.alphabet)
        return prepared
    
    def encrypt(self, plaintext: str, key: str) -> str:
        """
        Encrypt plaintext using the One-Time Pad algorithm.
        
        Args:
            plaintext: Message to encrypt
            key: Encryption key (must be at least as long as plaintext)
            
        Returns:
            Encrypted ciphertext
            
        Raises:
            ValueError: If key is shorter than plaintext or contains invalid characters
        """
        # Prepare the plaintext
        prepared_text = self._prepare_text(plaintext)
        
        if len(key) < len(prepared_text):
            raise ValueError(f"Key length ({len(key)}) must be at least as long as prepared text ({len(prepared_text)})")
        
        # Validate key characters
        for i, char in enumerate(key):
            if char not in self.alphabet:
                raise ValueError(f"Invalid character '{char}' at position {i} in key. Key must only contain characters from alphabet: {self.alphabet}")
        
        if not prepared_text:
            return ""
        
        ciphertext = []
        for i, char in enumerate(prepared_text):
            # Convert characters to numbers
            plaintext_num = self.char_to_num[char]
            key_num = self.char_to_num[key[i]]
            
            # Perform modular addition
            cipher_num = (plaintext_num + key_num) % self.alphabet_size
            
            # Convert back to character
            cipher_char = self.num_to_char[cipher_num]
            ciphertext.append(cipher_char)
        
        return ''.join(ciphertext)
    
    def decrypt(self, ciphertext: str, key: str) -> str:
        """
        Decrypt ciphertext using the One-Time Pad algorithm.
        
        Args:
            ciphertext: Encrypted message to decrypt
            key: Decryption key (same key used for encryption)
            
        Returns:
            Decrypted plaintext
            
        Raises:
            ValueError: If key is shorter than ciphertext or contains invalid characters
        """
        if len(key) < len(ciphertext):
            raise ValueError(f"Key length ({len(key)}) must be at least as long as ciphertext ({len(ciphertext)})")
        
        # Validate key characters
        for i, char in enumerate(key):
            if char not in self.alphabet:
                raise ValueError(f"Invalid character '{char}' at position {i} in key. Key must only contain characters from alphabet: {self.alphabet}")
        
        # Validate ciphertext characters
        for i, char in enumerate(ciphertext):
            if char not in self.alphabet:
                raise ValueError(f"Invalid character '{char}' at position {i} in ciphertext. Ciphertext must only contain characters from alphabet: {self.alphabet}")
        
        if not ciphertext:
            return ""
        
        plaintext = []
        for i, char in enumerate(ciphertext):
            # Convert characters to numbers
            cipher_num = self.char_to_num[char]
            key_num = self.char_to_num[key[i]]
            
            # Perform modular subtraction
            plaintext_num = (cipher_num - key_num) % self.alphabet_size
            
            # Convert back to character
            plaintext_char = self.num_to_char[plaintext_num]
            plaintext.append(plaintext_char)
        
        return ''.join(plaintext)
    
    def encrypt_with_generated_key(self, plaintext: str) -> Tuple[str, str]:
        """
        Encrypt plaintext with a newly generated random key.
        
        Args:
            plaintext: Message to encrypt
            
        Returns:
            Tuple of (ciphertext, key)
        """
        prepared_text = self._prepare_text(plaintext)
        if not prepared_text:
            return "", ""
        
        key = self.generate_key(len(prepared_text))
        ciphertext = self.encrypt(plaintext, key)
        return ciphertext, key
    
    def verify_encryption(self, plaintext: str, ciphertext: str, key: str) -> bool:
        """
        Verify that encryption/decryption works correctly.
        
        Args:
            plaintext: Original message
            ciphertext: Encrypted message
            key: Encryption/decryption key
            
        Returns:
            True if verification successful, False otherwise
        """
        try:
            # Test encryption
            test_cipher = self.encrypt(plaintext, key)
            if test_cipher != ciphertext:
                return False
            
            # Test decryption
            test_plain = self.decrypt(ciphertext, key)
            prepared_original = self._prepare_text(plaintext)
            if test_plain != prepared_original:
                return False
            
            return True
        except Exception:
            return False


def cli_encrypt_generated():
    """Command-line interface for encryption with generated key."""
    otp = OneTimePad()
    
    message = input("Enter message to encrypt: ")
    if not message.strip():
        print("Error: No message provided")
        return 1
    
    try:
        ciphertext, key = otp.encrypt_with_generated_key(message)
        prepared = otp._prepare_text(message)
        
        print(f"Original message: '{message}'")
        print(f"Prepared message: '{prepared}'")
        print(f"Generated key: '{key}'")
        print(f"Ciphertext: '{ciphertext}'")
        
        return 0
    except Exception as e:
        print(f"Error: {e}")
        return 1


def cli_encrypt_custom():
    """Command-line interface for encryption with custom key."""
    otp = OneTimePad()
    
    message = input("Enter message to encrypt: ")
    key = input("Enter encryption key: ")
    
    if not message.strip():
        print("Error: No message provided")
        return 1
    
    if not key.strip():
        print("Error: No key provided")
        return 1
    
    try:
        ciphertext = otp.encrypt(message, key)
        prepared = otp._prepare_text(message)
        
        print(f"Original message: '{message}'")
        print(f"Prepared message: '{prepared}'")
        print(f"Custom key: '{key}'")
        print(f"Ciphertext: '{ciphertext}'")
        
        return 0
    except ValueError as e:
        print(f"Error: {e}")
        return 1


def cli_decrypt():
    """Command-line interface for decryption."""
    otp = OneTimePad()
    
    ciphertext = input("Enter ciphertext to decrypt: ")
    key = input("Enter decryption key: ")
    
    if not ciphertext.strip():
        print("Error: No ciphertext provided")
        return 1
    
    if not key.strip():
        print("Error: No key provided")
        return 1
    
    try:
        plaintext = otp.decrypt(ciphertext, key)
        print(f"Ciphertext: '{ciphertext}'")
        print(f"Key: '{key}'")
        print(f"Decrypted plaintext: '{plaintext}'")
        
        return 0
    except ValueError as e:
        print(f"Error: {e}")
        return 1


def create_parser():
    """Create command-line argument parser."""
    parser = argparse.ArgumentParser(
        description="One-Time Pad Cipher - A theoretically unbreakable encryption method",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  %(prog)s -e                    # Encrypt with generated key
  %(prog)s --encrypt-custom      # Encrypt with custom key
  %(prog)s -d                    # Decrypt message
  %(prog)s --demo                # Run demonstration
        """
    )
    
    group = parser.add_mutually_exclusive_group()
    group.add_argument(
        '-e', '--encrypt',
        action='store_true',
        help='Encrypt message with generated key'
    )
    group.add_argument(
        '--encrypt-custom',
        action='store_true',
        help='Encrypt message with custom key'
    )
    group.add_argument(
        '-d', '--decrypt',
        action='store_true',
        help='Decrypt message'
    )
    group.add_argument(
        '--demo',
        action='store_true',
        help='Run demonstration and interactive mode'
    )
    
    return parser


def main():
    """Main entry point for command-line interface."""
    parser = create_parser()
    args = parser.parse_args()
    
    # If no arguments provided, show help
    if not any(vars(args).values()):
        parser.print_help()
        return 0
    
    # Handle different options
    if args.encrypt:
        return cli_encrypt_generated()
    elif args.encrypt_custom:
        return cli_encrypt_custom()
    elif args.decrypt:
        return cli_decrypt()
    elif args.demo:
        demo()
        return 0
    else:
        parser.print_help()
        return 0


def demo():
    """
    Demonstration of the One-Time Pad cipher functionality.
    """
    print("=== One-Time Pad Cipher Demo ===\n")
    
    # Create OTP instance
    otp = OneTimePad()
    
    # Example 1: Basic encryption/decryption
    print("1. Basic Encryption/Decryption:")
    message = "HELLO WORLD"
    print(f"Original message: '{message}'")
    
    # Generate key and encrypt
    ciphertext, key = otp.encrypt_with_generated_key(message)
    print(f"Generated key: '{key}'")
    print(f"Ciphertext: '{ciphertext}'")
    
    # Decrypt
    decrypted = otp.decrypt(ciphertext, key)
    print(f"Decrypted: '{decrypted}'")
    print(f"Verification: {otp.verify_encryption(message, ciphertext, key)}")
    print()
    
    # Example 2: User-provided key
    print("2. User-Provided Key:")
    message2 = "SECRET MESSAGE"
    prepared_msg = otp._prepare_text(message2)
    user_key = otp.generate_key(len(prepared_msg))
    print(f"Message: '{message2}' -> Prepared: '{prepared_msg}'")
    print(f"Key: '{user_key}'")
    
    encrypted2 = otp.encrypt(message2, user_key)
    decrypted2 = otp.decrypt(encrypted2, user_key)
    print(f"Encrypted: '{encrypted2}'")
    print(f"Decrypted: '{decrypted2}'")
    print()
    
    # Example 3: Show that different keys produce different results
    print("3. Different Keys Produce Different Results:")
    message3 = "TEST"
    key1 = "ABCD"
    key2 = "XYZA"
    
    cipher1 = otp.encrypt(message3, key1)
    cipher2 = otp.encrypt(message3, key2)
    
    print(f"Message: '{message3}'")
    print(f"Key 1: '{key1}' -> Cipher: '{cipher1}'")
    print(f"Key 2: '{key2}' -> Cipher: '{cipher2}'")
    print(f"Same result: {cipher1 == cipher2}")
    print()
    
    # Example 4: Security demonstration
    print("4. Security Notes:")
    print("- Keys should NEVER be reused")
    print("- Keys must be truly random")
    print("- Keys must be kept secret")
    print("- Key length must equal or exceed message length")


if __name__ == "__main__":
    # Use command-line interface if arguments provided, otherwise run demo
    if len(sys.argv) > 1:
        sys.exit(main())
    else:
        # Run the demonstration
        demo()
        
        # Interactive mode
        print("\n=== Interactive Mode ===")
        print("Enter your own message to encrypt/decrypt!")
        
        otp = OneTimePad()
        
        try:
            while True:
                print("\nOptions:")
                print("1. Encrypt with generated key")
                print("2. Encrypt with custom key")
                print("3. Decrypt message")
                print("4. Exit")
                
                choice = input("\nEnter choice (1-4): ").strip()
                
                if choice == "1":
                    message = input("Enter message to encrypt: ")
                    if message:
                        ciphertext, key = otp.encrypt_with_generated_key(message)
                        print(f"Key: {key}")
                        print(f"Ciphertext: {ciphertext}")
                
                elif choice == "2":
                    message = input("Enter message to encrypt: ")
                    key = input("Enter key: ")
                    if message and key:
                        try:
                            ciphertext = otp.encrypt(message, key)
                            print(f"Ciphertext: {ciphertext}")
                        except ValueError as e:
                            print(f"Error: {e}")
                
                elif choice == "3":
                    ciphertext = input("Enter ciphertext to decrypt: ")
                    key = input("Enter key: ")
                    if ciphertext and key:
                        try:
                            plaintext = otp.decrypt(ciphertext, key)
                            print(f"Plaintext: {plaintext}")
                        except ValueError as e:
                            print(f"Error: {e}")
                
                elif choice == "4":
                    print("Goodbye!")
                    break
                
                else:
                    print("Invalid choice. Please enter 1-4.")
                    
        except KeyboardInterrupt:
            print("\n\nExiting...")
