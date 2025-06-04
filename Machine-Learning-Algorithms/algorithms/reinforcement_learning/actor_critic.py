#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Actor-Critic Implementation
## This notebook demonstrates the implementation of the Actor-Critic algorithm,
## a policy gradient method that combines value-based and policy-based approaches
## in reinforcement learning.

## 1. Import Required Libraries
import numpy as np
import torch
import torch.nn as nn
import torch.optim as optim
import torch.nn.functional as F
import gymnasium as gym
import seaborn as sns
import matplotlib.pyplot as plt
import pandas as pd
import os
from typing import List, Tuple, Optional

## 2. Set Random Seed
np.random.seed(2220)
torch.manual_seed(2220)

## 3. Neural Network Functions
def create_actor_network(input_dim: int, output_dim: int, hidden_dims: List[int] = [64, 64]) -> nn.Module:
    """
    Create Actor Network.
    
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
        nn.Module: Actor network
    """
    layers = []
    prev_dim = input_dim
    
    for hidden_dim in hidden_dims:
        layers.extend([
            nn.Linear(prev_dim, hidden_dim),
            nn.ReLU()
        ])
        prev_dim = hidden_dim
    
    layers.append(nn.Linear(prev_dim, output_dim))
    network = nn.Sequential(*layers)
    
    return network

def create_critic_network(input_dim: int, hidden_dims: List[int] = [64, 64]) -> nn.Module:
    """
    Create Critic Network.
    
    Hyperparameters:
    - input_dim (int): Input dimension (state space size)
    - hidden_dims (List[int]): List of hidden layer dimensions. Larger networks
      can learn more accurate value estimates but require more training time.
    
    Args:
        input_dim: Input dimension (state space size)
        hidden_dims: List of hidden layer dimensions
        
    Returns:
        nn.Module: Critic network
    """
    layers = []
    prev_dim = input_dim
    
    for hidden_dim in hidden_dims:
        layers.extend([
            nn.Linear(prev_dim, hidden_dim),
            nn.ReLU()
        ])
        prev_dim = hidden_dim
    
    layers.append(nn.Linear(prev_dim, 1))
    network = nn.Sequential(*layers)
    
    return network

## 4. Actor-Critic Functions
def select_action(actor: nn.Module, state: np.ndarray, device: str) -> Tuple[int, float]:
    """
    Select action using the actor network.
    
    Args:
        actor: Actor network
        state: Current state
        device: Device to run the network on
        
    Returns:
        Tuple of (selected action, action probability)
    """
    state = torch.FloatTensor(state).unsqueeze(0).to(device)
    action_probs = F.softmax(actor(state), dim=-1)
    
    # Sample action from probability distribution
    action = torch.multinomial(action_probs, 1).item()
    action_prob = action_probs[0, action].item()
    
    return action, action_prob

def get_value(critic: nn.Module, state: np.ndarray, device: str) -> float:
    """
    Get state value from critic network.
    
    Args:
        critic: Critic network
        state: Current state
        device: Device to run the network on
        
    Returns:
        State value
    """
    state = torch.FloatTensor(state).unsqueeze(0).to(device)
    return critic(state).item()

def update_networks(
    actor: nn.Module,
    critic: nn.Module,
    actor_optimizer: optim.Optimizer,
    critic_optimizer: optim.Optimizer,
    states: List[np.ndarray],
    actions: List[int],
    rewards: List[float],
    next_states: List[np.ndarray],
    dones: List[bool],
    gamma: float,
    device: str
) -> Tuple[float, float]:
    """
    Update actor and critic networks.
    
    Hyperparameters:
    - gamma (float): Discount factor. Controls how much future rewards are valued
      compared to immediate rewards. Higher values (closer to 1) make the agent
      more farsighted.
    
    Args:
        actor: Actor network
        critic: Critic network
        actor_optimizer: Actor optimizer
        critic_optimizer: Critic optimizer
        states: List of states
        actions: List of actions
        rewards: List of rewards
        next_states: List of next states
        dones: List of done flags
        gamma: Discount factor
        device: Device to run the networks on
        
    Returns:
        Tuple of (actor loss, critic loss)
    """
    # Convert to tensors
    states = torch.FloatTensor(states).to(device)
    actions = torch.LongTensor(actions).to(device)
    rewards = torch.FloatTensor(rewards).to(device)
    next_states = torch.FloatTensor(next_states).to(device)
    dones = torch.FloatTensor(dones).to(device)
    
    # Calculate returns and advantages
    returns = []
    R = 0
    
    for r, next_state, done in zip(reversed(rewards), reversed(next_states), reversed(dones)):
        R = r + gamma * R * (1 - done)
        returns.insert(0, R)
    
    returns = torch.tensor(returns).to(device)
    returns = (returns - returns.mean()) / (returns.std() + 1e-8)
    
    # Get state values
    values = critic(states).squeeze()
    next_values = critic(next_states).squeeze()
    
    # Calculate advantages
    deltas = rewards + gamma * next_values * (1 - dones) - values
    advantages = []
    advantage = 0
    
    for delta in reversed(deltas):
        advantage = delta + gamma * advantage
        advantages.insert(0, advantage)
    
    advantages = torch.tensor(advantages).to(device)
    advantages = (advantages - advantages.mean()) / (advantages.std() + 1e-8)
    
    # Update actor
    action_probs = F.softmax(actor(states), dim=-1)
    selected_action_probs = action_probs.gather(1, actions.unsqueeze(1)).squeeze()
    actor_loss = -(torch.log(selected_action_probs) * advantages).mean()
    
    actor_optimizer.zero_grad()
    actor_loss.backward()
    actor_optimizer.step()
    
    # Update critic
    critic_loss = F.mse_loss(values, returns)
    
    critic_optimizer.zero_grad()
    critic_loss.backward()
    critic_optimizer.step()
    
    return actor_loss.item(), critic_loss.item()

