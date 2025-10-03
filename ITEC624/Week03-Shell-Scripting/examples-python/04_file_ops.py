#!/usr/bin/env python3
import sys
from pathlib import Path

if len(sys.argv) < 2:
    print("Usage: 04_file_ops.py FILE")
    sys.exit(1)

path = Path(sys.argv[1])
if not path.exists():
    print(f"File not found: {path}")
    sys.exit(1)

lines = path.read_text(encoding="utf-8", errors="replace").splitlines()
print(f"lines={len(lines)}")
print(f"bytes={path.stat().st_size}")
print(f"nonempty_lines={sum(1 for L in lines if L.strip())}")


