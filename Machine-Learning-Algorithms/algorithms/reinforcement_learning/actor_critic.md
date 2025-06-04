# Actor-Critic

## 1. Overview
Actor-Critic is a reinforcement learning algorithm that combines the benefits of both value-based and policy-based methods. It uses two networks: an actor that learns the policy and a critic that evaluates the actions, leading to more stable and efficient learning.

### Type of Learning
- Reinforcement Learning
- Actor-Critic Architecture
- Model-free Learning
- Policy and Value-based Learning

### Key Characteristics
- Combines policy and value learning
- Reduces variance in policy updates
- Provides more stable learning
- Can handle both discrete and continuous actions
- Uses bootstrapping for value estimation

### When to Use
- Complex control problems
- When sample efficiency is important
- Problems requiring stable learning
- Both discrete and continuous action spaces
- Real-world applications with limited data

## 2. Historical Context
- Developed as an improvement over REINFORCE
- Evolved through various architectures
- Led to modern algorithms like A2C, A3C
- Fundamental in many state-of-the-art methods
- Still actively researched and improved

## 3. Technical Details

### Mathematical Foundation

#### Policy and Value Functions

1. **Policy Function (Actor):**
   The policy $\pi_\theta$ maps states to action probabilities:
   $$
   \pi_\theta(a|s) = P(a|s;\theta)
   $$
   where $\theta$ are the policy parameters.

2. **Value Function (Critic):**
   The state-value function $V_w$ estimates the expected return:
   $$
   V_w(s) = \mathbb{E}_{\pi_\theta}\left[\sum_{t=0}^{\infty} \gamma^t r_t | s_0 = s\right]
   $$
   where $w$ are the value function parameters.

#### Advantage Function
The advantage function $A(s,a)$ measures how much better an action is compared to the average:
$$
A(s,a) = Q(s,a) - V(s)
$$

where $Q(s,a)$ is the action-value function:
$$
Q(s,a) = \mathbb{E}_{\pi_\theta}\left[\sum_{t=0}^{\infty} \gamma^t r_t | s_0 = s, a_0 = a\right]
$$

#### Actor Update
The policy gradient with advantage is:
$$
\nabla_\theta J(\theta) = \mathbb{E}_{\pi_\theta}[\nabla_\theta \log \pi_\theta(s,a) A(s,a)]
$$

The policy loss function:
$$
L_\pi(\theta) = -\mathbb{E}_{\pi_\theta}[\log \pi_\theta(s,a) A(s,a)]
$$

#### Critic Update
The value function is updated using TD learning:
$$
L(w) = \mathbb{E}[(r + \gamma V_w(s') - V_w(s))^2]
$$

The TD error:
$$
\delta = r + \gamma V_w(s') - V_w(s)
```

#### Entropy Regularization
To encourage exploration, add entropy term:
$$
L_\pi(\theta) = -\mathbb{E}_{\pi_\theta}[\log \pi_\theta(s,a) A(s,a)] - \beta H(\pi_\theta)
$$

where $H(\pi_\theta)$ is the entropy:
$$
H(\pi_\theta) = -\sum_a \pi_\theta(a|s) \log \pi_\theta(a|s)
```

#### Advantage Estimation Methods

1. **TD(0) Advantage:**
   $$
   A(s_t,a_t) = r_t + \gamma V(s_{t+1}) - V(s_t)
   ```

2. **Generalized Advantage Estimation (GAE):**
   $$
   A(s_t,a_t) = \sum_{l=0}^{\infty} (\gamma\lambda)^l \delta_{t+l}
   ```
   where $\lambda$ is the GAE parameter.

#### Time Complexity Analysis
- Forward pass: $O(B \times N)$
  - $B$ = batch size
  - $N$ = network size
- Backward pass: $O(B \times N)$
- Advantage computation: $O(B \times T)$
  - $T$ = trajectory length
- Total per update: $O(B \times N + B \times T)$

#### Space Complexity Analysis
- Network parameters: $O(N)$
- Experience buffer: $O(B \times T \times d)$
  - $d$ = state/action dimension
- Gradients: $O(B \times N)$
- Total space complexity: $O(N + B \times T \times d + B \times N)$

### Training Process
1. Initialize actor and critic networks
2. Collect experience using current policy
3. Compute advantages using critic
4. Update actor using policy gradient
5. Update critic using TD learning
6. Repeat until convergence

### Key Parameters
- Learning rates (actor and critic)
- Network architectures
- Advantage estimation method
- Entropy coefficient
- Discount factor
- Batch size

## 4. Performance Analysis

### Time Complexity
- Forward pass: O(B × N)
- Backward pass: O(B × N)
- Advantage computation: O(B)

where:
- B = batch size
- N = network size

### Space Complexity
- O(N) for network parameters
- O(B × T) for trajectories
- O(B × N) for gradients

### Computational Requirements
- GPU recommended
- Memory for networks
- Efficient advantage computation
- Parallel environment simulation

## 5. Practical Applications
- Robotics control
- Game playing
- Autonomous systems
- Process optimization
- Resource management
- Control systems

## 6. Advantages and Limitations

### Advantages
- More stable than pure policy gradients
- Better sample efficiency
- Can handle both action types
- Reduced variance in updates
- Better convergence properties

### Limitations
- More complex to implement
- Requires careful tuning
- Can be sensitive to hyperparameters
- May need large networks
- Computationally intensive

## 7. Implementation Guidelines

### Prerequisites
- PyTorch or TensorFlow
- NumPy
- Gym (for environments)
- Matplotlib (for visualization)
- CUDA (optional, for GPU acceleration)

### Environment Requirements
- State space representation
- Action space definition
- Reward function
- Advantage estimation
- Value function approximation

### Best Practices
- Proper advantage estimation
- Network architecture design
- Learning rate scheduling
- Gradient clipping
- Entropy regularization
- Reward normalization

## 8. Python Implementation
See `actor_critic.py` for complete implementation. 