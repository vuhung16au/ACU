# Regular Expressions Tutorial for Beginners

A comprehensive tutorial for learning Regular Expressions using command-line tools, shell scripting, and Python.

## 📚 Table of Contents

1. [Overview](#overview)
2. [Prerequisites](#prerequisites)
3. [Tutorial Structure](#tutorial-structure)
4. [Getting Started](#getting-started)
5. [Learning Path](#learning-path)
6. [Examples and Exercises](#examples-and-exercises)
7. [Best Practices](#best-practices)
8. [Troubleshooting](#troubleshooting)
9. [Contributing](#contributing)
10. [License](#license)

## 🎯 Overview

This tutorial is designed for beginners who want to learn Regular Expressions (regex) through hands-on examples using:

- **Command-line tools** (grep, sed, awk)
- **Shell scripting** (Bash)
- **Python** (with Jupyter notebooks)

The tutorial includes a realistic dataset (`members.csv`) with 100 sample records to practice various regex patterns and techniques.

## 📋 Prerequisites

### Basic Requirements
- Basic understanding of text processing
- Familiarity with command-line interface
- Basic knowledge of programming concepts

### Software Requirements
- **Unix/Linux/macOS** terminal or **Windows** with WSL/Git Bash
- **Python 3.7+** with required packages:
  - `pandas`
  - `matplotlib`
  - `seaborn`
  - `numpy`
- **Jupyter Notebook** (for Python examples)
- **Text editor** (VS Code, Sublime Text, etc.)

### Optional Tools
- **Regex testing tools**: [regex101.com](https://regex101.com/), [regexr.com](https://regexr.com/)
- **Code editor** with regex support

## 📁 Tutorial Structure

```
regular_expressions-tutorial/
├── shell-scripting/           # Shell script examples
│   ├── 01_basic_regex_search.sh
│   ├── 02_email_validation.sh
│   ├── 03_data_extraction.sh
│   ├── 04_data_validation.sh
│   └── 05_advanced_patterns.sh
├── python/                    # Python Jupyter notebooks
│   └── regular_expressions_tutorial.ipynb
├── regex_basics.md           # Comprehensive regex tutorial
├── regex_examples.md         # Practical examples
├── command-line-examples.md  # Command-line examples
├── members.csv               # Sample dataset (100 records)
├── members.md               # Dataset description
└── README.md                # This file
```

## 🚀 Getting Started

### 1. Clone or Download
```bash
# If using git
git clone <repository-url>
cd regular_expressions-tutorial

# Or download and extract the ZIP file
```

### 2. Set Up Python Environment
```bash
# Create virtual environment (recommended)
python -m venv regex-tutorial-env
source regex-tutorial-env/bin/activate  # On Windows: regex-tutorial-env\Scripts\activate

# Install required packages
pip install pandas matplotlib seaborn numpy jupyter
```

### 3. Make Shell Scripts Executable
```bash
chmod +x shell-scripting/*.sh
```

## 📖 Learning Path

### Phase 1: Fundamentals
1. **Start with `regex_basics.md`**
   - Learn basic regex syntax
   - Understand character classes and quantifiers
   - Practice with simple patterns

2. **Review `regex_examples.md`**
   - Study practical examples
   - Learn common patterns for validation
   - Understand real-world applications

### Phase 2: Command-Line Practice
1. **Read `command-line-examples.md`**
   - Learn grep, sed, and awk usage
   - Practice with the sample dataset
   - Understand command-line regex options

2. **Run shell scripts**
   ```bash
   # Basic regex search
   ./shell-scripting/01_basic_regex_search.sh
   
   # Email validation
   ./shell-scripting/02_email_validation.sh
   
   # Data extraction
   ./shell-scripting/03_data_extraction.sh
   
   # Data validation
   ./shell-scripting/04_data_validation.sh
   
   # Advanced patterns
   ./shell-scripting/05_advanced_patterns.sh
   ```

### Phase 3: Python Implementation
1. **Open Jupyter Notebook**
   ```bash
   jupyter notebook python/regular_expressions_tutorial.ipynb
   ```

2. **Follow the notebook sections**
   - Run each cell sequentially
   - Experiment with different patterns
   - Modify examples to practice

## 💡 Examples and Exercises

### Basic Patterns
```bash
# Find email addresses
grep -E "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}" members.csv

# Find dates
grep -E "\d{4}-\d{2}-\d{2}" members.csv

# Find high credit scores
grep -E "8[0-9][0-9]" members.csv
```

### Shell Scripting
```bash
# Email validation function
validate_email() {
    local email="$1"
    if [[ $email =~ ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$ ]]; then
        return 0
    else
        return 1
    fi
}
```

### Python Examples
```python
import re
import pandas as pd

# Load data
df = pd.read_csv('members.csv')

# Email validation
def validate_email(email):
    pattern = r'^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$'
    return bool(re.match(pattern, email))

# Apply validation
df['Valid_Email'] = df['Email'].apply(validate_email)
```

## 🎯 Learning Objectives

By the end of this tutorial, you will be able to:

### Basic Skills
- [ ] Understand regex syntax and metacharacters
- [ ] Write basic patterns for common use cases
- [ ] Use regex with command-line tools (grep, sed, awk)
- [ ] Apply regex in shell scripts
- [ ] Use regex in Python for data processing

### Intermediate Skills
- [ ] Validate email addresses, phone numbers, and dates
- [ ] Extract information from structured text
- [ ] Clean and standardize data using regex
- [ ] Handle edge cases and error conditions
- [ ] Optimize regex performance

### Advanced Skills
- [ ] Create complex patterns for data validation
- [ ] Use advanced regex features (lookahead, lookbehind)
- [ ] Debug and troubleshoot regex patterns
- [ ] Combine regex with other data processing tools
- [ ] Apply regex in real-world data analysis projects

## 📊 Dataset Information

The tutorial uses a sample dataset (`members.csv`) with the following structure:

| Field | Type | Description | Example |
|-------|------|-------------|---------|
| No | Integer | Member number | 1, 2, 3... |
| Full name | String | Complete name | "John Smith" |
| DoB | Date | Birth date (YYYY-MM-DD) | "1990-05-15" |
| Email | String | Email address | "john.smith@email.com" |
| Address | String | Full address | "123 Main St, New York, NY 10001" |
| Credit score | Integer | Credit score (300-850) | 750 |
| Gender | String | Gender | "Male", "Female" |
| Height (cm) | Integer | Height in centimeters | 175 |
| Weight (kg) | Integer | Weight in kilograms | 70 |

## 🔧 Best Practices

### General Guidelines
1. **Start simple**: Begin with basic patterns and gradually add complexity
2. **Test thoroughly**: Always test patterns on sample data
3. **Document patterns**: Add comments to complex regex patterns
4. **Handle edge cases**: Consider empty strings, null values, and unexpected formats
5. **Validate results**: Always verify your regex results make sense

### Performance Tips
1. **Use specific patterns**: More specific patterns are usually faster
2. **Avoid backtracking**: Use atomic groups when possible
3. **Test on large datasets**: Some patterns can be slow on large files
4. **Use appropriate tools**: Choose the right tool for the job

### Common Pitfalls
1. **Escaping issues**: Remember to escape special characters
2. **Greedy matching**: Use non-greedy quantifiers when needed
3. **Case sensitivity**: Consider case-insensitive matching
4. **Anchoring**: Use `^` and `$` for exact matches

## 🐛 Troubleshooting

### Common Issues

#### Shell Scripts Not Running
```bash
# Make scripts executable
chmod +x shell-scripting/*.sh

# Check file permissions
ls -la shell-scripting/
```

#### Python Import Errors
```bash
# Install missing packages
pip install pandas matplotlib seaborn numpy jupyter

# Check Python version
python --version
```

#### Regex Not Matching
1. Check pattern syntax
2. Verify escaping of special characters
3. Test with online regex tools
4. Use verbose mode for debugging

### Getting Help
- Check the error messages carefully
- Use online regex testers to debug patterns
- Refer to the documentation in each file
- Practice with simpler patterns first

## 📚 Additional Resources

### Online Tools
- [Regex101](https://regex101.com/) - Interactive regex tester
- [RegexPal](https://www.regexpal.com/) - Simple regex tester
- [RegExr](https://regexr.com/) - Regex learning tool

### Documentation
- [Python re module](https://docs.python.org/3/library/re.html)
- [GNU grep manual](https://www.gnu.org/software/grep/manual/)
- [GNU sed manual](https://www.gnu.org/software/sed/manual/)
- [GNU awk manual](https://www.gnu.org/software/gawk/manual/)

### Books
- "Mastering Regular Expressions" by Jeffrey Friedl
- "Regular Expressions Cookbook" by Jan Goyvaerts
- "Learning Regular Expressions" by Ben Forta

## 🤝 Contributing

We welcome contributions to improve this tutorial! Here's how you can help:

### How to Contribute
1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

### Areas for Improvement
- Additional examples and exercises
- More advanced patterns
- Performance optimization tips
- Real-world use cases
- Translation to other languages

## 📄 License

This tutorial is provided under the MIT License. Feel free to use, modify, and distribute for educational purposes.

## References

- [Regular Expressions - Wikipedia](https://en.wikipedia.org/wiki/Regular_expression)
- [Regular Expressions - Google](https://developers.google.com/edu/python/regular-expressions)
- [Regular Expressions - Microsoft](https://learn.microsoft.com/en-us/dotnet/standard/base-types/regular-expressions)

---

**Happy Learning! 🎉**

Remember: Regular expressions are powerful tools, but they can be complex. Start simple, practice regularly, and don't hesitate to experiment with different patterns. The more you practice, the more comfortable you'll become with regex!
