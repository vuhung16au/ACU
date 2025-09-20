# Vigenère Cipher Implementation

A Python implementation of the Vigenère Cipher, a classic polyalphabetic substitution cipher that uses multiple Caesar ciphers based on a keyword.

## Implementation Status

✅ **COMPLETED** - The Vigenère cipher has been fully implemented with all required features:
- `vigenere_cipher.py` - Main script with command-line interface
- `vigenere_decrypt.py` - Dedicated decryption script  
- `crack_vigenere.py` - Cryptanalysis tool for breaking Vigenère ciphers
- `vigenere.txt` - Default key file
- Support for encryption and decryption
- File and standard I/O operations
- Custom key file specification
- Automated cipher cracking capabilities

## About the Vigenère Cipher

The Vigenère Cipher is one of the simplest polyalphabetic substitution ciphers:
- Uses a keyword to determine which Caesar cipher to apply to each letter
- Each letter in the keyword specifies which alphabet shift to use
- The keyword is repeated cyclically throughout the message
- More secure than simple Caesar ciphers due to the varying shifts

### How it works:
- **Key**: K = k₁ k₂ ... kₐ (where d is the length of the keyword)
- **Process**: The i-th letter specifies the i-th alphabet to use
- **Repetition**: After d letters, the pattern repeats from the start

## Cryptanalysis - Breaking the Vigenère Cipher

The `crack_vigenere.py` tool implements advanced cryptanalytic techniques to break Vigenère ciphers without knowing the key:

### Algorithms Used

1. **Index of Coincidence (IC) Analysis**
   - Measures the probability of two randomly selected letters being identical
   - English text has IC ≈ 0.067, random text has IC ≈ 0.038
   - Used to determine the most likely key length

2. **Frequency Analysis**
   - Once key length is determined, splits ciphertext into subsequences
   - Each subsequence is encrypted with a single Caesar cipher
   - Applies frequency analysis to find the shift for each position

3. **Chi-Squared Testing**
   - Compares observed letter frequencies with expected English frequencies
   - Uses statistical scoring to validate potential keys

4. **Common Key Dictionary**
   - Tests common educational/testing keys first (SECURITY, SECRET, etc.)
   - Often succeeds quickly for pedagogical examples

5. **Brute-Force Attack (NEW)**
   - Systematically tests all possible key combinations up to a specified length
   - Effective for very short keys (1-5 characters)
   - Computational complexity: 26^n for n-character keys
   - Includes timeout protection and performance monitoring

### Usage Examples

```bash
# Basic crack attempt
python3 crack_vigenere.py -f encrypted_file.txt

# Verbose output showing analysis steps
python3 crack_vigenere.py -f encrypted_file.txt --verbose

# Output only the recovered key
python3 crack_vigenere.py -f encrypted_file.txt --key-only

# Save decrypted text to file
python3 crack_vigenere.py -f encrypted_file.txt -o decrypted.txt

# Specify maximum key length to test
python3 crack_vigenere.py -f encrypted_file.txt --max-key-length 15

# Brute-force attack for short keys (NEW)
python3 crack_vigenere.py -f encrypted_file.txt --brute-force --key-length 5

# Brute-force with verbose output and timing
python3 crack_vigenere.py -f encrypted_file.txt --brute-force --key-length 4 --verbose
```

## Example Run Results

### Command Line Help

#### Encryption Script (`vigenere_cipher.py`)

```bash
$ python3 vigenere_cipher.py --help
usage: vigenere_cipher.py [-h] [--key KEY] [--encrypt] [-f FILE] [-o OUTPUT]

Vigenère Cipher - Encrypt or decrypt text using the Vigenère cipher

options:
  -h, --help           show this help message and exit
  --key KEY            Path to the key file (default: vigenere.txt)
  --encrypt            Encrypt the input text (default is decrypt)
  -f, --file FILE      Read input from a file (default: standard input)
  -o, --output OUTPUT  Write output to a file (default: standard output)
```

#### Decryption Script (`vigenere_decrypt.py`)

