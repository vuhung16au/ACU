#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Transformer Model Implementation
Simple transformer for educational purposes.
"""
import torch
import torch.nn as nn
import torch.optim as optim
import numpy as np
import matplotlib.pyplot as plt
from tqdm import tqdm
import math
import os

def create_positional_encoding(d_model, max_len=5000):
    pe = torch.zeros(max_len, d_model)
    position = torch.arange(0, max_len, dtype=torch.float).unsqueeze(1)
    div_term = torch.exp(torch.arange(0, d_model, 2).float() * (-math.log(10000.0) / d_model))
    
    pe[:, 0::2] = torch.sin(position * div_term)
    pe[:, 1::2] = torch.cos(position * div_term)
    pe = pe.unsqueeze(0)
    
    return pe

def create_transformer_model(src_vocab_size, tgt_vocab_size, d_model=256, num_heads=8, num_layers=2, d_ff=1024, max_seq_length=20, dropout=0.1):
    # Embedding layers
    src_embedding = nn.Embedding(src_vocab_size, d_model)
    tgt_embedding = nn.Embedding(tgt_vocab_size, d_model)
    
    # Positional encoding
    positional_encoding = create_positional_encoding(d_model, max_seq_length)
    
    # Encoder layers
    encoder_layers = nn.ModuleList([
        nn.Sequential(
            nn.MultiheadAttention(d_model, num_heads, dropout=dropout),
            nn.LayerNorm(d_model),
            nn.Linear(d_model, d_ff),
            nn.ReLU(),
            nn.Dropout(dropout),
            nn.Linear(d_ff, d_model),
            nn.LayerNorm(d_model)
        ) for _ in range(num_layers)
    ])
    
    # Decoder layers
    decoder_layers = nn.ModuleList([
        nn.Sequential(
            nn.MultiheadAttention(d_model, num_heads, dropout=dropout),
            nn.LayerNorm(d_model),
            nn.MultiheadAttention(d_model, num_heads, dropout=dropout),
            nn.LayerNorm(d_model),
            nn.Linear(d_model, d_ff),
            nn.ReLU(),
            nn.Dropout(dropout),
            nn.Linear(d_ff, d_model),
            nn.LayerNorm(d_model)
        ) for _ in range(num_layers)
    ])
    
    # Output layer
    output_layer = nn.Linear(d_model, tgt_vocab_size)
    
    class Transformer(nn.Module):
        def __init__(self):
            super().__init__()
            self.src_embedding = src_embedding
            self.tgt_embedding = tgt_embedding
            self.positional_encoding = positional_encoding
            self.encoder_layers = encoder_layers
            self.decoder_layers = decoder_layers
            self.output_layer = output_layer
            
        def forward(self, src, tgt):
            # Padding masks
            src_key_padding_mask = (src == 0)  # (batch, src_seq)
            tgt_key_padding_mask = (tgt == 0)  # (batch, tgt_seq)
            seq_length = tgt.size(1)
            # Causal mask for decoder self-attention
            attn_mask = torch.triu(torch.ones(seq_length, seq_length, device=tgt.device), diagonal=1).bool()
            
            # Embedding and positional encoding
            src_embedded = self.src_embedding(src) + self.positional_encoding[:, :src.size(1)].to(src.device)
            tgt_embedded = self.tgt_embedding(tgt) + self.positional_encoding[:, :tgt.size(1)].to(tgt.device)
            
            # Transpose for MultiheadAttention: (batch, seq, d_model) -> (seq, batch, d_model)
            enc_output = src_embedded.transpose(0, 1)
            for enc_layer in self.encoder_layers:
                attn_output, _ = enc_layer[0](enc_output, enc_output, enc_output, key_padding_mask=src_key_padding_mask)
                enc_output = enc_layer[1]((enc_output + attn_output).transpose(0, 1)).transpose(0, 1)
                ff_output = enc_layer[2:](enc_output.transpose(0, 1)).transpose(0, 1)
                enc_output = enc_layer[1]((enc_output + ff_output).transpose(0, 1)).transpose(0, 1)
            
            dec_output = tgt_embedded.transpose(0, 1)
            for dec_layer in self.decoder_layers:
                # Self attention (with causal mask)
                attn_output, _ = dec_layer[0](dec_output, dec_output, dec_output, attn_mask=attn_mask, key_padding_mask=tgt_key_padding_mask)
                dec_output = dec_layer[1]((dec_output + attn_output).transpose(0, 1)).transpose(0, 1)
                # Cross attention (encoder-decoder)
                attn_output, _ = dec_layer[2](dec_output, enc_output, enc_output, key_padding_mask=src_key_padding_mask)
                dec_output = dec_layer[3]((dec_output + attn_output).transpose(0, 1)).transpose(0, 1)
                # Feed forward
                ff_output = dec_layer[4:](dec_output.transpose(0, 1)).transpose(0, 1)
                dec_output = dec_layer[3]((dec_output + ff_output).transpose(0, 1)).transpose(0, 1)
            # Output
            output = self.output_layer(dec_output.transpose(0, 1))
            return output
    
    return Transformer()

def train_model(model, train_loader, val_loader, device, num_epochs=5):
    criterion = nn.CrossEntropyLoss(ignore_index=0)
    optimizer = optim.Adam(model.parameters(), lr=0.0001)
    
    train_losses = []
    val_losses = []
    
    for epoch in range(num_epochs):
        # Training
        model.train()
        total_train_loss = 0
        
        for batch_idx, (src, tgt) in enumerate(tqdm(train_loader, desc=f'Epoch {epoch+1}/{num_epochs}')):
            src, tgt = src.to(device), tgt.to(device)
            
            # Prepare target for teacher forcing
            tgt_input = tgt[:, :-1]
            tgt_output = tgt[:, 1:]
            
            optimizer.zero_grad()
            output = model(src, tgt_input)
            loss = criterion(output.contiguous().view(-1, output.size(-1)), 
                           tgt_output.contiguous().view(-1))
            loss.backward()
            torch.nn.utils.clip_grad_norm_(model.parameters(), max_norm=1.0)
            optimizer.step()
            
            total_train_loss += loss.item()
        
        avg_train_loss = total_train_loss / len(train_loader)
        train_losses.append(avg_train_loss)
        
        # Validation
        model.eval()
        total_val_loss = 0
        
        with torch.no_grad():
            for src, tgt in val_loader:
                src, tgt = src.to(device), tgt.to(device)
                tgt_input = tgt[:, :-1]
                tgt_output = tgt[:, 1:]
                
                output = model(src, tgt_input)
                loss = criterion(output.contiguous().view(-1, output.size(-1)), 
                               tgt_output.contiguous().view(-1))
                total_val_loss += loss.item()
        
        avg_val_loss = total_val_loss / len(val_loader)
        val_losses.append(avg_val_loss)
        
        print(f'Epoch {epoch+1}/{num_epochs}:')
        print(f'Train Loss: {avg_train_loss:.4f}')
        print(f'Val Loss: {avg_val_loss:.4f}')
    
    # Plot and save training history
    plt.figure(figsize=(10, 5))
    plt.plot(train_losses, label='Train Loss')
    plt.plot(val_losses, label='Val Loss')
    plt.xlabel('Epoch')
    plt.ylabel('Loss')
    plt.title('Transformer Training History')
    plt.legend()
    plt.savefig('algorithms/deep_learning/transformer/transformer-training_history.png')
    plt.close()
    
    return train_losses, val_losses

def generate_synthetic_data(n_samples=1000, seq_length=20, vocab_size=100):
    src_sequences = []
    tgt_sequences = []
    
    for _ in range(n_samples):
        src_seq = np.random.randint(1, vocab_size, size=seq_length)
        tgt_seq = np.random.randint(1, vocab_size, size=seq_length)
        src_sequences.append(src_seq)
        tgt_sequences.append(tgt_seq)
    
    return np.array(src_sequences), np.array(tgt_sequences)

# Set random seed for reproducibility
torch.manual_seed(42)

# Check if CUDA is available
device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
print(f'Using device: {device}')

# Create output directory
os.makedirs('algorithms/deep_learning/transformer', exist_ok=True)

# Generate synthetic data
src_sequences, tgt_sequences = generate_synthetic_data()

# Create datasets
train_size = int(0.8 * len(src_sequences))
train_src = torch.LongTensor(src_sequences[:train_size])
train_tgt = torch.LongTensor(tgt_sequences[:train_size])
val_src = torch.LongTensor(src_sequences[train_size:])
val_tgt = torch.LongTensor(tgt_sequences[train_size:])

# Create data loaders
train_dataset = torch.utils.data.TensorDataset(train_src, train_tgt)
val_dataset = torch.utils.data.TensorDataset(val_src, val_tgt)

train_loader = torch.utils.data.DataLoader(train_dataset, batch_size=32, shuffle=True)
val_loader = torch.utils.data.DataLoader(val_dataset, batch_size=32, shuffle=False)

# Create and train model
model = create_transformer_model(
    src_vocab_size=100,
    tgt_vocab_size=100,
    d_model=256,
    num_heads=8,
    num_layers=2,
    d_ff=1024,
    max_seq_length=20
).to(device)

# Train model
train_losses, val_losses = train_model(
    model, train_loader, val_loader, device, num_epochs=5
)

# Save the trained model
torch.save(model.state_dict(), 'algorithms/deep_learning/transformer/transformer.pth')