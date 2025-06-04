#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Recurrent Neural Network Implementation
Includes LSTM and GRU models for sequence classification.
"""
import torch
import torch.nn as nn
import torch.optim as optim
from torch.utils.data import Dataset, DataLoader
import numpy as np
import matplotlib.pyplot as plt
from tqdm import tqdm
import os

def create_sequence_dataset(sequences, labels, seq_length):
    # Convert sequences and labels to tensors
    sequences = [torch.FloatTensor(seq) for seq in sequences]
    labels = torch.LongTensor(labels)
    
    # Pad or truncate sequences
    padded_sequences = []
    for sequence in sequences:
        if len(sequence) < seq_length:
            padded_seq = torch.nn.functional.pad(sequence, (0, seq_length - len(sequence)))
        else:
            padded_seq = sequence[:seq_length]
        padded_sequences.append(padded_seq)
    
    return padded_sequences, labels

# Custom LSTM model class
class LSTMClassifier(nn.Module):
    def __init__(self, input_size, hidden_size, num_layers, num_classes, dropout=0.5):
        super(LSTMClassifier, self).__init__()
        self.lstm = nn.LSTM(input_size=input_size, hidden_size=hidden_size, num_layers=num_layers, batch_first=True, dropout=dropout if num_layers > 1 else 0)
        self.fc = nn.Linear(hidden_size, num_classes)
    def forward(self, x):
        out, _ = self.lstm(x)
        out = out[:, -1, :]  # Take the last time step
        out = self.fc(out)
        return out

def create_lstm_model(input_size, hidden_size, num_layers, num_classes, dropout=0.5):
    return LSTMClassifier(input_size, hidden_size, num_layers, num_classes, dropout)

def create_gru_model(input_size, hidden_size, num_layers, num_classes, dropout=0.5):
    model = nn.Sequential(
        nn.GRU(
            input_size=input_size, hidden_size=hidden_size, num_layers=num_layers, 
            batch_first=True, dropout=dropout if num_layers > 1 else 0
        ),
        nn.Dropout(dropout),
        nn.Linear(hidden_size, num_classes)
    )
    return model

def train_epoch(model, train_loader, criterion, optimizer, device):
    model.train()
    running_loss = 0.0
    correct = 0
    total = 0
    
    for inputs, labels in tqdm(train_loader, desc='Training'):
        inputs, labels = inputs.to(device), labels.to(device)
        
        # Zero the parameter gradients
        optimizer.zero_grad()
        
        # Forward pass
        outputs = model(inputs)
        loss = criterion(outputs, labels)
        
        # Backward pass and optimize
        loss.backward()
        
        # Gradient clipping
        torch.nn.utils.clip_grad_norm_(model.parameters(), max_norm=1.0)
        
        optimizer.step()
        
        # Statistics
        running_loss += loss.item()
        _, predicted = outputs.max(1)
        total += labels.size(0)
        correct += predicted.eq(labels).sum().item()
        
    epoch_loss = running_loss / len(train_loader)
    accuracy = 100. * correct / total
    return epoch_loss, accuracy

def validate(model, val_loader, criterion, device):
    model.eval()
    running_loss = 0.0
    correct = 0
    total = 0
    
    with torch.no_grad():
        for inputs, labels in tqdm(val_loader, desc='Validation'):
            inputs, labels = inputs.to(device), labels.to(device)
            
            # Forward pass
            outputs = model(inputs)
            loss = criterion(outputs, labels)
            
            # Statistics
            running_loss += loss.item()
            _, predicted = outputs.max(1)
            total += labels.size(0)
            correct += predicted.eq(labels).sum().item()
            
    val_loss = running_loss / len(val_loader)
    accuracy = 100. * correct / total
    return val_loss, accuracy

def train_model(model, train_loader, val_loader, device, num_epochs=50, early_stopping_patience=10):
    criterion = nn.CrossEntropyLoss()
    optimizer = optim.Adam(model.parameters(), lr=0.001)
    scheduler = optim.lr_scheduler.ReduceLROnPlateau(
        optimizer, mode='min', factor=0.1, patience=5)
    
    best_val_loss = float('inf')
    patience_counter = 0
    train_losses = []
    val_losses = []
    train_accs = []
    val_accs = []
    
    for epoch in range(num_epochs):
        print(f'\nEpoch {epoch+1}/{num_epochs}')
        
        # Train and validate
        train_loss, train_acc = train_epoch(model, train_loader, criterion, optimizer, device)
        val_loss, val_acc = validate(model, val_loader, criterion, device)
        
        # Update learning rate
        scheduler.step(val_loss)
        
        # Save metrics
        train_losses.append(train_loss)
        val_losses.append(val_loss)
        train_accs.append(train_acc)
        val_accs.append(val_acc)
        
        # Print statistics
        print(f'Train Loss: {train_loss:.4f}, Train Acc: {train_acc:.2f}%')
        print(f'Val Loss: {val_loss:.4f}, Val Acc: {val_acc:.2f}%')
        
        # Early stopping
        if val_loss < best_val_loss:
            best_val_loss = val_loss
            patience_counter = 0
            # Save best model
            os.makedirs('algorithms/deep_learning/rnn', exist_ok=True)
            torch.save(model.state_dict(), 'algorithms/deep_learning/rnn/best_model.pth')
        else:
            patience_counter += 1
            if patience_counter >= early_stopping_patience:
                print('Early stopping triggered')
                break
                
    return train_losses, val_losses, train_accs, val_accs

def plot_training_history(train_losses, val_losses, train_accs, val_accs):
    """Plot training history and save to PNG file"""
    os.makedirs('algorithms/deep_learning/rnn', exist_ok=True)
    fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(15, 5))
    ax1.plot(train_losses, label='Train Loss')
    ax1.plot(val_losses, label='Val Loss')
    ax1.set_xlabel('Epoch')
    ax1.set_ylabel('Loss')
    ax1.set_title('Training and Validation Loss')
    ax1.legend()
    ax2.plot(train_accs, label='Train Acc')
    ax2.plot(val_accs, label='Val Acc')
    ax2.set_xlabel('Epoch')
    ax2.set_ylabel('Accuracy (%)')
    ax2.set_title('Training and Validation Accuracy')
    ax2.legend()
    plt.tight_layout()
    filename = 'algorithms/deep_learning/rnn/rnn-training_history.png'
    plt.savefig(filename)
    plt.close()

def plot_confusion_matrix(model, test_loader, device, num_classes):
    """Plot confusion matrix"""
    model.eval()
    confusion_matrix = torch.zeros(num_classes, num_classes)
    
    with torch.no_grad():
        for inputs, labels in test_loader:
            inputs, labels = inputs.to(device), labels.to(device)
            outputs = model(inputs)
            _, predicted = outputs.max(1)
            
            for t, p in zip(labels.view(-1), predicted.view(-1)):
                confusion_matrix[t.long(), p.long()] += 1
                
    # Normalize confusion matrix
    confusion_matrix = confusion_matrix / confusion_matrix.sum(1)
    
    # Plot
    plt.figure(figsize=(10, 8))
    plt.imshow(confusion_matrix.cpu().numpy(), interpolation='nearest')
    plt.title('Confusion Matrix')
    plt.colorbar()
    plt.tight_layout()
    plt.ylabel('True Label')
    plt.xlabel('Predicted Label')
    filename = 'algorithms/deep_learning/rnn/confusion_matrix.png'
    plt.savefig(filename)
    plt.close()

def generate_sequence_data(n_samples=100, seq_length=20, n_classes=3):
    # Generate synthetic sequence data
    sequences = []
    labels = []
    
    for _ in range(n_samples):
        # Generate random sequence with input_size=1
        sequence = np.random.randn(seq_length, 1)  # Changed to 2D array with shape (seq_length, 1)
        # Generate random label
        label = np.random.randint(0, n_classes)
        sequences.append(sequence)
        labels.append(label)
        
    return np.array(sequences), np.array(labels)

# Set random seed for reproducibility
torch.manual_seed(42)

# Check if CUDA is available
device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
print(f'Using device: {device}')

# Generate synthetic data
sequences, labels = generate_sequence_data()
padded_sequences, labels = create_sequence_dataset(sequences, labels, seq_length=20)

# Convert to tensors and create dataset
dataset = torch.utils.data.TensorDataset(
    torch.stack(padded_sequences),
    labels
)

# Split into train and validation sets
train_size = int(0.8 * len(dataset))
val_size = len(dataset) - train_size
train_dataset, val_dataset = torch.utils.data.random_split(dataset, [train_size, val_size])

# Create data loaders
train_loader = DataLoader(train_dataset, batch_size=32, shuffle=True)
val_loader = DataLoader(val_dataset, batch_size=32, shuffle=False)

# Create and train LSTM model
input_size = 1  # Size of each input element
hidden_size = 64
num_layers = 1
num_classes = 3

model = create_lstm_model(input_size, hidden_size, num_layers, num_classes).to(device)

# Train model
train_losses, val_losses, train_accs, val_accs = train_model(
    model, train_loader, val_loader, device, num_epochs=5)

# Plot training history
plot_training_history(train_losses, val_losses, train_accs, val_accs)

# Plot confusion matrix
plot_confusion_matrix(model, val_loader, device, num_classes)

# Load best model and evaluate
model.load_state_dict(torch.load('algorithms/deep_learning/rnn/best_model.pth'))
val_loss, val_acc = validate(model, val_loader, nn.CrossEntropyLoss(), device)
print(f'\nBest Model - Val Loss: {val_loss:.4f}, Val Acc: {val_acc:.2f}%')