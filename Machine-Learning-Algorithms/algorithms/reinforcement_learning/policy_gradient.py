#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Policy Gradient Implementation
## This notebook demonstrates the implementation of Policy Gradient,
## a reinforcement learning algorithm that directly optimizes the policy
## using gradient ascent.

## 1. Import Required Libraries
import numpy as np
import torch
import torch.nn as nn
import torch.optim as optim
import torch.nn.functional as F
from typing import List, Tuple
import gymnasium as gym
import seaborn as sns
import matplotlib.pyplot as plt
import pandas as pd
import os

## 2. Set Random Seed
np.random.seed(2220)
torch.manual_seed(2220)

## 3. Neural Network Functions
def create_policy_network(input_dim: int, output_dim: int, hidden_dims: List[int] = [64, 64]) -> nn.Module:
    """
    Create Policy Network.
    
    Hyperparameters:
    - input_dim (int): Input dimension (state space size)
    - output_dim (int): Output dimension (action space size)
    - hidden_dims (List[int]): List of hidden layer dimensions. Larger networks
      can learn more complex policies but require more training time.
    
    Args:
        input_dim: Input dimension (state space size)
        output_dim: Output dimension (action space size)
        hidden_dims: List of hidden layer dimensions
        
    Returns:
        nn.Module: Policy network
    """
    layers = []
    prev_dim = input_dim
    
    for hidden_dim in hidden_dims:
        layers.extend([
            nn.Linear(prev_dim, hidden_dim),
            nn.ReLU()
        ])
        prev_dim = hidden_dim
    
    # Output layer for action probabilities
    layers.append(nn.Linear(prev_dim, output_dim))
    network = nn.Sequential(*layers)
    
    return network

## 4. Policy Functions
def select_action(
    policy_net: nn.Module,
    state: np.ndarray,
    device: str
) -> Tuple[int, float]:
    """
    Select action using the policy network.
    
    Args:
        policy_net: Policy network
        state: Current state
        device: Device to run the network on
        
    Returns:
        Tuple of (selected action, action probability)
    """
    state = torch.FloatTensor(state).unsqueeze(0).to(device)
    action_probs = F.softmax(policy_net(state), dim=-1)
    
    # Sample action from probability distribution
    action = torch.multinomial(action_probs, 1).item()
    action_prob = action_probs[0, action].item()
    
    return action, action_prob

def update_policy(
    policy_net: nn.Module,
    optimizer: optim.Optimizer,
    rewards: List[float],
    log_probs: List[torch.Tensor],
    gamma: float,
    device: str
) -> float:
    """
    Update policy using REINFORCE algorithm.
    
    Hyperparameters:
    - gamma (float): Discount factor. Controls how much future rewards are valued
      compared to immediate rewards. Higher values (closer to 1) make the agent
      more farsighted.
    
    Args:
        policy_net: Policy network
        optimizer: Network optimizer
        rewards: List of rewards from episode
        log_probs: List of log probabilities of taken actions
        gamma: Discount factor
        device: Device to run the network on
        
    Returns:
        Loss value
    """
    # Calculate returns
    returns = []
    R = 0
    for r in rewards[::-1]:
        R = r + gamma * R
        returns.insert(0, R)
    
    # Normalize returns
    returns = torch.tensor(returns).to(device)
    returns = (returns - returns.mean()) / (returns.std() + 1e-8)
    
    # Calculate loss
    policy_loss = []
    for log_prob, R in zip(log_probs, returns):
        policy_loss.append(-log_prob * R)
    policy_loss = torch.stack(policy_loss).sum()
    
    # Update policy
    optimizer.zero_grad()
    policy_loss.backward()
    optimizer.step()
    
    return policy_loss.item()

