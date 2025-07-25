# Test Data Set for Mono-Alphabetic Cipher

This directory contains a comprehensive set of test data for testing the mono-alphabetic cipher implementation.

## Plaintext Files (input-plaintext/)

### test1_simple.txt
- **Content**: `SEND THE MONEY TO ALICE`
- **Description**: Simple, short message with common words
- **Use case**: Basic functionality testing

### test2_pangram.txt
- **Content**: `THE QUICK BROWN FOX JUMPS OVER THE LAZY DOG`
- **Description**: Pangram containing all letters of the alphabet
- **Use case**: Testing all character mappings

### test3_mixed.txt
- **Content**: Mixed case text with numbers, punctuation, and special characters
- **Description**: Real-world text with various character types
- **Use case**: Testing cipher behavior with non-alphabetic characters

### test4_long.txt
- **Content**: Long paragraph about cryptography and substitution ciphers
- **Description**: Extended text for frequency analysis
- **Use case**: Testing with larger data sets

### test5_single.txt
- **Content**: `a`
- **Description**: Single character test
- **Use case**: Edge case testing

### test6_multiline.txt
- **Content**: Military-style messages across multiple lines
- **Description**: Multi-line text with newlines
- **Use case**: Testing line break handling

## Ciphertext Files (output-ciphered/)

Each plaintext file has a corresponding ciphertext file encrypted using:
- **Cipher script**: `mono-alphabetic-cipher.py`
- **Key file**: `random-shuffle.txt`
- **Key mapping**: `ABCDEFGHIJKLMNOPQRSTUVWXYZ` → `DKVQFIBJWPESCXHTMYAUOLRGZN`

### Generated Files:
- `test1_simple_cipher.txt` → `AFXQ UJF CHXFZ UH DSWVF`
- `test2_pangram_cipher.txt` → `UJF MOWVE KYHRX IHG POCTA HLFY UJF SDNZ QHB`
- `test3_mixed_cipher.txt` → Mixed content with preserved punctuation and numbers
- `test4_long_cipher.txt` → Long encrypted paragraph
- `test5_single_cipher.txt` → `D`
- `test6_multiline_cipher.txt` → Multi-line encrypted text

## Usage Examples

### Encrypting plaintext:
```bash
python3 mono-alphabetic-cipher.py -f input-plaintext/test1_simple.txt
```

### Decrypting ciphertext:
```bash
python3 mono-alphabetic-decrypt.py -f output-ciphered/test1_simple_cipher.txt
```

### Cracking ciphertext:
```bash
python3 crack-mono-alphabetic.py -f output-ciphered/test1_simple_cipher.txt
```

## Key Information

The cipher uses the substitution key from `random-shuffle.txt`:
- **Plain alphabet**: `ABCDEFGHIJKLMNOPQRSTUVWXYZ`
- **Cipher alphabet**: `DKVQFIBJWPESCXHTMYAUOLRGZN`

## Test Validation

To verify the test data integrity:
1. Encrypt a plaintext file and compare with the corresponding ciphertext file
2. Decrypt a ciphertext file and compare with the original plaintext
3. Use the test script: `./test-mono-alphabetic.sh`
