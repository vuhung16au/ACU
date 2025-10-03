#!/usr/bin/env python3
"""
Run `history > my-history.txt` to generate the `my-history.txt` file.

Parse my-history.txt and write the 16 most-used commands into
most-used-16-commands.md. Normalizes entries by:
- Removing leading history numbers (bash/zsh `fc -l`)
- Handling zsh extended history prefix `: <epoch>:<flags>;`
- Collapsing `sudo cmd` to `cmd`
"""
from __future__ import annotations

import re
import sys
from collections import Counter
from pathlib import Path

ROOT = Path(__file__).resolve().parent
INPUT = ROOT / "my-history.txt"
OUTPUT = ROOT / "most-used-16-commands.md"


def normalize_line(line: str) -> str | None:
    s = line.rstrip("\n")
    if not s.strip():
        return None

    # Strip zsh extended history prefix: ": <epoch>:<flags>;command"
    s = re.sub(r"^: \d+:\d+;", "", s)

    # Strip leading history numbers like "  123  command" or "123\tcommand"
    s = re.sub(r"^\s*\d+\s+", "", s)

    # Collapse multiple spaces
    s = re.sub(r"\s+", " ", s).strip()
    if not s:
        return None

    # Extract first token; if sudo, take second token
    parts = s.split(" ")
    if not parts:
        return None
    if parts[0] == "sudo" and len(parts) >= 2:
        return parts[1]
    return parts[0]


def compute_top_commands(path: Path, limit: int = 16) -> list[tuple[str, int]]:
    counter: Counter[str] = Counter()
    with path.open("r", encoding="utf-8", errors="replace") as f:
        for line in f:
            cmd = normalize_line(line)
            if cmd:
                counter[cmd] += 1
    return counter.most_common(limit)


def write_markdown(results: list[tuple[str, int]], out_path: Path) -> None:
    lines = ["## Most-used 16 command lines (from shell history)", ""]
    for cmd, n in results:
        lines.append(f"- {n:7d}  {cmd}")
    out_path.write_text("\n".join(lines) + "\n", encoding="utf-8")


def main(argv: list[str]) -> int:
    input_path = INPUT
    output_path = OUTPUT
    if len(argv) >= 2:
        input_path = Path(argv[1])
    if len(argv) >= 3:
        output_path = Path(argv[2])

    if not input_path.exists():
        print(f"Input not found: {input_path}", file=sys.stderr)
        return 1

    results = compute_top_commands(input_path, limit=16)
    write_markdown(results, output_path)
    print(f"Wrote top 16 commands to: {output_path}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main(sys.argv))


