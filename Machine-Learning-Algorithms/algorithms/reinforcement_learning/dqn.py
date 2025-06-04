#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Deep Q-Network (DQN) Implementation
## This notebook demonstrates the implementation of Deep Q-Network (DQN),
## a reinforcement learning algorithm that combines Q-learning with deep neural networks.

## 1. Import Required Libraries
import numpy as np
import random
from collections import deque
from typing import List, Tuple, Optional
import torch
import torch.nn as nn
import torch.optim as optim
import torch.nn.functional as F
import gymnasium as gym
import seaborn as sns
import matplotlib.pyplot as plt
import pandas as pd
import os

## 2. Set Random Seed
np.random.seed(42)
torch.manual_seed(42)
random.seed(42)

## 3. Neural Network Functions
class DQN(nn.Module):
    def __init__(self, input_dim, output_dim):
        super(DQN, self).__init__()
        self.network = nn.Sequential(
            nn.Linear(input_dim, 64),
            nn.ReLU(),
            nn.Linear(64, output_dim)
        )
    
    def forward(self, x):
        return self.network(x)

## 4. Replay Buffer Functions
def create_replay_buffer(capacity: int) -> deque:
    """
    Create replay buffer.
    
    Hyperparameters:
    - capacity (int): Maximum number of experiences to store. Larger buffers
      provide more diverse training samples but require more memory.
    
    Args:
        capacity: Maximum number of experiences to store
        
    Returns:
        deque: Replay buffer
    """
    return deque(maxlen=capacity)

def push_to_buffer(buffer: deque, state: np.ndarray, action: int, reward: float,
                  next_state: np.ndarray, done: bool) -> None:
    """
    Add experience to buffer.
    
    Args:
        buffer: Replay buffer
        state: Current state
        action: Action taken
        reward: Reward received
        next_state: Next state
        done: Whether episode is done
    """
    buffer.append((state, action, reward, next_state, done))

def sample_from_buffer(buffer: deque, batch_size: int) -> Tuple:
    """
    Sample random batch of experiences.
    
    Args:
        buffer: Replay buffer
        batch_size: Number of experiences to sample
        
    Returns:
        Tuple of (states, actions, rewards, next_states, dones)
    """
    state, action, reward, next_state, done = zip(*random.sample(buffer, batch_size))
    return (np.array(state), np.array(action), np.array(reward),
            np.array(next_state), np.array(done))

## 5. DQN Functions
def select_action(
    policy_net: nn.Module,
    state: np.ndarray,
    epsilon: float,
    action_dim: int,
    device: str
) -> int:
    """
    Select action using epsilon-greedy policy.
    
    Hyperparameters:
    - epsilon (float): Exploration rate. Controls the balance between
      exploration and exploitation. Higher values encourage more exploration.
    
    Args:
        policy_net: Policy network
        state: Current state
        epsilon: Exploration rate
        action_dim: Number of possible actions
        device: Device to run the network on
        
    Returns:
        Selected action
    """
    if random.random() < epsilon:
        return random.randrange(action_dim)
    
    with torch.no_grad():
        state = torch.FloatTensor(state).unsqueeze(0).to(device)
        q_values = policy_net(state)
        return q_values.argmax().item()

def update_networks(
    policy_net: nn.Module,
    target_net: nn.Module,
    optimizer: optim.Optimizer,
    memory: deque,
    batch_size: int,
    gamma: float,
    device: str
) -> Optional[float]:
    """
    Update networks using experience replay.
    
    Hyperparameters:
    - batch_size (int): Number of experiences to sample for each update.
      Larger batches provide more stable updates but require more memory.
    - gamma (float): Discount factor. Controls how much future rewards are valued
      compared to immediate rewards. Higher values (closer to 1) make the agent
      more farsighted.
    
    Args:
        policy_net: Policy network
        target_net: Target network
        optimizer: Network optimizer
        memory: Replay buffer
        batch_size: Batch size for training
        gamma: Discount factor
        device: Device to run the networks on
        
    Returns:
        Loss value if update was performed, None otherwise
    """
    if len(memory) < batch_size:
        return None
    
    # Sample batch
    state, action, reward, next_state, done = sample_from_buffer(memory, batch_size)
    
    # Convert to tensors
    state = torch.FloatTensor(state).to(device)
    action = torch.LongTensor(action).to(device)
    reward = torch.FloatTensor(reward).to(device)
    next_state = torch.FloatTensor(next_state).to(device)
    done = torch.FloatTensor(done).to(device)
    
    # Compute current Q values
    current_q_values = policy_net(state).gather(1, action.unsqueeze(1))
    
    # Compute next Q values
    with torch.no_grad():
        next_q_values = target_net(next_state).max(1)[0]
        target_q_values = reward + (1 - done) * gamma * next_q_values
    
    # Compute loss and update
    loss = F.smooth_l1_loss(current_q_values, target_q_values.unsqueeze(1))
    
    optimizer.zero_grad()
    loss.backward()
    optimizer.step()
    
    return loss.item()

