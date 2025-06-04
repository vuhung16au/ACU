# Deep Learning Algorithms Implementation

This directory contains implementations of various deep learning architectures using PyTorch. Each implementation includes both the model architecture and training code, along with example usage.

## Implemented Models

1. **Convolutional Neural Networks (CNNs)**
   - Implementation: `cnn.py`
   - Documentation: `cnn.md`
   - Features:
     - Convolutional layers
     - Pooling layers
     - Batch normalization
     - Dropout
     - Image classification

2. **Recurrent Neural Networks (RNNs)**
   - Implementation: `rnn.py`
   - Documentation: `rnn.md`
   - Features:
     - LSTM implementation
     - GRU implementation
     - Sequence classification
     - Teacher forcing
     - Gradient clipping

3. **Generative Adversarial Networks (GANs)**
   - Implementation: `gan.py`
   - Documentation: `gan.md`
   - Features:
     - DCGAN architecture
     - Generator network
     - Discriminator network
     - Adversarial training
     - Image generation

4. **Transformer Models**
   - Implementation: `transformer.py`
   - Documentation: `transformer.md`
   - Features:
     - Multi-head attention
     - Positional encoding
     - Encoder-decoder architecture
     - Sequence-to-sequence tasks
     - Teacher forcing

## Requirements

- Python 3.7+
- PyTorch 1.7+
- NumPy
- Matplotlib
- tqdm
- CUDA (optional, for GPU acceleration)

## Installation

1. Create a virtual environment (recommended):
```bash
python -m venv venv
source venv/bin/activate  # On Windows: venv\Scripts\activate
```

2. Install required packages:
```bash
pip install torch torchvision numpy matplotlib tqdm
```

## Usage

Each model can be run independently. Here's how to run each implementation:

### CNN
```bash
python cnn.py
```
This will train a CNN model on the CIFAR-10 dataset and display training progress.

### RNN
```bash
python rnn.py
```
This will train both LSTM and GRU models on synthetic sequence data and compare their performance.

### GAN
```bash
python gan.py
```
This will train a GAN model on the MNIST dataset and save generated images in the `images` directory.

### Transformer
```bash
python transformer.py
```
This will train a Transformer model on synthetic sequence data and demonstrate sequence-to-sequence learning.

## Model Architecture Details

### CNN
- Input: 32x32x3 images
- Output: 10 classes
- Architecture:
  - 3 convolutional blocks
  - Each block: Conv2d + BatchNorm + ReLU + MaxPool
  - Fully connected layers with dropout

### RNN
- Input: Variable-length sequences
- Output: Classification
- Architectures:
  - LSTM with multiple layers
  - GRU with multiple layers
  - Both include dropout and batch normalization

### GAN
- Generator:
  - Input: Random noise (latent vector)
  - Output: Generated images
  - Architecture: Transposed convolutions with batch normalization
- Discriminator:
  - Input: Real or generated images
  - Output: Binary classification
  - Architecture: Convolutional layers with leaky ReLU

### Transformer
- Input: Source sequence
- Output: Target sequence
- Architecture:
  - Encoder: Self-attention + feed-forward
  - Decoder: Self-attention + cross-attention + feed-forward
  - Positional encoding
  - Multi-head attention

## Training Features

All implementations include:
- Learning rate scheduling
- Early stopping
- Gradient clipping
- Model checkpointing
- Training visualization
- Progress bars
- GPU support

## Performance Monitoring

Each implementation includes:
- Loss tracking
- Accuracy metrics
- Training curves
- Model evaluation
- Example predictions

## Contributing

Feel free to contribute to this project by:
1. Forking the repository
2. Creating a new branch
3. Making your changes
4. Submitting a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 