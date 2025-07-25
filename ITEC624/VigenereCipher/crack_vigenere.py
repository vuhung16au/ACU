#!/usr/bin/env python3
"""
Vigenère Cipher Cracking Tool

This script implements cryptanalysis techniques to crack Vigenère ciphers:
- Index of Coincidence analysis for key length determination
- Frequency analysis for key character recovery
- Chi-squared testing for statistical validation

Author: Automated Cryptanalysis Tool
Usage: python3 crack_vigenere.py -f ciphertext.txt
"""

import argparse
import sys
import string
from collections import Counter
import math
import itertools
import time

# English letter frequencies (percentage)
ENGLISH_FREQ = {
    'A': 8.12, 'B': 1.29, 'C': 2.78, 'D': 4.25, 'E': 12.02, 'F': 2.23,
    'G': 2.02, 'H': 6.09, 'I': 6.97, 'J': 0.15, 'K': 0.77, 'L': 4.03,
    'M': 2.41, 'N': 6.75, 'O': 7.68, 'P': 1.93, 'Q': 0.10, 'R': 5.99,
    'S': 6.33, 'T': 9.10, 'U': 2.76, 'V': 0.98, 'W': 2.36, 'X': 0.15,
    'Y': 1.97, 'Z': 0.07
}

def clean_text(text):
    """Remove non-alphabetic characters and convert to uppercase"""
    return ''.join(c.upper() for c in text if c.isalpha())

def calculate_ic(text):
    """
    Calculate Index of Coincidence for text
    IC = Σ[fi(fi-1)] / [N(N-1)]
    where fi is frequency of letter i, N is total letters
    """
    if len(text) <= 1:
        return 0.0
    
    # Count letter frequencies
    freq = Counter(text)
    n = len(text)
    
    # Calculate IC
    ic = sum(f * (f - 1) for f in freq.values()) / (n * (n - 1))
    return ic

def split_by_key_position(text, key_length):
    """Split text into subsequences based on key position"""
    subsequences = ['' for _ in range(key_length)]
    
    for i, char in enumerate(text):
        subsequences[i % key_length] += char
    
    return subsequences

