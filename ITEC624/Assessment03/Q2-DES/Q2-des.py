#!/usr/bin/env python3
"""
DES Encryption Implementation for Assessment 03 - Q2
Implements simplified DES key generation and data encryption procedures.
"""

def strip_parity_bits(key_64):
    """
    Strip the 8th, 16th, 24th, 32nd, 40th, 48th, 56th, and 64th bits from a 64-bit key.
    Returns a 56-bit sequence.
    """
    # Convert string to list for easier manipulation
    key_list = list(key_64)

    # Remove parity bits (1-indexed positions: 8, 16, 24, 32, 40, 48, 56, 64)
    # Convert to 0-indexed: 7, 15, 23, 31, 39, 47, 55, 63
    parity_positions = [7, 15, 23, 31, 39, 47, 55, 63]

    # Remove bits at parity positions (from right to left to maintain indices)
    for pos in sorted(parity_positions, reverse=True):
        key_list.pop(pos)

    return ''.join(key_list)

def split_into_halves(bit_sequence):
    """
    Split a 56-bit sequence into two 28-bit halves.
    Returns (left_half, right_half).
    """
    mid_point = len(bit_sequence) // 2
    left_half = bit_sequence[:mid_point]
    right_half = bit_sequence[mid_point:]
    return left_half, right_half

def rotate_left(bit_sequence, positions=1):
    """
    Rotate a bit sequence left by the specified number of positions.
    """
    return bit_sequence[positions:] + bit_sequence[:positions]

def apply_key_permutation(bit_sequence):
    """
    Apply the key permutation and contraction from 56 bits to 48 bits.
    """
    # Permutation table (1-indexed, convert to 0-indexed)
    permutation_table = [
        13, 16, 10, 23, 0, 4, 2, 27, 14, 5, 20, 9, 22, 18, 11, 3,
        25, 7, 15, 6, 26, 19, 12, 1, 40, 51, 30, 36, 46, 54, 29, 39,
        50, 44, 32, 47, 43, 48, 38, 55, 33, 52, 45, 41, 49, 35, 28, 31
    ]

    result = []
    for pos in permutation_table:
        result.append(bit_sequence[pos])

    return ''.join(result)

def expand_message(message_32):
    """
    Expand a 32-bit message to 48 bits using the expansion table.
    """
    # Expansion table (1-indexed, convert to 0-indexed)
    expansion_table = [
        31, 0, 1, 2, 3, 4, 3, 4, 5, 6, 7, 8, 7, 8, 9, 10,
        11, 12, 11, 12, 13, 14, 15, 16, 15, 16, 17, 18, 19, 20, 19, 20,
        21, 22, 23, 24, 23, 24, 25, 26, 27, 28, 27, 28, 29, 30, 31, 0
    ]

    result = []
    for pos in expansion_table:
        result.append(message_32[pos])

    return ''.join(result)

def xor_operation(bit_sequence1, bit_sequence2):
    """
    Perform XOR operation on two bit sequences of equal length.
    """
    result = []
    for bit1, bit2 in zip(bit_sequence1, bit_sequence2):
        result.append('1' if bit1 != bit2 else '0')
    return ''.join(result)

def generate_key(k0):
    """
    Generate the 48-bit key K from the 64-bit key K0 following the DES key generation procedure.
    """
    print("=== Key Generation Process ===")
    print(f"Original K0 (64 bits): {k0}")
    print(f"Length: {len(k0)} bits")

    # Step 1: Strip parity bits
    k0_stripped = strip_parity_bits(k0)
    print(f"\nStep 1 - After stripping parity bits (56 bits): {k0_stripped}")
    print(f"Length: {len(k0_stripped)} bits")

    # Step 2: Split into halves
    left_half, right_half = split_into_halves(k0_stripped)
    print(f"\nStep 2 - Split into halves:")
    print(f"Left half (28 bits):  {left_half}")
    print(f"Right half (28 bits): {right_half}")

    # Step 3: Rotate left by 1 position
    left_rotated = rotate_left(left_half, 1)
    right_rotated = rotate_left(right_half, 1)
    print(f"\nStep 3 - After left rotation by 1:")
    print(f"Left rotated (28 bits):  {left_rotated}")
    print(f"Right rotated (28 bits): {right_rotated}")

    # Combine rotated halves
    combined = left_rotated + right_rotated
    print(f"\nCombined (56 bits): {combined}")

    # Step 4: Apply permutation and contraction
    k = apply_key_permutation(combined)
    print(f"\nStep 4 - After permutation and contraction (48 bits): {k}")
    print(f"Length: {len(k)} bits")

    return k

def encrypt_message(message, key):
    """
    Encrypt a 32-bit message using the 48-bit key following the DES encryption procedure.
    """
    print("\n=== Data Encryption Process ===")
    print(f"Original message M (32 bits): {message}")
    print(f"Length: {len(message)} bits")

    # Step 1: Expand message to 48 bits
    m_expanded = expand_message(message)
    print(f"\nStep 1 - Expanded message M* (48 bits): {m_expanded}")
    print(f"Length: {len(m_expanded)} bits")

    # Step 2: XOR with key
    ciphertext = xor_operation(m_expanded, key)
    print(f"\nStep 2 - XOR operation (M* ⊕ K):")
    print(f"M*: {m_expanded}")
    print(f"K:  {key}")
    print(f"Result C (48 bits): {ciphertext}")
    print(f"Length: {len(ciphertext)} bits")

    return ciphertext

def main():
    """
    Main function to solve the DES encryption problem.
    """
    print("DES Encryption Implementation for Assessment 03 - Q2")
    print("=" * 60)

    # Given values
    k0 = "0011101011101011000011111111000010101111010100011110010100011010"
    message = "10011101001110000111100100011111"

    print(f"Given K0 (64 bits): {k0}")
    print(f"Given M (32 bits):  {message}")
    print()

    # Generate key
    key = generate_key(k0)

    # Encrypt message
    ciphertext = encrypt_message(message, key)

    print("\n" + "=" * 60)
    print("FINAL RESULT:")
    print(f"Ciphertext C: {ciphertext}")
    print("=" * 60)

if __name__ == "__main__":
    main()