## 5. Training Functions
def train_actor_critic(
    env: gym.Env,
    actor: nn.Module,
    critic: nn.Module,
    actor_optimizer: optim.Optimizer,
    critic_optimizer: optim.Optimizer,
    episodes: int = 1000,
    max_steps: int = 1000,
    gamma: float = 0.99,
    device: str = 'cuda' if torch.cuda.is_available() else 'cpu',
    render: bool = False
) -> Tuple[List[float], List[float], List[float]]:
    """
    Train Actor-Critic agent.
    
    Hyperparameters:
    - episodes (int): Number of training episodes. More episodes generally lead
      to better performance but require more training time.
    - max_steps (int): Maximum steps per episode. Should be set based on the
      environment's typical episode length.
    - gamma (float): Discount factor. Controls how much future rewards are valued
      compared to immediate rewards.
    
    Args:
        env: Environment
        actor: Actor network
        critic: Critic network
        actor_optimizer: Actor optimizer
        critic_optimizer: Critic optimizer
        episodes: Number of episodes
        max_steps: Maximum steps per episode
        gamma: Discount factor
        device: Device to run the networks on
        render: Whether to render the environment
        
    Returns:
        Tuple of (episode rewards, actor losses, critic losses)
    """
    episode_rewards = []
    actor_losses = []
    critic_losses = []
    
    for episode in range(episodes):
        state, _ = env.reset()
        episode_reward = 0
        states, actions, rewards, next_states, dones = [], [], [], [], []
        
        for step in range(max_steps):
            # Select action
            action, _ = select_action(actor, state, device)
            
            # Take action
            next_state, reward, terminated, truncated, _ = env.step(action)
            done = terminated or truncated
            
            # Store transition
            states.append(state)
            actions.append(action)
            rewards.append(reward)
            next_states.append(next_state)
            dones.append(done)
            
            state = next_state
            episode_reward += reward
            
            if done:
                break
        
        # Update networks
        actor_loss, critic_loss = update_networks(
            actor, critic, actor_optimizer, critic_optimizer,
            states, actions, rewards, next_states, dones,
            gamma, device
        )
        
        episode_rewards.append(episode_reward)
        actor_losses.append(actor_loss)
        critic_losses.append(critic_loss)
        
        if (episode + 1) % 10 == 0:
            print(f"Episode {episode + 1}, Reward: {episode_reward:.2f}")
    
    return episode_rewards, actor_losses, critic_losses

## 6. Visualization Functions
def plot_training_results(
    episode_rewards: List[float],
    actor_losses: List[float],
    critic_losses: List[float],
    window_size: int = 10
):
    """
    Plot training results using seaborn and save to PNG files.
    
    Args:
        episode_rewards: List of episode rewards
        actor_losses: List of actor losses
        critic_losses: List of critic losses
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
    plt.savefig(os.path.join(save_dir, 'actor_critic-rewards.png'))
    plt.close()
    
    # Plot and save actor losses
    plt.figure(figsize=(12, 6))
    actor_losses_df = pd.DataFrame({
        'Episode': range(len(actor_losses)),
        'Loss': actor_losses
    })
    sns.lineplot(data=actor_losses_df, x='Episode', y='Loss')
    plt.title('Actor Losses')
    plt.xlabel('Episode')
    plt.ylabel('Loss')
    plt.tight_layout()
    plt.savefig(os.path.join(save_dir, 'actor_critic-actor_losses.png'))
    plt.close()
    
    # Plot and save critic losses
    plt.figure(figsize=(12, 6))
    critic_losses_df = pd.DataFrame({
        'Episode': range(len(critic_losses)),
        'Loss': critic_losses
    })
    sns.lineplot(data=critic_losses_df, x='Episode', y='Loss')
    plt.title('Critic Losses')
    plt.xlabel('Episode')
    plt.ylabel('Loss')
    plt.tight_layout()
    plt.savefig(os.path.join(save_dir, 'actor_critic-critic_losses.png'))
    plt.close()

## 7. Main Execution
# Create environment
env = gym.make('CartPole-v1')
state_dim = env.observation_space.shape[0]
action_dim = env.action_space.n

# Create networks
actor = create_actor_network(state_dim, action_dim)
critic = create_critic_network(state_dim)

# Create optimizers
actor_optimizer = optim.Adam(actor.parameters(), lr=0.001)
critic_optimizer = optim.Adam(critic.parameters(), lr=0.001)

# Train agent
episode_rewards, actor_losses, critic_losses = train_actor_critic(
    env=env,
    actor=actor,
    critic=critic,
    actor_optimizer=actor_optimizer,
    critic_optimizer=critic_optimizer,
    episodes=1000,
    max_steps=1000,
    gamma=0.99
)

# Save results to PNG files
plot_training_results(episode_rewards, actor_losses, critic_losses)