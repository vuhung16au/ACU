#!/usr/bin/env python3
import sys

usage = "Usage: 06_awk_like_fields.py COLUMN [DELIM] [FILE]"

if len(sys.argv) < 2:
    print(usage)
    sys.exit(1)

col = int(sys.argv[1]) - 1
delim = sys.argv[2] if len(sys.argv) >= 3 else None
filename = sys.argv[3] if len(sys.argv) >= 4 else None

def process(stream):
    for line in stream:
        parts = line.rstrip("\n").split(delim) if delim is not None else line.split()
        if 0 <= col < len(parts):
            print(parts[col])

if filename:
    with open(filename, "r", encoding="utf-8", errors="replace") as f:
        process(f)
else:
    process(sys.stdin)


