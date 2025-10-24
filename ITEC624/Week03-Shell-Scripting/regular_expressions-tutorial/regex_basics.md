# Regular Expressions (Regex) Basics Tutorial

## Table of Contents
1. [Introduction](#introduction)
2. [Basic Characters](#basic-characters)
3. [Character Classes](#character-classes)
4. [Quantifiers](#quantifiers)
5. [Anchors](#anchors)
6. [Groups and Alternation](#groups-and-alternation)
7. [Special Characters](#special-characters)
8. [Common Patterns](#common-patterns)
9. [Practice Exercises](#practice-exercises)

## Introduction

Regular expressions (regex) are powerful patterns used to match, search, and manipulate text. They are supported by most programming languages and command-line tools like `grep`, `sed`, and `awk`.

### What are Regular Expressions?
Regular expressions are sequences of characters that define a search pattern. They are used to:
- Find specific text patterns
- Validate input formats
- Extract information from text
- Replace text patterns
- Split text into parts

## Basic Characters

### Literal Characters
Most characters match themselves literally.

| Pattern | Matches | Example |
|---------|---------|---------|
| `a` | The letter 'a' | `"cat"` matches `a` |
| `123` | The sequence "123" | `"12345"` matches `123` |

### Special Characters (Metacharacters)
These characters have special meaning in regex and need to be escaped with `\` to match literally:

| Character | Special Meaning | To Match Literally |
|-----------|----------------|-------------------|
| `.` | Any character | `\.` |
| `*` | Zero or more | `\*` |
| `+` | One or more | `\+` |
| `?` | Zero or one | `\?` |
| `^` | Start of string | `\^` |
| `$` | End of string | `\$` |
| `|` | Alternation | `\|` |
| `()` | Grouping | `\(\)` |
| `[]` | Character class | `\[\]` |
| `{}` | Quantifier | `\{\}` |

## Character Classes

Character classes allow you to match any one character from a set of characters.

### Basic Character Classes

| Pattern | Description | Matches | Example |
|---------|-------------|---------|---------|
| `[abc]` | Any of a, b, or c | Single character | `"cat"` matches `[abc]` at position 0 |
| `[^abc]` | Any character except a, b, or c | Single character | `"dog"` matches `[^abc]` at position 0 |
| `[a-z]` | Any lowercase letter | Single character | `"hello"` matches `[a-z]` at position 0 |
| `[A-Z]` | Any uppercase letter | Single character | `"Hello"` matches `[A-Z]` at position 0 |
| `[0-9]` | Any digit | Single character | `"123"` matches `[0-9]` at position 0 |
| `[a-zA-Z]` | Any letter (case insensitive) | Single character | `"Hello"` matches `[a-zA-Z]` at position 0 |
| `[a-zA-Z0-9]` | Any alphanumeric character | Single character | `"Hello123"` matches `[a-zA-Z0-9]` at position 0 |

### Predefined Character Classes

| Pattern | Description | Equivalent | Example |
|---------|-------------|------------|---------|
| `\d` | Any digit | `[0-9]` | `"123"` matches `\d` |
| `\D` | Any non-digit | `[^0-9]` | `"abc"` matches `\D` |
| `\w` | Any word character | `[a-zA-Z0-9_]` | `"hello123"` matches `\w` |
| `\W` | Any non-word character | `[^a-zA-Z0-9_]` | `"!@#"` matches `\W` |
| `\s` | Any whitespace character | `[ \t\n\r\f\v]` | `" "` matches `\s` |
| `\S` | Any non-whitespace character | `[^ \t\n\r\f\v]` | `"a"` matches `\S` |

## Quantifiers

Quantifiers specify how many times a character or group should match.

### Basic Quantifiers

| Pattern | Description | Example |
|---------|-------------|---------|
| `a?` | Zero or one 'a' | `"cat"` and `"ct"` both match `ca?t` |
| `a*` | Zero or more 'a's | `"ct"`, `"cat"`, `"caat"` all match `ca*t` |
| `a+` | One or more 'a's | `"cat"`, `"caat"` match `ca+t`, but `"ct"` doesn't |
| `a{3}` | Exactly 3 'a's | `"caaat"` matches `ca{3}t` |
| `a{3,}` | 3 or more 'a's | `"caaat"`, `"caaaat"` match `ca{3,}t` |
| `a{3,6}` | Between 3 and 6 'a's | `"caaat"`, `"caaaaat"` match `ca{3,6}t` |

### Greedy vs Non-Greedy
By default, quantifiers are **greedy** (match as much as possible). Add `?` to make them **non-greedy** (match as little as possible).

| Pattern | Type | Description | Example |
|---------|------|-------------|---------|
| `a*` | Greedy | Matches as many 'a's as possible | `"caaat"` matches `ca*t` → `"aaa"` |
| `a*?` | Non-greedy | Matches as few 'a's as possible | `"caaat"` matches `ca*?t` → `""` |

## Anchors

Anchors don't match characters but match positions in the string.

| Pattern | Description | Example |
|---------|-------------|---------|
| `^` | Start of string | `^hello` matches "hello" at the beginning |
| `$` | End of string | `world$` matches "world" at the end |
| `\b` | Word boundary | `\bcat\b` matches "cat" as a whole word |
| `\B` | Non-word boundary | `\Bcat\B` matches "cat" inside other words |

## Groups and Alternation

### Groups
Groups allow you to apply quantifiers to multiple characters and capture parts of the match.

| Pattern | Description | Example |
|---------|-------------|---------|
| `(abc)` | Capturing group | `(hello)` captures "hello" |
| `(?:abc)` | Non-capturing group | `(?:hello)` groups but doesn't capture |
| `(abc|def)` | Alternation within group | `(cat|dog)` matches "cat" or "dog" |

### Alternation
The `|` operator matches either the pattern before or after it.

| Pattern | Description | Example |
|---------|-------------|---------|
| `cat|dog` | Either "cat" or "dog" | Matches "cat" or "dog" |
| `(cat|dog)s` | Either "cats" or "dogs" | Matches "cats" or "dogs" |

## Special Characters

### The Dot (.)
The dot matches any character except newline (in most implementations).

| Pattern | Description | Example |
|---------|-------------|---------|
| `.` | Any single character | `c.t` matches "cat", "cut", "c1t" |
| `\.` | Literal dot | `\.` matches "." |

### Escaping Special Characters
Use backslash `\` to escape special characters and match them literally.

| Pattern | Matches | Example |
|---------|---------|---------|
| `\.` | Literal dot | `"file.txt"` matches `\.txt` |
| `\*` | Literal asterisk | `"2*3"` matches `\*` |
| `\+` | Literal plus | `"1+1"` matches `\+` |

## Common Patterns

### Email Address
```regex
^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$
```

### Phone Number (US format)
```regex
^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$
```

### Date (YYYY-MM-DD)
```regex
^\d{4}-\d{2}-\d{2}$
```

### URL
```regex
^https?://[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}(/.*)?$
```

### Credit Card Number
```regex
^\d{4}[-\s]?\d{4}[-\s]?\d{4}[-\s]?\d{4}$
```

## Practice Exercises

### Exercise 1: Basic Matching
Match the following patterns in the text "The quick brown fox jumps over the lazy dog":

1. Find all words that start with 'b'
2. Find all 3-letter words
3. Find all words that end with 's'

### Exercise 2: Email Validation
Create a regex to validate email addresses with the following rules:
- Must contain @ symbol
- Must have a domain with at least one dot
- Must not start or end with special characters

### Exercise 3: Phone Number Extraction
Extract phone numbers in various formats:
- (123) 456-7890
- 123-456-7890
- 123.456.7890
- 1234567890

### Exercise 4: Data Cleaning
Clean the following data using regex:
- Remove extra spaces
- Standardize phone number format
- Extract ZIP codes from addresses

## Tips for Learning Regex

1. **Start Simple**: Begin with basic patterns and gradually add complexity
2. **Use Online Tools**: Practice with regex testers like regex101.com
3. **Test Incrementally**: Build patterns step by step and test each part
4. **Read from Left to Right**: Regex patterns are read from left to right
5. **Use Comments**: In complex patterns, use comments to explain each part
6. **Practice Regularly**: Regular practice is key to mastering regex

## Common Mistakes

1. **Forgetting to Escape**: Special characters need to be escaped
2. **Greedy Matching**: Remember that `*` and `+` are greedy by default
3. **Anchoring**: Don't forget `^` and `$` for exact matches
4. **Character Classes**: Remember that `[abc]` matches one character, not the sequence "abc"
5. **Case Sensitivity**: Most regex implementations are case-sensitive by default

## Next Steps

After mastering these basics, you can explore:
- Advanced regex features like lookahead and lookbehind
- Regex in different programming languages
- Performance optimization
- Complex pattern matching
- Regex debugging techniques

Remember: Regular expressions are a powerful tool, but they can become complex quickly. Start simple and build up your skills gradually!
