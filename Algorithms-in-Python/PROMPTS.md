# Get new topic

Help me finish the #file by demonstrating the following topics

{topic}

Additional request:
- this is a tutorial  for beginners, so make it easy to understand 
- comment your code 
- name variables/functions... something meaningful 
- reformat the code so that it would be easier for me to convert this Python file to a Jupyter Notebook format.

{topic}
{/topic}


# Reformat code for Jupyter Notebooks

Reformat the code of ALL the .py files in this workspace so that it would be easier for me to convert this Python file to a Jupyter Notebook format.

Just change the Python comments Jupyter Headers like this

old code: 
```
# Basic Python Tutorial for Beginners

# 1. Variables and Data Types
age = 25  # int
height = 1.75  # float
name = "Alice"  # str
is_student = True  # bool
```

new code:

```
## Basic Python Tutorial for Beginners

## 1. Variables and Data Types
age = 25  # int
height = 1.75  # float
name = "Alice"  # str
is_student = True  # bool
```

# Convert .py to Jupyter Notebooks 

Convert all .py files in this workspace to Jupyter notebooks
Save `.ipynb` files under the same folder with `.py` files.

Example command: 

cd /Users/vuhung/Desktop/ACU/Algorithms-in-Python/Chapter01-Introduction-to-Python-Programming-Language
../.venv/bin/p2j chapter01-basic-python.py                    
Notebook chapter01-basic-python.ipynb written.

# Run test all .py 

Finish this program.

This program will find all .py files under folders `*ChapterXX` (e.g: Chapter01), 
then run all the .py files.

if any .py executions stop with error, report it.
otherwise, report "run success" 