# Vigenère Cipher Cracking Strategy

## Overview
This document outlines a comprehensive strategy for cracking Vigenère ciphers using cryptanalysis techniques. The Vigenère cipher is a polyalphabetic substitution cipher that uses a repeating key to encrypt plaintext.

## Known Information
- **Target Ciphertext**: `output-ciphertext/test_encrypted.txt` and other `*encrypted.txt` files
- **Known Key**: `vigenere.txt` contains `K = "security"` (8 characters)
- **Known Plaintext**: `inputs-plaintext/test_message.txt`
- **Sample Ciphertext**: `ZINFF EHPDH! VBZA BQ S XGMK UXQKEIY WWK RZI XCXMGCJI ECGPXP.`

## Vigenère Cipher Fundamentals

### How Vigenère Works
1. **Key Repetition**: The key "SECURITY" repeats: SECURITYSECURITYSECURITY...
2. **Character Mapping**: Each plaintext letter is shifted by the corresponding key letter
3. **Caesar Cipher Relationship**: Each position uses a different Caesar cipher shift
4. **Non-alphabetic Preservation**: Spaces, punctuation remain unchanged

### Mathematical Representation
```
C[i] = (P[i] + K[i mod |K|]) mod 26
P[i] = (C[i] - K[i mod |K|]) mod 26
```

## Cryptanalysis Strategy

### Phase 1: Key Length Determination

#### Method 1: Index of Coincidence (IC)
- **Principle**: Natural language has characteristic letter frequency patterns
- **English IC**: ~0.067 (for random text ~0.038)
- **Process**:
  1. Try different key lengths (1 to 20)
  2. Split ciphertext into subsequences based on key position
  3. Calculate IC for each subsequence
  4. Average IC across all subsequences
  5. Key length with highest average IC is likely correct

#### Method 2: Kasiski Examination
- **Principle**: Repeated patterns in ciphertext indicate key repetition
- **Process**:
  1. Find repeated trigrams/bigrams in ciphertext
  2. Measure distances between repetitions
  3. Find GCD of these distances
  4. Key length is likely a factor of this GCD

#### Method 3: Friedman Test
- **Mathematical approach** using letter frequency distributions
- **Formula**: Key length ≈ (0.027 × N) / (IC - 0.038)
- Where N = ciphertext length, IC = calculated index of coincidence

### Phase 2: Key Character Recovery

#### Frequency Analysis Approach
For each position in the key:
1. **Extract Subsequence**: Collect every nth character (where n = key length)
2. **Frequency Count**: Count letter frequencies in subsequence
3. **Chi-Squared Test**: Compare against expected English frequencies
4. **Best Fit**: Find Caesar shift that best matches English distribution

#### Expected English Letter Frequencies (%)
```
E: 12.02  T: 9.10   A: 8.12   O: 7.68   I: 6.97   N: 6.75
S: 6.33   H: 6.09   R: 5.99   D: 4.25   L: 4.03   C: 2.78
U: 2.76   M: 2.41   W: 2.36   F: 2.23   G: 2.02   Y: 1.97
P: 1.93   B: 1.29   V: 0.98   K: 0.77   J: 0.15   X: 0.15
Q: 0.10   Z: 0.07
```

#### Mutual Index of Coincidence
- Compare letter distributions between subsequences
- When two subsequences use the same Caesar shift, IC will be higher

### Phase 3: Key Validation and Refinement

#### Validation Methods
1. **Decrypt Sample**: Use recovered key to decrypt ciphertext
2. **Readability Check**: Assess if result looks like natural language
3. **Dictionary Words**: Count recognizable English words
4. **Bigram/Trigram Analysis**: Check for common English patterns

#### Refinement Techniques
1. **Partial Key**: If some positions are uncertain, try variants
2. **Dictionary Attack**: Test common words as keys
3. **Pattern Matching**: Look for repeated words in decrypted text

## Implementation Strategy

### Script: `crack_vigenere.py`

