#!/usr/bin/env bash
set -euo pipefail

if [[ $# -lt 2 ]]; then
  echo "Usage: $0 A B" >&2
  exit 1
fi

a=$1
b=$2

sum=$((a + b))
diff=$((a - b))
prod=$((a * b))

echo "sum=$sum"
echo "diff=$diff"
echo "prod=$prod"

[[ $a -eq $b ]] && echo "eq=true" || echo "eq=false"
[[ $a -ne $b ]] && echo "ne=true" || echo "ne=false"
[[ $a -gt $b ]] && echo "gt=true" || echo "gt=false"
[[ $a -lt $b ]] && echo "lt=true" || echo "lt=false"
[[ $a -ge $b ]] && echo "ge=true" || echo "ge=false"
[[ $a -le $b ]] && echo "le=true" || echo "le=false"


