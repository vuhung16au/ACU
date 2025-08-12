#!/usr/bin/env bash
# Simple curl-based smoke test for 00.HelloSpring endpoints
# Usage:
#   BASE_URL=http://localhost:8080 bash scripts/test_endpoints.sh [optional_path_to_json_payload]
# If a payload file is not provided, a default JSON body will be used.

set -euo pipefail

BASE_URL="${BASE_URL:-http://localhost:8080}"
ENDPOINT="/employees/"  # Note the trailing slash matches controller mappings
CURL_BIN="${CURL_BIN:-curl}"

have_jq() { command -v jq >/dev/null 2>&1; }

pretty_json() {
  if have_jq; then
    jq . || cat
  else
    cat
  fi
}

# Resolve data payload
if [[ "${1-}" != "" && -f "${1}" ]]; then
  DATA_PAYLOAD=$(cat "$1")
else
  DATA_PAYLOAD='{"firstName":"Noah","lastName":"Smith","email":"noah.smith@sydney.example"}'
fi

echo "==> GET ${BASE_URL}${ENDPOINT}"
set +e
GET_OUT=$($CURL_BIN -s -H 'Accept: application/json' "${BASE_URL}${ENDPOINT}")
GET_STATUS=$?
set -e
if [[ $GET_STATUS -ne 0 ]]; then
  echo "Error: Failed to GET ${BASE_URL}${ENDPOINT}. Is the server running?" >&2
  exit 1
fi
printf "%s" "$GET_OUT" | pretty_json

echo
echo "==> POST ${BASE_URL}${ENDPOINT}"
HTTP_RESPONSE=$($CURL_BIN -s -D - -o /dev/null -X POST "${BASE_URL}${ENDPOINT}" \
  -H 'Content-Type: application/json' \
  -d "${DATA_PAYLOAD}")
# Show headers
echo "$HTTP_RESPONSE"
STATUS_CODE=$(printf "%s" "$HTTP_RESPONSE" | head -n 1 | awk '{print $2}')
if [[ "$STATUS_CODE" != "201" ]]; then
  echo "Error: Expected 201 Created, got ${STATUS_CODE}" >&2
  exit 1
fi

echo
echo "==> GET (after POST) ${BASE_URL}${ENDPOINT}"
$CURL_BIN -s -H 'Accept: application/json' "${BASE_URL}${ENDPOINT}" | pretty_json

echo
echo "All requests completed."
