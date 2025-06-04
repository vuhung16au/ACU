#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Q-Learning Implementation
## This notebook demonstrates the implementation of Q-Learning,
## a reinforcement learning algorithm that learns the value of an action
## in a particular state.

## 1. Import Required Libraries
import numpy as np
from typing import Dict, Tuple, List
import random
import seaborn as sns
import matplotlib.pyplot as plt
import pandas as pd

## 2. Set Random Seed
np.random.seed(2220)
random.seed(2220)

## 3. Q-Learning Functions
def create_q_table(states: List[int], actions: List[int]) -> Dict[int, Dict[int, float]]:
    """
    Create Q-table initialized with zeros.
    
    Args:
        states: List of possible states
        actions: List of possible actions
        
    Returns:
        Dictionary mapping states to dictionaries of action-value pairs
    """
    return {state: {action: 0.0 for action in actions} for state in states}

def choose_action(
    q_table: Dict[int, Dict[int, float]],
    state: int,
    actions: List[int],
    exploration_rate: float
) -> int:
    """
    Choose action using epsilon-greedy policy.
    
    Hyperparameters:
    - exploration_rate (float): Probability of taking a random action.
      Higher values encourage more exploration but may slow down learning.
    
    Args:
        q_table: Q-table mapping states to action values
        state: Current state
        actions: List of possible actions
        exploration_rate: Probability of taking a random action
        
    Returns:
        Selected action
    """
    if random.random() < exploration_rate:
        # Exploration: choose random action
        return random.choice(actions)
    else:
        # Exploitation: choose best action
        return max(q_table[state].items(), key=lambda x: x[1])[0]

def update_q_value(
    q_table: Dict[int, Dict[int, float]],
    state: int,
    action: int,
    reward: float,
    next_state: int,
    learning_rate: float,
    discount_factor: float
) -> None:
    """
    Update Q-value using Q-learning update rule.
    
    Hyperparameters:
    - learning_rate (float): Step size for Q-value updates. Higher values
      make the agent learn faster but may lead to instability.
    - discount_factor (float): Importance of future rewards. Higher values
      make the agent more farsighted.
    
    Args:
        q_table: Q-table mapping states to action values
        state: Current state
        action: Action taken
        reward: Reward received
        next_state: Next state
        learning_rate: Step size for Q-value updates
        discount_factor: Importance of future rewards
    """
    # Q-learning update rule
    old_value = q_table[state][action]
    next_max = max(q_table[next_state].values())
    
    # Q(s,a) = Q(s,a) + α[r + γ max(Q(s',a')) - Q(s,a)]
    new_value = old_value + learning_rate * (
        reward + discount_factor * next_max - old_value
    )
    
    q_table[state][action] = new_value

def get_policy(q_table: Dict[int, Dict[int, float]]) -> Dict[int, int]:
    """
    Get the learned policy.
    
    Args:
        q_table: Q-table mapping states to action values
        
    Returns:
        Dictionary mapping states to their best actions
    """
    return {state: max(actions.items(), key=lambda x: x[1])[0]
            for state, actions in q_table.items()}

## 4. Environment Functions
def create_grid_world(size: int = 4) -> Tuple[List[int], List[int], int, int]:
    """
    Create a simple grid world environment.
    
    Args:
        size: Size of the grid (size x size)
        
    Returns:
        Tuple of (states, actions, initial_state, goal_state)
    """
    states = list(range(size * size))
    actions = [0, 1, 2, 3]  # 0=up, 1=right, 2=down, 3=left
    initial_state = 0  # Start at top-left
    goal_state = size * size - 1  # Goal at bottom-right
    
    return states, actions, initial_state, goal_state

def step_grid_world(
    state: int,
    action: int,
    size: int,
    goal_state: int
) -> Tuple[int, float, bool]:
    """
    Take a step in the grid world environment.
    
    Args:
        state: Current state
        action: Action to take
        size: Size of the grid
        goal_state: Goal state
        
    Returns:
        Tuple of (next_state, reward, done)
    """
    row = state // size
    col = state % size
    
    if action == 0:  # Up
        row = max(0, row - 1)
    elif action == 1:  # Right
        col = min(size - 1, col + 1)
    elif action == 2:  # Down
        row = min(size - 1, row + 1)
    elif action == 3:  # Left
        col = max(0, col - 1)
    
    next_state = row * size + col
    
    # Reward: -1 for each step, +10 for reaching goal
    reward = 10.0 if next_state == goal_state else -1.0
    done = next_state == goal_state
    
    return next_state, reward, done

