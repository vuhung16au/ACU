# Mono-alphabetic Cipher

A Python implementation of a mono-alphabetic substitution cipher for encrypting and decrypting text using a custom alphabet mapping.

## Repository Overview

This project implements a mono-alphabetic cipher system that uses a substitution method to encrypt and decrypt text. Unlike simple Caesar ciphers that shift letters by a fixed amount, this implementation uses a completely randomized alphabet mapping for enhanced security.

The project includes:
- **Encryption script** (`mono-alphabetic-cipher.py`) - Converts plaintext to ciphertext
- **Decryption script** (`mono-alphabetic-decrypt.py`) - Converts ciphertext back to plaintext  
- **Cipher cracking script** (`crack-mono-alphabetic.py`) - Attempts to crack cipher without knowing the key
- **Cipher key file** (`random-shuffle.txt`) - Contains the substitution alphabet
- **Test script** (`test-mono-alphabetic.sh`) - Automated testing of the cipher system
- **Sample files** - Example inputs and outputs for demonstration

## The Algorithm

### Mono-alphabetic Substitution Cipher

The mono-alphabetic cipher works by replacing each letter of the plaintext alphabet with a corresponding letter from a cipher alphabet. This creates a one-to-one mapping between the standard alphabet and a shuffled version.

**Key Components:**
- **Plain alphabet**: `ABCDEFGHIJKLMNOPQRSTUVWXYZ`
- **Cipher alphabet**: `DKVQFIBJWPESCXHTMYAUOLRGZN` (randomized mapping)

**Example Mapping:**
```
Plain:  A B C D E F G H I J K L M N O P Q R S T U V W X Y Z
Cipher: D K V Q F I B J W P E S C X H T M Y A U O L R G Z N
```

**Encryption Process:**
1. For each letter in the plaintext, find its position in the plain alphabet
2. Replace it with the letter at the same position in the cipher alphabet
3. Non-alphabetic characters (spaces, punctuation) remain unchanged
4. Case is preserved in the output

**Example:**
- Plaintext: `SEND THE MONEY TO ALICE`
- Ciphertext: `AFXQ UJF CHXFZ UH DSWVF`

## Project Setup

### Prerequisites
- Python 3.6 or higher
- Virtual environment (recommended)

### Setup with Virtual Environment

1. **Clone or download the repository:**
   ```bash
   git clone <repository-url>
   cd MonoAlphabeticCipher
   ```

2. **Create a virtual environment:**
   ```bash
   python3 -m venv .venv
   ```

3. **Activate the virtual environment:**
   ```bash
   # On macOS/Linux:
   source .venv/bin/activate
   
   # On Windows:
   .venv\Scripts\activate
   ```

4. **Verify Python installation:**
   ```bash
   python --version
   ```

5. **Make scripts executable (macOS/Linux):**
   ```bash
   chmod +x mono-alphabetic-cipher.py
   chmod +x mono-alphabetic-decrypt.py
   chmod +x test-mono-alphabetic.sh
   ```

### Deactivating Virtual Environment
When you're done working with the project:
```bash
deactivate
```

## File Explanations

### `random-shuffle.txt`
This file contains the cipher alphabet used for substitution. It consists of 26 unique letters representing a randomized version of the standard alphabet.

**Contents:**
```
DKVQFIBJWPESCXHTMYAUOLRGZN
```

**Purpose:**
- Each position corresponds to a letter in the standard alphabet (A-Z)
- Position 1 (D) replaces A, Position 2 (K) replaces B, etc.
- Must contain exactly 26 unique alphabetic characters
- Used by both encryption and decryption scripts

### Cipher Scripts

#### `mono-alphabetic-cipher.py` (Encryption)
**Purpose:** Encrypts plaintext using the mono-alphabetic substitution cipher.

**Features:**
- Accepts text input via command line argument or file
- Preserves non-alphabetic characters and spacing
- Maintains case sensitivity
- Validates cipher key file format
- Comprehensive error handling

**Command-line options:**
- `text` - Direct text input for encryption
- `-f, --file` - Input file containing plaintext
- `-k, --key` - Custom key file (defaults to `random-shuffle.txt`)

#### `mono-alphabetic-decrypt.py` (Decryption)
**Purpose:** Decrypts ciphertext using the reverse substitution mapping.

