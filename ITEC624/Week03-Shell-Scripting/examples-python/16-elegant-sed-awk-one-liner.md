
**Sed One-Liners**  
1. **Double-space a file:**  
   `sed G`  
   *Elegance:* Simplicity—using the `G` command to append a blank line after each line, with no extra logic needed.

2. **Remove leading whitespace from every line:**  
   `sed 's/^[ \t]*//'`  
   *Elegance:* Uses regex to clean up messy lines, making code more readable or data easier to process.

3. **Delete trailing whitespace from each line:**  
   `sed 's/[ \t]*$//'`  
   *Elegance:* Focused, uses regex to clean only where needed; no side effects.

4. **Center all text to 79 columns:**  
   `sed -e :a -e 's/^.\{1,77\}$/ & /;ta'`  
   *Elegance:* Combines looping and regex for formatting that would otherwise require multiple lines in other languages.

5. **Reverse order of lines in a file (“tac”):**  
   `sed '1!G;h;$!d'`  
   *Elegance:* Achieves reversal with just a few well-chosen buffer operations.

6. **Find and replace all occurrences of “foo” with “bar”:**  
   `sed 's/foo/bar/g'`  
   *Elegance:* Everyone knows it, but it's powerful and concise for global replacements.

7. **Insert a blank line above every line matching regex:**  
   `sed '/regex/{x;p;x;}'`  
   *Elegance:* Clean conditional targeting using sed blocks (`{}`).

8. **Digit group (add commas to numbers):**  
   `sed -e :a -e 's/$$.*[0-9]$$$$[0-9]\{3\}$$/\1,\2/;ta'`  
   *Elegance:* Pattern-recognition and looping for formatting numbers—all inline.

**Awk One-Liners**  
9. **Print the last field of every line:**  
   `awk '{print $NF}'`  
   *Elegance:* `$NF` dynamically refers to last field, regardless of column count.

10. **Print only non-blank lines with line numbers:**  
    `awk 'NF { $0=++a ":" $0 }; { print }'`  
    *Elegance:* Combines conditionals and dynamic variables for selective processing.

11. **Count the total number of lines (“wc -l”):**  
    `awk 'END { print NR }'`  
    *Elegance:* Leverages special END block, showcasing awk’s stream processing nature.

12. **Print every line with more than 4 fields:**  
    `awk 'NF > 4'`  
    *Elegance:* Very readable, uses built-in field counter for filtering.

13. **Sum all values in first column:**  
    `awk '{s+=$1} END {print s}'`  
    *Elegance:* Reduces a column to its sum in just a handful of characters.

14. **Number lines for all files together:**  
    `awk '{ print NR "\t" $0 }'`  
    *Elegance:* Utilizes NR for seamless numbering across files.

15. **Replace every field by its absolute value:**  
    `awk '{ for (i = 1; i <= NF; i++) if ($i < 0) $i = -$i; print }'`  
    *Elegance:* Shows the power of awk’s field references and control structures.

16. **Find line with largest first field:**  
    `awk 'NR == 1 { max = $1; maxline = $0; next; } $1 > max { max=$1; maxline=$0 }; END { print max, maxline }'`  
    *Elegance:* In-line tracking of max value/state, minimal code for useful data extraction.

***

**Why these are elegant:**  
- **Brevity:** These scripts solve complex problems in one line, showcasing Unix philosophy.
- **Readability:** Patterns and field references (awk) are very human-readable.
- **Power:** Many combine regex and stream processing for solutions that would require much more code elsewhere.
- **Idiomatic Use:** They demonstrate deep understanding of Unix tools, using built-in variables and commands for concise logic.

---

Final note: `sed` and `awk` are powerful tools yet `Python` is a more versatile and powerful language.