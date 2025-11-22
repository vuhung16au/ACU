# PDF Password Cracking Guide

## Overview
This guide demonstrates how to crack PDF passwords using the `pdfcrack` command-line tool.

## Prerequisites

### Install pdfcrack
```bash
# On macOS
brew install pdfcrack

# On Ubuntu/Debian
sudo apt-get install pdfcrack

# On CentOS/RHEL
sudo yum install pdfcrack
```

### Install additional tools (optional)
```bash
# For PDF content extraction
brew install poppler  # includes pdftotext

# For PDF decryption
brew install qpdf
```

## Method 1: Built-in Brute Force (Recommended)

### For 6-digit numeric passwords:
```bash
pdfcrack -f filename.pdf -n 6 -m 6 -c 0123456789
```

### Parameters:
- `-f filename.pdf`: Input PDF file
- `-n 6`: Minimum password length (6 characters)
- `-m 6`: Maximum password length (6 characters)  
- `-c 0123456789`: Character set (only digits)

### For other password types:
```bash
# Alphanumeric passwords (6-8 characters)
pdfcrack -f filename.pdf -n 6 -m 8 -c abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789

# Lowercase letters only (4-6 characters)
pdfcrack -f filename.pdf -n 4 -m 6 -c abcdefghijklmnopqrstuvwxyz
```

## Method 2: Wordlist Attack

### Generate wordlist:
```python
# Generate 6-digit numeric wordlist
with open('wordlist.txt', 'w') as f:
    for i in range(1000000):
        f.write(f"{i:06d}\n")
```

### Use wordlist:
```bash
pdfcrack -f filename.pdf -w wordlist.txt
```

## Method 3: Dictionary Attack

### Download common password lists:
```bash
# Download rockyou.txt (common passwords)
wget https://github.com/brannondorsey/naive-hashcat/releases/download/data/rockyou.txt

# Use with pdfcrack
pdfcrack -f filename.pdf -w rockyou.txt
```

## Extracting Content After Cracking

### Method 1: Using qpdf (recommended)
```bash
# Decrypt the PDF
qpdf --password=PASSWORD --decrypt input.pdf output.pdf

# Extract text
pdftotext output.pdf content.txt
```

### Method 2: Direct extraction (if supported)
```bash
pdftotext -layout input.pdf content.txt
```

## Python Automation Script

```python
#!/usr/bin/env python3
import subprocess
import os

def crack_pdf_password(pdf_file):
    """Crack PDF password using pdfcrack"""
    cmd = ['pdfcrack', '-f', pdf_file, '-n', '6', '-m', '6', '-c', '0123456789']
    
    try:
        result = subprocess.run(cmd, capture_output=True, text=True)
        
        if "found user-password" in result.stdout:
            # Extract password from output
            lines = result.stdout.split('\n')
            for line in lines:
                if "found user-password" in line:
                    password = line.split("'")[1]
                    return password
        return None
    except Exception as e:
        print(f"Error: {e}")
        return None

# Usage
password = crack_pdf_password("encrypted.pdf")
if password:
    print(f"Password found: {password}")
else:
    print("Password not found")
```

## Performance Tips

1. **Use appropriate character sets**: Limit to known character types
2. **Set reasonable length limits**: Don't try unnecessarily long passwords
3. **Use wordlists for common passwords**: Much faster than brute force
4. **Parallel processing**: Use multiple cores if available

## Common Issues and Solutions

### Issue: "pdfcrack: command not found"
**Solution**: Install pdfcrack using package manager

### Issue: "Permission denied"
**Solution**: Check file permissions and ownership

### Issue: "PDF not encrypted"
**Solution**: Verify the PDF is actually password-protected

### Issue: "Timeout"
**Solution**: Increase timeout or use more targeted attacks

## Security Considerations

- **Legal use only**: Only crack PDFs you own or have permission to crack
- **Ethical hacking**: Use for educational purposes and authorized testing
- **Strong passwords**: This demonstrates why strong passwords are important
- **Rate limiting**: Be respectful of system resources

## Advanced Techniques

### Custom character sets:
```bash
# Only numbers and common symbols
pdfcrack -f file.pdf -c 0123456789!@#$%^&*()

# Specific pattern (e.g., year + 4 digits)
pdfcrack -f file.pdf -c 2020123456789
```

### State saving (for long attacks):
```bash
# Save progress
pdfcrack -f file.pdf -l state.txt

# Resume from saved state
pdfcrack -f file.pdf -l state.txt
```

## Troubleshooting

1. **Check PDF encryption**: Some PDFs use owner passwords vs user passwords
2. **Try different tools**: If pdfcrack fails, try `john` or `hashcat`
3. **Verify file integrity**: Ensure the PDF isn't corrupted
4. **Check system resources**: Ensure sufficient memory and CPU

## Conclusion

PDF password cracking can be an effective security testing tool when used responsibly. The key is choosing the right method based on your knowledge of the password characteristics and using appropriate tools for the job.