## 6. Training Functions
def train_dqn(env_name='CartPole-v1', episodes=200, max_steps=200):
    env = gym.make(env_name)
    state_dim = env.observation_space.shape[0]
    action_dim = env.action_space.n
    
    # Initialize networks
    policy_net = DQN(state_dim, action_dim)
    target_net = DQN(state_dim, action_dim)
    target_net.load_state_dict(policy_net.state_dict())
    
    optimizer = optim.Adam(policy_net.parameters(), lr=0.001)
    memory = deque(maxlen=10000)
    
    episode_rewards = []
    epsilon = 1.0
    epsilon_min = 0.01
    epsilon_decay = 0.995

    # Ensure the directory exists for saving
    save_dir = os.path.dirname(os.path.abspath(__file__))
    
    for episode in range(episodes):
        state, _ = env.reset()
        episode_reward = 0
        
        for step in range(max_steps):
            # Epsilon-greedy action selection
            if random.random() < epsilon:
                action = env.action_space.sample()
            else:
                with torch.no_grad():
                    state_tensor = torch.FloatTensor(state).unsqueeze(0)
                    q_values = policy_net(state_tensor)
                    action = q_values.argmax().item()
            
            # Take action
            next_state, reward, terminated, truncated, _ = env.step(action)
            done = terminated or truncated
            
            # Store transition
            memory.append((state, action, reward, next_state, done))
            
            # Update networks
            if len(memory) >= 64:
                batch = random.sample(memory, 64)
                state_batch = torch.FloatTensor([x[0] for x in batch])
                action_batch = torch.LongTensor([x[1] for x in batch])
                reward_batch = torch.FloatTensor([x[2] for x in batch])
                next_state_batch = torch.FloatTensor([x[3] for x in batch])
                done_batch = torch.FloatTensor([x[4] for x in batch])
                
                current_q_values = policy_net(state_batch).gather(1, action_batch.unsqueeze(1))
                with torch.no_grad():
                    next_q_values = target_net(next_state_batch).max(1)[0]
                    target_q_values = reward_batch + (1 - done_batch) * 0.99 * next_q_values
                
                loss = F.smooth_l1_loss(current_q_values, target_q_values.unsqueeze(1))
                optimizer.zero_grad()
                loss.backward()
                optimizer.step()
            
            state = next_state
            episode_reward += reward
            
            if done:
                break
        
        # Update target network
        if (episode + 1) % 10 == 0:
            target_net.load_state_dict(policy_net.state_dict())
        
        # Decay exploration rate
        epsilon = max(epsilon_min, epsilon * epsilon_decay)
        episode_rewards.append(episode_reward)
        
        if (episode + 1) % 10 == 0:
            print(f"Episode {episode + 1}, Reward: {episode_reward:.2f}")
    
    # Plot results
    plt.figure(figsize=(10, 5))
    plt.plot(episode_rewards)
    plt.title('DQN Training Progress')
    plt.xlabel('Episode')
    plt.ylabel('Total Reward')
    plt.savefig(os.path.join(save_dir, 'dqn_training.png'))
    plt.close()
    
    env.close()
    return episode_rewards

## 7. Visualization Functions
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
    plt.savefig(os.path.join(save_dir, 'dqn-rewards.png'))
    plt.close()
    
    # Plot and save losses
    if losses:
        plt.figure(figsize=(12, 6))
        losses_df = pd.DataFrame({
            'Step': range(len(losses)),
            'Loss': losses
        })
        sns.lineplot(data=losses_df, x='Step', y='Loss')
        plt.title('Training Losses')
        plt.xlabel('Step')
        plt.ylabel('Loss')
        plt.tight_layout()
        plt.savefig(os.path.join(save_dir, 'dqn-losses.png'))
        plt.close()

## 8. Main Execution
if __name__ == "__main__":
    train_dqn()