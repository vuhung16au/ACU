#!/usr/bin/env python3
"""
Frequency Analysis Attack on Caesar Cipher

This script uses statistical analysis of letter frequencies to determine
the most likely key used in a Caesar cipher. It compares the frequency
distribution of letters in potential decryptions against expected
English letter frequencies.

Usage:
    python3 frequency_analysis_caesar.py "CIPHERTEXT"
    python3 frequency_analysis_caesar.py -f /path/to/file/name.ext.cae
    python3 frequency_analysis_caesar.py --file /path/to/file/name.ext.cae

Examples:
    python3 frequency_analysis_caesar.py "VqgwkhprqhwqDolfh"
    python3 frequency_analysis_caesar.py -f encrypted.txt.cae

Author: Caesar Cipher Analysis Tool
"""

import sys
import string
import argparse
from collections import Counter


def get_english_frequencies():
    """
    Return expected frequencies of letters in English text (as percentages).
    Based on analysis of large English text corpora.
    """
    return {
        'E': 12.7, 'T': 9.1, 'A': 8.2, 'O': 7.5, 'I': 7.0, 'N': 6.7,
        'S': 6.3, 'H': 6.1, 'R': 6.0, 'D': 4.3, 'L': 4.0, 'C': 2.8,
        'U': 2.8, 'M': 2.4, 'W': 2.4, 'F': 2.2, 'G': 2.0, 'Y': 2.0,
        'P': 1.9, 'B': 1.3, 'V': 1.0, 'K': 0.8, 'J': 0.15, 'X': 0.15,
        'Q': 0.10, 'Z': 0.07
    }


def caesar_decrypt_with_key(ciphertext, key):
    """
    Decrypt ciphertext using Caesar cipher with given key.
    
    Args:
        ciphertext (str): The encrypted text
        key (int): The decryption key (shift amount)
    
    Returns:
        str: Decrypted text with only alphabetic characters
    """
    result = ""
    for char in ciphertext.upper():
        if char.isalpha():
            # Shift character back by key positions
            shifted = ord(char) - ord('A')
            shifted = (shifted - key) % 26
            result += chr(shifted + ord('A'))
    return result


def calculate_chi_squared_score(text):
    """
    Calculate chi-squared statistic comparing text frequencies to English.
    Lower scores indicate better match to English letter frequencies.
    
    Args:
        text (str): Text to analyze
    
    Returns:
        float: Chi-squared score (lower is better)
    """
    if not text:
        return float('inf')
    
    # Count letter frequencies in the text
    letter_count = Counter(char for char in text.upper() if char.isalpha())
    total_letters = sum(letter_count.values())
    
    if total_letters == 0:
        return float('inf')
    
    # Calculate actual frequencies as percentages
    actual_frequencies = {}
    for letter in string.ascii_uppercase:
        count = letter_count.get(letter, 0)
        actual_frequencies[letter] = (count / total_letters) * 100
    
    # Get expected English frequencies
    expected_frequencies = get_english_frequencies()
    
    # Calculate chi-squared statistic
    chi_squared = 0
    for letter in string.ascii_uppercase:
        expected = expected_frequencies.get(letter, 0)
        actual = actual_frequencies.get(letter, 0)
        
        # Standard chi-squared formula: (observed - expected)² / expected
        if expected > 0:
            chi_squared += ((actual - expected) ** 2) / expected
        elif actual > 0:
            # Penalty for letters that shouldn't appear frequently
            chi_squared += actual ** 2
    
    return chi_squared