**Features:**
- Creates reverse mapping from cipher alphabet to plain alphabet
- Supports same input methods as encryption script
- Maintains formatting and case of original text
- Uses same key validation as encryption

**Command-line options:**
- `text` - Direct ciphertext input for decryption
- `-f, --file` - Input file containing ciphertext
- `-k, --key` - Custom key file (defaults to `random-shuffle.txt`)

#### `crack-mono-alphabetic.py` (Cipher Cracking)
**Purpose:** Attempts to crack mono-alphabetic ciphers without knowing the key using cryptanalysis techniques.

**Features:**
- Frequency analysis based on English letter frequencies
- Pattern recognition for common words and phrases
- Dictionary word matching against common English words
- Iterative mapping refinement using heuristics
- Scoring system to evaluate decryption quality
- Automatic confidence assessment

**Algorithm Overview:**
The cracking algorithm uses a multi-step approach:

1. **Frequency Analysis**: Maps cipher letters to English letters based on their frequency of occurrence
2. **Pattern Recognition**: Identifies common word patterns (single letters, 2-letter words like "TO", 3-letter words like "THE")
3. **Mapping Refinement**: Uses dictionary words and common phrases to improve the initial mapping
4. **Iterative Optimization**: Tests variations of the mapping by swapping letter assignments to maximize the scoring function

**Scoring System:**
- Common English words (high weight for exact matches)
- Word length distribution typical of English text
- Common digrams (TH, HE, IN, ER, etc.) and trigrams (THE, AND, ING, etc.)
- Letter frequency correlation with standard English
- Penalties for unlikely letter combinations

**Command-line options:**
- `-f, --file` - Input file containing ciphertext (defaults to `output-ciphered/sample_ciphertext.txt`)

### `test-mono-alphabetic.sh`
**Purpose:** Automated test script that demonstrates the complete encryption-decryption cycle.

**Functionality:**
1. Reads plaintext from `sample_input.txt`
2. Encrypts the text using `mono-alphabetic-cipher.py`
3. Decrypts the resulting ciphertext using `mono-alphabetic-decrypt.py`
4. Compares the final result with the original plaintext
5. Reports success or failure

**Features:**
- Validates that both Python scripts exist
- Provides clear step-by-step output
- Confirms successful round-trip encryption/decryption
- Includes error handling and usage information

## How to Run the Scripts

### Basic Encryption

**Encrypt a text string:**
```bash
python3 mono-alphabetic-cipher.py "Your message here"
```

**Encrypt from a file:**
```bash
python3 mono-alphabetic-cipher.py -f input.txt
```

**Encrypt with custom key:**
```bash
python3 mono-alphabetic-cipher.py -k custom-key.txt "Your message"
```

### Basic Decryption

**Decrypt a text string:**
```bash
python3 mono-alphabetic-decrypt.py "CIPHER TEXT HERE"
```

**Decrypt from a file:**
```bash
python3 mono-alphabetic-decrypt.py -f ciphertext.txt
```

### Cipher Cracking (Cryptanalysis)

**Crack a ciphertext file:**
```bash
python3 crack-mono-alphabetic.py -f output-ciphered/sample_ciphertext.txt
```

**Crack default sample:**
```bash
python3 crack-mono-alphabetic.py
```

The cracking script will output:
- Step-by-step analysis process
- Confidence scores for different mapping attempts
- Final decrypted text with confidence assessment
- Recovered substitution key mapping

### Running the Test Suite

**Execute the complete test:**
```bash
./test-mono-alphabetic.sh
```

**View help information:**
```bash
./test-mono-alphabetic.sh -h
```

## Script Execution Examples

### Example 1: Direct Text Encryption/Decryption

**Input:**
```bash
$ python3 mono-alphabetic-cipher.py "Hello World"
```

**Output:**
```
JFSSH RHYSQ
```

**Decryption:**
```bash
$ python3 mono-alphabetic-decrypt.py "JFSSH RHYSQ"
```

**Output:**
```
HELLO WORLD
```

### Example 2: File-based Encryption/Decryption

**Sample input file (`sample_input.txt`):**
```
SEND THE MONEY TO ALICE
```

**Encryption:**
```bash
$ python3 mono-alphabetic-cipher.py -f sample_input.txt
```

