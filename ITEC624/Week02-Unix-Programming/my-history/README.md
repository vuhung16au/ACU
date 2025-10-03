## my-history — Top 16 Commands from Your Shell History

This utility extracts the most-used commands from a saved shell history file and writes a concise summary.

### What it does
- Reads `my-history.txt` (exported history) in this folder
- Normalizes entries:
  - Removes leading history numbers (bash/zsh `fc -l` style)
  - Strips zsh extended prefix `: <epoch>:<flags>;`
  - Counts `sudo cmd` as `cmd`
- Produces a ranked list of your top 16 commands in `most-used-16-commands.md`

### Files
- `my-history.txt`: your raw history input (one command per line)
- `my-history.py`: script that parses the history and writes the report
- `most-used-16-commands.md`: generated output (markdown table or list)

### How to run
From the repository root:
```bash
python3 Week02-Unix-Programming/my-history/my-history.py \
  Week02-Unix-Programming/my-history/my-history.txt \
  Week02-Unix-Programming/my-history/most-used-16-commands.md
```

Or, from this folder:
```bash
cd Week02-Unix-Programming/my-history
python3 my-history.py my-history.txt most-used-16-commands.md
```

If you omit arguments, the script defaults to `my-history.txt` → `most-used-16-commands.md` in this folder.

### Example output
```markdown
| Rank | Command | Count |
| ---: | :------ | ----: |
| 1 | cd | 2497 |
| 2 | ls | 468 |
| ... | ... | ... |
```

### Tips for exporting history
- Bash: `history > my-history.txt`
- Zsh: `fc -l 1 > my-history.txt` (or `print -rl -- $history | sed 's/^ *[0-9]\+\t//' > my-history.txt`)
- Fish: `history > my-history.txt`

### Troubleshooting
- Ensure Python 3 is installed: `python3 --version`
- Large history files are supported; processing is streaming and memory-light.
- If counts look off, confirm the export method for your shell and that normalization rules apply.


