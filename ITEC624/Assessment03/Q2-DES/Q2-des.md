# Assessment 03 - Q2: DES Encryption Analysis

## Problem Statement

This question involves implementing simplified procedures from the first round of DES (Data Encryption Standard) encryption and key generation. We need to:

1. Generate a 48-bit key from a 64-bit initial key
2. Encrypt a 32-bit message using the generated key
3. Find the final ciphertext

## Given Values

- **Initial Key K₀**: `0011101011101011000011111111000010101111010100011110010100011010` (64 bits)
- **Message M**: `10011101001110000111100100011111` (32 bits)

## Key Generation Procedure

### Step 1: Strip Parity Bits
Remove the 8th, 16th, 24th, 32nd, 40th, 48th, 56th, and 64th bits from the 64-bit key.

**Mathematical representation:**
```
K₀ = b₁b₂b₃...b₆₄
K₀' = b₁b₂b₃b₄b₅b₆b₇b₉b₁₀b₁₁b₁₂b₁₃b₁₄b₁₅b₁₇...b₆₃
```

**Result:** 56-bit sequence

### Step 2: Split into Halves
Divide the 56-bit sequence into two 28-bit halves:
- **Left half (L)**: First 28 bits
- **Right half (R)**: Last 28 bits

### Step 3: Left Rotation
Rotate both halves left by 1 position:

```
L' = L << 1 (circular left shift)
R' = R << 1 (circular left shift)
```

### Step 4: Permutation and Contraction
Apply the permutation table to contract from 56 bits to 48 bits:

```
P = (14,17,11,24,1,5,3,28,15,6,21,10,23,19,12,4,26,8,16,7,27,20,13,2,41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,34,53,46,42,50,36,29,32)
```

**Mathematical representation:**
```
K[i] = Combined[P[i] - 1] for i = 0 to 47
```

## Data Encryption Procedure

### Step 1: Message Expansion
Expand the 32-bit message to 48 bits using the expansion table:

```
E = (32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,13,14,15,16,17,16,17,18,19,20,21,20,21,22,23,24,25,24,25,26,27,28,29,28,29,30,31,32,1)
```

**Mathematical representation:**
```
M*[i] = M[E[i] - 1] for i = 0 to 47
```

### Step 2: XOR Operation
Perform XOR between the expanded message and the key:

```
C = M* ⊕ K
```

**Mathematical representation:**
```
C[i] = M*[i] ⊕ K[i] for i = 0 to 47
```

## Solution Implementation

The Python implementation follows these exact steps:

1. **Key Generation**:
   - Strip parity bits from K₀
   - Split into L and R halves
   - Rotate both halves left by 1
   - Apply permutation to get 48-bit key K

2. **Encryption**:
   - Expand 32-bit message M to 48 bits M*
   - XOR M* with key K to get ciphertext C

## Mathematical Verification

The XOR operation is defined as:
```
A ⊕ B = (A + B) mod 2
```

For binary strings:
- `0 ⊕ 0 = 0`
- `0 ⊕ 1 = 1`
- `1 ⊕ 0 = 1`
- `1 ⊕ 1 = 0`

## Expected Result

Running the implementation will show:
- The step-by-step key generation process
- The message expansion process
- The final XOR operation
- The resulting 48-bit ciphertext

The ciphertext represents the encrypted form of the original 32-bit message using the generated 48-bit key, following the simplified DES procedures specified in the problem.
