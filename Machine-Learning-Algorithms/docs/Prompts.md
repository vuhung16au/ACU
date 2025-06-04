# Sample markdown for Jupyter Notebook in .py file 

```
# %% [markdown]
# ## 6. Example Usage
```

# Code checklist 

Review the prompt file, and the Python code, 

help me to create a checklist so that I am confident to say they are implemented consistenly using similar styles, structure, folder/file structure, conventions...

# Update `run-all-py-files.sh`

Expected output:
- Run time for each .py files
- Run result for each .py files (failed or success)
- Run time for each .py files

Make it a table in Markdown format.

Save to `docs/run-all-py-files.md`

Print total time to run all .py files.
Clear `docs/run-all-py-files.md` before running the script.

# Create `docs/INSTALL.md` and guide how to run this repo 

- Install python 3.9 (note: python 3.9 is compartible with tensorflow, keras on Mac os, newer python version (3.10+) can't install tensorflow, keras)
- create virtual env named .venv
- activate it 
- install requirements.txt 
- run run-all-py-files.sh
- how to deactivate virt environemnt 

# Create `CONTRIBUTING.md`

Create a `CONTRIBUTING.md` file to guide the contributors to contribute to the project.

The `CONTRIBUTING.md` file should be saved in the root of the project.

The `CONTRIBUTING.md` file should have the following sections:

- Introduction
- How to contribute
- How to run the project
- How to test the project


# Any models do we have to implement from scratch?

# Creat a Markdown page to introduce the models

The page should be saved as `Machine-Learning-Algorithms.md`

this page introduce *ALL* the models in the `algorithms` folder.

the structure of the page should be:

- Introduction

- TOC for Models (with links to the models)

- Introduction to each model (with links to the model)

For each model

- Introduction to the model
- Mathematical Foundation (using LaTeX in Markdown `$$` and `$$ $$` )
- Implementation
- Performance Analysis
- Use Cases
- Advantages and Limitations
- Relation to other models (if any)
- Python Implementation (using any library, such as scikit-learn, tensorflow, keras, etc.)? And using what functions from the library? what hyperparameters are used?

- Conclusion


# Create `Makefile` for the project

Create a `Makefile` for the project.

The `Makefile` should have the following targets:

- `all`: Run all the .py files in the `algorithms` folder.
- `clean`: Clean all the .png files in the `algorithms` folder.
- `model-name`: Run all the .py files in the `algorithms/model-name` folder.

For example, if the model-name is `linear_regression`, the target is `make model-name=linear_regression`.

The `Makefile` should be saved in the root of the project.

# Fix decision_tree_induction.py 
-> fixed 

runs without errors but doesn't produce any output. This might be intentional if it's meant to be imported as a module, but if it's meant to be run directly, it should probably include some example usage or demonstration code.

# Fix fp_growth.py
-> fixed 

fp_growth.py takes too long to run (longer than 5 mins)
Please make it simplier so that I can run it in short time.

any problem with algorithm? implementation?
problem with datasets?

This is just an example of using fp_growth so make it as simple as possible.

# Step 7
-> done

Create .gitignore file to ignore all the .png files in the `algorithms` folder.

# Step 6 

run `run-all-py-files.sh` and fix the errors.

# Step 5.3 Review all .py files

Scan all .py files under `algorithms/*/*.py` to ensure that they meet the following requirements:
OOP to Functional Conversion Issues

If you remove class definitions, you must also refactor all code that uses self, class methods, or instantiates objects.
Any code that calls methods as object.method() will break if object is no longer defined.

main() Function Removal

If the main logic is inside a main() function and you remove it, you must ensure the code runs sequentially at the top level, or refactor the logic into functions and call them directly.

Plotting Code

Removing plt.show() and plt.switch_backend('agg') is safe, but you must ensure that all plots are saved using plt.savefig() or, preferably, with seaborn as per your requirements.

Unused Imports

After removing OOP and plotting code, you may have unused imports (e.g., import matplotlib.pyplot as plt or import seaborn as sns).

Variable Scope and Initialization

If variables were initialized inside a class or main(), they may now be undefined or out of scope.

File/Folder Structure for Saving Visuals

If you save plots, the code must ensure the output directory exists, or it will throw a FileNotFoundError.

General Syntax Errors

Indentation errors or missing code blocks after removing large sections.

# Step 5.2 

Make sure that all the .py files (`ls -lR algorithms/[a-z]*/*.py`): 

- Don't have `class` in the code.
- Don't have `main()` in the code.
- Don't have `plt.show()` in the code, just save the visuals to png files.
- Don't have `plt.switch_backend('agg')` in the code.


# Step 5.2 Improved (by broken down the list of files into smaller chunks)

A problem iwth Step 5.1 prompt is that, the list of files is too long, so it is better to break it down into smaller chunks so that it is easier to process by the AI.

The list of files is:
```
algorithms/support_vector_machines/SVM.py
algorithms/time_series/arima.py
algorithms/time_series/exponential_smoothing.py
algorithms/time_series/prophet.py
algorithms/xgboost/XGBoost.py
```

Proceed with the files in the list above, in the order of the list.
Don't process any other .py files.

(The rest of the prompt is the same as Step 5.1)

# Step 5.1 Don't plot visuals, save to png files

Proceed with file in this list and order: `ls -lR algorithms/[a-z]*/*.py`

The list of files is:
```
ls -lR algorithms/[e-z]*/*.py
-rw-r--r--@ 1 vuhung  staff  10460  4 Jun 03:55 algorithms/ensemble_methods/adaboost.py
-rw-r--r--@ 1 vuhung  staff  11203  4 Jun 03:57 algorithms/ensemble_methods/bagging.py
-rw-r--r--@ 1 vuhung  staff  10005  4 Jun 03:55 algorithms/ensemble_methods/blending.py
-rw-r--r--@ 1 vuhung  staff  13724  4 Jun 03:57 algorithms/ensemble_methods/combination.py
-rw-r--r--@ 1 vuhung  staff  11889  4 Jun 03:55 algorithms/ensemble_methods/gradient_boosting.py
-rw-r--r--@ 1 vuhung  staff  10082  4 Jun 03:54 algorithms/ensemble_methods/stacking.py
-rw-r--r--@ 1 vuhung  staff   8102  4 Jun 03:55 algorithms/ensemble_methods/voting.py
-rw-r--r--@ 1 vuhung  staff   6066  4 Jun 10:13 algorithms/gradient_boosting/Gradient-Boosting.py
-rw-r--r--@ 1 vuhung  staff  13059  4 Jun 10:54 algorithms/k_nearest_neighbors/KNN.py
-rw-r--r--@ 1 vuhung  staff   5768  4 Jun 04:01 algorithms/lightgbm/LightGBM.py
...
```

Proceed with the files in the list above, in the order of the list.

For all `.py` files in the list above, don't plot visuals, save the visuals to png files.

Visual files are save to `algorithms/<category>/<algorithm-name>-<visual-name>.png` files.

with the same name as the algorithm-name, 

For example 

For example, if the algorithm-name is `arima`, the visual-name is `acf`, the visual file is saved to `algorithms/time_series/arima/arima-acf.png`

Additional requirements:
- Make sure that you don't plot visuals in the code, just save the visuals to png files. e.g: `plt.show()`, instead of `plt.savefig('arima-acf.png')`
- Make sure that you use seaborn to plot the results of the algorithm/model (don't use matplotlib if not necessary).
- Make sure .py doens't have main(), no `class`, no OOP.
- We don't need `plt.switch_backend('agg')` in the code.


# Step 5 Create shell to run all .py files 

Create a shell script to run all .py files under `algorithms/*/*.py` in this order: `~/Desktop/ACU/Machine-Learning-Algorithms$ ls -lR algorithms/*/*.py`

Save the shell script as `run-all-py-files.sh`

# Step 4


For all .py files under  ls -lR algorithms/[o-z]*/*.py   
proceed with all the files in this order:  ls -lR algorithms/[g-z]*/*.py   
Don't ask me to confirm the order, just proceed with the order.

- please use seaborn to plot the results of the algorithm/model (don't use matplotlib if not necessary).
- don't use main() function in the code, just let the code run sequentially
- make sure the code is ready to convert to Jupyter Notebook format.
- make sure the code is well-commented 
- don't implement OOP in the code, just use functions (and call them)
- Write python comments, markdown comments for the code for all the sections in the code.
- Write python comments, markdown comments for the code to explain the hyperparameters when they are used (for all models, such as xgboost, random forest, etc.)
- use `np.random.seed(2220)` as seed for all the initializations of random numbers for all the models.

For example, when we call 
```
  custom_model = CustomRandomForest(
        n_estimators=100,
        max_depth=5,
        min_samples_split=2,
        min_samples_leaf=1,
        max_features='sqrt',
        n_jobs=-1,
        random_state=42
    )
```
-> explain the hyperparameters in the code.

Format the code of ALL the Python/.py files so thatit is ready to convert to Jupyter Notebook format. 
Jupyter-Notebook-ready Python comments look like:

```
## Basic Python Tutorial for Beginners

## 1. Variables and Data Types
age = 25  # int
height = 1.75  # float
name = "Alice"  # str
is_student = True  # bool
```

Write markdown comments for the code for all the sections in the code.

# Step 3.2 

Compare *ALL* algorithms we have under folder: {model-name}

{model-name}

Make it a table in Markdown format. 

Things to compare:

- Time/Space Complexity
- Best for
- Limitations
- Advantages
- Use Cases (when to use)
- Things they have in common and differences

Save the table as `algorithms/{model-name}/{model-name}-comparison.md`

{model-name} is the name of the model we are comparing, and it is the name of the folder under `algorithms` folder.

# Step 3.1 

Make sure that all algorithms have

- XYZ.py code 
- XYZ.md docs for explanation

Pls check all .py files under `algorithms` folder and add .md files accordingly 

# Step 3

For all <algorithm-name>.md files

- (Expand the "Mathematical Foundation" section), Explain the algorithm/model in details mathematically using markdown and LaTeX.
- Use LaTeX (to be displayed in the markdown file, on github) to write equations and formulas. (formula to be quoted with `$$` (inline) or `$$ $$`(block) )
- Explain Time/Space Complexity in the "Performance Analysis" section mathematically.


# Step 2 

For each machine learning algorithm/model in `Machine-Learning-Model-List.md`, please provide a comprehensive and detailed description that covers both theoretical foundations and practical aspects, along with ready-to-use Python implementations.

Structure the documentation as follows:

1. Overview
   - Brief definition and purpose
   - Type of learning (supervised/unsupervised/reinforcement)
   - Key characteristics and assumptions
   - When to use this algorithm

2. Historical Context
   - Original inventors and year of invention
   - Key historical developments and milestones
   - Evolution of the algorithm over time
   - Notable variations and improvements

3. Technical Details
   - Mathematical foundations and key concepts
   - Step-by-step explanation of how it works
   - Core components and their functions
   - Training process and methodology
   - Key parameters and hyperparameters

4. Performance Analysis
   - Time complexity
   - Space complexity
   - Computational requirements
   - Scalability considerations
   - Performance metrics and evaluation methods

5. Practical Applications
   - Real-world use cases with specific examples
   - Industry applications
   - Success stories and case studies
   - Implementation considerations
   - Common challenges and solutions

6. Advantages and Limitations
   - Strengths and benefits
   - Weaknesses and constraints
   - Comparison with similar algorithms
   - Best and worst-case scenarios
   - Tips for optimal implementation

7. Implementation Guidelines
   - Prerequisites and dependencies
   - Data requirements and preprocessing steps
   - Common pitfalls to avoid
   - Best practices for implementation
   - Available libraries and tools

8. Python Implementation
   - Complete, well-documented code using scikit-learn and/or TensorFlow/Keras/statsmodels
   - Code should be Jupyter Notebook-ready with proper markdown formatting
   - Include the following sections in the code:
     * Required libraries and dependencies
     * Data preparation and preprocessing
     * Model implementation
     * Training process
     * Evaluation and metrics
     * Visualization (where applicable)
     * Example usage with sample data
   - Code should be self-contained and not require user input
   - Include detailed comments explaining each step

File Organization:
- Save the documentation as `<Algorithm-Name>.md`
- Save the Python implementation as `<Algorithm-Name>.py`
- Both files should be in the same directory
- Use consistent naming across both files

Example file structure:
```
algorithms/
├── linear_regression/
│   ├── Linear-Regression.md
│   └── Linear-Regression.py
├── logistic_regression/
│   ├── Logistic-Regression.md
│   └── Logistic-Regression.py
└── ...
```

Code Formatting Requirements:
1. Use markdown cells for section headers and explanations
2. Use code cells for Python implementation
3. Include proper docstrings and comments
4. Format code blocks as follows:

```python
## Algorithm Implementation

## 1. Import Required Libraries
import numpy as np
import pandas as pd
from sklearn.model_selection import train_test_split

## 2. Data Preparation
# Load and preprocess data
def load_data():
    """
    Load and preprocess the dataset.
    Returns:
        X: Features
        y: Target variable
    """
    # Implementation here
    pass

## 3. Model Implementation
class CustomModel:
    """
    Custom implementation of the algorithm.
    """
    def __init__(self, param1, param2):
        """
        Initialize the model with parameters.
        """
        self.param1 = param1
        self.param2 = param2
```

Additional Requirements:
1. All code should be production-ready and well-tested
2. Include error handling and input validation
3. Provide example outputs and visualizations
4. Include performance benchmarks where applicable
5. Document any assumptions or limitations in the implementation
6. Provide clear instructions for running the code
7. Include requirements.txt or environment.yml for dependencies
8. Datasets should be small, simple, and included in the code so that the programs can run quickly, and the code should be able to run without any additional datasets.
9. The code should be able to run on a single machine, and should not require any additional hardware.

Please proceed automatically, without asking me confirmations.

# Step 1.1 

Pls improve the prompt:

here is another requirement: 

I also want sample code, written in Python (preferred scikit-learn and/or Tensorflow/Kerras/statmodels) 

Format the code of ALL the Python/.py files so thatit is ready to convert to Jupyter Notebook format.

Jupyter-Notebook-ready Python comments look like:

Sample code: 

```
## Basic Python Tutorial for Beginners

## 1. Variables and Data Types
age = 25  # int
height = 1.75  # float
name = "Alice"  # str
is_student = True  # bool
```

Addition requirements: 
- Well commented code 
- Explain in details 
- Don't ask users for users prompts/inputs 

Save the Python file as `<Algorithm-Name>.md`


# Step 1 

Pls organize the list of algorithms/models below in a structured manner, then save the result as `Machine-Learning-Model-List.md`


*   **Linear Regression**
    *   This includes training methods like the Normal Equation and various forms of Gradient Descent (Batch, Stochastic, Mini-batch).
*   **Polynomial Regression**
*   **Logistic Regression**
*   **Softmax Regression**
*   **k-Nearest Neighbors**
*   **Support Vector Machines (SVMs)**
    *   SVMs are discussed for linear or nonlinear classification, regression, and even outlier detection.
*   **Decision Trees**
    *   Covered for both classification and regression tasks, including the CART training algorithm.
*   **Random Forests**
*   **Ensemble methods**
    *   Discussed as methods that often improve performance by combining multiple models. Specific methods mentioned include Bagging, Pasting, and Voting Ensembles, as well as AdaBoost and Gradient Boosting.
*   **Dimensionality Reduction Techniques**
    *   Specific techniques listed in the table of contents for Chapter 8 include Principal Component Analysis (PCA), Randomized PCA, Incremental PCA, Kernel PCA, and LLE. Note that Kernel PCA is mentioned as moved online in the third edition.
*   **Unsupervised Learning Techniques**
    *   Categories of unsupervised learning covered include Clustering, Density Estimation, Anomaly Detection, and Mixture Models.
    *   Specific algorithms and models mentioned within this context in Part I include Clustering algorithms (such as K-Means, DBSCAN, agglomerative clustering, BIRCH, Mean-Shift, affinity propagation, and spectral clustering), Gaussian Mixture Models (GMM), Bayesian Gaussian Mixture Models, and Association Rule Learning.

Based on the provided source excerpts from "Dunham - Data Mining.pdf", here are the algorithms and models mentioned in Chapters 1, 2, 3, 4, and 5:

**Chapter 1: Introduction**

*   **Data Mining Tasks/Models:**
    *   **Predictive Model**
        *   Classification
        *   Regression
        *   Time series analysis
        *   Prediction
    *   **Descriptive Model**
        *   Clustering
        *   Summarization
        *   Association Rules
        *   Sequence Discovery
*   **Algorithms (General Mention):**
    *   Distributed algorithms
    *   Parallel algorithms
    *   Incremental algorithms
    *   Many different algorithms to accomplish different tasks
    *   Algorithms are described in pseudocode
*   **Models (Components of Data Mining Algorithms):**
    *   Model: The purpose of the algorithm is to fit a model to the data.
    *   Preference: Criteria used to fit one model over another.
    *   Search: Technique to search the data.
*   **Model Types:**
    *   Predictive
    *   Descriptive

**Chapter 2: Related Concepts**

*   **Algorithms:**
    *   Genetic algorithms
*   **Models:**
    *   Fuzzy sets
    *   Fuzzy logic
    *   Dimensional Modeling
    *   Multidimensional Schemas
    *   Pattern Matching
    *   Machine Learning
        *   Supervised learning
        *   Unsupervised learning
    *   Neural networks
    *   Decision trees
    *   Relational data model
    *   CRISP-DM (CRoss-Industry Standard Process for Data Mining) life cycle
    *   3W model
    *   Dimension algebra

**Chapter 3: Data Mining Techniques**

*   **Statistical Concepts (Basis for Techniques):**
    *   Point Estimation
        *   Estimator technique
        *   Expectation-Maximization (EM) algorithm
        *   Maximum Likelihood Estimate (MLE)
    *   Models Based on Summarization
        *   Mean
        *   Variance
        *   Standard deviation
        *   Median
        *   Mode
        *   Frequency distribution
        *   Histogram
        *   Box plot
    *   Bayes Theorem (Bayes Rule)
    *   Hypothesis Testing
        *   Null hypothesis
        *   Alternative hypothesis
    *   Regression
    *   Correlation
    *   Similarity Measures
        *   Overlap
        *   Dice's coefficient
        *   Jaccard's coefficient
        *   Cosine coefficient
*   **Data Mining Techniques/Models:**
    *   Decision Trees (DT)
        *   Decision Tree (DT) Model
        *   Algorithm to create the tree
        *   Algorithm that applies the tree to data
    *   Neural Networks (NN)
        *   Neural Network (NN) Model
        *   Neural network graph
        *   Learning algorithm
        *   Recall techniques (Propagation)
    *   Genetic Algorithms (GA)
        *   Genetic Algorithm (GA) Model
        *   Crossover technique
        *   Mutation algorithm
        *   Fitness function
        *   Algorithm applying techniques
*   **Algorithms (Detailed Mention):**
    *   Expectation-Maximization (EM) algorithm
    *   DTProc algorithm (Decision Tree Processing)
    *   Propagation algorithm
    *   Genetic algorithm

**Chapter 4: Classification**

*   **Classification Algorithms:**
    *   **Statistical-Based Algorithms**
        *   Regression
            *   Linear regression
            *   Logistic regression
        *   Bayesian Classification (Naive Bayes classification)
    *   **Distance-Based Algorithms**
        *   Simple Approach (Distance-Based)
        *   K Nearest Neighbors (KNN)
    *   **Decision Tree-Based Algorithms**
        *   Decision Tree Induction (Algorithm to construct DT)
        *   ID3
        *   C4.5
        *   C5.0
        *   CART
        *   Scalable DT Techniques
            *   SPRINT (Scalable PaRallelizable INduction of decision Trees)
            *   RainForest
    *   **Neural Network-Based Algorithms**
        *   Propagation
        *   NN Supervised Learning
            *   Hebb rule (Hebbian learning)
            *   Delta rule
            *   Backpropagation
            *   Gradient Descent
        *   Radial Basis Function (RBF) Networks
        *   Perceptrons
            *   Multilayer Perceptron (MLP)
    *   **Rule-Based Algorithms**
        *   Generating Rules from a DT
        *   Generating Rules from a Neural Net
            *   RX algorithm (Rule extraction)
        *   Generating Rules Without a DT or NN (Covering algorithms)
            *   1R algorithm
            *   PRISM algorithm
    *   **Combining Techniques**
        *   Boosting
        *   CMC (Combination of Multiple Classifiers)
        *   DCS (Dynamic Classifier Selection)
        *   ACC (Adaptive Classifier Combination)
*   **Other Mentioned Algorithms/Concepts:**
    *   Chi squared automatic interaction detection (CHAID)
    *   QUEST (quick unbiased efficient statistical tree)
    *   SLIQ
    *   AGNES (AGglomerative NESting)
    *   DIANA (Dlvisia ANAlysis)
    *   EM algorithm
    *   ISODATA
    *   CLARA

**Chapter 5: Clustering**

*   **Clustering Algorithms:**
    *   **Hierarchical Algorithms**
        *   Agglomerative Algorithms
            *   Single Link Technique (Nearest Neighbor clustering)
            *   Minimum Spanning Tree (MST) (based on single link)
            *   Complete Link Algorithm (Farthest neighbor algorithm)
            *   Average Link
            *   AGNES (AGglomerative NESting)
        *   Divisive Clustering
            *   DIANA (Dlvisia ANAlysis)
    *   **Partitional Algorithms**
        *   Minimum Spanning Tree (MST) (Partitional)
        *   Squared Error Clustering Algorithm
        *   K-Means Clustering
            *   ISODATA
            *   K-Modes
        *   Nearest Neighbor Algorithm
        *   PAM (Partitioning Around Medoids) (K-medoids algorithm)
            *   CLARA
            *   CLARANS
        *   Bond Energy Algorithm (BEA)
        *   Clustering with Genetic Algorithms
        *   Clustering with Neural Networks
            *   Self-Organizing Feature Maps (SOFM) (Self-organizing map - SOM)
            *   Kohonen self-organizing map
*   **Clustering Large Databases Algorithms:**
    *   BIRCH (balanced iterative reducing and clustering using hierarchies)
    *   DBSCAN (Distribution Based Clustering of LArge Spatial Databases)
        *   GDBSCAN (Generalized DBSCAN)
        *   DENCLUE
        *   OPTICS []
    *   CURE (Clustering Using REpresentatives)
    *   Clustering with Categorical Attributes Algorithms:
        *   CACTUS (CAtegorical ClusTering Using Summaries)
        *   ROCK (RObust Clustering using linKs)
    *   Data Bubbles (Compression technique)
    *   CHAMELEON
    *   OAK (Online Adaptive Clustering)
    *   DBCLASD (Distribution Based Clustering of LArge Spatial Databases)
    *   WaveCluster

This list covers algorithms and models explicitly mentioned and discussed within the excerpts provided for the requested chapters.