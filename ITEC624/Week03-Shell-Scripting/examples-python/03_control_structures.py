#!/usr/bin/env python3
import sys

items = sys.argv[1:] or ["apple", "banana", "cherry"]

# for-in
for idx, item in enumerate(items, start=1):
    print(f"{idx}: {item}")

# while
i = 0
while i < len(items):
    if items[i].startswith("b"):
        print(f"starts-with-b: {items[i]}")
    i += 1

# case-like (match if Python >= 3.10), fallback to dict
color = (items[0] if items else "")
if color in {"apple", "cherry"}:
    print("case: red-ish fruit")
elif color == "banana":
    print("case: yellow fruit")
else:
    print("case: other")


