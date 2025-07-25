#!/usr/bin/env python3
"""
Unit tests for the One-Time Pad cipher implementation.

Run with: python -m pytest tests/
"""

import unittest
import sys
import os
import subprocess
import tempfile
from unittest.mock import patch, MagicMock
from io import StringIO

# Add src directory to path to import our module
sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..', 'src'))

from oneTimepad import OneTimePad, cli_encrypt_generated, cli_encrypt_custom, cli_decrypt, create_parser, main


class TestOneTimePad(unittest.TestCase):
    """Test cases for the OneTimePad class."""
    
    def setUp(self):
        """Set up test fixtures before each test method."""
        self.otp = OneTimePad()
    
    def test_initialization(self):
        """Test proper initialization of OneTimePad."""
        self.assertEqual(len(self.otp.alphabet), 26)
        self.assertEqual(self.otp.alphabet_size, 26)
        self.assertIn('A', self.otp.alphabet)
        self.assertIn('Z', self.otp.alphabet)
    
    def test_custom_alphabet(self):
        """Test initialization with custom alphabet."""
        custom_alphabet = "0123456789"
        otp_custom = OneTimePad(custom_alphabet)
        self.assertEqual(otp_custom.alphabet, custom_alphabet)
        self.assertEqual(otp_custom.alphabet_size, 10)
    
    def test_key_generation(self):
        """Test random key generation."""
        key_length = 10
        key = self.otp.generate_key(key_length)
        
        self.assertEqual(len(key), key_length)
        self.assertTrue(all(char in self.otp.alphabet for char in key))
        
        # Test that two generated keys are different (extremely high probability)
        key2 = self.otp.generate_key(key_length)
        self.assertNotEqual(key, key2)
    
    def test_key_generation_invalid_length(self):
        """Test key generation with invalid length."""
        with self.assertRaises(ValueError):
            self.otp.generate_key(0)
        
        with self.assertRaises(ValueError):
            self.otp.generate_key(-1)
    
    def test_text_preparation(self):
        """Test text preparation functionality."""
        test_cases = [
            ("hello world", "HELLOWORLD"),
            ("Hello, World!", "HELLOWORLD"),
            ("Test123", "TEST"),
            ("", ""),
            ("!@#$%", ""),
            ("A-B-C", "ABC")
        ]
        
        for input_text, expected in test_cases:
            with self.subTest(input_text=input_text):
                result = self.otp._prepare_text(input_text)
                self.assertEqual(result, expected)
    
    def test_basic_encryption_decryption(self):
        """Test basic encryption and decryption."""
        plaintext = "HELLO"
        key = "XMCKL"
        
        # Encrypt
        ciphertext = self.otp.encrypt(plaintext, key)
        self.assertEqual(len(ciphertext), len(plaintext))
        self.assertTrue(all(char in self.otp.alphabet for char in ciphertext))
        
        # Decrypt
        decrypted = self.otp.decrypt(ciphertext, key)
        self.assertEqual(decrypted, plaintext)
    
    def test_encryption_with_text_preparation(self):
        """Test encryption with text that needs preparation."""
        plaintext = "Hello, World!"
        expected_prepared = "HELLOWORLD"
        key = self.otp.generate_key(len(expected_prepared))
        
        ciphertext = self.otp.encrypt(plaintext, key)
        decrypted = self.otp.decrypt(ciphertext, key)
        
        self.assertEqual(decrypted, expected_prepared)
    
    def test_encrypt_with_generated_key(self):
        """Test encryption with automatically generated key."""
        plaintext = "SECRET MESSAGE"
        ciphertext, key = self.otp.encrypt_with_generated_key(plaintext)
        
        prepared_text = self.otp._prepare_text(plaintext)
        self.assertEqual(len(key), len(prepared_text))
        self.assertEqual(len(ciphertext), len(prepared_text))
        
        # Verify decryption works
        decrypted = self.otp.decrypt(ciphertext, key)
        self.assertEqual(decrypted, prepared_text)
    
    def test_empty_message(self):
        """Test encryption/decryption of empty messages."""
        ciphertext, key = self.otp.encrypt_with_generated_key("")
        self.assertEqual(ciphertext, "")
        self.assertEqual(key, "")
        
        # Test decrypt empty
        decrypted = self.otp.decrypt("", "")
        self.assertEqual(decrypted, "")
    
    def test_key_too_short(self):
        """Test error handling when key is too short."""
        plaintext = "HELLO"
        short_key = "ABC"
        
        with self.assertRaises(ValueError):
            self.otp.encrypt(plaintext, short_key)
        
        with self.assertRaises(ValueError):
            self.otp.decrypt("HELLO", short_key)
    
    def test_verification_function(self):
        """Test the verification function."""
        plaintext = "TEST MESSAGE"
        ciphertext, key = self.otp.encrypt_with_generated_key(plaintext)
        
        # Should verify correctly
        self.assertTrue(self.otp.verify_encryption(plaintext, ciphertext, key))
        
        # Should fail with wrong key
        wrong_key = self.otp.generate_key(len(key))
        self.assertFalse(self.otp.verify_encryption(plaintext, ciphertext, wrong_key))
        
        # Should fail with wrong ciphertext
        wrong_cipher = self.otp.generate_key(len(ciphertext))
        self.assertFalse(self.otp.verify_encryption(plaintext, wrong_cipher, key))
    
    def test_mathematical_properties(self):
        """Test mathematical properties of the cipher."""
        # Test that encryption is reversible
        plaintext = "MATHEMATICS"
        key = "RANDOMKEYFORTEST"[:len(plaintext)]
        
        ciphertext = self.otp.encrypt(plaintext, key)
        decrypted = self.otp.decrypt(ciphertext, key)
        
        self.assertEqual(plaintext, decrypted)
        
        # Test specific known values
        # A (0) + A (0) = A (0)
        self.assertEqual(self.otp.encrypt("A", "A"), "A")
        # A (0) + B (1) = B (1)
        self.assertEqual(self.otp.encrypt("A", "B"), "B")
        # Z (25) + B (1) = A (0) (wraparound)
        self.assertEqual(self.otp.encrypt("Z", "B"), "A")
    
    def test_different_keys_different_results(self):
        """Test that different keys produce different results."""
        plaintext = "SAME MESSAGE"
        
        ciphertext1, key1 = self.otp.encrypt_with_generated_key(plaintext)
        ciphertext2, key2 = self.otp.encrypt_with_generated_key(plaintext)
        
        # Keys should be different
        self.assertNotEqual(key1, key2)
        # Ciphertexts should be different (extremely high probability)
        self.assertNotEqual(ciphertext1, ciphertext2)
    
    def test_case_insensitive_input(self):
        """Test that input is handled case-insensitively."""
        plaintext_upper = "HELLO"
        plaintext_lower = "hello"
        plaintext_mixed = "HeLLo"
        
        key = "ABCDE"
        
        cipher_upper = self.otp.encrypt(plaintext_upper, key)
        cipher_lower = self.otp.encrypt(plaintext_lower, key)
        cipher_mixed = self.otp.encrypt(plaintext_mixed, key)
        
        # All should produce the same result
        self.assertEqual(cipher_upper, cipher_lower)
        self.assertEqual(cipher_upper, cipher_mixed)


