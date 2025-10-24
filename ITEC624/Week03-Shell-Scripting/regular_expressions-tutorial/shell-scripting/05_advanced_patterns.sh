#!/bin/bash

# Advanced Pattern Matching Script
# This script demonstrates advanced regex patterns and techniques

echo "=== Advanced Pattern Matching Examples ==="
echo

# Set the data file path
DATA_FILE="../members.csv"

# Check if data file exists
if [ ! -f "$DATA_FILE" ]; then
    echo "Error: Data file $DATA_FILE not found!"
    exit 1
fi

echo "1. Complex Email Pattern Matching"
echo "----------------------------------"
echo "Emails with specific patterns:"
echo "Emails with firstname.lastname pattern:"
grep -E "[a-zA-Z]+\.[a-zA-Z]+@[a-zA-Z]+\.[a-zA-Z]+" "$DATA_FILE" | head -5
echo

echo "Emails with numbers in username:"
grep -E "[a-zA-Z0-9]+[0-9]+@[a-zA-Z]+\.[a-zA-Z]+" "$DATA_FILE" | head -5
echo

echo "2. Advanced Date Pattern Matching"
echo "---------------------------------"
echo "Dates from specific decades:"
echo "1980s:"
grep -E "198[0-9]-[0-9]{2}-[0-9]{2}" "$DATA_FILE" | head -5
echo

echo "1990s:"
grep -E "199[0-9]-[0-9]{2}-[0-9]{2}" "$DATA_FILE" | head -5
echo

echo "3. Complex Name Pattern Matching"
echo "---------------------------------"
echo "Names with specific patterns:"
echo "Names starting with 'J' and ending with 'n':"
grep -E "^[0-9]+,J[a-zA-Z]*n," "$DATA_FILE" | head -5
echo

echo "Names with double letters:"
grep -E "[a-zA-Z]*([a-zA-Z])\1[a-zA-Z]*" "$DATA_FILE" | head -5
echo

echo "4. Advanced Address Pattern Matching"
echo "------------------------------------"
echo "Addresses in specific states:"
echo "California addresses:"
grep -E ", CA [0-9]{5}$" "$DATA_FILE" | head -5
echo

echo "New York addresses:"
grep -E ", NY [0-9]{5}$" "$DATA_FILE" | head -5
echo

echo "Texas addresses:"
grep -E ", TX [0-9]{5}$" "$DATA_FILE" | head -5
echo

echo "5. Credit Score Pattern Analysis"
echo "--------------------------------"
echo "Credit score ranges:"
echo "Excellent (800+):"
grep -E "8[0-9][0-9]" "$DATA_FILE" | wc -l
echo

echo "Good (700-799):"
grep -E "7[0-9][0-9]" "$DATA_FILE" | wc -l
echo

echo "Fair (600-699):"
grep -E "6[0-9][0-9]" "$DATA_FILE" | wc -l
echo

echo "6. Height and Weight Pattern Analysis"
echo "------------------------------------"
echo "Height ranges:"
echo "Tall (180cm+):"
awk -F',' 'NR>1 && $8 >= 180 {count++} END {print count+0}' "$DATA_FILE"
echo

echo "Average height (160-180cm):"
awk -F',' 'NR>1 && $8 >= 160 && $8 <= 180 {count++} END {print count+0}' "$DATA_FILE"
echo

echo "Short (<160cm):"
awk -F',' 'NR>1 && $8 < 160 {count++} END {print count+0}' "$DATA_FILE"
echo

echo "7. Gender and Credit Score Correlation"
echo "-------------------------------------"
echo "Male average credit score:"
awk -F',' 'NR>1 && $7 == "Male" {sum+=$6; count++} END {print (count>0) ? sum/count : 0}' "$DATA_FILE"
echo

echo "Female average credit score:"
awk -F',' 'NR>1 && $7 == "Female" {sum+=$6; count++} END {print (count>0) ? sum/count : 0}' "$DATA_FILE"
echo

echo "8. Age Group Analysis"
echo "--------------------"
echo "Age groups (assuming current year 2024):"
echo "20-30 years old:"
awk -F',' 'NR>1 {
    year = substr($3, 1, 4)
    age = 2024 - year
    if (age >= 20 && age <= 30) count++
} END {print count+0}' "$DATA_FILE"
echo

echo "31-40 years old:"
awk -F',' 'NR>1 {
    year = substr($3, 1, 4)
    age = 2024 - year
    if (age >= 31 && age <= 40) count++
} END {print count+0}' "$DATA_FILE"
echo

echo "9. Complex Pattern Matching with Multiple Conditions"
echo "-----------------------------------------------------"
echo "People with specific characteristics:"
echo "High credit score, tall, male:"
awk -F',' 'NR>1 && $6 > 800 && $7 == "Male" && $8 > 175 {print $2, $6, $8}' "$DATA_FILE" | head -5
echo

echo "Young, high credit score, specific state:"
awk -F',' 'NR>1 && $3 ~ /^199[0-9]/ && $6 > 750 && $5 ~ /, CA / {print $2, $3, $6, $5}' "$DATA_FILE" | head -5
echo

