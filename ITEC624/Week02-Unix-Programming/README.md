# Unix Commands


## Summarise an YouTube video

A collection of useful Unix commands for cybersecurity tasks.

The prompt:

```text
Watch this video, and list the most basic Unix commands the author mentioned
https://www.youtube.com/watch?v=Cpxwo4atrEc&t=40s
```
 
Use can you Google [Gemini](https://gemini.google.com/) to

- Summarise videos
- Chat with the content of a video

## What is Unix?

Unix is a family of multiuser, multitasking operating systems originally developed at Bell Labs in the late 1960s/early 1970s. It provides a portable, modular design with a small kernel, a rich set of command-line tools, and the philosophy of "do one thing well" joined together by pipes. Many modern systems (macOS, BSDs, and Linux) follow Unix principles or are Unix-like.

## Difference between Unix and Linux

| Aspect | Unix (e.g., AIX, HP-UX, Solaris) | Linux (kernel + distro) |
| --- | --- | --- |
| Licensing/source | Often proprietary or mixed; UNIX is a trademark for SUS/POSIX-certified systems | Open source; Linux kernel is GPLv2 and most userland is free software |
| Development model | Vendor-controlled releases, patches, and tooling | Community-driven upstream kernel; distros integrate kernels, packages, and updates |
| Package management | Vendor-specific tools (e.g., SVR4 pkgadd, IBM installp) | Distro package managers (apt/dpkg, dnf/rpm, pacman, zypper) |
| Hardware/driver support | Targeted to specific enterprise hardware (SPARC, PA-RISC, POWER) | Broad architecture and device support (x86_64, ARM, RISC-V, POWER, s390x) |
| Filesystems | Commonly UFS/ZFS/JFS/VxFS (vendor-dependent) | ext4, XFS, Btrfs, ZFS (via modules), and others |

## What is GNU/Linux?

"GNU/Linux" refers to operating system distributions that combine the Linux kernel with GNU libraries and userland tools (shell, coreutils, compiler toolchains). This pairing creates a complete, Unix-like system packaged by distributions such as Debian, Ubuntu, Fedora, and Arch. Colloquially people say "Linux" to mean the whole OS, but technically Linux is just the kernel.

## Basic Commands

- `ls` : list directory contents
- `cd` : change directory
- `pwd` : print working directory
- `cp` : copy files and directories
- `mv` : move/rename files and directories
- `rm` : remove files and directories
- `mkdir` : make directories
- ...

## Variables

- `echo $VARIABLE_NAME` : print the value of a variable
- `export VARIABLE_NAME=value` : set the value of a variable

### Wildcards

- `*` : matches any number of characters
- `?` : matches a single character

Example:

- `ls *.txt` lists all files ending with `.txt`
- `ls file?.txt` lists files like `file1.txt`, `fileA.txt`, but not `file10.txt`

## Shells

- [bash](https://www.gnu.org/software/bash/) (Recommended for beginners)
- [zsh](https://www.zsh.org/)

## Oh My Zsh

- [Oh My Zsh](https://ohmyz.sh/): A delightful, open source, community-driven framework for managing your zsh configuration
- Surfing the web with Oh My Zsh

## Text Editors

- vi/[vim](https://www.vim.org/) (Recommended)
- [nano](https://www.nano-editor.org/)
- [emacs](https://www.gnu.org/software/emacs/)
- [pico](https://en.wikipedia.org/wiki/Pico_(text_editor))

## Unix Power Tools

- 1600+ pages book: [Unix Power Tools](https://www.amazon.com.au/Unix-Power-Tools-Jerry-Peek/dp/0596003307) (2002,4.6/5 rating) by Jerry Peek, Tim O'Reilly, Mike Loukides

## (VH's) Most-used 16 command lines (from shell history)

| Rank | Command | Count |
| ---: | :------ | ----: |
| 1 | cd | 2497 |
| 2 | ls | 468 |
| 3 | mkdir | 468 |
| 4 | curl | 410 |
| 5 | docker | 381 |
| 6 | python3 | 333 |
| 7 | rm | 304 |
| 8 | mvn | 286 |
| 9 | python | 261 |
| 10 | chmod | 216 |
| 11 | source | 170 |
| 12 | mv | 162 |
| 13 | make | 150 |
| 14 | cp | 144 |
| 15 | ffmpeg | 134 |
| 16 | git | 133 |