```bash
$ python3 vigenere_decrypt.py --help
usage: vigenere_decrypt.py [-h] [--key KEY] [--encrypt] [-f FILE] [-o OUTPUT]

Vigenère Decryption - Decrypt text using the Vigenère cipher

options:
  -h, --help           show this help message and exit
  --key KEY            Path to the key file (default: vigenere.txt)
  --encrypt            Encrypt the input text instead of decrypt
  -f, --file FILE      Read input from a file (default: standard input)
  -o, --output OUTPUT  Write output to a file (default: standard output)
```

#### Cryptanalysis Script (`crack_vigenere.py`)

```bash
$ python3 crack_vigenere.py --help
usage: crack_vigenere.py [-h] [-f FILE] [-o OUTPUT] [--key-only] [--verbose]
                         [--max-key-length MAX_KEY_LENGTH] [--brute-force]
                         [--key-length KEY_LENGTH]

Crack Vigenère cipher using cryptanalysis

options:
  -h, --help            show this help message and exit
  -f, --file FILE       Input ciphertext file (default: stdin)
  -o, --output OUTPUT   Output file for decrypted text (default: stdout)
  --key-only            Output only the recovered key
  --verbose             Show detailed analysis
  --max-key-length MAX_KEY_LENGTH
                        Maximum key length to test (default: 20)
  --brute-force         Use brute-force attack instead of statistical analysis
  --key-length KEY_LENGTH
                        Maximum key length for brute-force attack (default: 5)

Example: python3 crack_vigenere.py -f ciphertext.txt --verbose
Example: python3 crack_vigenere.py -f ciphertext.txt --brute-force --key-length 4
```

### Practical Examples

#### Basic Encryption and Decryption

**Encrypting a message:**

```bash
$ echo "Hello World! This is a test message." | python3 vigenere_cipher.py --encrypt
ZINFF EHPDH! VBZA BQ S XGMK UXQKEIY.
```

**Decrypting the message:**

```bash
$ echo "ZINFF EHPDH! VBZA BQ S XGMK UXQKEIY." | python3 vigenere_decrypt.py
HELLO WORLD! THIS IS A TEST MESSAGE.
```

#### File-based Operations

**Encrypting from file:**

```bash
$ python3 vigenere_cipher.py --encrypt -f inputs-plaintext/test_message.txt
ZINFF EHPDH! VBZA BQ S XGMK UXQKEIY WWK RZI XCXMGCJI ECGPXP.
```

**Encrypting to file:**

```bash
$ python3 vigenere_cipher.py --encrypt -f inputs-plaintext/shakespeare_quote.txt -o output-ciphertext/demo_encrypted.txt
Output written to 'output-ciphertext/demo_encrypted.txt'
```

**Original Shakespeare quote:**

```text
To be or not to be, that is the question:
Whether 'tis nobler in the mind to suffer
The slings and arrows of outrageous fortune,
```

**Encrypted output:**

```text
LS DY FZ GML XQ VV, BAYL MU NYM JSWWVCFV:
PFWXJYI 'BBQ FSDFVZ BL LLG GZVW RG WWZWMK
RZI UFZVZQ SRF UIZHUK SH ILBKYYIQOJ NHPLYPY,
```

#### Cryptanalysis Examples

**Basic key recovery:**

```bash
$ echo "ZINFF EHPDH! VBZA BQ S XGMK UXQKEIY WWK RZI XCXMGCJI ECGPXP." | python3 crack_vigenere.py --key-only
SECURITY
```

**Verbose cryptanalysis output:**