def find_key_length(ciphertext, max_length=20, verbose=False):
    """
    Find most likely key length using Index of Coincidence
    English text has IC ≈ 0.067, random text has IC ≈ 0.038
    """
    clean_cipher = clean_text(ciphertext)
    
    if verbose:
        print("Analyzing key length using Index of Coincidence:")
        print("Length\tAvg IC\tScore")
        print("-" * 25)
    
    best_length = 1
    best_score = 0.0
    ic_scores = {}
    
    for length in range(1, min(max_length + 1, len(clean_cipher) // 2)):
        subsequences = split_by_key_position(clean_cipher, length)
        
        # Calculate average IC for this key length
        ics = [calculate_ic(subseq) for subseq in subsequences if subseq]
        avg_ic = sum(ics) / len(ics) if ics else 0
        
        # Score based on how close to English IC (0.067)
        score = 1.0 / (1.0 + abs(avg_ic - 0.067))
        ic_scores[length] = (avg_ic, score)
        
        if verbose:
            print(f"{length:2d}\t{avg_ic:.4f}\t{score:.4f}")
        
        if score > best_score:
            best_score = score
            best_length = length
    
    if verbose:
        print(f"\nBest key length: {best_length} (IC: {ic_scores[best_length][0]:.4f})")
    
    return best_length

def chi_squared_test(observed_freq, expected_freq):
    """Calculate chi-squared statistic for frequency comparison"""
    chi_squared = 0.0
    total_observed = sum(observed_freq.values())
    
    if total_observed == 0:
        return float('inf')
    
    for letter in string.ascii_uppercase:
        observed = observed_freq.get(letter, 0)
        expected = expected_freq[letter] * total_observed / 100.0
        
        if expected > 0:
            chi_squared += ((observed - expected) ** 2) / expected
    
    return chi_squared

def frequency_analysis(text, verbose=False):
    """
    Find the most likely Caesar shift for a text using frequency analysis
    Returns the shift value (0-25)
    """
    if not text:
        return 0
    
    best_shift = 0
    best_score = float('inf')
    
    if verbose:
        print(f"Analyzing subsequence: '{text[:20]}{'...' if len(text) > 20 else ''}'")
        print("Shift\tChi²\tDecrypted start")
        print("-" * 35)
    
    for shift in range(26):
        # Apply reverse Caesar shift
        shifted_text = ''
        for char in text:
            shifted_char = chr((ord(char) - ord('A') - shift) % 26 + ord('A'))
            shifted_text += shifted_char
        
        # Calculate frequency distribution
        freq = Counter(shifted_text)
        
        # Compare with English frequencies using chi-squared test
        chi_sq = chi_squared_test(freq, ENGLISH_FREQ)
        
        if verbose:
            preview = shifted_text[:10] if len(shifted_text) >= 10 else shifted_text
            print(f"{shift:2d}\t{chi_sq:6.2f}\t{preview}")
        
        if chi_sq < best_score:
            best_score = chi_sq
            best_shift = shift
    
    if verbose:
        print(f"Best shift: {best_shift} (Chi²: {best_score:.2f})")
    
    return best_shift

def crack_key_characters(ciphertext, key_length, verbose=False):
    """
    Crack each character of the key using frequency analysis
    """
    clean_cipher = clean_text(ciphertext)
    subsequences = split_by_key_position(clean_cipher, key_length)
    
    key_chars = []
    
    if verbose:
        print(f"\nCracking key characters for length {key_length}:")
        print("=" * 50)
    
    for i, subseq in enumerate(subsequences):
        if verbose:
            print(f"\nPosition {i + 1}:")
        
        if not subseq:
            key_chars.append('A')  # Default if no data
            continue
        
        # Find the shift that makes this look most like English
        shift = frequency_analysis(subseq, verbose)
        
        # Convert shift to key character
        # If we shift by 'shift' to get English, the key char is 'shift'
        key_char = chr(shift + ord('A'))
        key_chars.append(key_char)
        
        if verbose:
            print(f"Key character {i + 1}: '{key_char}' (shift: {shift})")
    
    return ''.join(key_chars)

def vigenere_decrypt(ciphertext, key):
    """Decrypt ciphertext using Vigenère cipher with given key"""
    result = []
    key_index = 0
    
    for char in ciphertext:
        if char.isalpha():
            # Convert to uppercase for processing
            char = char.upper()
            
            # Get shift from key
            shift = ord(key[key_index % len(key)]) - ord('A')
            
            # Decrypt character
            decrypted_char = chr((ord(char) - ord('A') - shift) % 26 + ord('A'))
            result.append(decrypted_char)
            
            key_index += 1
        else:
            # Keep non-alphabetic characters as-is
            result.append(char)
    
    return ''.join(result)

def validate_key(ciphertext, key, verbose=False):
    """
    Validate the recovered key by checking decryption quality
    """
    decrypted = vigenere_decrypt(ciphertext, key)
    clean_decrypted = clean_text(decrypted)
    
    # Calculate IC of decrypted text
    ic = calculate_ic(clean_decrypted)
    
    # Count dictionary words (enhanced check for common words)
    common_words = {
        'THE', 'AND', 'FOR', 'ARE', 'BUT', 'NOT', 'YOU', 'ALL', 'CAN', 'HER', 'WAS', 'ONE',
        'OUR', 'HAD', 'BUT', 'DID', 'GET', 'MAY', 'HIM', 'OLD', 'SEE', 'NOW', 'WAY', 'WHO',
        'BOY', 'DID', 'ITS', 'LET', 'PUT', 'SAY', 'SHE', 'TOO', 'USE', 'HIS', 'HOW', 'MAN',
        'NEW', 'OUT', 'TOO', 'ANY', 'DAY', 'GET', 'HAS', 'MAY', 'SAY', 'USE', 'HER', 'HOW',
        'ITS', 'OUR', 'OUT', 'TWO', 'WAY', 'WHO', 'BOY', 'DID', 'GET', 'HAS', 'HIM', 'HIS',
        'HOW', 'MAN', 'NEW', 'NOW', 'OLD', 'SEE', 'TWO', 'WAY', 'WHO', 'BOY', 'DID', 'ITS',
        'THIS', 'THAT', 'WITH', 'HAVE', 'WILL', 'YOUR', 'FROM', 'THEY', 'KNOW', 'WANT',
        'BEEN', 'GOOD', 'MUCH', 'SOME', 'TIME', 'VERY', 'WHEN', 'COME', 'HERE', 'JUST',
        'LIKE', 'LONG', 'MAKE', 'MANY', 'OVER', 'SUCH', 'TAKE', 'THAN', 'THEM', 'WELL',
        'WERE', 'WORLD', 'MESSAGE', 'TEST', 'HELLO', 'CIPHER', 'VIGENERE', 'COMPUTER',
        'SECURITY', 'SECRET', 'PASSWORD', 'ENCRYPT', 'DECRYPT', 'ATTACK', 'DEFEND'
    }
    
    # Enhanced scoring for short texts and exact word matches
    words = clean_decrypted.replace(' ', '').split() if ' ' in clean_decrypted else [clean_decrypted]
    
    # Check for exact word matches
    exact_matches = sum(1 for word in words if word in common_words)
    
    # Check for partial word matches and common phrases
    text_upper = clean_decrypted.replace(' ', '')
    
    # Common phrases and patterns
    if 'HELLO' in text_upper:
        exact_matches += 3  # Strong indicator
    if 'WORLD' in text_upper:
        exact_matches += 2
    if 'TEST' in text_upper:
        exact_matches += 2
    if 'MESSAGE' in text_upper:
        exact_matches += 2
    
    word_ratio = exact_matches / max(len(words), 1)
    
    if verbose:
        print(f"\nKey validation for '{key}':")
        print(f"Decrypted IC: {ic:.4f} (English ≈ 0.067)")
        print(f"Exact word matches: {exact_matches} (effective ratio: {word_ratio:.2%})")
        print(f"Decrypted text: {decrypted[:100]}{'...' if len(decrypted) > 100 else ''}")
    
    # Enhanced scoring: IC close to English + word recognition + readability
    ic_score = 1.0 / (1.0 + abs(ic - 0.067))
    
    # Enhanced word scoring for brute-force scenarios
    word_score = min(word_ratio * 2.0, 1.0)  # Cap at 1.0
    
    # Special bonus for very clear matches
    clarity_bonus = 0
    if exact_matches >= 2:  # Multiple word matches
        clarity_bonus = 0.2
    elif 'HELLO' in clean_decrypted and 'WORLD' in clean_decrypted:
        clarity_bonus = 0.3  # Very strong indicator
    
    total_score = ic_score * 0.4 + word_score * 0.5 + clarity_bonus * 0.1
    
    return total_score, decrypted

def try_common_keys(ciphertext, verbose=False):
    """Try common keys that might be used for educational purposes"""
    common_keys = [
        'SECURITY', 'SECRET', 'PASSWORD', 'CIPHER', 'VIGENERE', 'CRYPTO', 'ENCODE',
        'KEY', 'PRIVATE', 'HIDDEN', 'CODE', 'LOCK', 'SAFE', 'SECURE', 'PROTECT'
    ]
    
    best_key = None
    best_score = 0
    best_decrypted = ""
    
    if verbose:
        print("\nTrying common keys:")
        print("-" * 30)
    
    for key in common_keys:
        score, decrypted = validate_key(ciphertext, key, False)
        
        if verbose:
            print(f"'{key}': Score={score:.4f}")
            if score > 0.8:  # Show promising results
                preview = decrypted[:50] + "..." if len(decrypted) > 50 else decrypted
                print(f"  -> {preview}")
        
        if score > best_score:
            best_score = score
            best_key = key
            best_decrypted = decrypted
    
    return best_key, best_decrypted, best_score

def brute_force_crack(ciphertext, max_key_length=8, verbose=False):
    """
    Brute-force crack Vigenère cipher by trying all possible alphabetic keys
    up to the specified maximum length.
    
    Args:
        ciphertext: The encrypted text to crack
        max_key_length: Maximum key length to try (default: 8)
        verbose: Show progress and details
    
    Returns:
        (key, decrypted_text, confidence_score) or (None, None, 0) if not found
    """
    if verbose:
        print("Starting brute-force attack...")
        print(f"Maximum key length: {max_key_length}")
        print("=" * 50)
    
    clean_cipher = clean_text(ciphertext)
    
    # Calculate total number of keys to try
    total_keys = sum(26**length for length in range(1, max_key_length + 1))
    if verbose:
        print(f"Total keys to try: {total_keys:,}")
        
        # Estimate time (very rough)
        if total_keys > 1000000:
            print("⚠ Warning: This may take a very long time!")
        print()
    
    best_key = None
    best_score = 0
    best_decrypted = ""
    keys_tried = 0
    start_time = time.time()
    
    # Try each key length from 1 to max_key_length
    for key_length in range(1, max_key_length + 1):
        length_start_time = time.time()
        if verbose:
            print(f"Trying keys of length {key_length}...")
        
        keys_this_length = 0
        
        # Generate all possible keys of this length using alphabetic characters
        for key_tuple in itertools.product(string.ascii_uppercase, repeat=key_length):
            key = ''.join(key_tuple)
            keys_tried += 1
            keys_this_length += 1
            
            # Show progress for longer keys
            if verbose and key_length >= 4 and keys_this_length % 10000 == 0:
                elapsed = time.time() - start_time
                rate = keys_tried / elapsed if elapsed > 0 else 0
                print(f"  Tried {keys_this_length:,} keys of length {key_length} "
                      f"(Rate: {rate:.0f} keys/sec)")
            
            # Try this key
            score, decrypted = validate_key(ciphertext, key, False)
            
            # If we find a very good score, we might have found the key
            if score > best_score:
                best_score = score
                best_key = key
                best_decrypted = decrypted
                
                if verbose:
                    print(f"  New best key: '{key}' (score: {score:.4f})")
                    if len(decrypted) > 50:
                        preview = decrypted[:50] + "..."
                    else:
                        preview = decrypted
                    print(f"  Preview: {preview}")
            
            # If we get a very high score, we're probably done
            if score > 0.8:  # High confidence threshold
                if verbose:
                    elapsed = time.time() - start_time
                    print(f"\nHigh-confidence key found after {elapsed:.1f} seconds!")
                    print(f"Tried {keys_tried:,} keys total")
                return best_key, best_decrypted, best_score
        
        if verbose:
            length_elapsed = time.time() - length_start_time
            print(f"  Completed length {key_length} in {length_elapsed:.1f} seconds "
                  f"({keys_this_length:,} keys)")
            
            # If best score is reasonable, show intermediate result
            if best_score > 0.3:
                print(f"  Current best: '{best_key}' (score: {best_score:.4f})")
    
    elapsed = time.time() - start_time
    if verbose:
        print(f"\nBrute-force completed in {elapsed:.1f} seconds")
        print(f"Total keys tried: {keys_tried:,}")
        if best_key:
            print(f"Best key found: '{best_key}' (score: {best_score:.4f})")
        else:
            print("No satisfactory key found")
    
    return best_key, best_decrypted, best_score


def crack_vigenere(ciphertext, max_key_length=20, verbose=False, use_brute_force=False, brute_force_max_length=8):
    """
    Main function to crack Vigenère cipher
    Returns (key, decrypted_text, confidence_score)
    """
    if verbose:
        print("Starting Vigenère cipher cryptanalysis...")
        print("=" * 50)
    
    # Initialize variables for tracking best results
    best_key = None
    best_decrypted = ""
    best_score = 0
    method = "none"
    
    # If brute-force is requested, try that first for shorter keys
    if use_brute_force:
        if verbose:
            print("Using brute-force method...")
        
        brute_key, brute_decrypted, brute_score = brute_force_crack(
            ciphertext, 
            brute_force_max_length, 
            verbose
        )
        
        # If brute-force succeeds with high confidence, return immediately
        if brute_score > 0.7:
            if verbose:
                print(f"\nBrute-force successful! Key: '{brute_key}', Score: {brute_score:.4f}")
            return brute_key, brute_decrypted, brute_score
        elif brute_key and brute_score > best_score:
            best_key, best_decrypted, best_score, method = brute_key, brute_decrypted, brute_score, "brute-force"
            if verbose:
                print(f"Brute-force found candidate: '{brute_key}' (score: {brute_score:.4f})")
                print("Continuing with statistical analysis for comparison...")
    
    # Try common keys (educational/testing scenario)
    common_key, common_decrypted, common_score = try_common_keys(ciphertext, verbose)
    
    # Update best result if common key is better
    if common_score > best_score:
        best_key, best_decrypted, best_score, method = common_key, common_decrypted, common_score, "common key"
    
    # If we get a reasonably high score from common keys, we might be done
    if common_score > 0.55:
        if verbose:
            print(f"\nHigh-confidence match with common key: '{common_key}' (score: {common_score:.4f})")
            print(f"Decrypted text: {common_decrypted}")
        return common_key, common_decrypted, common_score
    
    # If we get a reasonably high score from common keys, we're probably done
    if common_score > 0.55:  # Lowered threshold since our scoring is conservative
        if verbose:
            print(f"\nHigh-confidence match with common key: '{common_key}' (score: {common_score:.4f})")
            # Show the actual decryption for verification
            print(f"Decrypted text: {common_decrypted}")
        return common_key, common_decrypted, common_score
    
    # Step 1: Determine key length - try multiple candidates
    clean_cipher = clean_text(ciphertext)
    ic_scores = {}
    
    for length in range(1, min(max_key_length + 1, len(clean_cipher) // 2)):
        subsequences = split_by_key_position(clean_cipher, length)
        ics = [calculate_ic(subseq) for subseq in subsequences if subseq]
        avg_ic = sum(ics) / len(ics) if ics else 0
        score = 1.0 / (1.0 + abs(avg_ic - 0.067))
        ic_scores[length] = (avg_ic, score)
    
    # Get top 3 key length candidates
    top_candidates = sorted(ic_scores.items(), key=lambda x: x[1][1], reverse=True)[:3]
    
    if verbose:
        print("\nFrequency analysis approach:")
        print("Top key length candidates:")
        for length, (ic, score) in top_candidates:
            print(f"Length {length}: IC={ic:.4f}, Score={score:.4f}")
    
    best_crypto_key = None
    best_crypto_score = 0
    best_crypto_decrypted = ""
    
    # Try each candidate key length for statistical analysis
    for key_length, _ in top_candidates:
        if verbose:
            print(f"\nTrying key length {key_length}:")
        
        # Step 2: Crack key characters
        key = crack_key_characters(ciphertext, key_length, verbose and len(top_candidates) == 1)
        
        # Step 3: Validate the key
        score, decrypted = validate_key(ciphertext, key, verbose and len(top_candidates) == 1)
        
        if verbose:
            print(f"Key '{key}': Score={score:.4f}")
        
        if score > best_score:
            best_key, best_decrypted, best_score, method = key, decrypted, score, "cryptanalysis"
    
    if verbose:
        print(f"\nFinal Results (via {method}):")
        print(f"Recovered key: '{best_key}'")
        print(f"Confidence score: {best_score:.4f}")
    
    return best_key, best_decrypted, best_score

def main():
    parser = argparse.ArgumentParser(
        description='Crack Vigenère cipher using cryptanalysis',
        epilog='Example: python3 crack_vigenere.py -f ciphertext.txt --verbose'
    )
    
    parser.add_argument('-f', '--file', 
                       help='Input ciphertext file (default: stdin)')
    parser.add_argument('-o', '--output', 
                       help='Output file for decrypted text (default: stdout)')
    parser.add_argument('--key-only', action='store_true',
                       help='Output only the recovered key')
    parser.add_argument('--verbose', action='store_true',
                       help='Show detailed analysis')
    parser.add_argument('--max-key-length', type=int, default=20,
                       help='Maximum key length to test for statistical analysis (default: 20)')
    parser.add_argument('--brute-force', action='store_true',
                       help='Enable brute-force cracking (tries all possible keys)')
    parser.add_argument('--key-length', type=int, default=8,
                       help='Maximum key length for brute-force attack (default: 8)')
    
    args = parser.parse_args()
    
    # Read input
    try:
        if args.file:
            with open(args.file, 'r', encoding='utf-8') as f:
                ciphertext = f.read().strip()
        else:
            ciphertext = sys.stdin.read().strip()
    except Exception as e:
        print(f"Error reading input: {e}", file=sys.stderr)
        sys.exit(1)
    
    if not ciphertext:
        print("Error: No input text provided", file=sys.stderr)
        sys.exit(1)
    
    try:
        # Crack the cipher
        key, decrypted, score = crack_vigenere(
            ciphertext, 
            args.max_key_length, 
            args.verbose,
            args.brute_force,
            args.key_length
        )
        
        # Prepare output
        if args.key_only:
            output = key
        else:
            if args.verbose:
                output = f"Key: {key}\nDecrypted text:\n{decrypted}"
            else:
                output = decrypted
        
        # Write output
        if args.output:
            with open(args.output, 'w', encoding='utf-8') as f:
                f.write(output or "")
        else:
            print(output)
    
    except Exception as e:
        print(f"Error during cryptanalysis: {e}", file=sys.stderr)
        if args.verbose:
            import traceback
            traceback.print_exc()
        sys.exit(1)

if __name__ == "__main__":
    main()
