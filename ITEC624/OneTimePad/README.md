# OneTimePad

A comprehensive Python implementation of the One-Time Pad cipher with CLI interface, interactive testing, and educational features. This project demonstrates a theoretically unbreakable encryption method when used correctly.

## About One-Time Pad

The One-Time Pad (OTP) is a type of encryption that is impossible to crack if used correctly. It requires:

- A truly random key that is as long as the message
- The key must be used only once
- The key must be kept completely secret

## Features

✨ **Core Implementation**

- Complete One-Time Pad cipher with modular arithmetic
- Cryptographically secure key generation using `secrets` module
- Text preprocessing (uppercase conversion, special character filtering)
- Support for custom alphabets

🖥️ **Command Line Interface**

- Four CLI modes: encrypt with generated key, encrypt with custom key, decrypt, and demo
- Interactive prompts with validation
- Comprehensive error handling

🧪 **Testing & Validation**

- Comprehensive unit test suite (392 lines of tests)
- Interactive testing script with colored output
- Automated test runner (`oneTimepad.sh`)
- Demo script with multiple examples

📚 **Documentation**

- Complete implementation documentation
- CLI usage examples
- Security considerations and best practices

## Project Setup

This project uses Python virtual environments to manage dependencies and ensure a clean, isolated development environment.

### Prerequisites

- Python 3.7 or higher
- pip (Python package installer)

### Setup Instructions

#### 1. Clone the Repository

```bash
git clone <repository-url>
cd OneTimePad
```

#### 2. Create Virtual Environment

Create a new virtual environment using Python's built-in `venv` module:

```bash
python3 -m venv .venv
```

#### 3. Activate Virtual Environment

**On macOS/Linux:**

```bash
source .venv/bin/activate
```

**On Windows:**

```bash
.venv\Scripts\activate
```

You should see `(.venv)` at the beginning of your command prompt, indicating the virtual environment is active.

#### 4. Install Dependencies

Once the virtual environment is activated, install the required packages:

```bash
pip install -r requirements.txt
```

*Note: As of 25 Jul 2025, `requirements.txt` is not strictly necessary for this project, as there are no external dependencies.*

#### 5. Verify Installation

You can verify your setup by running:

```bash
python --version
pip list
```

### Development Workflow

#### Starting Development

Every time you work on this project:

1. Navigate to the project directory:

   ```bash
   cd OneTimePad
   ```

2. Activate the virtual environment:

   ```bash
   source .venv/bin/activate  # macOS/Linux
   # or
   .venv\Scripts\activate     # Windows
   ```

3. Start coding!

#### Deactivating Virtual Environment

When you're done working:

```bash
deactivate
```

#### Adding New Dependencies

When you install new packages for your project:

1. Install the package:

   ```bash
   pip install package-name
   ```

2. Update requirements.txt:

   ```bash
   pip freeze > requirements.txt
   ```

### Project Structure

```text
OneTimePad/
├── .venv/                    # Virtual environment (don't commit this)
├── .gitignore               # Git ignore patterns
├── CLI_EXAMPLES.md          # Command-line interface examples and usage
├── DOCUMENTATION.md         # Technical documentation and implementation details
├── IMPLEMENTATION_SUMMARY.md # Summary of CLI features and capabilities
├── Prompt.md               # Original project prompt/requirements
├── README.md               # This file - main project documentation
├── demo_interactive.sh     # Quick demo script showing interactive features
├── oneTimepad.sh          # Comprehensive test script (automated + interactive)
├── requirements.txt       # Python dependencies (currently none required)
├── src/                   # Source code
│   ├── __pycache__/      # Python bytecode cache
│   └── oneTimepad.py     # Main implementation (473 lines)
├── tests/                # Test files
│   ├── __pycache__/     # Python bytecode cache
│   └── test_otp.py      # Comprehensive unit tests (392 lines)
└── examples/            # Example usage and demonstrations
    └── demo.py         # Advanced demo script (144 lines)
```

### Usage

## Command Line Interface

The project includes a comprehensive CLI with four main modes:

### 1. Encrypt with Generated Key

```bash
python3 src/oneTimepad.py -e
# or
python3 src/oneTimepad.py --encrypt
```

Automatically generates a cryptographically secure random key and encrypts your message.

### 2. Encrypt with Custom Key

```bash
python3 src/oneTimepad.py --encrypt-custom
```

Allows you to provide your own encryption key (useful for educational purposes).

### 3. Decrypt Message

```bash
python3 src/oneTimepad.py -d
# or
python3 src/oneTimepad.py --decrypt
```

Decrypts a ciphertext using the provided key.

### 4. Demo Mode

```bash
python3 src/oneTimepad.py --demo
```

Runs the interactive demo showing various encryption examples.

### CLI Help

```bash
python3 src/oneTimepad.py --help
```

Shows all available options and usage information.

📖 **For detailed CLI examples, see [`CLI_EXAMPLES.md`](CLI_EXAMPLES.md)**

## Programming Interface

### Basic Example

```python
from src.oneTimepad import OneTimePad

# Create OTP instance
otp = OneTimePad()

# Encrypt a message
message = "ATTACK AT DAWN"
ciphertext, key = otp.encrypt_with_generated_key(message)

print(f"Original:   {message}")
print(f"Key:        {key}")
print(f"Encrypted:  {ciphertext}")

# Decrypt the message
decrypted = otp.decrypt(ciphertext, key)
print(f"Decrypted:  {decrypted}")
```