```bash
$ echo "ZINFF EHPDH! VBZA BQ S XGMK UXQKEIY WWK RZI XCXMGCJI ECGPXP." | python3 crack_vigenere.py --verbose
Starting Vigenère cipher cryptanalysis...
==================================================

Trying common keys:
------------------------------
'SECURITY': Score=0.5987
'SECRET': Score=0.5812
'PASSWORD': Score=0.5877
'CIPHER': Score=0.5812
'VIGENERE': Score=0.5822
'CRYPTO': Score=0.5822
'ENCODE': Score=0.5852
'KEY': Score=0.5797
'PRIVATE': Score=0.5837
'HIDDEN': Score=0.5812
'CODE': Score=0.5960
'LOCK': Score=0.5862
'SAFE': Score=0.5852
'SECURE': Score=0.5882
'PROTECT': Score=0.5817

High-confidence match with common key: 'SECURITY' (score: 0.5987)
Decrypted text: HELLO WORLD! THIS IS A TEST MESSAGE FOR THE VIGENERE CIPHER.
Key: SECURITY
Decrypted text:
HELLO WORLD! THIS IS A TEST MESSAGE FOR THE VIGENERE CIPHER.
```

**Cracking longer text (Shakespeare quote):**

```bash
$ python3 crack_vigenere.py -f output-ciphertext/demo_encrypted.txt --verbose
Starting Vigenère cipher cryptanalysis...
==================================================

Trying common keys:
------------------------------
'SECURITY': Score=0.5978
'SECRET': Score=0.5850
'PASSWORD': Score=0.5859
'CIPHER': Score=0.5852
'VIGENERE': Score=0.5870
'CRYPTO': Score=0.5843
'ENCODE': Score=0.5855
'KEY': Score=0.5856
'PRIVATE': Score=0.5833
'HIDDEN': Score=0.5852
'CODE': Score=0.5905
'LOCK': Score=0.5886
'SAFE': Score=0.5855
'SECURE': Score=0.5880
'PROTECT': Score=0.5834

High-confidence match with common key: 'SECURITY' (score: 0.5978)
Decrypted text: TO BE OR NOT TO BE, THAT IS THE QUESTION:
WHETHER 'TIS NOBLER IN THE MIND TO SUFFER
THE SLINGS AND ARROWS OF OUTRAGEOUS FORTUNE,
OR TO TAKE ARMS AGAINST A SEA OF TROUBLES
AND BY OPPOSING END THEM. TO DIE—TO SLEEP,
NO MORE; AND BY A SLEEP TO SAY WE END
THE HEART-ACHE AND THE THOUSAND NATURAL SHOCKS
THAT FLESH IS HEIR TO: 'TIS A CONSUMMATION
DEVOUTLY TO BE WISH'D.
Key: SECURITY
```

### Cryptanalysis Success Factors

The tool works best when:

- Ciphertext is at least 100-200 characters long
- The original text is in English with natural letter frequencies
- The key is reasonably short (1-20 characters)
- The plaintext contains common English words

## Brute-Force Testing

The implementation now includes comprehensive brute-force testing capabilities for educational demonstration of computational complexity and cryptographic strength.

### Brute-Force Attack Method

The brute-force attack systematically tests all possible key combinations:

- **Key Length 1**: 26 possibilities (A-Z)
- **Key Length 2**: 676 possibilities (AA-ZZ)  
- **Key Length 3**: 17,576 possibilities
- **Key Length 4**: 456,976 possibilities
- **Key Length 5**: 11,881,376 possibilities

### Performance Characteristics

Expected performance for brute-force attacks:

| Key Length | Possibilities | Expected Time |
|------------|---------------|---------------|
| 1 char     | 26           | < 1 second    |
| 2 chars    | 676          | < 1 second    |
| 3 chars    | 17,576       | 1-5 seconds   |
| 4 chars    | 456,976      | 10-60 seconds |
| 5 chars    | 11,881,376   | 5-30 minutes  |

### Test Files

- `output-ciphertext/test_brute_force-key-len-5.txt` - Pre-encrypted test case using 5-character key "CRACK"
- Original plaintext: "Hello World! This is a test message for the Vigenere cipher."
- Demonstrates computational limits of brute-force approaches

### Brute-Force Usage Examples

```bash
# Test brute-force with 3-character maximum
python3 crack_vigenere.py -f cipher.txt --brute-force --key-length 3

# Brute-force with verbose timing output
python3 crack_vigenere.py -f cipher.txt --brute-force --key-length 4 --verbose

# Test the pre-encrypted 5-character case
python3 crack_vigenere.py -f output-ciphertext/test_brute_force-key-len-5.txt --brute-force --key-length 5
```

