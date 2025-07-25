# Test Data Documentation

This folder contains various test files for testing Caesar cipher encryption, decryption, and cracking algorithms.

## Plain Text Files (for encryption testing)

- `short_english.txt` - Simple "HELLO WORLD" message
- `medium_english.txt` - Pangram with alphabet coverage
- `long_english.txt` - Extended text about Caesar cipher with good frequency distribution
- `shakespeare_quote.txt` - Famous Hamlet quote for literary testing
- `mixed_case.txt` - Text with mixed case, numbers, and special characters

## Pre-encrypted Files (for decryption testing)

- `encrypted_shift3.txt` - "HELLO WORLD" encrypted with shift 3 → "KHOOR ZRUOG"
- `encrypted_shift3_medium.txt` - Pangram encrypted with shift 3
- `encrypted_shift4.txt` - "HELLO WORLD" encrypted with shift 4 → "EBIIL TLOIA"
- `encrypted_shift13.txt` - "HELLO WORLD" encrypted with shift 13 (ROT13) → "URYYB JBEYQ"
- `encrypted_shift25.txt` - "HELLO WORLD" encrypted with shift 25 → "GDKKN VNQKC"

## Special Test Cases

- `alphabet.txt` - Complete alphabet for testing all character mappings
- `repeated_letters.txt` - High frequency of single letter for IC testing
- `common_words.txt` - Common English words for dictionary matching
- `bigrams_trigrams.txt` - Common letter combinations for n-gram analysis
- `mixed_alphanumeric.txt` - Text with letters and numbers

## Usage Examples

### For Encryption Testing:
```python
with open('test-data/short_english.txt', 'r') as f:
    plaintext = f.read()
    encrypted = caesar_encrypt(plaintext, 3)
```

### For Decryption Testing:
```python
with open('test-data/encrypted_shift3.txt', 'r') as f:
    ciphertext = f.read()
    decrypted = caesar_decrypt(ciphertext, 3)
    # Should return "HELLO WORLD"
```

### For Cracking Algorithm Testing:
```python
with open('test-data/encrypted_shift3_medium.txt', 'r') as f:
    ciphertext = f.read()
    best_shift = frequency_analysis_crack(ciphertext)
    # Should identify shift = 3
```

## Expected Results

| File | Original | Shift | Expected Encrypted |
|------|----------|-------|-------------------|
| short_english.txt | HELLO WORLD | 3 | KHOOR ZRUOG |
| short_english.txt | HELLO WORLD | 4 | EBIIL TLOIA |
| short_english.txt | HELLO WORLD | 13 | URYYB JBEYQ |
| short_english.txt | HELLO WORLD | 25 | GDKKN VNQKC |