## 5. Training Functions
def train_q_learning(
    states: List[int],
    actions: List[int],
    initial_state: int,
    goal_state: int,
    size: int,
    episodes: int = 1000,
    max_steps: int = 100,
    learning_rate: float = 0.1,
    discount_factor: float = 0.95,
    initial_exploration_rate: float = 0.1,
    exploration_decay: float = 0.995,
    min_exploration_rate: float = 0.01
) -> Tuple[Dict[int, Dict[int, float]], List[float]]:
    """
    Train Q-learning agent.
    
    Hyperparameters:
    - episodes (int): Number of training episodes. More episodes generally lead
      to better performance but require more training time.
    - max_steps (int): Maximum steps per episode. Should be set based on the
      environment's typical episode length.
    - learning_rate (float): Step size for Q-value updates. Higher values
      make the agent learn faster but may lead to instability.
    - discount_factor (float): Importance of future rewards. Higher values
      make the agent more farsighted.
    - initial_exploration_rate (float): Initial probability of taking a random action.
      Higher values encourage more exploration at the start.
    - exploration_decay (float): Rate at which exploration rate decreases.
      Controls how quickly the agent transitions from exploration to exploitation.
    - min_exploration_rate (float): Minimum exploration rate. Prevents the agent
      from completely stopping exploration.
    
    Args:
        states: List of possible states
        actions: List of possible actions
        initial_state: Initial state
        goal_state: Goal state
        size: Size of the grid
        episodes: Number of episodes
        max_steps: Maximum steps per episode
        learning_rate: Step size for Q-value updates
        discount_factor: Importance of future rewards
        initial_exploration_rate: Initial probability of taking a random action
        exploration_decay: Rate at which exploration rate decreases
        min_exploration_rate: Minimum exploration rate
        
    Returns:
        Tuple of (Q-table, episode rewards)
    """
    # Initialize Q-table
    q_table = create_q_table(states, actions)
    episode_rewards = []
    exploration_rate = initial_exploration_rate
    
    for episode in range(episodes):
        state = initial_state
        total_reward = 0
        
        for step in range(max_steps):
            action = choose_action(q_table, state, actions, exploration_rate)
            next_state, reward, done = step_grid_world(state, action, size, goal_state)
            
            update_q_value(q_table, state, action, reward, next_state,
                          learning_rate, discount_factor)
            
            total_reward += reward
            state = next_state
            
            if done:
                break
        
        episode_rewards.append(total_reward)
        
        # Decay exploration rate
        exploration_rate = max(min_exploration_rate,
                             exploration_rate * exploration_decay)
    
    return q_table, episode_rewards

## 6. Visualization Functions
def plot_training_results(episode_rewards: List[float], window_size: int = 10):
    """
    Plot training results using seaborn and save to PNG file.
    """
    import os
    # Create figure
    plt.figure(figsize=(12, 6))
    rewards_df = pd.DataFrame({
        'Episode': range(len(episode_rewards)),
        'Reward': episode_rewards
    })
    sns.lineplot(data=rewards_df, x='Episode', y='Reward')
    plt.title('Q-Learning Training Rewards')
    plt.xlabel('Episode')
    plt.ylabel('Total Reward')
    plt.tight_layout()
    save_dir = os.path.dirname(os.path.abspath(__file__))
    plt.savefig(os.path.join(save_dir, 'q_learning-rewards.png'))
    plt.close()

def visualize_policy(
    policy: Dict[int, int],
    size: int,
    initial_state: int,
    goal_state: int
):
    """
    Visualize the learned policy in the grid world and save to PNG file.
    """
    import os
    grid = np.zeros((size, size))
    grid[initial_state // size, initial_state % size] = 1  # Start
    grid[goal_state // size, goal_state % size] = 2  # Goal
    plt.figure(figsize=(8, 8))
    sns.heatmap(grid, cmap='YlOrRd', cbar=False, square=True)
    for state, action in policy.items():
        if state == goal_state:
            continue
        row = state // size
        col = state % size
        if action == 0:  # Up
            plt.arrow(col + 0.5, row + 0.7, 0, -0.3, head_width=0.1, head_length=0.1)
        elif action == 1:  # Right
            plt.arrow(col + 0.3, row + 0.5, 0.3, 0, head_width=0.1, head_length=0.1)
        elif action == 2:  # Down
            plt.arrow(col + 0.5, row + 0.3, 0, 0.3, head_width=0.1, head_length=0.1)
        elif action == 3:  # Left
            plt.arrow(col + 0.7, row + 0.5, -0.3, 0, head_width=0.1, head_length=0.1)
    plt.title('Learned Policy')
    plt.tight_layout()
    save_dir = os.path.dirname(os.path.abspath(__file__))
    plt.savefig(os.path.join(save_dir, 'q_learning-policy.png'))
    plt.close()

## 7. Main Execution
# Create environment
size = 4
states, actions, initial_state, goal_state = create_grid_world(size)

# Train agent
q_table, episode_rewards = train_q_learning(
    states=states,
    actions=actions,
    initial_state=initial_state,
    goal_state=goal_state,
    size=size,
    episodes=1000,
    max_steps=100,
    learning_rate=0.1,
    discount_factor=0.95,
    initial_exploration_rate=0.1,
    exploration_decay=0.995,
    min_exploration_rate=0.01
)

# Get and print policy
policy = get_policy(q_table)
print("\nLearned Policy:")
for state in range(size * size):
    action = policy[state]
    action_name = ["Up", "Right", "Down", "Left"][action]
    print(f"State {state}: {action_name}")

# Save results to PNG files
plot_training_results(episode_rewards)
visualize_policy(policy, size, initial_state, goal_state)