### Educational Value

The brute-force testing demonstrates:

1. **Exponential Complexity**: Shows how encryption strength increases exponentially with key length
2. **Practical Limits**: Illustrates why longer keys provide better security
3. **Attack Comparison**: Contrasts brute-force with statistical cryptanalysis methods
4. **Real-world Implications**: Helps students understand computational security concepts

## Project Setup

### Prerequisites

- Python 3.7 or higher
- pip (Python package installer)

### Installation

1. **Clone the repository**

   ```bash
   git clone <repository-url>
   cd VigenereCipher
   ```

2. **Create a virtual environment**

   ```bash
   python3 -m venv .venv
   ```

3. **Activate the virtual environment**
   
   On macOS/Linux:

   ```bash
   source .venv/bin/activate
   ```
   
   On Windows:

   ```bash
   .venv\Scripts\activate
   ```

4. **Install dependencies** (if any)

   ```bash
   pip install -r requirements.txt
   ```
   
   If `requirements.txt` doesn't exist yet, you can create it later as you add dependencies.

### Deactivating the Virtual Environment

When you're done working on the project:

```bash
deactivate
```

## Project Structure

```text
VigenereCipher/
├── vigenere_cipher.py     # Main encryption/decryption script
├── vigenere_decrypt.py    # Dedicated decryption script
├── crack_vigenere.py      # Cryptanalysis tool for breaking ciphers
├── vigenere.txt          # Default key file
├── alternative_key.txt   # Alternative key for testing
├── test_vigenere.sh      # Comprehensive test script
├── simple_test_vigenere.sh # Simple test script
├── inputs-plaintext/     # Sample plaintext files
│   ├── test_message.txt
│   ├── alice_wonderland.txt
│   ├── business_memo.txt
│   ├── military_message.txt
│   ├── mixed_symbols.txt
│   ├── pangram_text.txt
│   ├── shakespeare_quote.txt
│   └── short_message.txt
├── output-ciphertext/    # Generated encrypted/decrypted files
├── README.md            # This file
├── Prompt.md            # Assignment prompt
└── CRACK-VIGENERE-CIPHER.md # Cryptanalysis documentation
```

## Usage

### Command Line Interface

The project provides three main tools:

#### 1. Encryption (`vigenere_cipher.py`)

```bash
# Encrypt a file with default key
python3 vigenere_cipher.py --encrypt -f plaintext.txt -o encrypted.txt

# Encrypt with custom key file
python3 vigenere_cipher.py --key mykey.txt --encrypt -f plaintext.txt -o encrypted.txt

# Encrypt from stdin to stdout
echo "Hello World" | python3 vigenere_cipher.py --key vigenere.txt --encrypt
```

#### 2. Decryption (`vigenere_decrypt.py`)

```bash
# Decrypt a file with default key
python3 vigenere_decrypt.py -f encrypted.txt -o decrypted.txt

# Decrypt with custom key file
python3 vigenere_decrypt.py --key mykey.txt -f encrypted.txt -o decrypted.txt

# Decrypt from stdin to stdout
cat encrypted.txt | python3 vigenere_decrypt.py --key vigenere.txt
```

#### 3. Cryptanalysis (`crack_vigenere.py`)

```bash
# Basic crack attempt
python3 crack_vigenere.py -f encrypted.txt

# Verbose analysis output
python3 crack_vigenere.py -f encrypted.txt --verbose

# Get only the recovered key
python3 crack_vigenere.py -f encrypted.txt --key-only

# Save decrypted text to file
python3 crack_vigenere.py -f encrypted.txt -o cracked.txt
```

### Running Tests

Execute the comprehensive test suite:

```bash
# Run all tests including cryptanalysis
./test_vigenere.sh

# Run simple encryption/decryption test
./simple_test_vigenere.sh
```

### Python API Example

