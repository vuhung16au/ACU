# OneTimePad Project Documentation

## Quick Start

```bash
# Clone and setup
cd OneTimePad
python3 -m venv .venv
source .venv/bin/activate  # On macOS/Linux

# Run the main program
python3 src/oneTimepad.py

# Run the demo
python3 examples/demo.py

# Run tests
python3 -m unittest tests.test_otp -v
```

## API Usage

```python
from src.oneTimepad import OneTimePad

# Create instance
otp = OneTimePad()

# Basic encryption/decryption
ciphertext, key = otp.encrypt_with_generated_key("Hello World")
plaintext = otp.decrypt(ciphertext, key)

# Custom key
key = "MYSECRETKEY"
cipher = otp.encrypt("HELLO", key)
decrypted = otp.decrypt(cipher, key)

# Custom alphabet
numeric_otp = OneTimePad("0123456789")
```

## Security Guidelines

1. **Never reuse keys** - Each key must be used only once
2. **Use cryptographically secure random keys** - The implementation uses `secrets` module
3. **Keep keys secret** - Store and transmit keys securely
4. **Key length** - Must be at least as long as the message
5. **Educational purposes** - This implementation is for learning, not production use

## File Structure

- `src/oneTimepad.py` - Main implementation
- `examples/demo.py` - Comprehensive demonstration
- `tests/test_otp.py` - Unit tests
- `requirements.txt` - Dependencies (none required for core functionality)
- `README.md` - Project overview and setup instructions

## Features

- ✅ Theoretically unbreakable encryption (when used correctly)
- ✅ Cryptographically secure key generation
- ✅ Text preprocessing (removes non-alphabetic characters)
- ✅ Custom alphabet support
- ✅ Comprehensive error handling
- ✅ Interactive command-line interface
- ✅ Extensive unit tests
- ✅ Educational demonstrations
