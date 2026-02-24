# Shell Configuration for Development

## Overview

A shell is a command-line interface that allows you to interact with your operating system. For software development, using an enhanced shell can improve productivity through features like auto-completion, syntax highlighting, and useful plugins.

## Default Shells by Platform

- **macOS**: Zsh (default since macOS Catalina)
- **Linux (Ubuntu)**: Bash (can easily switch to Zsh)
- **Windows**: PowerShell or Command Prompt (WSL provides Linux shells)

## Recommended: Oh My Zsh

**Oh My Zsh** is a popular framework for managing Zsh configuration. It provides:
- 300+ plugins (git, docker, node, etc.)
- 150+ themes for customizing your prompt
- Auto-completion and helpful aliases
- Active community and regular updates

**Official Website**: [https://ohmyz.sh/](https://ohmyz.sh/)

---

## Installing Oh My Zsh

### Prerequisites

Oh My Zsh requires:
- Zsh shell (version 5.0.8 or newer)
- `curl` or `wget`
- `git`

### macOS Installation

macOS comes with Zsh pre-installed. Simply install Oh My Zsh:

```bash
# Install Oh My Zsh
sh -c "$(curl -fsSL https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"
```

The installer will:
1. Clone the Oh My Zsh repository to `~/.oh-my-zsh`
2. Back up your existing `~/.zshrc` file
3. Create a new `~/.zshrc` with Oh My Zsh configuration
4. Set Zsh as your default shell (if not already)

### Linux (Ubuntu) Installation

```bash
# 1. Install Zsh (if not already installed)
sudo apt update
sudo apt install zsh -y

# 2. Install Oh My Zsh
sh -c "$(curl -fsSL https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"

# 3. Set Zsh as default shell
chsh -s $(which zsh)
```

Log out and log back in for the shell change to take effect.

### WSL (Ubuntu) Installation

The process for WSL is identical to Ubuntu:

```bash
# 1. Install Zsh
sudo apt update
sudo apt install zsh -y

# 2. Install Oh My Zsh
sh -c "$(curl -fsSL https://raw.githubusercontent.com/ohmyzsh/ohmyzsh/master/tools/install.sh)"

# 3. Set Zsh as default shell
chsh -s $(which zsh)
```

Close and reopen your WSL terminal for changes to take effect.

---

## Using Oh My Zsh

### Configuration File

All Oh My Zsh configuration is managed through `~/.zshrc`:

```bash
# Edit your configuration
nano ~/.zshrc
# or
code ~/.zshrc
```

After making changes, reload the configuration:

```bash
source ~/.zshrc
```

### Changing Themes

Edit `~/.zshrc` and change the `ZSH_THEME` variable:

```bash
# Default theme
ZSH_THEME="robbyrussell"

# Popular alternatives
ZSH_THEME="agnoster"      # Shows git branch and status
ZSH_THEME="powerlevel10k" # Highly customizable (requires separate install)
```

Browse all themes: [https://github.com/ohmyzsh/ohmyzsh/wiki/Themes](https://github.com/ohmyzsh/ohmyzsh/wiki/Themes)

### Enabling Plugins

Edit the `plugins` array in `~/.zshrc`:

```bash
plugins=(
    git              # Git aliases and completion
    dotnet           # .NET CLI completion
    docker           # Docker completion
    vscode           # VS Code aliases
    sudo             # Press ESC twice to add sudo
    zsh-autosuggestions  # Suggests commands as you type (requires separate install)
)
```

**Note**: Most plugins are built-in, but some (like `zsh-autosuggestions`) require manual installation. Check plugin documentation for details.

Browse all plugins: [https://github.com/ohmyzsh/ohmyzsh/wiki/Plugins](https://github.com/ohmyzsh/ohmyzsh/wiki/Plugins)

### Useful Built-in Features

Once Oh My Zsh is installed, you get:

```bash
# Git shortcuts (when in a git repository)
gst          # git status
ga .         # git add .
gcmsg "msg"  # git commit -m "msg"
gp           # git push

# Directory navigation
..           # cd ..
...          # cd ../..
-            # cd to previous directory

# Quick directory listing
l            # ls -lah
la           # ls -lAh
```

---

## Windows PowerShell Alternative

For Windows users not using WSL, **PowerShell** is the recommended shell:

### PowerShell Features

- Built-in on Windows 10/11
- Object-oriented pipeline
- Extensive .NET integration
- Cross-platform (PowerShell Core)

### Enhancing PowerShell

Consider **Oh My Posh** as an alternative to Oh My Zsh for PowerShell:

```powershell
# Install Oh My Posh (requires Windows Terminal)
winget install JanDeDobbeleer.OhMyPosh -s winget
```

Learn more: [https://ohmyposh.dev/](https://ohmyposh.dev/)

---

## Recommended Setup for ITEC323

For consistency across the course, we recommend:

1. **macOS/Linux/WSL**: Use Zsh with Oh My Zsh
2. **Windows (no WSL)**: Use PowerShell with Oh My Posh

### Recommended Plugins for .NET Development

Add these to your `~/.zshrc` plugins array:

```bash
plugins=(
    git
    dotnet
    vscode
    sudo
    colored-man-pages
)
```

---

## Troubleshooting

### Oh My Zsh not loading

```bash
# Check if Zsh is your default shell
echo $SHELL

# Should output: /bin/zsh or /usr/bin/zsh
```

If not, run:
```bash
chsh -s $(which zsh)
```

### Slow shell startup

Too many plugins can slow down shell startup. Disable unused plugins in `~/.zshrc`.

### Permission errors on macOS

If you get "insecure directories" warnings:

```bash
chmod 755 /usr/local/share/zsh
chmod 755 /usr/local/share/zsh/site-functions
```

---

## Resources

- **Oh My Zsh**: [https://ohmyz.sh/](https://ohmyz.sh/)
- **Oh My Zsh GitHub**: [https://github.com/ohmyzsh/ohmyzsh](https://github.com/ohmyzsh/ohmyzsh)
- **Zsh Documentation**: [https://zsh.sourceforge.io/](https://zsh.sourceforge.io/)
- **Oh My Posh** (PowerShell): [https://ohmyposh.dev/](https://ohmyposh.dev/)

---

**Last Updated**: February 2026  
**Course**: ITEC323, ACU
