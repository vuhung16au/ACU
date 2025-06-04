## Data Bubbles Clustering Algorithm (Demo Version)
## This is a simplified, fast demonstration of the data bubbles clustering technique.

import numpy as np
import seaborn as sns
import pandas as pd
import matplotlib
matplotlib.use('Agg')  # Use non-interactive backend for saving plots
import matplotlib.pyplot as plt

# Set random seed for reproducibility
np.random.seed(2220)

## Helper function to create a bubble from a point

def create_bubble(point):
    return {
        'n': 1,
        'mu': point,
        'r': 0.0,
        'points': [point]
    }

## Merge two bubbles if close enough

def merge_bubbles(b1, b2, alpha):
    distance = np.linalg.norm(b1['mu'] - b2['mu'])
    if distance > alpha * (b1['r'] + b2['r'] + 1e-6):  # avoid zero radius
        return None
    n_new = b1['n'] + b2['n']
    mu_new = (b1['n'] * b1['mu'] + b2['n'] * b2['mu']) / n_new
    r_new = max(b1['r'], b2['r']) + distance
    return {
        'n': n_new,
        'mu': mu_new,
        'r': r_new,
        'points': b1['points'] + b2['points']
    }

## Main function to create bubbles

def create_data_bubbles(X, alpha=0.5, max_bubbles=10):
    bubbles = []
    for point in X:
        new_bubble = create_bubble(point)
        merged = False
        for i, bubble in enumerate(bubbles):
            merged_bubble = merge_bubbles(bubble, new_bubble, alpha)
            if merged_bubble is not None:
                bubbles[i] = merged_bubble
                merged = True
                break
        if not merged:
            bubbles.append(new_bubble)
        if len(bubbles) > max_bubbles:
            # Merge closest pair
            min_dist = float('inf')
            pair = None
            for i in range(len(bubbles)):
                for j in range(i+1, len(bubbles)):
                    d = np.linalg.norm(bubbles[i]['mu'] - bubbles[j]['mu'])
                    if d < min_dist:
                        min_dist = d
                        pair = (i, j)
            if pair:
                i, j = pair
                merged = merge_bubbles(bubbles[i], bubbles[j], alpha=1.0)  # force merge
                if merged:
                    bubbles[i] = merged
                    bubbles.pop(j)
    return bubbles

## Plotting function

def plot_bubbles(X, bubbles, title="Data Bubbles Clustering (Demo)"):
    df = pd.DataFrame(X, columns=['x', 'y'])
    plt.figure(figsize=(8, 6))
    sns.scatterplot(data=df, x='x', y='y', color='gray', alpha=0.5, label='Data Points')
    centers = np.array([b['mu'] for b in bubbles])
    sizes = [b['n']*10 for b in bubbles]
    sns.scatterplot(x=centers[:,0], y=centers[:,1], s=sizes, color='red', label='Bubble Centers')
    plt.title(title)
    plt.legend()
    plt.savefig('algorithms/clustering/data_bubbles_demo.png')
    plt.close()

## --- DEMO SECTION ---

## Generate a small synthetic dataset
n_samples = 100  # Fewer points for quick demo
n_features = 2
X = np.random.randn(n_samples, n_features)

## Create data bubbles (demo hyperparameters)
bubbles = create_data_bubbles(
    X,
    alpha=0.5,  # Merging threshold: higher = more merging
    max_bubbles=10  # Maximum number of bubbles (smaller for demo)
)

## Plot the result
plot_bubbles(X, bubbles)

## The plot is saved as 'data_bubbles_demo.png'. 