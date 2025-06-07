# LeNet Deep Dive and Implementation in TensorFlow/Keras

This repository contains a comprehensive implementation and explanation of the LeNet family of convolutional neural networks using TensorFlow and Keras. The project explores the architectures from LeNet-1 through LeNet-5, their historical significance, and their performance with modern frameworks.

## About the Project

The LeNet family of networks, developed by Yann LeCun and colleagues in the late 1980s and early 1990s, established the foundation for modern convolutional neural networks. This project:

- Implements LeNet-1 through LeNet-5 using TensorFlow/Keras
- Compares results with the original papers
- Explores the mathematical concepts behind CNNs
- Demonstrates the performance of these classic architectures on the MNIST dataset

## Installation and Setup

### Install and Run Locally

1. Create a virtual environment with **Python 3.9**:
   ```bash
   python3.9 -m venv .venv
   ```

2. Activate the virtual environment:
   - On macOS/Linux:
     ```bash
     source .venv/bin/activate
     ```
   - On Windows:
     ```bash
     .venv\Scripts\activate
     ```

3. Install required packages:
   ```bash
   pip install -r requirements.txt
   ```

4. Run the Jupyter notebook:
   ```bash
   jupyter notebook
   ```
   Then open the `LeNet-Deep-Dive-and-Implementation-in-TensorFlow-Keras.ipynb` in your browser using Python 3.9 kernel.

### Run on Google Colab

1. Access Google Colab at [colab.research.google.com](https://colab.research.google.com)

2. Import the notebook directly from GitHub:
   - Click "File" > "Open notebook"
   - Select the "GitHub" tab
   - Enter this repository URL and select the notebook

   OR

3. Upload the notebook manually:
   - Download the notebook from this repository
   - Click "File" > "Upload notebook"
   - Select the downloaded notebook file

4. Run the notebook cells in sequence by clicking the play button or pressing Shift+Enter.

## Key Takeaways

- **Historical Significance**: The LeNet architectures were revolutionary in the late 1980s and early 1990s, establishing the foundation for modern CNNs.
- **Architectural Innovations**: These networks introduced key concepts that remain central to deep learning today, including convolutional layers with weight sharing, hierarchical feature learning, and subsampling operations.
- **Performance**: Our TensorFlow/Keras implementations (also, of course) achieve impressive accuracy on the MNIST dataset.
- **Educational Value**: The notebook provides insights into the mathematical concepts behind CNNs and their practical implementation.

## Requirements

- Python 3.9
- Required Python packages listed in in the Notebook.

## License

[MIT License](LICENSE.md)