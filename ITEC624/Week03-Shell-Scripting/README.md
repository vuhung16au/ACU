## Week 03 — Programming with Shell Script

Welcome to Week 3. This week you will learn practical shell scripting to automate routine tasks and work efficiently on the command line.

### Topics
- Pipes and pipelines
- SED and AWK basics
- Arithmetic and relational operations
- File operations (read, write, filter)
- Control structures: `for`, `while`, `case`, `if`/`elif`/`else`, and `test`

---

## How to run the shell scripts

### Linux / macOS
1. Ensure you have a POSIX shell (bash/zsh) available. On macOS, install Homebrew if needed and `brew install bash`.
2. Make the script executable:
   ```bash
   chmod +x examples/01_hello.sh
   ```
3. Run it:
   ```bash
   ./examples/01_hello.sh
   ```
4. Alternatively, run with an interpreter explicitly:
   ```bash
   bash examples/01_hello.sh
   ```

### Windows
- Option A: Use Windows Subsystem for Linux (WSL). Install Ubuntu from the Microsoft Store, then follow the Linux steps inside WSL.
- Option B: Use Git Bash (bundled with Git for Windows). Open Git Bash and run scripts as on Linux/macOS.
- Option C: Use a container (Docker) with a Linux image and run scripts inside the container.

Notes:
- Scripts use a shebang `#!/usr/bin/env bash`. You can switch to `sh` for stricter POSIX if desired.
- Some examples use `sed`/`awk` which are available by default on Linux/macOS and in WSL/Git Bash.

---

## Exercises
Try these small exercises (place your work in `examples/`):

1. Create a script that sums numbers from 1 to `N` using a `for` loop.
2. Write a script that reads a filename and prints the number of lines containing a given word (use `grep` or `awk`).
3. Replace all email domains in a file from `example.com` to `example.org` using `sed`.
4. Given a CSV file, print the second column and the average of that column using `awk`.
5. Build a pipeline that sorts unique words from a text file by frequency (use `tr`, `sort`, `uniq`, and `head`).

## John the Ripper 

### Install `john` from source code

1. Download the latest source code from the [official website](https://www.openwall.com/john/).
2. Extract the downloaded archive

```bash 
# wget https://www.openwall.com/john/k/john-1.9.0-jumbo-1.tar.gz
tar -xvf john-<version>.tar.gz
cd john-<version>/src
#./configure --help 
# ./configure --prefix=$HOME/bin
./configure 
make 
# sudo make install
```

### Install `john` on MacOS using Homebrew

```bash
brew install john
```
### Wordlist files for `john`

https://www.openwall.com/wordlists/

---

## Further reading
- The Linux Command Line, William Shotts
- Bash Reference Manual (`man bash`)
- `sed` manual (`man sed`) and GNU sed handbook
- `awk` by Aho, Kernighan, and Weinberger; `gawk` manual
- TLDP Advanced Bash-Scripting Guide


---

## Conclusions
Shell scripting is a powerful glue for quick automation. Mastering pipelines, text filters (`sed`, `awk`), and core control structures enables you to process files and data efficiently. Keep scripts small, composable, and readable.


