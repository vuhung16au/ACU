#!/usr/bin/env python3
import sys

# Reads stdin, uppercases, and writes to stdout
for line in sys.stdin:
    sys.stdout.write(line.upper())