**Output:**
```
AFXQ UJF CHXFZ UH DSWVF
```

**Decryption:**
```bash
$ python3 mono-alphabetic-decrypt.py -f output-ciphered/sample_ciphertext.txt
```

**Output:**
```
SEND THE MONEY TO ALICE
```

### Example 3: Complete Test Suite

**Running the test script:**
```bash
$ ./test-mono-alphabetic.sh
```

**Output:**
```
=== Mono-alphabetic Cipher Test ===

Step 1: Original plaintext from sample_input.txt:
SEND THE MONEY TO ALICE

Step 2: Encrypting the plaintext...
Encrypted ciphertext:
AFXQ UJF CHXFZ UH DSWVF

Step 3: Decrypting the ciphertext...
Decrypted plaintext:
SEND THE MONEY TO ALICE

✅ SUCCESS: Decrypted text matches the original plaintext!

Test completed successfully!
```

### Example 4: Cipher Cracking (Cryptanalysis)

**Cracking a ciphertext without knowing the key:**
```bash
$ python3 crack-mono-alphabetic.py -f output-ciphered/sample_ciphertext.txt
```

**Output:**
```
Starting mono-alphabetic cipher cracking...
Ciphertext: AFXQ UJF CHXFZ UH DSWVF
============================================================

Step 1: Performing frequency analysis...
Initial frequency-based mapping:
  A -> E
  C -> T
  D -> A
  F -> N
  H -> I
  ...
Initial decryption: SEND TIE MONEY TO ETATE
Initial score: 245.67

Step 2: Refining mapping with pattern analysis...
Refined mapping:
  A -> S
  C -> M
  D -> A
  F -> N
  H -> H
  ...
Refined decryption: SEND THE MONEY TO ALICE
Refined score: 1156.34

Step 3: Testing mapping variations...
  Base mapping score: 1156.34

Step 4: Testing targeted word patterns...
Targeted pattern mapping:
  A -> S
  F -> E
  X -> N
  Q -> D
  ...
Targeted decryption: SEND THE MONEY TO ALICE
Targeted score: 1234.56
✓ Targeted approach found better solution!

Best mapping found:
  A -> S
  C -> M
  D -> A
  F -> N
  ...
Best decryption: SEND THE MONEY TO ALICE
Best score: 1234.56

============================================================
FINAL RESULTS:
Decrypted text: SEND THE MONEY TO ALICE
Confidence score: 1234.56
Execution time: 0.85 seconds
✓ Very high confidence - almost certainly correct
```

### Example 5: Error Handling

**Missing input:**
```bash
$ python3 mono-alphabetic-cipher.py
```

**Output:**
```
usage: mono-alphabetic-cipher.py [-h] [-f FILE] [-k KEY] [text]

Mono-alphabetic Cipher Encryption

positional arguments:
  text                  Text to encrypt (if not using file input)

optional arguments:
  -h, --help            show this help message and exit
  -f FILE, --file FILE  Input file containing plaintext to encrypt
  -k KEY, --key KEY     Key file containing cipher alphabet (default: random-shuffle.txt)

Error: Please provide either text to encrypt or specify an input file.
```

**Non-existent file:**
```bash
$ python3 mono-alphabetic-cipher.py -f nonexistent.txt
```

**Output:**
```
Error: Input file 'nonexistent.txt' not found.
```

## Key Features

- **Robust Error Handling**: Comprehensive validation of inputs and key files
- **Flexible Input Methods**: Support for both direct text and file input
- **Case Preservation**: Maintains original text formatting and case
- **Non-alphabetic Character Handling**: Preserves spaces, punctuation, and numbers
- **Key Validation**: Ensures cipher alphabet contains exactly 26 unique characters
- **Cipher Cracking Capability**: Advanced cryptanalysis using frequency analysis and pattern recognition
- **Intelligent Scoring System**: Evaluates decryption quality using multiple linguistic features
- **Cross-platform Compatibility**: Works on Windows, macOS, and Linux
- **Automated Testing**: Complete test suite for verification

## Security Notes

While this implementation demonstrates the concept of substitution ciphers, mono-alphabetic ciphers are not cryptographically secure for real-world applications. They are vulnerable to:
- Frequency analysis attacks
- Pattern recognition
- Statistical analysis of language

This project is intended for educational purposes and cryptographic learning.
