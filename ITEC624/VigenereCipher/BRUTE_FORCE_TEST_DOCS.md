# Brute-Force Test Documentation

## Test File: `test_brute_force-key-len-5.txt`

This file contains cipher text encrypted with a 5-character key for testing brute-force cracking capabilities.

### Test Setup Details

- **Original Text**: `Hello World! This is a test message for the Vigenere cipher.`
- **Key Used**: `CRACK` (5 characters)
- **Cipher Text**: `JVLNY YFRNN! VYIU SU R TGCV DEUCCXE HYT KHG FKXEPOTV CKZJVR.`

### Expected Test Results

- **Key Length 1-2**: Should crack almost instantly
- **Key Length 3**: Should crack in seconds  
- **Key Length 4**: May take several seconds
- **Key Length 5**: May take minutes; could find equivalent keys or timeout

### Notes

- The brute-force method tests all possible key combinations up to the specified length
- For 5-character keys, this means testing up to 26^5 = 11,881,376 possibilities
- The method may find equivalent keys that produce different but valid-looking plaintext
- Performance depends heavily on implementation and hardware capabilities

### Usage in Test Script

The `test_vigenere.sh` script now includes comprehensive brute-force testing:

- Section 5.2 tests key lengths 1-5 systematically
- Uses both generated test cases and the pre-encrypted `test_brute_force-key-len-5.txt`
- Measures timing and success rates for each key length
- Compares found keys with original keys and validates decryption quality
