# Regular-Expressions

Pattern matching, validation

## 📋 Overview
This project introduces Regular Expressions (regex) in Java, providing a practical guide for graduate students transitioning from Python. It covers the basics of regex syntax, pattern matching, and real-world applications in text processing.

## 📁 Files in this Directory
- `RegularExpressions.java`: Main source file demonstrating regex usage in Java.
- `Makefile`: Automates compilation and running of the Java code.
- `README.md`: This documentation file.
- `examples/Example1.java`: Simple regex pattern matching example.
- `examples/Example2.java`: Regex for extracting and validating data.
- `examples/Advanced.java`: Advanced regex features and real-world scenarios.
- `data/input.txt`: Sample input text for regex processing.
- `data/sample.csv`: Sample CSV data for regex extraction.
- `docs/concepts.md`: In-depth explanation of regex concepts in Java.

## 🛠 Building and Running
1. **Compile:**
   ```sh
   make
   ```
2. **Run Main Example:**
   ```sh
   make run
   ```
3. **Run Example Files:**
   ```sh
   make run-example1
   make run-example2
   make run-advanced
   ```

## 📚 Learning Objectives
- Understand regex syntax and usage in Java
- Learn to match, extract, and validate text patterns
- Compare Java regex with Python's `re` module
- Apply regex to real-world text processing tasks

## 🗝️ Key Takeaways
- Java's `Pattern` and `Matcher` classes are central to regex
- Regex is powerful for searching, extracting, and validating text
- Syntax is similar to Python but API usage differs

## 📖 Important Concepts
- Regex syntax: literals, metacharacters, quantifiers, groups
- Pattern compilation and matching in Java
- Common pitfalls and best practices
- Differences from Python's regex usage

## 🔍 Code Examples
```java
// Simple match
Pattern pattern = Pattern.compile("\\d{3}-\\d{2}-\\d{4}"); // SSN format
Matcher matcher = pattern.matcher("123-45-6789");
System.out.println(matcher.matches()); // true

// Python comparison:
// import re
// print(bool(re.match(r"\\d{3}-\\d{2}-\\d{4}", "123-45-6789")))
```

## 📝 Notes for Python Developers
- Java requires explicit pattern compilation (`Pattern.compile`)
- Use `Matcher` for operations; no direct `re.match` equivalent
- Methods like `find()`, `matches()`, and `group()` are used for matching and extraction
- Regex syntax is nearly identical, but API usage is more verbose in Java
