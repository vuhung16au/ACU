#!/usr/bin/env bash
set -euo pipefail

if [[ $# -lt 1 ]]; then
  echo "Usage: $0 FILE" >&2
  exit 1
fi

file=$1
if [[ ! -f "$file" ]]; then
  echo "File not found: $file" >&2
  exit 1
fi

lines=$(wc -l < "$file" | tr -d ' ')
bytes=$(wc -c < "$file" | tr -d ' ')
nonempty=$(grep -cve '^\s*$' "$file")

echo "lines=$lines"
echo "bytes=$bytes"
echo "nonempty_lines=$nonempty"


