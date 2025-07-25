# Caesar Cipher Implementation and Analysis

This repository provides a comprehensive implementation of the Caesar cipher cryptographic algorithm, along with various attack methods to demonstrate its vulnerabilities. The project includes encryption, decryption, brute force attacks, frequency analysis, and automated testing tools.

## Overview

This repo demonstrates:

- **Classical Ciphers**
  - **Substitution**: Where letters of plaintext are replaced by other letters, numbers, or symbols
  - **Transposition**: The plaintext is encrypted by changing the positions of the letters and/or symbols, by some sort of permutation

## Caesar Cipher Algorithm

The Caesar cipher is one of the simplest and most widely known encryption techniques. It is a substitution cipher where each letter in the plaintext is shifted a certain number of places down the alphabet.

### Mathematical Representation

Mathematically, each letter is assigned a number:

- a/A → 0, b/B → 1, c/C → 2, ..., z/Z → 25

The Caesar cipher formula:

- **Encryption**: `Cipher text = E(p) = (p + k) mod 26`
- **Decryption**: `Plain text = D(C) = (C – k) mod 26`

Where `k` is the shift key (typically 3 in classical implementations).

### Example

**Input:** "Send the money to Alice"  
**Key:** 3  
**Output:** "VhqgwkhprqhbwrDolfh"

*Note: Spaces and non-alphabetic characters are removed during encryption.*

## Project Files

### Python Scripts

#### 1. `caesar_cipher.py` - Encryption Script

Encrypts plaintext using the Caesar cipher algorithm.

**Features:**

- Text and file input modes
- Customizable shift key (default: 3)
- Preserves case but removes spaces and non-alphabetic characters
- File output with `.cae` extension

**Usage:**

```bash
# Text input
python3 caesar_cipher.py "Hello World" 5
# Output: MjqqtBtwqi

# File input
python3 caesar_cipher.py -f input.txt 5
# Creates: input.txt.cae

# Default key (3)
python3 caesar_cipher.py "Send the money to Alice"
# Output: VhqgwkhprqhbwrDolfh
```

#### 2. `caesar_decrypt.py` - Decryption Script

Decrypts Caesar cipher text when the key is known.

**Features:**

- Text and file input modes
- Customizable shift key (default: 3)
- Preserves spaces and non-alphabetic characters during decryption

**Usage:**

```bash
# Text input
python3 caesar_decrypt.py "MjqqtBtwqi" 5
# Output: HelloWorld

# File input
python3 caesar_decrypt.py -f encrypted.txt.cae 5

# Default key (3)
python3 caesar_decrypt.py "VhqgwkhprqhbwrDolfh"
# Output: SendthemoneytoAlice
```

#### 3. `brute_force_caesar.py` - Brute Force Attack

Attempts to break Caesar cipher by trying all possible keys (1-25).

**Features:**

- Displays all possible decryptions
- Text and file input modes
- Easy visual identification of correct plaintext

**Usage:**

```bash
python3 brute_force_caesar.py "VhqgwkhprqhbwrDolfh"
```

**Sample Output:**

```text
Brute-forcing Caesar cipher for: "VhqgwkhprqhbwrDolfh"
============================================================
Key  | Decrypted Text
------------------------------------------------------------
1    | UgpfvjgoqpgavqCnkeg
2    | TfoeuifnpofzupBmjdf
3    | SendthemoneytoAlice
4    | RdmcsgdlnmdxsnZkhbd
5    | QclbrfckmlcwrmYjgac
...
```

#### 4. `frequency_analysis_caesar.py` - Statistical Attack

Uses statistical analysis of letter frequencies to determine the most likely decryption key.

**Features:**

- Chi-squared statistical analysis
- Compares against English letter frequency patterns
- Ranks all possible keys by likelihood
- Confidence scoring system

**Usage:**

```bash
python3 frequency_analysis_caesar.py "VhqgwkhprqhbwrDolfh"
```

**Sample Output:**

```text
Frequency Analysis Attack on Caesar Cipher
============================================================
Analyzing ciphertext: VhqgwkhprqhbwrDolfh
Ciphertext length: 19 letters

Results ranked by frequency analysis (lower score = more English-like):
------------------------------------------------------------
Rank Key  Score      Decrypted Text
------------------------------------------------------------
1    3    43.28      SENDTHEMONEYTOALICE ← BEST MATCH
2    14   283.01     HTCSIWTBDCTNIDPAXRT
3    9    451.80     MYHXNBYGIHYSNIUFCWY
...

Analysis Summary:
------------------------------
Most likely key: 3
Most likely plaintext: SENDTHEMONEYTOALICE
Confidence score: 43.28
Confidence level: HIGH
```

### Shell Script

#### `test_caesar_cipher_decrypt.sh` - Comprehensive Test Suite

Automated testing script that validates the entire Caesar cipher implementation through round-trip testing.

**Features:**

- Tests encryption → decryption → verification workflow
- Supports text and file input modes
- Runs brute force verification
- Compares original and decrypted text
- Comprehensive error handling

**Usage:**

```bash
# Test with default text
./test_caesar_cipher_decrypt.sh

# Test with custom text
./test_caesar_cipher_decrypt.sh "Your custom message"

# Test with file input
./test_caesar_cipher_decrypt.sh -f test-data/short_english.txt

# Show help
./test_caesar_cipher_decrypt.sh --help
```

**Sample Output:**

```text
=== Caesar Cipher Test Script ===

Using default text input mode
Original text: "Send the money to Alice"
Key: 3

Step 1: Encrypting text...
Encrypted text: "VhqgwkhprqhbwrDolfh"

Step 2: Decrypting text...
Decrypted text: "SendthemoneytoAlice"

Step 3: Comparing texts...
Original text (spaces removed): "SendthemoneytoAlice"
Decrypted text: "SendthemoneytoAlice"

✅ SUCCESS: Decrypted text matches original text (with spaces removed)!
The Caesar cipher encryption/decryption process works correctly.

Step 4: Testing brute force decryption...
...

✅ SUCCESS: Brute force attack correctly identified key 3!
All Caesar cipher tests passed successfully.
```

## Development Environment

**Requirements:**

- Python 3.9 or later
- No external dependencies required

**Setup:**

```bash
python3 -m venv .venv
source .venv/bin/activate
# No additional packages needed - uses only Python standard library
```

## Test Data

The `test-data/` directory contains various sample files for testing:

- `short_english.txt` - Short English text samples
- `medium_english.txt` - Medium-length English text
- `long_english.txt` - Longer text for frequency analysis
- `shakespeare_quote.txt` - Classic literature sample
- `encrypted_shift*.txt` - Pre-encrypted samples with different keys

## Security Analysis

This implementation demonstrates why the Caesar cipher is **not secure** for modern use:

1. **Small Key Space**: Only 25 possible keys make brute force trivial
2. **Pattern Preservation**: Letter frequency patterns remain detectable
3. **No Key Management**: Simple shift values are easily guessed
4. **Deterministic**: Same plaintext always produces same ciphertext

The included attack methods (brute force and frequency analysis) show how easily Caesar ciphers can be broken, making this purely an educational exercise in classical cryptography.
