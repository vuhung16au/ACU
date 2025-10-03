#!/usr/bin/env bash
set -euo pipefail

if [[ $# -lt 1 ]]; then
  echo "Usage: $0 COLUMN [DELIM] [FILE]" >&2
  exit 1
fi

col=$1
delim=${2:-}
file=${3:-}

if [[ -n "$delim" ]]; then
  awk -v c="$col" -v d="$delim" -F"$delim" '{ if (NF>=c) print $c }' ${file:+"$file"}
else
  awk -v c="$col" '{ if (NF>=c) print $c }' ${file:+"$file"}
fi