#### Core Functions Needed
```python
def calculate_ic(text):
    """Calculate Index of Coincidence"""

def find_key_length(ciphertext, max_length=20):
    """Determine most likely key length using IC"""

def frequency_analysis(text):
    """Find Caesar shift using frequency analysis"""

def chi_squared_test(observed, expected):
    """Statistical test for frequency matching"""

def crack_vigenere(ciphertext):
    """Main cracking function"""

def validate_key(ciphertext, key):
    """Validate recovered key"""
```

#### Algorithm Flow
```
1. Load ciphertext from file
2. Determine key length using IC analysis
3. For each key position:
   a. Extract subsequence
   b. Perform frequency analysis
   c. Find best Caesar shift
4. Reconstruct full key
5. Validate by decrypting
6. Output key and decrypted text
```

### Testing Strategy

#### Test Cases
1. **Known Case**: Use `test_encrypted.txt` with known key "SECURITY"
2. **Validation**: Decrypt and compare with `test_message.txt`
3. **Multiple Files**: Test on all `*encrypted.txt` files
4. **Different Key Lengths**: Create test cases with various key lengths

#### Success Metrics
- **Key Recovery**: Exactly match known key "SECURITY"
- **Decryption Accuracy**: Perfect match with original plaintext
- **Performance**: Handle reasonable ciphertext lengths efficiently

## Advanced Techniques

### For Stronger Security
1. **Probable Word Attack**: If certain words are likely in plaintext
2. **Crib Dragging**: Using known plaintext fragments
3. **Ciphertext-Only Attack**: Pure frequency analysis
4. **Multiple Key Candidates**: Ranking potential keys by likelihood

### Handling Edge Cases
1. **Short Ciphertext**: May not have enough statistical data
2. **Non-English Text**: Different frequency distributions
3. **Mixed Case/Punctuation**: Proper filtering needed
4. **Key Length = Text Length**: Essentially one-time pad (unbreakable)

## Expected Challenges

### Statistical Challenges
- **Insufficient Data**: Short ciphertexts may not show clear patterns
- **Noise**: Non-alphabetic characters can skew analysis
- **Multiple Candidates**: Several keys might seem plausible

### Implementation Challenges
- **Efficiency**: Trying all combinations can be computationally expensive
- **Edge Cases**: Handling unusual input formats
- **Accuracy**: Floating-point precision in statistical calculations

## Success Criteria

### Primary Goals
1. **Recover Key**: Successfully find "SECURITY" from `test_encrypted.txt`
2. **Decrypt Text**: Accurately recover original plaintext
3. **Automation**: Work without manual intervention

### Secondary Goals
1. **Robustness**: Handle various ciphertext formats
2. **Performance**: Complete analysis in reasonable time
3. **Extensibility**: Easy to modify for different scenarios

## File Structure

```
crack_vigenere.py           # Main cracking script
├── Key Length Detection    # IC analysis, Kasiski examination
├── Frequency Analysis      # Chi-squared tests, Caesar breaking
├── Key Recovery           # Combine individual character findings
├── Validation            # Test recovered key
└── Output               # Display results

Supporting files:
├── english_frequencies.py  # Letter frequency data
├── statistical_tests.py   # IC, chi-squared implementations
└── utils.py               # Helper functions
```

## Usage Examples

```bash
# Basic usage
python3 crack_vigenere.py -f output-ciphertext/test_encrypted.txt

# Output key only
python3 crack_vigenere.py -f output-ciphertext/test_encrypted.txt --key-only

# Verbose analysis
python3 crack_vigenere.py -f output-ciphertext/test_encrypted.txt --verbose

# Batch processing
for file in output-ciphertext/*encrypted.txt; do
    echo "Cracking $file"
    python3 crack_vigenere.py -f "$file"
done
```

## Next Steps

1. **Implement** the core cryptanalysis functions
2. **Test** with known case (`test_encrypted.txt`)
3. **Validate** against expected results
4. **Optimize** for performance and accuracy
5. **Document** findings and limitations

This strategy provides a solid foundation for successfully cracking Vigenère ciphers using established cryptanalytic techniques.
