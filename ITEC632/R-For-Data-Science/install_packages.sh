#!/bin/bash
# R packages installation script for R for Data Science repository
# Supports Bash and Zsh shells on Linux and macOS

echo "=========================================="
echo "R for Data Science - Package Installation"
echo "=========================================="
echo

# Check if R is installed (multiple methods for compatibility)
if command -v R >/dev/null 2>&1; then
    R_CMD=$(command -v R)
elif which R >/dev/null 2>&1; then
    R_CMD=$(which R)
elif [ -x /usr/bin/R ]; then
    R_CMD="/usr/bin/R"
else
    echo "Error: R is not installed. Please install R first."
    echo "Visit: https://cran.r-project.org/"
    exit 1
fi

echo "R installation found at: $R_CMD"
echo "R version: $($R_CMD --version | head -n 1)"
echo

# Set up R library path
R_LIB_DIR="$HOME/R/library"
echo "Setting up R library directory at: $R_LIB_DIR"

# Create library directory if it doesn't exist
mkdir -p "$R_LIB_DIR"

# Determine shell and update configuration
if [[ -n "$ZSH_VERSION" ]]; then
    SHELL_RC="$HOME/.zshrc"
    echo "Detected Zsh shell"
elif [[ -n "$BASH_VERSION" ]]; then
    SHELL_RC="$HOME/.bashrc"
    echo "Detected Bash shell"
else
    SHELL_RC="$HOME/.profile"
    echo "Shell not detected. Using ~/.profile"
fi

# Add R_LIBS_USER to shell config if not already present
if ! grep -q "R_LIBS_USER" "$SHELL_RC" 2>/dev/null; then
    echo "" >> "$SHELL_RC"
    echo "# R library path for Data Science projects" >> "$SHELL_RC"
    echo "export R_LIBS_USER=\"$R_LIB_DIR\"" >> "$SHELL_RC"
    echo "Added R_LIBS_USER to $SHELL_RC"
else
    echo "R_LIBS_USER already configured in $SHELL_RC"
fi

# Set for current session
export R_LIBS_USER="$R_LIB_DIR"
echo
echo "Installing required R packages..."
echo "This may take 10-20 minutes depending on your internet connection."
echo

# Create R installation script
cat > /tmp/install_r_packages.R << 'EOF'
# R package installation script
.libPaths(Sys.getenv("R_LIBS_USER"))

# Define package groups
core_packages <- c(
    "tidyverse", "ggplot2", "dplyr", "corrplot", "MASS", "mvtnorm",
    "caret", "e1071", "pROC", "rmarkdown", "knitr", "lubridate",
    "scales", "gridExtra", "grid", "RColorBrewer"
)

data_packages <- c(
    "readxl", "arrow", "janitor", "naniar", "skimr", "here",
    "tidyr", "readr", "stringr", "forcats"
)

ml_packages <- c(
    "randomForest", "rpart", "rpart.plot", "glmnet", "class",
    "cluster", "factoextra"
)

text_packages <- c(
    "tm", "tidytext", "topicmodels", "SnowballC", "textstem",
    "textdata", "wordcloud", "rvest", "xml2"
)

ts_packages <- c("tseries", "zoo")
arules_packages <- c("arules", "arulesViz")
util_packages <- c("VIM", "mice", "isotree", "magick", "cowplot")

# Function to install packages with error handling
install_packages <- function(packages, description) {
    cat("\nInstalling", description, "packages...\n")
    cat("----------------------------------------\n")

    # Check which packages are already installed
    installed <- packages %in% installed.packages()[, "Package"]
    to_install <- packages[!installed]

    if (length(to_install) > 0) {
        cat("Installing:", paste(to_install, collapse = ", "), "\n")

        # Install packages
        result <- tryCatch({
            install.packages(to_install, repos = "https://cloud.r-project.org/",
                           quiet = TRUE)
            TRUE
        }, error = function(e) {
            cat("Error installing packages:", e$message, "\n")
            FALSE
        })

        if (result) {
            cat("✓ Successfully installed:", paste(to_install, collapse = ", "), "\n")
        }
    } else {
        cat("✓ All", description, "packages already installed\n")
    }

    # Verify installation
    still_missing <- packages[!packages %in% installed.packages()[, "Package"]]
    if (length(still_missing) > 0) {
        cat("⚠ Warning: The following packages could not be installed:",
            paste(still_missing, collapse = ", "), "\n")
    }
}

# Install all package groups
install_packages(core_packages, "core")
install_packages(data_packages, "data processing")
install_packages(ml_packages, "machine learning")
install_packages(text_packages, "text mining")
install_packages(ts_packages, "time series")
install_packages(arules_packages, "association rules")
install_packages(util_packages, "utility")

cat("\n========================================\n")
cat("Installation Summary:\n")
cat("========================================\n")

# Count installed packages
all_packages <- c(core_packages, data_packages, ml_packages, text_packages,
                  ts_packages, arules_packages, util_packages)
installed_count <- sum(all_packages %in% installed.packages()[, "Package"])
total_count <- length(all_packages)

cat("Installed:", installed_count, "out of", total_count, "packages\n")

# List any missing packages
missing <- all_packages[!all_packages %in% installed.packages()[, "Package"]]
if (length(missing) > 0) {
    cat("\nMissing packages:\n")
    for (pkg in missing) {
        cat("  -", pkg, "\n")
    }
    cat("\nNote: Some packages may require additional system dependencies.\n")
    cat("Refer to the README.md for detailed installation instructions.\n")
}

cat("\n✓ Installation completed!\n")
cat("\nTo use the packages, restart your terminal or run:\n")
cat("  source", paste0(ifelse(Sys.getenv("SHELL") == "", "~/.bashrc",
                        Sys.getenv("SHELL"))), "\n")
cat("\nOr set R_LIBS_USER manually:\n")
cat("  export R_LIBS_USER='", Sys.getenv("R_LIBS_USER"), "'\n", sep = "")
EOF

# Run the R installation script
$R_CMD --slave --vanilla -f /tmp/install_r_packages.R

# Clean up
rm -f /tmp/install_r_packages.R

echo
echo "=========================================="
echo "Installation completed!"
echo "=========================================="
echo
echo "Please restart your terminal or run:"
echo "  source $SHELL_RC"
echo
echo "Then you can run any R script with:"
echo "  Rscript 00-00-ggplot2/ggplot2.R"
echo
echo "For detailed troubleshooting, see README.md"