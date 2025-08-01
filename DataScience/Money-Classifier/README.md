# Money-Classifier

## Overview

A machine learning project that uses VGG16 transfer learning to classify Australian money denominations through camera input. This project demonstrates computer vision techniques for currency recognition.

## How to Setup Virtual Environment

1. Create a virtual environment:

```bash
   python -m venv .venv
```
2. Activate the virtual environment:
   - On macOS/Linux:

     ```bash
     source .venv/bin/activate
     ```

   - On Windows:

     ```bash
     venv\Scripts\activate
     ```

3. Install required dependencies:

   ```bash
   pip install -r requirements.txt
   ```

## How to Use `train_data.py`

The `train_data.py` script is used to train the VGG16 model for money classification:

1. Ensure your training data is properly organized in the `data/` directory
2. Run the training script:

   ```bash
   python train_data.py
   ```

3. The script will train the model using transfer learning with VGG16
4. Monitor the training progress and validation accuracy

# Ref. 
https://miai.vn/2020/04/21/nhan-dang-tien-viet-nam-voi-transfer-learning-vgg16-cnn-classify/