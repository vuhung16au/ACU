# R for Data Science — Examples and Mini Projects

This repository is a curated collection of practical R examples for data science: data preparation, visualization, modeling (classification and regression), evaluation, and statistical simulations. Each topic is organized as a small, self-contained module with:

- R scripts (`.R`) for quick runs
- R Markdown (`.Rmd`) for reproducible reports
- Rendered Markdown/HTML outputs and figures under `images/`
- Small example datasets where applicable

Use this repo as a reference, a set of templates, or a starting point for your own work.

## Repository structure

- `00-00-ggplot2/` — ggplot2 basics for data visualization (script, doc, images).
- `00-01-corrplot/` — correlation matrix visualization with `corrplot` (script, doc, images).
- `00-02-ggplot-advanced/` — prompts/notes toward advanced ggplot use (work in progress).
- `02-01-Data-Cleansing/` — end-to-end data cleansing workflow: setup, loading, assessment, standardization, type conversion, missing values, outliers, text normalization, validation, duplicates, derived variables, visuals, and export. Includes modular scripts, docs, and images.
- `05-01-k-NN/` — k-NN classification (Pumpkin Seeds dataset) with code, results, images, and findings.
- `05-02-Linear-Discriminant-Analysis/` — Linear Discriminant Analysis (Pokemon dataset) with code, results, images, and findings.
- `05-03-Quadratic-Discriminant-Analysis/` — Quadratic Discriminant Analysis (Heart Disease dataset) with code, results, images, and findings.
- `05-04-Naive-Bayes-Classifier/` — Naive Bayes (Adult Income dataset) with code, results, images, dataset notes, and findings.
- `05-05-Triangulation-Methodology-with-k-NN/` — Triangulation via cross-validation and multiple metrics (accuracy, AUC, lift, confusion matrix) using k-NN (Pokemon dataset).
- `06-01-Linear-Regression/` — Linear Regression (Salary dataset) with code, results, and images.
- `06-02-Logistic-Regression/` — Logistic Regression (Weather dataset) with code, results, and images.
- `99.MultivariateNormal/` — Multivariate Normal simulation and density/probability demos (`MASS::mvrnorm`, `mvtnorm`).
- `images/` — shared/root-level images if needed.
- `Prompt.md`, `TODO.md` — planning notes and backlog for upcoming modules.

Each subfolder typically contains its own `README.md` with details and how to run.

## Getting started

### Prerequisites
- R (4.5 or later recommended)
- RStudio (optional but recommended)
- Git

### Quick Installation

#### Option 1: Automated Installation Script (Recommended)

Run the provided installation script (Linux/macOS):
```bash
# Clone the repository
git clone https://github.com/your-username/R-For-Data-Science.git
cd R-For-Data-Science

# Run the installation script
./install_packages.sh

# Restart your terminal after installation
```

The script will:
- Detect your shell (Bash/Zsh)
- Set up the R library path
- Install all required R packages
- Configure your environment

#### Option 2: Manual Installation

For **Linux/macOS** users:
```bash
# Set up R library path in your shell profile
echo 'export R_LIBS_USER="$HOME/R/library"' >> ~/.bashrc  # for Bash
# or
echo 'export R_LIBS_USER="$HOME/R/library"' >> ~/.zshrc   # for Zsh

# Apply changes to current session
export R_LIBS_USER="$HOME/R/library"
```

### Manual Package Installation

The repository contains 30 R projects covering visualization, data preparation, classification, regression, text mining, time series, and more. To install all required packages:

#### Step 1: Install System Dependencies (Linux)

```bash
# Ubuntu/Debian
sudo apt-get update
sudo apt-get install -y \
    libcurl4-openssl-dev \
    libssl-dev \
    libxml2-dev \
    libgit2-dev \
    libudunits2-dev \
    libgdal-dev \
    libproj-dev \
    libgeos-dev \
    libprotobuf-dev \
    protobuf-compiler \
    libv8-dev \
    libjq-dev \
    libprotobuf-dev \
    protobuf-compiler \
    libssh2-1-dev \
    libcairo2-dev \
    libxt-dev \
    libharfbuzz-dev \
    libfribidi-dev \
    libfreetype6-dev \
    libpng-dev \
    libtiff5-dev \
    libjpeg-dev

# CentOS/RHEL/Fedora
sudo yum install -y \
    libcurl-devel \
    openssl-devel \
    libxml2-devel \
    git2-devel \
    udunits2-devel \
    gdal-devel \
    proj-devel \
    geos-devel \
    protobuf-devel \
    v8-devel \
    jq-devel \
    protobuf-compiler \
    libssh2-devel \
    cairo-devel \
    libXt-devel \
    harfbuzz-devel \
    fribidi-devel \
    freetype-devel \
    libpng-devel \
    libtiff-devel \
    libjpeg-turbo-devel
```

#### Step 2: Install Core R Packages

```r
# Run this in R console or Rscript
# Create user library if it doesn't exist
if (!dir.exists("~/R/library")) {
    dir.create("~/R/library", recursive = TRUE)
}

# Set library path
.libPaths("~/R/library")

# Install core packages
core_packages <- c(
    "tidyverse",      # Collection of data science packages
    "ggplot2",        # Data visualization
    "dplyr",          # Data manipulation
    "corrplot",       # Correlation matrices
    "MASS",           # Statistical functions
    "mvtnorm",        # Multivariate normal distributions
    "caret",          # Machine learning
    "e1071",          # Statistical functions
    "pROC",           # ROC curves
    "rmarkdown",      # Dynamic documents
    "knitr",          # Report generation
    "lubridate",      # Date/time manipulation
    "scales",         # Scale functions for ggplot2
    "gridExtra",      # Arrange multiple plots
    "grid",           # Grid graphics
    "RColorBrewer"    # Color palettes
)

install.packages(core_packages, repos = "https://cloud.r-project.org/")
```

