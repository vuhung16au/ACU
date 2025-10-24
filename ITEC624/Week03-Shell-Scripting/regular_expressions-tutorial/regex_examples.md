# Regular Expression Examples

This document provides practical examples of using regular expressions for common validation and text processing tasks.

## Table of Contents
1. [Email Validation](#email-validation)
2. [Phone Number Validation](#phone-number-validation)
3. [Date Validation](#date-validation)
4. [URL Validation](#url-validation)
5. [HTML Tag Validation](#html-tag-validation)
6. [Credit Card Validation](#credit-card-validation)
7. [Password Validation](#password-validation)
8. [Data Extraction Examples](#data-extraction-examples)

## Email Validation

### Basic Email Pattern
```regex
^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$
```

**Explanation:**
- `^` - Start of string
- `[a-zA-Z0-9._%+-]+` - One or more alphanumeric characters, dots, underscores, percent signs, plus signs, or hyphens
- `@` - Literal @ symbol
- `[a-zA-Z0-9.-]+` - One or more alphanumeric characters, dots, or hyphens for domain
- `\.` - Literal dot
- `[a-zA-Z]{2,}` - Two or more letters for top-level domain
- `$` - End of string

**Examples:**
- ✅ `user@example.com`
- ✅ `test.email@domain.org`
- ✅ `user+tag@example.co.uk`
- ❌ `invalid.email`
- ❌ `@example.com`
- ❌ `user@`

### Advanced Email Pattern (RFC 5322 compliant)
```regex
^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$
```

## Phone Number Validation

### US Phone Number (Various Formats)
```regex
^(\+1[-.\s]?)?\(?([0-9]{3})\)?[-.\s]?([0-9]{3})[-.\s]?([0-9]{4})$
```

**Explanation:**
- `(\+1[-.\s]?)?` - Optional country code +1 with optional separator
- `\(?([0-9]{3})\)?` - Optional parentheses around 3-digit area code
- `[-.\s]?` - Optional separator (hyphen, dot, or space)
- `([0-9]{3})` - 3-digit exchange code
- `[-.\s]?` - Optional separator
- `([0-9]{4})` - 4-digit number

**Examples:**
- ✅ `(123) 456-7890`
- ✅ `123-456-7890`
- ✅ `123.456.7890`
- ✅ `1234567890`
- ✅ `+1 (123) 456-7890`
- ❌ `123-45-6789`
- ❌ `123-456-789`

### International Phone Number
```regex
^\+[1-9]\d{1,14}$
```

## Date Validation

### ISO 8601 Date Format (YYYY-MM-DD)
```regex
^\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$
```

**Explanation:**
- `\d{4}` - Four digits for year
- `-` - Literal hyphen
- `(0[1-9]|1[0-2])` - Month: 01-09 or 10-12
- `-` - Literal hyphen
- `(0[1-9]|[12][0-9]|3[01])` - Day: 01-09, 10-29, or 30-31

**Examples:**
- ✅ `2023-12-25`
- ✅ `2023-01-01`
- ✅ `2023-02-28`
- ❌ `23-12-25`
- ❌ `2023-13-01`
- ❌ `2023-02-30`

### US Date Format (MM/DD/YYYY)
```regex
^(0[1-9]|1[0-2])/(0[1-9]|[12][0-9]|3[01])/\d{4}$
```

### European Date Format (DD/MM/YYYY)
```regex
^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\d{4}$
```

## URL Validation

### Basic URL Pattern
```regex
^https?://[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}(/.*)?$
```

**Explanation:**
- `https?` - http or https
- `://` - Literal ://
- `[a-zA-Z0-9.-]+` - Domain name with letters, numbers, dots, and hyphens
- `\.` - Literal dot
- `[a-zA-Z]{2,}` - Top-level domain (2+ letters)
- `(/.*)?` - Optional path

**Examples:**
- ✅ `https://www.example.com`
- ✅ `http://example.org/path/to/page`
- ✅ `https://subdomain.example.co.uk`
- ❌ `ftp://example.com`
- ❌ `example.com`
- ❌ `https://`

### Advanced URL Pattern
```regex
^https?://(?:[-\w.])+(?:\:[0-9]+)?(?:/(?:[\w/_.])*(?:\?(?:[\w&=%.])*)?(?:\#(?:[\w.])*)?)?$
```

## HTML Tag Validation

### Basic HTML Tag
```regex
<[^>]+>
```

**Explanation:**
- `<` - Opening angle bracket
- `[^>]+` - One or more characters that are not closing angle bracket
- `>` - Closing angle bracket

**Examples:**
- ✅ `<div>`
- ✅ `<img src="image.jpg" alt="Image">`
- ✅ `<a href="https://example.com">Link</a>`
- ❌ `<div`
- ❌ `div>`

### HTML Tag with Content
```regex
<([a-zA-Z][a-zA-Z0-9]*)\b[^>]*>(.*?)</\1>
```

**Explanation:**
- `<([a-zA-Z][a-zA-Z0-9]*)` - Opening tag with captured tag name
- `\b[^>]*>` - Word boundary, any attributes, closing bracket
- `(.*?)` - Captured content (non-greedy)
- `</\1>` - Closing tag using backreference to captured group

## Credit Card Validation

### Basic Credit Card Pattern
```regex
^\d{4}[-\s]?\d{4}[-\s]?\d{4}[-\s]?\d{4}$
```

**Examples:**
- ✅ `1234 5678 9012 3456`
- ✅ `1234-5678-9012-3456`
- ✅ `1234567890123456`
- ❌ `1234-567-890-123-456`
- ❌ `1234 5678 9012`

### Credit Card with Luhn Algorithm
```regex
^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14}|3[47][0-9]{13}|3[0-9]{13}|6(?:011|5[0-9]{2})[0-9]{12})$
```

## Password Validation

### Strong Password (8+ chars, uppercase, lowercase, number, special char)
```regex
^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$
```

**Explanation:**
- `(?=.*[a-z])` - Positive lookahead for lowercase letter
- `(?=.*[A-Z])` - Positive lookahead for uppercase letter
- `(?=.*\d)` - Positive lookahead for digit
- `(?=.*[@$!%*?&])` - Positive lookahead for special character
- `[A-Za-z\d@$!%*?&]{8,}` - 8+ characters from allowed set

**Examples:**
- ✅ `Password123!`
- ✅ `MyStr0ng@Pass`
- ❌ `password123`
- ❌ `PASSWORD123!`
- ❌ `Pass123`

## Data Extraction Examples

### Extract Email Addresses from Text
```regex
\b[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}\b
```

### Extract Phone Numbers
```regex
\b(?:\+?1[-.\s]?)?\(?([0-9]{3})\)?[-.\s]?([0-9]{3})[-.\s]?([0-9]{4})\b
```

### Extract URLs
```regex
https?://[^\s<>"{}|\\^`\[\]]+
```

### Extract Dates
```regex
\b\d{4}-\d{2}-\d{2}\b
```

### Extract ZIP Codes (US)
```regex
\b\d{5}(?:-\d{4})?\b
```

## Advanced Patterns

### Balanced Parentheses
```regex
^\((?:[^()]++|\([^()]*\))*\)$
```

### IPv4 Address
```regex
^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$
```

### IPv6 Address
```regex
^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$
```

### Social Security Number (US)
```regex
^\d{3}-\d{2}-\d{4}$
```

### ISBN-13
```regex
^(?:ISBN(?:-13)?:? )?(?=[0-9]{13}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)97[89][- ]?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9]$
```

## Testing Your Regex

### Online Tools
- [Regex101](https://regex101.com/) - Interactive regex tester
- [RegexPal](https://www.regexpal.com/) - Simple regex tester
- [RegExr](https://regexr.com/) - Regex learning tool

### Command Line Testing
```bash
# Test with grep
echo "test@example.com" | grep -E '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'

# Test with sed
echo "test@example.com" | sed -n '/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/p'
```

## Common Pitfalls

1. **Greedy vs Non-Greedy**: Use `*?` instead of `*` for non-greedy matching
2. **Anchoring**: Always use `^` and `$` for exact matches
3. **Escaping**: Remember to escape special characters
4. **Character Classes**: `[abc]` matches one character, not the sequence "abc"
5. **Case Sensitivity**: Use flags for case-insensitive matching

## Best Practices

1. **Test Thoroughly**: Test with various valid and invalid inputs
2. **Document Patterns**: Add comments to complex regex patterns
3. **Use Tools**: Leverage online regex testers and debuggers
4. **Consider Performance**: Some patterns can be slow on large texts
5. **Validate Input**: Always validate user input before processing
6. **Handle Edge Cases**: Consider empty strings, null values, and special characters

Remember: Regular expressions are powerful but can be complex. Start simple and build up complexity gradually!
