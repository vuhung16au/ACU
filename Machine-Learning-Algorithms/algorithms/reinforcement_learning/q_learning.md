# Q-Learning

## 1. Overview
Q-Learning is a model-free reinforcement learning algorithm that learns the value of an action in a particular state. It does not require a model of the environment and can handle problems with stochastic transitions and rewards.

### Type of Learning
- Reinforcement Learning
- Model-free Learning
- Value-based Learning

### Key Characteristics
- Learns optimal action-selection policy
- Works without a model of the environment
- Handles stochastic environments
- Uses temporal difference learning
- Converges to optimal policy

### When to Use
- When environment model is unknown
- For problems with discrete state and action spaces
- When learning from experience is needed
- For problems requiring exploration vs exploitation
- When dealing with delayed rewards

## 2. Historical Context
- Developed by Chris Watkins in 1989
- Based on temporal difference learning
- One of the most fundamental RL algorithms
- Inspired many modern RL approaches
- Still widely used in practice

## 3. Technical Details

### Mathematical Foundation

#### Q-Value Function
The Q-value function represents the expected cumulative reward for taking action $a$ in state $s$ and following the optimal policy thereafter:

$$
Q(s,a) = \mathbb{E}\left[\sum_{t=0}^{\infty} \gamma^t r_t | s_0 = s, a_0 = a\right]
$$

where:
- $\gamma \in [0,1]$ is the discount factor
- $r_t$ is the reward at time step $t$
- $\mathbb{E}$ denotes the expectation operator

#### Bellman Equation
The optimal Q-value function satisfies the Bellman equation:

$$
Q^*(s,a) = \mathbb{E}\left[r + \gamma \max_{a'} Q^*(s',a') | s,a\right]
$$

where:
- $s'$ is the next state
- $a'$ is the next action
- $r$ is the immediate reward

#### Q-Learning Update Rule
The Q-value is updated using temporal difference learning:

$$
Q(s_t, a_t) \leftarrow Q(s_t, a_t) + \alpha[r_t + \gamma \max_a Q(s_{t+1}, a) - Q(s_t, a_t)]
$$

where:
- $\alpha \in (0,1]$ is the learning rate
- $r_t$ is the immediate reward
- $\gamma \in [0,1]$ is the discount factor
- $\max_a Q(s_{t+1}, a)$ is the maximum Q-value for the next state

#### Convergence Conditions
The algorithm converges to the optimal Q-values with probability 1 if:
1. All state-action pairs are visited infinitely often
2. The learning rate $\alpha_t$ satisfies:
   $$
   \sum_{t=0}^{\infty} \alpha_t = \infty \quad \text{and} \quad \sum_{t=0}^{\infty} \alpha_t^2 < \infty
   $$

#### Exploration Strategies

1. ε-Greedy Policy:
$$
\pi(s) = \begin{cases}
\text{argmax}_a Q(s,a) & \text{with probability } 1-\epsilon \\
\text{random action} & \text{with probability } \epsilon
\end{cases}
$$

2. Boltzmann Exploration:
$$
\pi(a|s) = \frac{\exp(Q(s,a)/\tau)}{\sum_b \exp(Q(s,b)/\tau)}
$$
where $\tau$ is the temperature parameter.

#### Performance Metrics

1. Expected Return:
$$
G_t = \sum_{k=0}^{\infty} \gamma^k r_{t+k}
$$

2. Value Function Error:
$$
\text{Error} = \|Q - Q^*\|_{\infty} = \max_{s,a} |Q(s,a) - Q^*(s,a)|
$$

#### Time Complexity Analysis
For each update:
- Q-value lookup: $O(1)$
- Action selection: $O(|A|)$
- Q-value update: $O(1)$

Total complexity per episode:
- $O(T \times |A|)$ where $T$ is the maximum steps per episode

#### Space Complexity Analysis
- Q-table: $O(|S| \times |A|)$
- State representation: $O(d)$ where $d$ is the state dimension
- Action space: $O(|A|)$

Total space complexity: $O(|S| \times |A| + d + |A|)$

### Training Process
1. Initialize Q-values
2. Select action using exploration policy
3. Execute action and observe reward
4. Update Q-value using the update rule
5. Repeat until convergence

### Key Parameters
- Learning rate (α)
- Discount factor (γ)
- Exploration rate (ε)
- Number of episodes
- Maximum steps per episode

## 4. Performance Analysis

### Time Complexity
- Per update: O(1)
- Per episode: O(T)
- Total: O(E × T)

where:
- T = maximum steps per episode
- E = number of episodes

### Space Complexity
- O(|S| × |A|) for Q-table
where:
- |S| = number of states
- |A| = number of actions

### Computational Requirements
- Memory scales with state-action space
- Can be parallelized
- Suitable for discrete spaces
- May require function approximation for large spaces

## 5. Practical Applications
- Game playing
- Robotics control
- Autonomous systems
- Resource management
- Traffic control
- Recommendation systems

## 6. Advantages and Limitations

### Advantages
- Model-free learning
- Guaranteed convergence
- Simple to implement
- Works with stochastic environments
- Can handle delayed rewards

### Limitations
- Requires discrete state-action space
- Can be slow to converge
- Memory intensive for large spaces
- May need function approximation
- Sensitive to parameter tuning

## 7. Implementation Guidelines

### Prerequisites
- NumPy
- Gym (for environments)
- Matplotlib (for visualization)
- Pandas (for data handling)

### Environment Requirements
- Discrete state space
- Discrete action space
- Reward function
- State transition function
- Terminal states

### Best Practices
- Proper exploration strategy
- Appropriate learning rate decay
- State space discretization
- Reward function design
- Convergence monitoring

## 8. Python Implementation
See `q_learning.py` for complete implementation. 