def frequency_analysis_attack(ciphertext):
    """
    Perform frequency analysis attack on Caesar cipher.
    
    Args:
        ciphertext (str): The encrypted text to analyze
    
    Returns:
        tuple: (best_key, best_plaintext, all_results)
    """
    results = []
    
    print("Frequency Analysis Attack on Caesar Cipher")
    print("=" * 60)
    print(f"Analyzing ciphertext: {ciphertext}")
    print(f"Ciphertext length: {len([c for c in ciphertext if c.isalpha()])} letters")
    print()
    
    # Try all possible keys (0-25)
    for key in range(26):
        decrypted = caesar_decrypt_with_key(ciphertext, key)
        score = calculate_chi_squared_score(decrypted)
        results.append((key, decrypted, score))
    
    # Sort by chi-squared score (lower is better)
    results.sort(key=lambda x: x[2])
    
    print("Results ranked by frequency analysis (lower score = more English-like):")
    print("-" * 60)
    print(f"{'Rank':<4} {'Key':<4} {'Score':<10} {'Decrypted Text'}")
    print("-" * 60)
    
    # Display top 10 candidates
    for i, (key, decrypted, score) in enumerate(results[:10]):
        rank = i + 1
        marker = " ← BEST MATCH" if i == 0 else ""
        print(f"{rank:<4} {key:<4} {score:<10.2f} {decrypted}{marker}")
    
    if len(results) > 10:
        print(f"... (showing top 10 of 26 possibilities)")
    
    print()
    print("Analysis Summary:")
    print("-" * 30)
    best_key, best_plaintext, best_score = results[0]
    print(f"Most likely key: {best_key}")
    print(f"Most likely plaintext: {best_plaintext}")
    print(f"Confidence score: {best_score:.2f}")
    
    # Provide interpretation of results
    if best_score < 50:
        confidence = "HIGH"
    elif best_score < 100:
        confidence = "MEDIUM"
    else:
        confidence = "LOW"
    
    print(f"Confidence level: {confidence}")
    
    if len([c for c in ciphertext if c.isalpha()]) < 50:
        print("\nNote: Short texts may not provide reliable frequency analysis.")
        print("Consider using brute force method for verification.")
    
    return best_key, best_plaintext, results


def main():
    """Main function to handle command line arguments and run analysis."""
    parser = argparse.ArgumentParser(
        description="Analyze Caesar cipher using frequency analysis",
        formatter_class=argparse.RawDescriptionHelpFormatter,
        epilog="""
Examples:
  python3 frequency_analysis_caesar.py "VqgwkhprqhwqDolfh"
  python3 frequency_analysis_caesar.py -f encrypted.txt.cae
  python3 frequency_analysis_caesar.py --file /path/to/document.txt.cae
        """
    )
    
    # Create mutually exclusive group for text input vs file input
    input_group = parser.add_mutually_exclusive_group(required=True)
    input_group.add_argument('text', nargs='?', help='Cipher text to analyze (use quotes for multiple words)')
    input_group.add_argument('-f', '--file', dest='file_path', help='Path to input file')
    
    # Custom parsing to handle file input
    if len(sys.argv) >= 2 and sys.argv[1] in ['-f', '--file']:
        # File input mode: parse arguments differently
        if len(sys.argv) >= 3:
            # python script.py -f filename
            args = argparse.Namespace(file_path=sys.argv[2], text=None)
        else:
            print("Error: File path required with -f option")
            sys.exit(1)
    else:
        # Text input mode: use standard argument parsing
        args = parser.parse_args()
    
    # Get input text
    if args.file_path:
        # Read from file
        try:
            with open(args.file_path, 'r', encoding='utf-8') as file:
                ciphertext = file.read().strip()
        except FileNotFoundError:
            print(f"Error: File '{args.file_path}' not found")
            sys.exit(1)
        except IOError as e:
            print(f"Error reading file '{args.file_path}': {e}")
            sys.exit(1)
    else:
        # Use text from command line
        ciphertext = args.text
        if ciphertext is None:
            print("Error: Text input required")
            sys.exit(1)
    
    if not ciphertext:
        print("Error: Please provide a non-empty ciphertext.")
        sys.exit(1)
    
    # Remove any non-alphabetic characters for analysis
    clean_ciphertext = ''.join(c for c in ciphertext if c.isalpha())
    
    if not clean_ciphertext:
        print("Error: Ciphertext contains no alphabetic characters.")
        sys.exit(1)
    
    try:
        best_key, best_plaintext, all_results = frequency_analysis_attack(clean_ciphertext)
        
        print(f"\nRecommendation: Try key {best_key} for decryption.")
        
    except Exception as e:
        print(f"Error during analysis: {e}")
        sys.exit(1)


if __name__ == "__main__":
    main()