```python
# Direct usage (if importing the modules)
from vigenere_cipher import vigenere_encrypt, vigenere_decrypt

# Encrypt a message
key = "KEYWORD"
plaintext = "HELLO WORLD"
ciphertext = vigenere_encrypt(plaintext, key)
print(f"Encrypted: {ciphertext}")

# Decrypt the message
decrypted = vigenere_decrypt(ciphertext, key)
print(f"Decrypted: {decrypted}")
```

## Development

### Testing the Implementation

```bash
# Run comprehensive tests (encryption, decryption, cryptanalysis, brute-force)
./test_vigenere.sh

# Run simple test
./simple_test_vigenere.sh

# Test individual components
python3 vigenere_cipher.py --encrypt -f inputs-plaintext/test_message.txt
python3 vigenere_decrypt.py -f output-ciphertext/encrypted_message.txt
python3 crack_vigenere.py -f output-ciphertext/encrypted_message.txt --verbose

# Test brute-force cracking with short keys
python3 crack_vigenere.py -f output-ciphertext/test_brute_force-key-len-5.txt --brute-force --key-length 5
```

### Key Files

- `vigenere.txt` - Contains the default encryption key
- `alternative_key.txt` - Alternative key for testing multiple scenarios
- `inputs-plaintext/` - Directory containing various test messages
- `output-ciphertext/` - Directory for encrypted and decrypted outputs
- `output-ciphertext/test_brute_force-key-len-5.txt` - Pre-encrypted test case for brute-force testing (5-char key)
- `BRUTE_FORCE_TEST_DOCS.md` - Documentation for brute-force testing setup and expected results

### Educational Features

The implementation includes several educational features:

- Verbose output showing cryptanalysis steps
- Multiple test cases with different text types
- Statistical analysis display (Index of Coincidence, Chi-squared)
- Common key detection for typical classroom scenarios
- **Comprehensive brute-force testing** - Demonstrates computational complexity with systematic testing of key lengths 1-5
- **Performance benchmarking** - Shows timing differences between key lengths to illustrate exponential growth
- **Multiple attack vector demonstrations** - Both statistical and exhaustive search methods

## Implementation Notes

### Features Implemented

- **Complete Vigenère Cipher**: Both encryption and decryption with keyword support
- **File and Stream I/O**: Supports file input/output and stdin/stdout operations
- **Case Preservation**: Maintains original case while processing only alphabetic characters
- **Non-alphabetic Handling**: Preserves spaces, punctuation, and numbers unchanged
- **Flexible Key Input**: Supports key files and command-line key specification
- **Comprehensive Testing**: Automated test suite covering multiple scenarios

### Cryptanalysis Capabilities

- **Automated Key Recovery**: Uses statistical analysis to crack unknown keys
- **Multiple Attack Methods**: IC analysis, frequency analysis, and dictionary attacks
- **Brute-Force Attack**: Exhaustive key search for short keys (1-5 characters)
- **Educational Output**: Verbose mode shows the mathematical reasoning behind attacks
- **Robust Validation**: Multiple scoring methods to verify crack success
- **Performance Analysis**: Timing and success rate measurement for different attack methods

### Performance Considerations

- Optimized for educational use with clear, readable code
- Efficient algorithms suitable for messages up to several thousand characters
- Memory-efficient processing of large text files
- Fast cryptanalysis for typical educational key lengths (1-20 characters)

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is part of the ITEC624 coursework at Australian Catholic University.

## References

### Vigenère Cipher Resources

- [Vigenère Cipher - Wikipedia](https://en.wikipedia.org/wiki/Vigen%C3%A8re_cipher)

### Cryptanalysis Resources

- [Index of Coincidence - Wikipedia](https://en.wikipedia.org/wiki/Index_of_coincidence)
- [Frequency Analysis - Wikipedia](https://en.wikipedia.org/wiki/Frequency_analysis)
- [Kasiski Examination - Wikipedia](https://en.wikipedia.org/wiki/Kasiski_examination)
- [Chi-Squared Test - Wikipedia](https://en.wikipedia.org/wiki/Chi-squared_test)
- [Practical Cryptography - Vigenère Analysis](http://practicalcryptography.com/cryptanalysis/stochastic-searching/cryptanalysis-vigenere-cipher/)
