#!/bin/bash

# Basic Regex Search Examples
# This script demonstrates basic regex pattern matching using grep, sed, and awk

echo "=== Basic Regex Search Examples ==="
echo

# Set the data file path
DATA_FILE="../members.csv"

# Check if data file exists
if [ ! -f "$DATA_FILE" ]; then
    echo "Error: Data file $DATA_FILE not found!"
    exit 1
fi

echo "1. Searching for names starting with 'J'"
echo "----------------------------------------"
grep -E "^[0-9]+,J" "$DATA_FILE" | head -5
echo

echo "2. Searching for email addresses"
echo "--------------------------------"
grep -E "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" "$DATA_FILE" | head -5
echo

echo "3. Searching for dates in YYYY-MM-DD format"
echo "-------------------------------------------"
grep -E "[0-9]{4}-[0-9]{2}-[0-9]{2}" "$DATA_FILE" | head -5
echo

echo "4. Searching for high credit scores (800+)"
echo "------------------------------------------"
grep -E "8[0-9][0-9]" "$DATA_FILE" | head -5
echo

echo "5. Searching for addresses in California"
echo "----------------------------------------"
grep -E ", CA [0-9]{5}$" "$DATA_FILE" | head -5
echo

echo "6. Searching for names with specific pattern"
echo "--------------------------------------------"
grep -E "[A-Z][a-z]+ [A-Z][a-z]+" "$DATA_FILE" | head -5
echo

echo "7. Case-insensitive search for 'john'"
echo "-------------------------------------"
grep -i "john" "$DATA_FILE" | head -5
echo

echo "8. Counting matches"
echo "------------------"
echo "Total lines with email addresses: $(grep -c -E "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" "$DATA_FILE")"
echo "Total lines with dates: $(grep -c -E "[0-9]{4}-[0-9]{2}-[0-9]{2}" "$DATA_FILE")"
echo "Total lines with high credit scores: $(grep -c -E "8[0-9][0-9]" "$DATA_FILE")"
echo

echo "9. Using sed for pattern replacement"
echo "------------------------------------"
echo "Original:"
head -3 "$DATA_FILE"
echo
echo "After replacing 'email.com' with 'company.com':"
sed 's/@email\.com/@company.com/g' "$DATA_FILE" | head -3
echo

echo "10. Using awk for field extraction"
echo "----------------------------------"
echo "Names and emails:"
awk -F',' 'NR>1 {print $2, $4}' "$DATA_FILE" | head -5
echo

echo "11. Using awk for pattern matching"
echo "----------------------------------"
echo "People with credit score > 800:"
awk -F',' 'NR>1 && $6 > 800 {print $2, $6}' "$DATA_FILE" | head -5
echo

echo "12. Complex pattern matching"
echo "-----------------------------"
echo "People with specific characteristics (Male, Credit > 750, Height > 175cm):"
awk -F',' 'NR>1 && $7 == "Male" && $6 > 750 && $8 > 175 {print $2, $6, $8}' "$DATA_FILE" | head -5
echo

echo "=== End of Basic Regex Search Examples ==="