echo "10. Pattern Frequency Analysis"
echo "------------------------------"
echo "Most common first names:"
awk -F',' 'NR>1 {print $2}' "$DATA_FILE" | awk '{print $1}' | sort | uniq -c | sort -nr | head -5
echo

echo "Most common last names:"
awk -F',' 'NR>1 {print $2}' "$DATA_FILE" | awk '{print $2}' | sort | uniq -c | sort -nr | head -5
echo

echo "11. Email Domain Analysis"
echo "-------------------------"
echo "Email domain distribution:"
awk -F',' 'NR>1 {print $4}' "$DATA_FILE" | sed 's/.*@\(.*\)/\1/' | sort | uniq -c | sort -nr
echo

echo "12. Address Pattern Analysis"
echo "----------------------------"
echo "Street name patterns:"
awk -F',' 'NR>1 {print $5}' "$DATA_FILE" | sed 's/"\([^"]*\)".*/\1/' | awk '{print $1}' | sort | uniq -c | sort -nr | head -5
echo

echo "13. Credit Score Distribution"
echo "----------------------------"
echo "Credit score ranges:"
echo "300-399: $(awk -F',' 'NR>1 && $6 >= 300 && $6 < 400 {count++} END {print count+0}' "$DATA_FILE")"
echo "400-499: $(awk -F',' 'NR>1 && $6 >= 400 && $6 < 500 {count++} END {print count+0}' "$DATA_FILE")"
echo "500-599: $(awk -F',' 'NR>1 && $6 >= 500 && $6 < 600 {count++} END {print count+0}' "$DATA_FILE")"
echo "600-699: $(awk -F',' 'NR>1 && $6 >= 600 && $6 < 700 {count++} END {print count+0}' "$DATA_FILE")"
echo "700-799: $(awk -F',' 'NR>1 && $6 >= 700 && $6 < 800 {count++} END {print count+0}' "$DATA_FILE")"
echo "800-850: $(awk -F',' 'NR>1 && $6 >= 800 && $6 <= 850 {count++} END {print count+0}' "$DATA_FILE")"
echo

echo "14. Advanced Data Correlation"
echo "-----------------------------"
echo "Correlation between height and weight:"
echo "Tall and heavy (height > 175cm, weight > 80kg):"
awk -F',' 'NR>1 && $8 > 175 && $9 > 80 {count++} END {print count+0}' "$DATA_FILE"
echo

echo "Short and light (height < 165cm, weight < 60kg):"
awk -F',' 'NR>1 && $8 < 165 && $9 < 60 {count++} END {print count+0}' "$DATA_FILE"
echo

echo "15. Pattern Matching with Lookahead"
echo "-----------------------------------"
echo "Names with specific patterns:"
echo "Names starting with 'A' and ending with 'a':"
grep -E "^[0-9]+,A[a-zA-Z]*a," "$DATA_FILE" | head -5
echo

echo "16. Complex Validation Patterns"
echo "-------------------------------"
echo "Validating data integrity:"
echo "Records with complete information:"
awk -F',' 'NR>1 && $2 != "" && $3 != "" && $4 != "" && $5 != "" && $6 != "" && $7 != "" && $8 != "" && $9 != "" {count++} END {print count+0}' "$DATA_FILE"
echo

echo "17. Pattern Extraction and Transformation"
echo "----------------------------------------"
echo "Transforming data formats:"
echo "Converting dates to different format:"
awk -F',' 'NR>1 {
    split($3, date, "-")
    printf "%s/%s/%s\n", date[2], date[3], date[1]
}' "$DATA_FILE" | head -5
echo

echo "18. Advanced Statistical Analysis"
echo "---------------------------------"
echo "Statistical measures:"
echo "Credit score statistics:"
awk -F',' 'NR>1 {
    scores[NR-1] = $6
    sum += $6
}
END {
    n = NR - 1
    avg = sum / n
    print "Average:", avg
    print "Total records:", n
}' "$DATA_FILE"
echo

echo "19. Pattern Matching with Multiple Files"
echo "----------------------------------------"
echo "Creating filtered datasets:"
echo "High credit score people:"
awk -F',' 'NR>1 && $6 > 800 {print $0}' "$DATA_FILE" > high_credit.csv
echo "Created high_credit.csv with $(wc -l < high_credit.csv) records"
echo

echo "20. Complex Pattern Matching Summary"
echo "------------------------------------"
echo "Pattern matching summary:"
echo "Total records processed: $(wc -l < "$DATA_FILE")"
echo "Records with valid email: $(grep -c -E "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" "$DATA_FILE")"
echo "Records with valid date: $(grep -c -E "[0-9]{4}-[0-9]{2}-[0-9]{2}" "$DATA_FILE")"
echo "Records with high credit score: $(grep -c -E "8[0-9][0-9]" "$DATA_FILE")"
echo

echo "=== End of Advanced Pattern Matching Examples ==="
