#!/usr/bin/env bash
set -euo pipefail

if [[ $# -lt 2 ]]; then
  echo "Usage: $0 PATTERN REPLACEMENT [FILE|-]" >&2
  exit 1
fi

pat=$1
rep=$2
file=${3:--}

if [[ "$file" == "-" ]]; then
  sed "s/${pat}/${rep}/g"
else
  sed "s/${pat}/${rep}/g" "$file"
fi


