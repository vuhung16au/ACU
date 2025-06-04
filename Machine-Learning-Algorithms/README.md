# Machine Learning Algorithms Implementation

This repository contains comprehensive implementations of various machine learning algorithms, including both theoretical documentation and practical Python code.

## Project Structure

```
algorithms/
├── linear_regression/
│   ├── Linear-Regression.md
│   └── Linear-Regression.py
├── polynomial_regression/
├── logistic_regression/
├── softmax_regression/
├── knn/
├── svm/
├── decision_trees/
├── random_forests/
├── neural_networks/
├── ensemble_methods/
├── bayesian_classification/
├── clustering/
├── dimensionality_reduction/
├── association_rules/
├── time_series/
├── deep_learning/
├── reinforcement_learning/
└── specialized_models/
```

## Getting Started

### Prerequisites

- Python 3.8 or higher
- pip (Python package installer)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/yourusername/Machine-Learning-Algorithms.git
cd Machine-Learning-Algorithms
```

2. Create a virtual environment (recommended):
```bash
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
```

3. Install dependencies:
```bash
pip install -r requirements.txt
```

## Using Make and Build Tools

### Make Commands

This project includes a Makefile with several useful commands:

1. Run all Python files in the algorithms directory:
```bash
make all
```

2. Run all Python files using the run-all-py-files.py script:
```bash
make run-all
```

3. Clean all generated PNG files:
```bash
make clean
```

4. Run Python files for a specific model:
```bash
make model-name=linear_regression
```

### Alternative Build Tools

You can also use the provided Python script to run all algorithms and generate a report:

```bash
python run-all-py-files.py
```

This will:
- Execute all Python files in the algorithms directory
- Generate a markdown report in `docs/run-all-py-files.md`
- Show execution status and timing for each algorithm

## Usage

Each algorithm implementation includes:
- Detailed documentation in markdown format
- Python implementation with example usage
- Visualization tools
- Performance metrics

To run a specific implementation:
```bash
python algorithms/<algorithm_name>/<algorithm_name>.py
```

## Documentation Structure

Each algorithm's documentation includes:
1. Overview
2. Historical Context
3. Technical Details
4. Performance Analysis
5. Practical Applications
6. Advantages and Limitations
7. Implementation Guidelines
8. Python Implementation

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