class TestCLIFunctionality(unittest.TestCase):
    """Test cases for the command-line interface functionality."""
    
    def setUp(self):
        """Set up test fixtures before each test method."""
        self.otp = OneTimePad()
        self.script_path = os.path.join(os.path.dirname(__file__), '..', 'src', 'oneTimepad.py')
    
    def test_cli_help(self):
        """Test CLI help option."""
        result = subprocess.run([
            'python3', self.script_path, '--help'
        ], capture_output=True, text=True)
        
        self.assertEqual(result.returncode, 0)
        self.assertIn('One-Time Pad Cipher', result.stdout)
        self.assertIn('--encrypt', result.stdout)
        self.assertIn('--decrypt', result.stdout)
    
    def test_cli_no_args(self):
        """Test CLI with no arguments runs demo mode."""
        # Test that without arguments, it tries to run demo mode
        # We'll test with a timeout since demo mode has interactive elements
        result = subprocess.run([
            'python3', self.script_path
        ], capture_output=True, text=True, input='4\n', timeout=5)
        
        # Should start demo mode (may exit due to interactive elements)
        # We just check that it doesn't crash immediately
        self.assertIn('One-Time Pad Cipher Demo', result.stdout)
    
    def test_cli_demo_option(self):
        """Test CLI --demo option."""
        result = subprocess.run([
            'python3', self.script_path, '--demo'
        ], capture_output=True, text=True, input='4\n', timeout=5)
        
        # Should run demo mode
        self.assertIn('One-Time Pad Cipher Demo', result.stdout)
    
    @patch('builtins.input')
    @patch('sys.stdout', new_callable=StringIO)
    def test_cli_encrypt_generated_success(self, mock_stdout, mock_input):
        """Test CLI encrypt with generated key functionality."""
        # Mock user input
        mock_input.return_value = "TEST MESSAGE"
        
        # Import here to avoid import errors in test discovery
        sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..', 'src'))
        from oneTimepad import cli_encrypt_generated
        
        result = cli_encrypt_generated()
        output = mock_stdout.getvalue()
        
        self.assertEqual(result, 0)
        self.assertIn("Original message: 'TEST MESSAGE'", output)
        self.assertIn("Generated key:", output)
        self.assertIn("Ciphertext:", output)
    
    @patch('builtins.input')
    @patch('sys.stdout', new_callable=StringIO)
    def test_cli_encrypt_generated_empty_message(self, mock_stdout, mock_input):
        """Test CLI encrypt with generated key with empty message."""
        # Mock user input
        mock_input.return_value = ""
        
        # Import here to avoid import errors in test discovery
        sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..', 'src'))
        from oneTimepad import cli_encrypt_generated
        
        result = cli_encrypt_generated()
        output = mock_stdout.getvalue()
        
        self.assertEqual(result, 1)
        self.assertIn("Error: No message provided", output)
    
    @patch('builtins.input')
    @patch('sys.stdout', new_callable=StringIO)
    def test_cli_encrypt_custom_success(self, mock_stdout, mock_input):
        """Test CLI encrypt with custom key functionality."""
        # Mock user input
        mock_input.side_effect = ["HELLO", "WORLD"]
        
        # Import here to avoid import errors in test discovery
        sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..', 'src'))
        from oneTimepad import cli_encrypt_custom
        
        result = cli_encrypt_custom()
        output = mock_stdout.getvalue()
        
        self.assertEqual(result, 0)
        self.assertIn("Original message: 'HELLO'", output)
        self.assertIn("Custom key: 'WORLD'", output)
        self.assertIn("Ciphertext:", output)
    
    @patch('builtins.input')
    @patch('sys.stdout', new_callable=StringIO)
    def test_cli_encrypt_custom_key_too_short(self, mock_stdout, mock_input):
        """Test CLI encrypt with custom key that's too short."""
        # Mock user input
        mock_input.side_effect = ["HELLO WORLD", "ABC"]
        
        # Import here to avoid import errors in test discovery
        sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..', 'src'))
        from oneTimepad import cli_encrypt_custom
        
        result = cli_encrypt_custom()
        output = mock_stdout.getvalue()
        
        self.assertEqual(result, 1)
        self.assertIn("Error:", output)
    
    @patch('builtins.input')
    @patch('sys.stdout', new_callable=StringIO)
    def test_cli_decrypt_success(self, mock_stdout, mock_input):
        """Test CLI decrypt functionality."""
        # First encrypt a message to get valid ciphertext
        otp = OneTimePad()
        plaintext = "HELLO"
        key = "WORLD"
        ciphertext = otp.encrypt(plaintext, key)
        
        # Mock user input with the ciphertext and key
        mock_input.side_effect = [ciphertext, key]
        
        # Import here to avoid import errors in test discovery
        sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..', 'src'))
        from oneTimepad import cli_decrypt
        
        result = cli_decrypt()
        output = mock_stdout.getvalue()
        
        self.assertEqual(result, 0)
        self.assertIn(f"Ciphertext: '{ciphertext}'", output)
        self.assertIn(f"Key: '{key}'", output)
        self.assertIn("Decrypted plaintext:", output)
    
    @patch('builtins.input')
    @patch('sys.stdout', new_callable=StringIO)
    def test_cli_decrypt_empty_inputs(self, mock_stdout, mock_input):
        """Test CLI decrypt with empty inputs."""
        # Mock user input
        mock_input.side_effect = ["", ""]
        
        # Import here to avoid import errors in test discovery
        sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..', 'src'))
        from oneTimepad import cli_decrypt
        
        result = cli_decrypt()
        output = mock_stdout.getvalue()
        
        self.assertEqual(result, 1)
        self.assertIn("Error: No ciphertext provided", output)
    
    def test_argument_parser_creation(self):
        """Test that the argument parser is created correctly."""
        # Import here to avoid import errors in test discovery
        sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..', 'src'))
        from oneTimepad import create_parser
        
        parser = create_parser()
        
        # Test that parser exists and has expected arguments
        self.assertIsNotNone(parser)
        
        # Test parsing valid arguments
        args_encrypt = parser.parse_args(['--encrypt'])
        self.assertTrue(args_encrypt.encrypt)
        
        args_decrypt = parser.parse_args(['-d'])
        self.assertTrue(args_decrypt.decrypt)
        
        args_custom = parser.parse_args(['--encrypt-custom'])
        self.assertTrue(args_custom.encrypt_custom)
    
    def test_mutually_exclusive_args(self):
        """Test that mutually exclusive arguments work correctly."""
        # Import here to avoid import errors in test discovery
        sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..', 'src'))
        from oneTimepad import create_parser
        
        parser = create_parser()
        
        # Test that conflicting arguments raise an error
        with self.assertRaises(SystemExit):
            parser.parse_args(['--encrypt', '--decrypt'])


if __name__ == '__main__':
    unittest.main()
