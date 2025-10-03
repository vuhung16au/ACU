#!/usr/bin/env bash
set -euo pipefail

items=("apple" "banana" "cherry")

# for loop
for i in "${!items[@]}"; do
  echo "$((i+1)): ${items[$i]}"
done

# while with test
i=0
while [[ $i -lt ${#items[@]} ]]; do
  if [[ ${items[$i]} == b* ]]; then
    echo "starts-with-b: ${items[$i]}"
  fi
  ((i++))
done

# case
color=${items[0]}
case "$color" in
  apple|cherry) echo "case: red-ish fruit" ;;
  banana)       echo "case: yellow fruit" ;;
  *)            echo "case: other" ;;
esac


