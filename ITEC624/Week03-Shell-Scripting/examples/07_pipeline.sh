#!/usr/bin/env bash
set -euo pipefail

# Sort unique words by frequency from stdin
tr -cs '[:alnum:]' '\n' | tr '[:upper:]' '[:lower:]' | sort | uniq -c | sort -rn | head -n 10