## 5. Training Functions
def train_policy_gradient(
    env: gym.Env,
    policy_net: nn.Module,
    optimizer: optim.Optimizer,
    episodes: int = 1000,
    max_steps: int = 1000,
    gamma: float = 0.99,
    device: str = 'cuda' if torch.cuda.is_available() else 'cpu'
) -> Tuple[List[float], List[float]]:
    """
    Train Policy Gradient agent.
    
    Hyperparameters:
    - episodes (int): Number of training episodes. More episodes generally lead
      to better performance but require more training time.
    - max_steps (int): Maximum steps per episode. Should be set based on the
      environment's typical episode length.
    - gamma (float): Discount factor. Controls how much future rewards are valued
      compared to immediate rewards.
    
    Args:
        env: Environment
        policy_net: Policy network
        optimizer: Network optimizer
        episodes: Number of episodes
        max_steps: Maximum steps per episode
        gamma: Discount factor
        device: Device to run the network on
        
    Returns:
        Tuple of (episode rewards, losses)
    """
    episode_rewards = []
    losses = []
    
    for episode in range(episodes):
        state, _ = env.reset()
        episode_reward = 0
        log_probs = []
        rewards = []
        
        for step in range(max_steps):
            # Select action
            action, action_prob = select_action(policy_net, state, device)
            log_probs.append(torch.log(torch.tensor(action_prob, requires_grad=True)).to(device))
            
            # Take action
            next_state, reward, terminated, truncated, _ = env.step(action)
            done = terminated or truncated
            rewards.append(reward)
            episode_reward += reward
            
            state = next_state
            if done:
                break
        
        # Update policy
        loss = update_policy(policy_net, optimizer, rewards, log_probs, gamma, device)
        losses.append(loss)
        
        episode_rewards.append(episode_reward)
        
        if (episode + 1) % 10 == 0:
            print(f"Episode {episode + 1}, Reward: {episode_reward:.2f}, Loss: {loss:.4f}")
    
    return episode_rewards, losses

## 6. Visualization Functions
def plot_training_results(
    episode_rewards: List[float],
    losses: List[float],
    window_size: int = 10
):
    """
    Plot training results using seaborn and save to PNG files.
    
    Args:
        episode_rewards: List of episode rewards
        losses: List of training losses
        window_size: Window size for moving average
    """
    # Ensure the directory exists
    save_dir = os.path.dirname(os.path.abspath(__file__))
    
    # Plot and save rewards
    plt.figure(figsize=(12, 6))
    rewards_df = pd.DataFrame({
        'Episode': range(len(episode_rewards)),
        'Reward': episode_rewards
    })
    sns.lineplot(data=rewards_df, x='Episode', y='Reward')
    plt.title('Episode Rewards')
    plt.xlabel('Episode')
    plt.ylabel('Reward')
    plt.tight_layout()
    plt.savefig(os.path.join(save_dir, 'policy_gradient-rewards.png'))
    plt.close()
    
    # Plot and save losses
    if losses:
        plt.figure(figsize=(12, 6))
        losses_df = pd.DataFrame({
            'Episode': range(len(losses)),
            'Loss': losses
        })
        sns.lineplot(data=losses_df, x='Episode', y='Loss')
        plt.title('Training Losses')
        plt.xlabel('Episode')
        plt.ylabel('Loss')
        plt.tight_layout()
        plt.savefig(os.path.join(save_dir, 'policy_gradient-losses.png'))
        plt.close()

## 7. Main Execution
# Create environment
env = gym.make('CartPole-v1')
state_dim = env.observation_space.shape[0]
action_dim = env.action_space.n

# Create policy network
policy_net = create_policy_network(state_dim, action_dim)

# Create optimizer
optimizer = optim.Adam(policy_net.parameters(), lr=0.001)

# Train agent
episode_rewards, losses = train_policy_gradient(
    env=env,
    policy_net=policy_net,
    optimizer=optimizer,
    episodes=1000,
    max_steps=1000,
    gamma=0.99
)

# Save results to PNG files
plot_training_results(episode_rewards, losses)