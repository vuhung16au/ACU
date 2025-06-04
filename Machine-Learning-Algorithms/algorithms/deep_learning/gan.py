#!/usr/bin/env python3
# -*- coding: utf-8 -*-

print("We exclude GAN from the demonstration")
quit()

"""
Generative Adversarial Network Implementation
Simple GAN for educational purposes.
"""
import torch
import torch.nn as nn
import torch.optim as optim
import torchvision
import torchvision.transforms as transforms
from torch.utils.data import DataLoader
import matplotlib.pyplot as plt
import numpy as np
from tqdm import tqdm
import os

def create_generator(latent_dim, channels=3):
    model = nn.Sequential(
        nn.Linear(latent_dim, 128 * 8 * 8),
        nn.LeakyReLU(0.2, inplace=True),
        nn.Unflatten(1, (128, 8, 8)),
        nn.ConvTranspose2d(128, 128, 4, stride=2, padding=1),
        nn.BatchNorm2d(128),
        nn.LeakyReLU(0.2, inplace=True),
        nn.ConvTranspose2d(128, 64, 4, stride=2, padding=1),
        nn.BatchNorm2d(64),
        nn.LeakyReLU(0.2, inplace=True),
        nn.Conv2d(64, channels, 3, stride=1, padding=1),
        nn.Tanh()
    )
    return model

def create_discriminator(channels=3):
    model = nn.Sequential(
        nn.Conv2d(channels, 64, 4, stride=2, padding=1),
        nn.LeakyReLU(0.2, inplace=True),
        nn.Conv2d(64, 128, 4, stride=2, padding=1),
        nn.BatchNorm2d(128),
        nn.LeakyReLU(0.2, inplace=True),
        nn.Conv2d(128, 256, 4, stride=2, padding=1),
        nn.BatchNorm2d(256),
        nn.LeakyReLU(0.2, inplace=True),
        nn.Flatten(),
        nn.Linear(256 * 4 * 4, 1),
        nn.Sigmoid()
    )
    return model

def train_gan(generator, discriminator, dataloader, device, latent_dim, num_epochs=5):
    os.makedirs("algorithms/deep_learning/gan", exist_ok=True)
    
    # Loss function and optimizers
    adversarial_loss = nn.BCELoss()
    g_optimizer = optim.Adam(generator.parameters(), lr=0.0002, betas=(0.5, 0.999))
    d_optimizer = optim.Adam(discriminator.parameters(), lr=0.0002, betas=(0.5, 0.999))
    
    g_losses = []
    d_losses = []
    
    for epoch in range(num_epochs):
        g_loss_epoch = []
        d_loss_epoch = []
        
        for i, (real_imgs, _) in enumerate(tqdm(dataloader, desc=f'Epoch {epoch+1}/{num_epochs}')):
            batch_size = real_imgs.shape[0]
            real_imgs = real_imgs.to(device)
            
            # Ground truths
            valid = torch.ones(batch_size, 1).to(device)
            fake = torch.zeros(batch_size, 1).to(device)
            
            # Train Generator
            g_optimizer.zero_grad()
            z = torch.randn(batch_size, latent_dim).to(device)
            gen_imgs = generator(z)
            g_loss = adversarial_loss(discriminator(gen_imgs), valid)
            g_loss.backward()
            g_optimizer.step()
            
            # Train Discriminator
            d_optimizer.zero_grad()
            real_loss = adversarial_loss(discriminator(real_imgs), valid)
            fake_loss = adversarial_loss(discriminator(gen_imgs.detach()), fake)
            d_loss = (real_loss + fake_loss) / 2
            d_loss.backward()
            d_optimizer.step()
            
            g_loss_epoch.append(g_loss.item())
            d_loss_epoch.append(d_loss.item())
            
            # Save sample images every 100 batches
            if i % 100 == 0:
                save_sample_images(generator, epoch, i, latent_dim, device)
        
        g_losses.append(np.mean(g_loss_epoch))
        d_losses.append(np.mean(d_loss_epoch))
        print(f"[Epoch {epoch+1}/{num_epochs}] [D loss: {d_losses[-1]:.4f}] [G loss: {g_losses[-1]:.4f}]")
    
    # Plot and save training history
    plt.figure(figsize=(10, 5))
    plt.plot(g_losses, label='Generator Loss')
    plt.plot(d_losses, label='Discriminator Loss')
    plt.xlabel('Epoch')
    plt.ylabel('Loss')
    plt.title('GAN Training History')
    plt.legend()
    plt.savefig('algorithms/deep_learning/gan/gan-training_history.png')
    plt.close()
    
    return g_losses, d_losses

def save_sample_images(generator, epoch, batch, latent_dim, device):
    # Generate sample images
    z = torch.randn(16, latent_dim).to(device)
    gen_imgs = generator(z)
    
    # Save images
    torchvision.utils.save_image(
        gen_imgs.data,
        f"algorithms/deep_learning/gan/samples/epoch_{epoch}_batch_{batch}.png",
        nrow=4,
        normalize=True
    )

# Set random seed for reproducibility
torch.manual_seed(42)

# Check if CUDA is available
device = torch.device('cuda' if torch.cuda.is_available() else 'cpu')
print(f'Using device: {device}')

# Configure data loader
transform = transforms.Compose([
    transforms.Resize(32),
    transforms.ToTensor(),
    transforms.Normalize([0.5], [0.5])
])

# Load MNIST dataset
dataloader = DataLoader(
    torchvision.datasets.MNIST(
        'data', train=True, download=True, transform=transform
    ),
    batch_size=64, shuffle=True
)

# Create output directory
os.makedirs('algorithms/deep_learning/gan/samples', exist_ok=True)

# Initialize generator and discriminator
latent_dim = 100
generator = create_generator(latent_dim, channels=1).to(device)
discriminator = create_discriminator(channels=1).to(device)

# Train the model
g_losses, d_losses = train_gan(
    generator, discriminator, dataloader, device, latent_dim,
    num_epochs=5
)

# Save the trained models
torch.save(generator.state_dict(), 'algorithms/deep_learning/gan/generator.pth')
torch.save(discriminator.state_dict(), 'algorithms/deep_learning/gan/discriminator.pth') 