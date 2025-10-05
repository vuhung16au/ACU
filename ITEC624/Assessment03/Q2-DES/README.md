# DES Encryption Implementation - Assessment 03 Q2

This directory contains the solution for Assessment 03, Question 2, which implements simplified DES (Data Encryption Standard) key generation and data encryption procedures.

## Files

- `Q2-des.py` - Python implementation of the DES encryption algorithm
- `Q2-des.md` - Detailed explanation of the problem and mathematical procedures
- `README.md` - This file explaining the code and usage

## What the Code Does

The Python script implements a simplified version of the first round of DES encryption with the following key features:

### Key Generation Process
1. **Parity Bit Removal**: Strips the 8th, 16th, 24th, 32nd, 40th, 48th, 56th, and 64th bits from a 64-bit key
2. **Splitting**: Divides the resulting 56-bit sequence into two 28-bit halves
3. **Rotation**: Performs left circular rotation by 1 position on both halves
4. **Permutation**: Applies a specific permutation table to contract from 56 bits to 48 bits

### Data Encryption Process
1. **Message Expansion**: Expands a 32-bit message to 48 bits using a predefined expansion table
2. **XOR Operation**: Performs XOR between the expanded message and the generated 48-bit key
3. **Ciphertext Output**: Produces the final 48-bit ciphertext

## How to Run the Script

### Prerequisites
- Python 3.x installed on your system
- No additional dependencies required (uses only standard library)

### Running the Script

1. **Navigate to the directory**:
   ```bash
   cd Assessment03/Q2-DES
   ```

2. **Run the Python script**:
   ```bash
   python3 Q2-des.py
   ```

3. **Alternative execution**:
   ```bash
   python Q2-des.py
   ```

### Expected Output

The script will display:
- Step-by-step key generation process
- Message expansion details
- XOR operation between expanded message and key
- Final 48-bit ciphertext result

## Code Structure

### Main Functions

- `strip_parity_bits()` - Removes parity bits from 64-bit key
- `split_into_halves()` - Divides 56-bit sequence into two 28-bit halves
- `rotate_left()` - Performs left circular rotation
- `apply_key_permutation()` - Applies permutation and contraction
- `expand_message()` - Expands 32-bit message to 48 bits
- `xor_operation()` - Performs XOR between two bit sequences
- `generate_key()` - Complete key generation process
- `encrypt_message()` - Complete encryption process

### Key Features

- **Detailed Logging**: Each step is printed with intermediate results
- **Bit Manipulation**: Handles binary string operations efficiently
- **Mathematical Accuracy**: Implements exact DES procedures as specified
- **Clear Output**: Shows all intermediate steps for verification

## Example Usage

```python
# The script automatically processes the given values:
k0 = "0011101011101011000011111111000010101111010100011110010100011010"
message = "10011101001110000111100100011111"

# Generates 48-bit key and produces ciphertext
```

## Verification

The implementation follows the exact mathematical procedures specified in the problem:
- Uses the provided permutation and expansion tables
- Implements correct bit manipulation operations
- Produces verifiable intermediate and final results

## Educational Value

This implementation demonstrates:
- Understanding of DES encryption fundamentals
- Bit manipulation techniques
- Cryptographic algorithm implementation
- Step-by-step verification of encryption processes
