### Introduction
`grep` is a command-line utility for searching plain-text data for lines that match a given pattern. It is ubiquitous on Unix-like systems and is essential for filtering logs, inspecting configuration files, and composing powerful pipelines.

### History of the name `grep`
The name comes from the ed/ex command `g/re/p`, which means: apply a command to all lines that match a regular expression and print them.
- **g**: global (all lines)
- **re**: regular expression
- **p**: print

### Basic usage
- **Search for a literal string in a file**:
```bash
grep "pattern" file.txt
```
- **Case-insensitive search**:
```bash
grep -i "pattern" file.txt
```
- **Recursive search in a directory**:
```bash
grep -R "pattern" /path/to/dir
```
- **Show line numbers**:
```bash
grep -n "pattern" file.txt
```
- **Show only the matching part (not the whole line)**:
```bash
grep -o "pattern" file.txt
```
- **Use extended regular expressions** (ERE, enables `+`, `?`, `|`, group `()` without escaping):
```bash
grep -E "regular_expression" file.txt
```
- **Invert match** (show lines that do NOT match):
```bash
grep -v "pattern" file.txt
```
- **Count matches**:
```bash
grep -c "pattern" file.txt
```
- **Highlight matches**:
```bash
grep --color=auto "pattern" file.txt
```

### Examples with /etc/passwd
The `/etc/passwd` file uses colon-separated fields. The third field is the numeric user ID (UID).

- **Grep lines containing the literal word "root"**:
```bash
grep "root" /etc/passwd
```

- **List users with UID less than 1000** (matches UIDs 0–999 by selecting 1–3 digit numbers in the UID field):
```bash
grep -E '^[^:]*:[^:]*:[0-9]{1,3}:' /etc/passwd
```
Explanation: `^[^:]*:[^:]*:` skips to the UID field; `[0-9]{1,3}` matches 1 to 3 digits (0–999), then `:` ensures we are at the end of the UID field.

### Example: fetch a web page and grep its Title
Fetch the HTML and extract the `<title>...</title>` element. On many systems, GNU or BSD `grep` may not support PCRE `-P` consistently, so the following is a portable approach that uses `grep` to capture the whole tag and `sed` to strip the tags:
```bash
wget -qO- https://www.google.com \
  | grep -iEo '<title>[^<]*</title>' \
  | sed -E 's#</?title>##gi'
```
- `wget -qO-` writes the page to stdout quietly.
- `grep -iEo` prints only the matching part of the line, case-insensitively.
- `sed` removes the opening and closing `title` tags, leaving only the text.

If your `grep` supports PCRE (`-P`), you can capture just the inner text with:
```bash
wget -qO- https://www.google.com \
  | grep -iPo '(?<=<title>)[^<]+'
```

### Conclusion
`grep` is a foundational tool for text processing. Mastering its options, especially regular expressions and pipeline usage, enables efficient searching and data extraction across files, logs, and streamed content.
