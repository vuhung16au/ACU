#!/usr/bin/env python3
import sys

def to_int(value: str) -> int:
    try:
        return int(value)
    except ValueError:
        print("Please provide integers.")
        sys.exit(1)

if len(sys.argv) < 3:
    print("Usage: 02_arithmetic_relations.py A B")
    sys.exit(1)

a = to_int(sys.argv[1])
b = to_int(sys.argv[2])

print(f"sum={a + b}")
print(f"diff={a - b}")
print(f"prod={a * b}")
print(f"eq={a == b}")
print(f"ne={a != b}")
print(f"gt={a > b}")
print(f"lt={a < b}")
print(f"ge={a >= b}")
print(f"le={a <= b}")


