#!/usr/bin/env python3
import sys
import re

usage = "Usage: 05_sed_like_substitution.py PATTERN REPLACEMENT [FILE]"

if len(sys.argv) < 3:
    print(usage)
    sys.exit(1)

pattern, replacement = sys.argv[1], sys.argv[2]
text = None

if len(sys.argv) >= 4 and sys.argv[3] != "-":
    with open(sys.argv[3], "r", encoding="utf-8", errors="replace") as f:
        text = f.read()
else:
    text = sys.stdin.read()

print(re.sub(pattern, replacement, text), end="")


