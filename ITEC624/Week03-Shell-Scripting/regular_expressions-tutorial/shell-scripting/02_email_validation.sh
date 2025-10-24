#!/bin/bash

# Email Validation Script
# This script demonstrates email validation using regular expressions

echo "=== Email Validation Examples ==="
echo

# Set the data file path
DATA_FILE="../members.csv"

# Check if data file exists
if [ ! -f "$DATA_FILE" ]; then
    echo "Error: Data file $DATA_FILE not found!"
    exit 1
fi

# Function to validate email format
validate_email() {
    local email="$1"
    if [[ $email =~ ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$ ]]; then
        return 0  # Valid
    else
        return 1  # Invalid
    fi
}

# Function to extract domain from email
extract_domain() {
    local email="$1"
    echo "$email" | sed 's/.*@\(.*\)/\1/'
}

# Function to extract username from email
extract_username() {
    local email="$1"
    echo "$email" | sed 's/\(.*\)@.*/\1/'
}

echo "1. Basic Email Pattern Matching"
echo "--------------------------------"
echo "All email addresses in the dataset:"
grep -o -E "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" "$DATA_FILE" | head -10
echo

echo "2. Email Domain Analysis"
echo "------------------------"
echo "Unique domains:"
grep -o -E "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" "$DATA_FILE" | \
sed 's/.*@\(.*\)/\1/' | sort | uniq -c | sort -nr
echo

echo "3. Email Validation"
echo "-------------------"
echo "Validating all emails in the dataset:"
valid_count=0
invalid_count=0

while IFS=',' read -r no name dob email address credit gender height weight; do
    if [ "$no" != "No" ]; then  # Skip header
        if validate_email "$email"; then
            echo "✓ Valid: $email"
            ((valid_count++))
        else
            echo "✗ Invalid: $email"
            ((invalid_count++))
        fi
    fi
done < "$DATA_FILE"

echo
echo "Validation Summary:"
echo "Valid emails: $valid_count"
echo "Invalid emails: $invalid_count"
echo

echo "4. Email Domain Filtering"
echo "-------------------------"
echo "Emails from 'email.com' domain:"
grep -E "@email\.com" "$DATA_FILE" | awk -F',' '{print $2, $4}'
echo

echo "Emails from 'company.com' domain:"
grep -E "@company\.com" "$DATA_FILE" | awk -F',' '{print $2, $4}'
echo

echo "5. Email Username Analysis"
echo "--------------------------"
echo "Usernames with dots:"
grep -E "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" "$DATA_FILE" | \
grep -o -E "[a-zA-Z0-9._%+-]+@" | \
grep -E "\." | \
sed 's/@$//' | head -5
echo

echo "6. Email Format Standardization"
echo "-------------------------------"
echo "Original emails:"
head -5 "$DATA_FILE" | awk -F',' '{print $4}'
echo
echo "Standardized emails (lowercase):"
head -5 "$DATA_FILE" | awk -F',' '{print $4}' | tr '[:upper:]' '[:lower:]'
echo

echo "7. Email Domain Replacement"
echo "---------------------------"
echo "Replacing all domains with 'example.com':"
sed 's/@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}/@example.com/g' "$DATA_FILE" | head -5 | awk -F',' '{print $2, $4}'
echo

echo "8. Email Pattern Extraction"
echo "--------------------------"
echo "Extracting email patterns:"
echo "Emails with firstname.lastname pattern:"
grep -E "[a-zA-Z]+\.[a-zA-Z]+@[a-zA-Z]+\.[a-zA-Z]+" "$DATA_FILE" | head -5
echo

echo "9. Email Validation with Custom Rules"
echo "-------------------------------------"
echo "Custom validation rules:"
echo "- Must contain @ symbol"
echo "- Must have valid domain"
echo "- Must not start or end with special characters"

while IFS=',' read -r no name dob email address credit gender height weight; do
    if [ "$no" != "No" ]; then  # Skip header
        # Check if email contains @
        if [[ $email == *"@"* ]]; then
            # Check if email doesn't start or end with special characters
            if [[ ! $email =~ ^[._%+-] ]] && [[ ! $email =~ [._%+-]$ ]]; then
                echo "✓ Custom valid: $email"
            else
                echo "✗ Custom invalid (starts/ends with special char): $email"
            fi
        else
            echo "✗ Custom invalid (no @): $email"
        fi
    fi
done < "$DATA_FILE" | head -10
echo

echo "10. Email Statistics"
echo "--------------------"
echo "Total emails: $(grep -c -E "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" "$DATA_FILE")"
echo "Unique domains: $(grep -o -E "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" "$DATA_FILE" | sed 's/.*@\(.*\)/\1/' | sort | uniq | wc -l)"
echo "Most common domain: $(grep -o -E "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" "$DATA_FILE" | sed 's/.*@\(.*\)/\1/' | sort | uniq -c | sort -nr | head -1 | awk '{print $2}')"
echo

echo "=== End of Email Validation Examples ==="
