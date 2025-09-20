#!/usr/bin/env python3
"""
Demo script for the One-Time Pad cipher implementation.

This script demonstrates various features of the OneTimePad class.
"""

import sys
import os

# Add src directory to path to import our module
sys.path.insert(0, os.path.join(os.path.dirname(__file__), '..', 'src'))

from oneTimepad import OneTimePad


def main():
    """Run demonstrations of the One-Time Pad cipher."""
    print("=" * 50)
    print("One-Time Pad Cipher - Advanced Demo")
    print("=" * 50)
    
    # Create OTP instance
    otp = OneTimePad()
    
    # Demo 1: Classic example
    print("\n🔐 Demo 1: Classic Encryption Example")
    print("-" * 40)
    message = "ATTACK AT DAWN"
    print(f"Secret message: '{message}'")
    
    ciphertext, key = otp.encrypt_with_generated_key(message)
    print(f"Random key:     '{key}'")
    print(f"Ciphertext:     '{ciphertext}'")
    
    decrypted = otp.decrypt(ciphertext, key)
    print(f"Decrypted:      '{decrypted}'")
    print(f"✅ Success: {message.replace(' ', '') == decrypted}")
    
    # Demo 2: Show cryptographic strength
    print("\n🛡️ Demo 2: Cryptographic Strength")
    print("-" * 40)
    message = "TOPSECRET"
    print(f"Message: '{message}'")
    
    # Encrypt same message with different keys
    results = []
    for i in range(5):
        cipher, key = otp.encrypt_with_generated_key(message)
        results.append((key, cipher))
        print(f"Key {i+1}: '{key}' → Cipher: '{cipher}'")
    
    print("Notice: Same message + different keys = completely different ciphers!")
    
    # Demo 3: Perfect secrecy demonstration
    print("\n🔍 Demo 3: Perfect Secrecy")
    print("-" * 40)
    
    # Show that any key can decrypt to any message of same length
    cipher = "XMCKL"
    print(f"Intercepted cipher: '{cipher}'")
    print("Possible plaintexts with different keys:")
    
    test_keys = ["ABCDE", "HELLO", "ZYXWV", "QWERT"]
    for test_key in test_keys:
        possible_plain = otp.decrypt(cipher, test_key)
        print(f"  Key '{test_key}' → '{possible_plain}'")
    
    print("Without the key, ALL possibilities are equally likely!")
    
    # Demo 4: Security warnings
    print("\n⚠️ Demo 4: Security Considerations")
    print("-" * 40)
    
    message = "SECRET"
    key = "DANGER"
    
    print(f"Message: '{message}'")
    print(f"Key: '{key}'")
    
    # First encryption
    cipher1 = otp.encrypt(message, key)
    print(f"First encryption: '{cipher1}'")
    
    # Reusing the same key (NEVER DO THIS!)
    message2 = "ATTACK"
    cipher2 = otp.encrypt(message2, key)
    print(f"Second encryption with SAME key: '{cipher2}'")
    
    print("\n❌ NEVER reuse keys in real applications!")
    print("❌ Key reuse breaks the security of One-Time Pad!")
    
    # Demo 5: Custom alphabet
    print("\n🔤 Demo 5: Custom Alphabet")
    print("-" * 40)
    
    # Numeric alphabet
    numeric_otp = OneTimePad("0123456789")
    numeric_message = "123456"
    numeric_cipher, numeric_key = numeric_otp.encrypt_with_generated_key(numeric_message)
    
    print(f"Numeric message: '{numeric_message}'")
    print(f"Numeric key:     '{numeric_key}'")
    print(f"Numeric cipher:  '{numeric_cipher}'")
    
    decrypted_numeric = numeric_otp.decrypt(numeric_cipher, numeric_key)
    print(f"Decrypted:       '{decrypted_numeric}'")
    
    # Demo 6: Text with mixed characters
    print("\n📝 Demo 6: Real-World Text Processing")
    print("-" * 40)
    
    real_message = "Hello, World! How are you today?"
    print(f"Original text: '{real_message}'")
    
    prepared = otp._prepare_text(real_message)
    print(f"Prepared text: '{prepared}'")
    
    cipher, key = otp.encrypt_with_generated_key(real_message)
    print(f"Key:          '{key}'")
    print(f"Cipher:       '{cipher}'")
    
    decrypted = otp.decrypt(cipher, key)
    print(f"Decrypted:    '{decrypted}'")
    
    print("\nNote: Non-alphabetic characters are filtered out during preparation.")
    
    # Final thoughts
    print("\n" + "=" * 50)
    print("🎓 Key Takeaways:")
    print("=" * 50)
    print("✅ One-Time Pad is theoretically unbreakable when used correctly")
    print("✅ Key must be truly random and as long as the message")
    print("✅ Each key must be used only once (hence 'One-Time')")
    print("✅ Key must be kept completely secret")
    print("❌ Never reuse keys")
    print("❌ Never use predictable keys")
    print("❌ This implementation is for educational purposes only")
    print("=" * 50)


if __name__ == "__main__":
    main()