#### Step 3: Install Domain-Specific Packages

```r
# Data processing and utilities
data_packages <- c(
    "readxl",         # Excel files
    "arrow",          # Apache Arrow
    "janitor",        # Data cleaning
    "naniar",         # Missing values
    "skimr",          # Summary statistics
    "here",           # File paths
    "tidyr",          # Data tidying
    "readr",          # Reading data
    "stringr",        # String manipulation
    "forcats"         # Factor handling
)

# Machine learning
ml_packages <- c(
    "randomForest",   # Random forests
    "rpart",          # Decision trees
    "rpart.plot",     # Plot trees
    "glmnet",         # Lasso/elastic-net
    "class",          # Classification functions
    "cluster",        # Clustering
    "factoextra"      # Multivariate analysis visualization
)

# Text mining and NLP
text_packages <- c(
    "tm",             # Text mining
    "tidytext",       # Tidy text analysis
    "topicmodels",    # Topic modeling
    "SnowballC",      # Stemming
    "textstem",       # Text stemming
    "textdata",       # Text datasets
    "SentimentAnalysis",
    "wordcloud",      # Word clouds
    "rvest",          # Web scraping
    "xml2"            # XML parsing
)

# Time series
ts_packages <- c(
    "tseries",        # Time series analysis
    "zoo"             # Time series objects
)

# Association rules
arules_packages <- c(
    "arules",         # Association rule mining
    "arulesViz"       # Visualization
)

# Deep learning (requires Python)
dl_packages <- c(
    "tensorflow",     # TensorFlow interface
    "keras",          # High-level neural networks
    "reticulate"      # Python interface
)

# Additional utilities
util_packages <- c(
    "VIM",            # Missing value visualization
    "mice",           # Multiple imputation
    "isotree",        # Isolation forest
    "magick",         # Image processing
    "cowplot"         # Enhanced plots
)

# Install all packages
all_packages <- c(data_packages, ml_packages, text_packages,
                  ts_packages, arules_packages, util_packages)

install.packages(all_packages, repos = "https://cloud.r-project.org/")

# Deep learning packages (install separately due to Python dependency)
# Note: Ensure Python and TensorFlow are installed first
# install.packages(dl_packages, repos = "https://cloud.r-project.org/")
```

### Setting Up Your Environment

#### For Bash Users:
```bash
# Add to ~/.bashrc
echo 'export R_LIBS_USER="$HOME/R/library"' >> ~/.bashrc
source ~/.bashrc

# Verify installation
Rscript -e ".libPaths()"
```

#### For Zsh Users:
```bash
# Add to ~/.zshrc
echo 'export R_LIBS_USER="$HOME/R/library"' >> ~/.zshrc
source ~/.zshrc

# Verify installation
Rscript -e ".libPaths()"
```

#### For Windows Users:
```r
# In R console, create .Renviron file
file.edit("~/.Renviron")

# Add this line to the file:
R_LIBS_USER=C:/Users/YourUsername/Documents/R/library
```

### Running the Projects

1. **Clone the repository:**
   ```bash
   git clone https://github.com/your-username/R-For-Data-Science.git
   cd R-For-Data-Science
   ```

2. **Run individual scripts:**
   ```bash
   # Using environment variable
   R_LIBS_USER="$HOME/R/library" Rscript 00-00-ggplot2/ggplot2.R

   # Or set it permanently in your shell profile
   Rscript 00-00-ggplot2/ggplot2.R
   ```

3. **Render R Markdown documents:**
   ```bash
   cd 00-00-ggplot2
   R_LIBS_USER="$HOME/R/library" Rscript -e "rmarkdown::render('ggplot2.Rmd')"
   ```

4. **In RStudio:**
   - Open any `.R` or `.Rmd` file
   - The project should automatically detect packages
   - Click "Source" to run scripts
   - Click "Knit" to render R Markdown documents

### Project Categories

The repository is organized into the following categories:

- **00-Visualization** (00-00 to 00-03): Data visualization fundamentals
- **02-Data-Cleansing**: Complete data preprocessing workflow
- **03-Association-Rules**: Market basket analysis
- **04-Clustering**: Unsupervised learning
- **05-Classification**: Supervised learning algorithms
- **06-Regression**: Predictive modeling
- **07-Decision-Trees**: Tree-based models
- **08-Deep-Learning**: Neural networks
- **09-Text-Mining**: Natural language processing
- **10-Model-Evaluation**: Cross-validation and metrics
- **11-Time-Series**: Temporal data analysis
- **12-Ethics**: Responsible data science
- **99-Statistical-Methods**: Theoretical foundations

### Troubleshooting

1. **Permission errors on Linux:**
   ```bash
   # Use user library instead of system-wide
   R_LIBS_USER="$HOME/R/library" Rscript your_script.R
   ```

2. **Missing system dependencies:**
   - Install the system packages listed in Step 1
   - Restart R after installation

3. **Deep learning packages:**
   - Install Python and TensorFlow first
   - Use `reticulate::install_python()` if needed

4. **Package conflicts:**
   ```r
   # Update all packages
   update.packages(ask = FALSE)

   # Check package versions
   packageVersion("packageName")
   ```

See each module's `README.md` for specific requirements and dataset information.

## Roadmap — more examples coming

I will keep adding more R code examples to cover:

- 01 Data preparation
- 03 CORRELATION ANALYSIS
- 04 k-Means
- 07 decision trees
- 08 neural network
- 09 text mining
- 10 Model Evaluation
- 11-01 Time Series Forecasting
- 11-02 Anomaly Detection
- 12 Ethics

Contributions and suggestions are welcome. Stay tuned for new modules and improvements.
