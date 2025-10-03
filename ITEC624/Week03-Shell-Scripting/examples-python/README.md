## Python Scripting Examples (Week 03)

This folder mirrors the shell scripting topics using short Python scripts. 
Each example is small, focused, and runnable from the command line.

### What's inside
- Arithmetic and relational operators
- Control structures (for, while, if, case-like via dict/if)
- File operations
- `sed`-like text substitution
- `awk`-like field processing
- Pipelines via stdin/stdout

---

## How to run

### Linux / macOS
1. Ensure Python 3 is available:
   ```bash
   python3 --version
   ```
2. Run any script:
   ```bash
   python3 01_hello.py
   ```

### Windows
- Option A: Use Python from the Microsoft Store or python.org. Then run:
  ```bash
  py 01_hello.py
  ```
- Option B: Use WSL and run as on Linux.

Notes:
- Scripts also support `#!/usr/bin/env python3` and can be made executable on Unix:
  ```bash
  chmod +x 01_hello.py && ./01_hello.py
  ```

---

## Exercises
1. Extend `04_file_ops.py` to print the longest line length.
2. Modify `06_awk_like_fields.py` to compute min, max, and average.
3. Update `05_sed_like_substitution.py` to support regex flags (case-insensitive).
4. Create a new script that mimics `grep -n PATTERN file` with line numbers.
5. Build a mini pipeline: read stdin, filter a keyword, sort lines, and deduplicate.

---

## Further reading
- Python `argparse`, `pathlib`, `sys`, `re`, `csv`, `subprocess` modules
- Real Python tutorials on command-line interfaces
- Official Python docs: I/O, Text Processing Services

---

## Conclusions
Python is an excellent complement to shell scripting: it handles complex text and data tasks with readable code while integrating smoothly with stdin/stdout pipelines.


