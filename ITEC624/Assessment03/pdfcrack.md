# PDFCrack

## What is PDFCrack?

PDFCrack is a command-line password recovery tool for PDF files. It's a GNU/Linux tool (compatible with other POSIX systems) designed to crack passwords and recover content from password-protected PDF documents. The tool is small, lightweight, and doesn't require external dependencies.

## Key Features

- Supports standard security handler (revision 2, 3, and 4) on all known PDF versions
- Can crack both owner and user passwords
- Supports wordlist attacks and brute force attacks
- Simple permutations (e.g., trying first character as uppercase)
- Ability to save and load running jobs
- Built-in benchmarking capabilities
- Optimized search for owner-password when user-password is known

## Installation

### macOS (using Homebrew)

```bash
brew install pdfcrack
```

### Linux (Ubuntu/Debian)

```bash
sudo apt-get install pdfcrack
```

### From Source

```bash
# Download from SourceForge and compile
wget http://sourceforge.net/projects/pdfcrack/files/pdfcrack/pdfcrack-0.21/pdfcrack-0.21.tar.gz
tar -xzf pdfcrack-0.21.tar.gz
cd pdfcrack-0.21
make
```

## Basic Usage

### Simple Password Cracking

```bash
# Basic brute force attack
pdfcrack -f encrypted.pdf

# Using a wordlist
pdfcrack -f encrypted.pdf -w wordlist.txt

# Specify minimum and maximum password length
pdfcrack -f encrypted.pdf -m 4 -x 8
```

### Advanced Options

```bash
# Crack user password (default)
pdfcrack -f encrypted.pdf -u

# Crack owner password
pdfcrack -f encrypted.pdf -o

# Save progress to file
pdfcrack -f encrypted.pdf -s savefile.sav

# Load saved progress
pdfcrack -f encrypted.pdf -l savefile.sav

# Benchmark mode
pdfcrack -b
```

### Example Commands

```bash
# Crack a PDF with wordlist attack
pdfcrack -f document.pdf -w /usr/share/wordlists/rockyou.txt

# Brute force with specific character set and length
pdfcrack -f document.pdf -c "0123456789" -m 4 -x 6

# Resume a previous cracking session
pdfcrack -f document.pdf -l previous_session.sav
```

## Common Use Cases

1. **Forgotten Password Recovery**: Recover passwords for your own PDF files
2. **Security Testing**: Test the strength of PDF passwords in security assessments
3. **Digital Forensics**: Analyze password-protected documents in forensic investigations

## Important Notes

- PDFCrack is intended for legitimate password recovery and security testing
- Always ensure you have proper authorization before attempting to crack PDF passwords
- The tool's effectiveness depends on password complexity and length
- Brute force attacks can be time-consuming for complex passwords

## Legal and Ethical Considerations

- Only use PDFCrack on PDF files you own or have explicit permission to test
- Respect applicable laws and regulations regarding password cracking
- Use responsibly for educational, research, or legitimate recovery purposes

## References

- Official Website: <https://pdfcrack.sourceforge.net/>
- Homebrew Formula: <https://formulae.brew.sh/formula/pdfcrack>
- License: GPL-2.0-or-later