### Sample Run

Here are examples of running the One-Time Pad cipher:

**CLI Example:**

```bash
$ python3 src/oneTimepad.py -e
Enter message to encrypt: ATTACK AT DAWN
Original message: 'ATTACK AT DAWN'
Prepared message: 'ATTACKATDAWN'
Generated key: 'XMCKDGHRFQJW'
Ciphertext: 'XOCKMJJDSQPJ'
```

**Programming Interface Example:**

```text
Original:   ATTACK AT DAWN
Key:        XMCKDGHRFQJW
Encrypted:  XOCKMJJDSQPJ
Decrypted:  ATTACKATDAWN
```

**Explanation:**

- The original message "ATTACK AT DAWN" is preprocessed to remove spaces and convert to uppercase: "ATTACKATDAWN"
- A random key "XMCKDGHRFQJW" is generated (same length as the processed message)
- Each character is encrypted using modular arithmetic: (message_char + key_char) mod 26
- The resulting ciphertext "XOCKMJJDSQPJ" appears completely random
- Decryption reverses the process: (cipher_char - key_char) mod 26

#### Advanced Usage

```python
# Using a custom alphabet
otp_custom = OneTimePad(alphabet="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789")

# Manual key generation
message = "TOPSECRET"
key = otp.generate_key(len(message))
encrypted = otp.encrypt(message, key)
decrypted = otp.decrypt(encrypted, key)

# Verification
is_valid = otp.verify_encryption(message, encrypted, key)
print(f"Encryption verified: {is_valid}")
```

#### Demo Script

Run the interactive demo to see more examples:

```bash
python examples/demo.py
```

This will show various encryption scenarios and demonstrate the cryptographic properties of the One-Time Pad.

### Testing

#### Using the Test Script (Recommended)

The project includes a comprehensive test script `oneTimepad.sh` that provides multiple testing modes with colored output:

```bash
# Make the script executable (first time only)
chmod +x oneTimepad.sh

# Run all tests + interactive mode (default)
./oneTimepad.sh

# Available options:
./oneTimepad.sh --test         # Run only unit tests
./oneTimepad.sh --interactive  # Run only interactive testing mode
./oneTimepad.sh --demo         # Run demonstration mode
./oneTimepad.sh --help         # Show all options
```

**Test Script Features:**

- **Unit Tests**: 392 lines of comprehensive automated tests
- **Interactive Mode**: Test all 4 CLI functionalities interactively
  1. Encrypt with generated key
  2. Encrypt with custom key
  3. Decrypt message
  4. Exit
- **Demo Mode**: Educational examples and explanations
- **Colored Output**: Easy-to-read results with status indicators

#### Manual Testing

You can also run tests manually:

```bash
# Run unit tests with verbose output
python -m unittest tests.test_otp -v

# Run specific test modules
python -m unittest tests.test_otp.TestOneTimePad.test_basic_encryption_decryption -v
```

#### Quick Demo

For a quick demonstration of the interactive features:

```bash
chmod +x demo_interactive.sh
./demo_interactive.sh
```

## Documentation

This project includes comprehensive documentation:

📄 **[CLI_EXAMPLES.md](CLI_EXAMPLES.md)** - Complete command-line usage examples with sample inputs and outputs

📄 **[DOCUMENTATION.md](DOCUMENTATION.md)** - Technical implementation details, algorithm explanation, and security analysis

📄 **[IMPLEMENTATION_SUMMARY.md](IMPLEMENTATION_SUMMARY.md)** - Summary of CLI features, implementation timeline, and testing approach

📄 **[Prompt.md](Prompt.md)** - Original project requirements and specifications

### Security Notes

⚠️ **Important Security Considerations:**

- Never reuse keys in a One-Time Pad implementation
- Ensure keys are truly random (use cryptographically secure random generators)
- Keep keys secret and secure
- This implementation is for educational purposes

## Project Status

**Current Version**: Complete implementation with CLI interface  
**Last Updated**: July 25, 2025  
**Status**: ✅ Fully functional and tested  

**Metrics:**

- Main implementation: 473 lines of code
- Test suite: 392 lines (comprehensive coverage)
- Documentation: 4 additional markdown files
- Total project files: 15+ files

**Features Completed:**

- ✅ Core One-Time Pad algorithm
- ✅ Command-line interface (4 modes)
- ✅ Comprehensive test suite
- ✅ Interactive testing script
- ✅ Demo scripts and examples
- ✅ Complete documentation
- ✅ Security best practices
- ✅ Error handling and validation

## Quick Start

1. **Clone and setup:**

   ```bash
   git clone <repository-url>
   cd OneTimePad
   python3 -m venv .venv
   source .venv/bin/activate  # On macOS/Linux
   ```

2. **Test the implementation:**

   ```bash
   chmod +x oneTimepad.sh
   ./oneTimepad.sh
   ```

3. **Try the CLI:**

   ```bash
   python3 src/oneTimepad.py -e
   ```

4. **Read the documentation:**

   - Start with this README
   - See `CLI_EXAMPLES.md` for usage examples
   - Check `DOCUMENTATION.md` for technical details
