# Installation Guide

This guide will help you set up Python and run the code examples in this repository.

## Installing Python

### On Windows

- Download the latest Python installer from [python.org](https://www.python.org/downloads/windows/).
- Run the installer and check "Add Python to PATH" during installation.

### On macOS

- Open Terminal and run:

  ```zsh
  brew install python
  ```

- Or download the installer from [python.org](https://www.python.org/downloads/macos/).

### On Linux

- Use your package manager. For example, on Ubuntu:

  ```sh
  sudo apt update
  sudo apt install python3
  ```

## Creating and Running a Virtual Environment

A virtual environment is a self-contained directory that allows you to manage Python packages for each project separately. This helps avoid conflicts between dependencies of different projects.

### On macOS and Linux

- Open Terminal and navigate to your project folder.

- Create a virtual environment named `venv`:

  ```zsh
  python3 -m venv venv
  ```

- Activate the virtual environment:

  ```zsh
  source venv/bin/activate
  ```

- Your prompt should now show `(venv)` at the beginning, indicating the environment is active.

- To deactivate the environment when done:

  ```zsh
  deactivate
  ```

### On Windows (Command Prompt)

- Open Command Prompt and navigate to your project folder.

- Create a virtual environment named `venv`:

  ```cmd
  python -m venv venv
  ```

- Activate the virtual environment:

  ```cmd
  venv\Scripts\activate
  ```

- To deactivate the environment when done:

  ```cmd
  deactivate
  ```

You can now install packages (like Jupyter) inside your virtual environment without affecting your system Python.

## Running Code with Jupyter Notebooks

- You can run Python code interactively using Jupyter notebooks.
- Install Jupyter with:

  ```sh
  pip install notebook
  ```

- Start Jupyter with:

  ```sh
  jupyter notebook
  ```

- Open the notebook file and run the code cells in your browser.

## Online Options: Kaggle and Google Colab

- You can also run Python code online without installing anything:
  - [Kaggle Notebooks](https://www.kaggle.com/code)
  - [Google Colab](https://colab.research.google.com/)
- Upload the code or notebook files and run them directly in your browser.

---

Choose the method that works best for you and start exploring the algorithms!
