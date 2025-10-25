#!/usr/bin/env python3
"""
Script to generate random lists of integers for testing radix sort performance.
"""

import argparse
import random
import sys


def generate_random_list(n, min_val, max_val):
    """Generate a random list of integers."""
    return [random.randint(min_val, max_val) for _ in range(n)]


def save_list_to_file(numbers, filename):
    """Save a list of numbers to a file."""
    with open(filename, 'w') as f:
        for num in numbers:
            f.write(f"{num}\n")


def main():
    parser = argparse.ArgumentParser(description='Generate random list of integers')
    parser.add_argument('-n', '--count', type=int, default=10000000,
                        help='Number of integers to generate (default: 10000000)')
    parser.add_argument('-min', '--minimum', type=int, default=0,
                        help='Minimum value of integers (default: 0)')
    parser.add_argument('-max', '--maximum', type=int, default=10000000,
                        help='Maximum value of integers (default: 10000000)')
    parser.add_argument('-o', '--output', type=str, required=True,
                        help='Output filename')
    
    args = parser.parse_args()
    
    if args.minimum >= args.maximum:
        print("Error: minimum value must be less than maximum value")
        sys.exit(1)
    
    print(f"Generating {args.count} random integers between {args.minimum} and {args.maximum}...")
    
    # Generate the random list
    random_list = generate_random_list(args.count, args.minimum, args.maximum)
    
    # Save to file
    save_list_to_file(random_list, args.output)
    
    print(f"Random list saved to {args.output}")
    print(f"List contains {len(random_list)} integers")
    print(f"Range: {min(random_list)} to {max(random_list)}")


if __name__ == "__main__":
    main()
