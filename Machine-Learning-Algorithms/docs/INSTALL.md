# Installation & Usage Guide

This guide will help you set up and run the Machine Learning Algorithms repository on macOS.

## 1. Install Python 3.9

TensorFlow and Keras are only compatible with Python 3.9 on macOS. Newer versions (3.10+) are not supported.

**Recommended:** Use [pyenv](https://github.com/pyenv/pyenv) to manage Python versions.

```bash
# Install pyenv if you don't have it
git clone https://github.com/pyenv/pyenv.git ~/.pyenv
export PYENV_ROOT="$HOME/.pyenv"
export PATH="$PYENV_ROOT/bin:$PATH"
eval "$(pyenv init --path)"

# Install Python 3.9
pyenv install 3.9.19
pyenv local 3.9.19
```

Or download Python 3.9 directly from the [official website](https://www.python.org/downloads/release/python-3919/).

## 2. Create a Virtual Environment

It is recommended to use a virtual environment to avoid dependency conflicts. Name it `.venv`:

```bash
python3.9 -m venv .venv
```

## 3. Activate the Virtual Environment

```bash
source .venv/bin/activate
```

You should see `(.venv)` at the beginning of your terminal prompt.

## 4. Install Dependencies

Install all required packages using `requirements.txt`:

```bash
pip install -r requirements.txt
```

## 5. Run All Algorithms

To execute all Python scripts in the `algorithms/` subdirectories, run:

```bash
bash run-all-py-files.sh
```

This will run each `.py` file in every subdirectory of `algorithms/`.

## 6. Deactivate the Virtual Environment

When you are done, deactivate the environment with:

```bash
deactivate
```

---

If you encounter any issues, please ensure you are using Python 3.9 and that your virtual environment is activated before installing dependencies or running scripts. 