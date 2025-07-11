# Regular Expressions in Java

## What is a Regular Expression?
A regular expression (regex) is a sequence of characters that defines a search pattern, mainly for use in pattern matching with strings.

## Java Regex API
- `Pattern`: Compiles a regex into a pattern.
- `Matcher`: Applies the pattern to a string.

## Basic Syntax
- `.` : Any character
- `*` : Zero or more
- `+` : One or more
- `?` : Zero or one
- `[]`: Character class
- `()` : Grouping
- `|` : OR
- `^` : Start of string
- `$` : End of string

## Example: Match a Date
```java
Pattern p = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");
Matcher m = p.matcher("2024-06-01");
System.out.println(m.matches()); // true
```

## Python Comparison
- Java: `Pattern.compile`, `Matcher`
- Python: `re.compile`, `re.match`, `re.findall`

## Common Pitfalls
- Java requires double escaping (e.g., `\\d`)
- Always use `Pattern.compile` for reuse

## Best Practices
- Precompile patterns for performance
- Use groups for extraction
- Validate input with regex
