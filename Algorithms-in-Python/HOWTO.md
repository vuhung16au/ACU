# HOWTO: Using the Algorithms-in-Python Repository

This guide will help you get started with the Algorithms-in-Python repository. Follow these steps to explore, run, and experiment with the code examples.

## 1. Clone the Repository
Open your terminal and run:

```zsh
git clone https://github.com/vuhung16au/ACU.git
cd ACU/Algorithms-in-Python
```

## 2. Set Up Python
Make sure you have Python 3 installed. You can check your Python version with:

```zsh
python3 --version
```
If you need to install Python, download it from [python.org](https://www.python.org/downloads/) or use Homebrew:

```zsh
brew install python
```

## 3. Running Python Files
Navigate to the chapter folder and run any Python file. For example:

```zsh
cd Chapter01-Introduction-to-Python-Programming-Language
python3 chapter01-basic-python.py
```

## 4. Running Jupyter Notebooks

### Option 1: Running Locally
1. Install Jupyter Notebook:
```zsh
pip install notebook
```

2. Start Jupyter Notebook:
```zsh
jupyter notebook
```

3. Navigate to the desired chapter folder and open the .ipynb file

### Option 2: Running in Google Colab (Recommended)
1. Open [Google Colab](https://colab.research.google.com/)
2. Click on "File" > "Open notebook"
3. Select the "GitHub" tab
4. Enter the repository URL: `https://github.com/vuhung16au/ACU`
5. Navigate to the Algorithms-in-Python directory and select the notebook (.ipynb file) you want to open
6. The notebook will open in Colab and you can run the cells directly in your browser

## 5. See the Results
The output will appear in your terminal or notebook cell output. Follow any on-screen prompts for input if required.

## 6. Change the Code and Experiment
Open any `.py` file or `.ipynb` file in your favorite code editor or Jupyter environment. Modify the code, save your changes, and run the file again to see how your changes affect the results.

## 7. Converting .py to Jupyter Notebooks 
If you need to convert Python files to Jupyter Notebooks:

```zsh
# Install p2j
pip install p2j

# Convert a Python file to Jupyter Notebook
p2j filename.py
```

---

Feel free to explore all chapters, try out the algorithms, and learn by doing!
