#!/bin/bash

# Data Extraction Script
# This script demonstrates various data extraction techniques using regex

echo "=== Data Extraction Examples ==="
echo

# Set the data file path
DATA_FILE="../members.csv"

# Check if data file exists
if [ ! -f "$DATA_FILE" ]; then
    echo "Error: Data file $DATA_FILE not found!"
    exit 1
fi

echo "1. Extract Names Only"
echo "----------------------"
echo "First 10 names:"
awk -F',' 'NR>1 {print $2}' "$DATA_FILE" | head -10
echo

echo "2. Extract Email Addresses"
echo "---------------------------"
echo "First 10 email addresses:"
awk -F',' 'NR>1 {print $4}' "$DATA_FILE" | head -10
echo

echo "3. Extract Credit Scores"
echo "-------------------------"
echo "First 10 credit scores:"
awk -F',' 'NR>1 {print $6}' "$DATA_FILE" | head -10
echo

echo "4. Extract States from Addresses"
echo "---------------------------------"
echo "Unique states:"
awk -F',' 'NR>1 {
    if ($5 ~ /, [A-Z]{2} /) {
        state = substr($5, match($5, /, [A-Z]{2} /) + 2, 2)
        print state
    }
}' "$DATA_FILE" | sort | uniq -c | sort -nr
echo

echo "5. Extract ZIP Codes"
echo "--------------------"
echo "First 10 ZIP codes:"
awk -F',' 'NR>1 {
    if ($5 ~ /[0-9]{5}$/) {
        zip = substr($5, length($5) - 4, 5)
        print zip
    }
}' "$DATA_FILE" | head -10
echo

echo "6. Extract Birth Years"
echo "---------------------"
echo "Birth years:"
awk -F',' 'NR>1 {
    year = substr($3, 1, 4)
    print year
}' "$DATA_FILE" | sort | uniq -c | sort -nr
echo

echo "7. Extract Height and Weight"
echo "-----------------------------"
echo "Height and weight data:"
awk -F',' 'NR>1 {print $2, $8 "cm", $9 "kg"}' "$DATA_FILE" | head -10
echo

echo "8. Extract People by Gender"
echo "----------------------------"
echo "Male count: $(awk -F',' 'NR>1 && $7 == "Male" {count++} END {print count+0}' "$DATA_FILE")"
echo "Female count: $(awk -F',' 'NR>1 && $7 == "Female" {count++} END {print count+0}' "$DATA_FILE")"
echo

echo "9. Extract High Credit Score People"
echo "-----------------------------------"
echo "People with credit score > 800:"
awk -F',' 'NR>1 && $6 > 800 {print $2, $6}' "$DATA_FILE" | head -10
echo

echo "10. Extract People by Age Range"
echo "--------------------------------"
echo "People born in 1990s:"
awk -F',' 'NR>1 && $3 ~ /^199[0-9]/ {print $2, $3}' "$DATA_FILE" | head -10
echo

echo "11. Extract Email Domains"
echo "-------------------------"
echo "Email domains:"
awk -F',' 'NR>1 {
    if ($4 ~ /@/) {
        domain = substr($4, index($4, "@") + 1)
        print domain
    }
}' "$DATA_FILE" | sort | uniq -c | sort -nr
echo

echo "12. Extract Address Components"
echo "------------------------------"
echo "Cities:"
awk -F',' 'NR>1 {
    if ($5 ~ /, [A-Za-z ]+, [A-Z]{2} /) {
        # Extract city (between first comma and second comma)
        city = substr($5, index($5, ",") + 2)
        city = substr(city, 1, index(city, ",") - 1)
        print city
    }
}' "$DATA_FILE" | sort | uniq -c | sort -nr | head -10
echo

echo "13. Extract People by Height Range"
echo "----------------------------------"
echo "People with height 170-180cm:"
awk -F',' 'NR>1 && $8 >= 170 && $8 <= 180 {print $2, $8 "cm"}' "$DATA_FILE" | head -10
echo

echo "14. Extract People by Weight Range"
echo "----------------------------------"
echo "People with weight 60-80kg:"
awk -F',' 'NR>1 && $9 >= 60 && $9 <= 80 {print $2, $9 "kg"}' "$DATA_FILE" | head -10
echo

echo "15. Extract Complete Records with Specific Criteria"
echo "---------------------------------------------------"
echo "People with high credit score and specific characteristics:"
awk -F',' 'NR>1 && $6 > 800 && $7 == "Male" && $8 > 175 {print $2, $4, $6, $8 "cm"}' "$DATA_FILE" | head -10
echo

echo "16. Extract and Format Data"
echo "--------------------------"
echo "Formatted output (Name, Email, Credit Score):"
awk -F',' 'NR>1 {printf "%-20s %-30s %s\n", $2, $4, $6}' "$DATA_FILE" | head -10
echo

echo "17. Extract Statistical Information"
echo "-----------------------------------"
echo "Credit score statistics:"
awk -F',' 'NR>1 {
    scores[NR-1] = $6
    sum += $6
}
END {
    n = NR - 1
    avg = sum / n
    print "Average credit score:", avg
    print "Total records:", n
}' "$DATA_FILE"
echo

echo "18. Extract People by Name Pattern"
echo "----------------------------------"
echo "People with names starting with 'J':"
awk -F',' 'NR>1 && $2 ~ /^J/ {print $2}' "$DATA_FILE" | head -10
echo

echo "19. Extract People by Email Pattern"
echo "-----------------------------------"
echo "People with emails containing 'test':"
awk -F',' 'NR>1 && $4 ~ /test/ {print $2, $4}' "$DATA_FILE" | head -10
echo

echo "20. Extract and Sort Data"
echo "-------------------------"
echo "People sorted by credit score (descending):"
awk -F',' 'NR>1 {print $6, $2}' "$DATA_FILE" | sort -nr | head -10
echo

echo "=== End of Data Extraction Examples ==="
