# PDF Password Cracking Results

## Password Found
**Password: `dddddd`**

## Secret Message
```
Congratulations!!!
   You got it!!
```

## Cracking Process Summary

1. **Tool Used**: `pdfcrack` command line tool
2. **Method**: Brute force attack with 6-digit numeric passwords
3. **Command**: `/opt/homebrew/bin/pdfcrack -f Act2.pdf -n 6 -m 6 -c 0123456789`
4. **Result**: Successfully cracked the password in seconds

## Technical Details

- **PDF Version**: 1.6
- **Security Handler**: Standard
- **Encryption**: AES-128
- **Password Length**: 6 digits
- **Password Characters**: Numeric only (0-9)

## Files Generated

- `b.py` - Python script for PDF password cracking
- `b.txt` - Detailed output and results
- `guide.md` - Step-by-step guide for PDF password cracking

## Conclusion

The PDF password was successfully cracked using `pdfcrack`'s optimized brute force attack. The password `dddddd` was found quickly due to the tool's efficient implementation for numeric password generation.