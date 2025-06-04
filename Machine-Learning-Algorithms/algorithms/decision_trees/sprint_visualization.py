"""
Visualization functions for the SPRINT decision tree algorithm.
"""

import numpy as np
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.metrics import confusion_matrix
from typing import Dict, List, Union
import os

def plot_confusion_matrix(y_true: np.ndarray, y_pred: np.ndarray, title: str, save_path: str) -> None:
    """
    Plot confusion matrix using seaborn.
    
    Args:
        y_true (numpy.ndarray): True labels
        y_pred (numpy.ndarray): Predicted labels
        title (str): Plot title
        save_path (str): Path to save the plot
    """
    # Create directory if it doesn't exist
    os.makedirs('algorithms/decision_trees', exist_ok=True)
    
    cm = confusion_matrix(y_true, y_pred)
    plt.figure(figsize=(8, 6))
    sns.heatmap(cm, annot=True, fmt='d', cmap='Blues',
                xticklabels=['Class 0', 'Class 1'],
                yticklabels=['Class 0', 'Class 1'])
    plt.title(title)
    plt.xlabel('Predicted')
    plt.ylabel('True')
    
    # Ensure directory exists
    os.makedirs(os.path.dirname(save_path), exist_ok=True)
    plt.savefig(save_path)
    plt.close()

def plot_feature_importance(importance: Dict[int, float], feature_names: List[str], 
                          title: str, save_path: str) -> None:
    """
    Plot feature importance using seaborn.
    
    Args:
        importance (dict): Feature importance scores
        feature_names (list): List of feature names
        title (str): Plot title
        save_path (str): Path to save the plot
    """
    plt.figure(figsize=(10, 6))
    features = [feature_names[i] for i in importance.keys()]
    importances = list(importance.values())
    sns.barplot(x=features, y=importances)
    plt.title(title)
    plt.xlabel('Features')
    plt.ylabel('Importance')
    plt.xticks(rotation=45)
    
    # Ensure directory exists
    os.makedirs(os.path.dirname(save_path), exist_ok=True)
    plt.savefig(save_path)
    plt.close()

def plot_tree_structure(tree: Dict, feature_names: List[str], save_path: str) -> None:
    """
    Plot the decision tree structure.
    
    Args:
        tree (dict): Trained tree
        feature_names (list): List of feature names
        save_path (str): Path to save the plot
    """
    def get_tree_depth(node: Dict) -> int:
        if 'value' in node:
            return 0
        return 1 + max(get_tree_depth(node['left']), get_tree_depth(node['right']))
    
    def plot_node(node: Dict, x: float, y: float, width: float) -> None:
        if 'value' in node:
            plt.text(x, y, f'Class {node["value"]}', 
                    bbox=dict(facecolor='white', alpha=0.5),
                    ha='center', va='center')
            return
        
        condition = f'{feature_names[node["feature"]]} ≤ {node["threshold"]:.2f}'
        plt.text(x, y, condition,
                bbox=dict(facecolor='white', alpha=0.5),
                ha='center', va='center')
        
        child_y = y - 1
        left_x = x - width/4
        right_x = x + width/4
        
        plt.plot([x, left_x], [y, child_y], 'k-')
        plt.plot([x, right_x], [y, child_y], 'k-')
        
        plt.text((x + left_x)/2, (y + child_y)/2, 'True',
                ha='center', va='center')
        plt.text((x + right_x)/2, (y + child_y)/2, 'False',
                ha='center', va='center')
        
        plot_node(node['left'], left_x, child_y, width/2)
        plot_node(node['right'], right_x, child_y, width/2)
    
    depth = get_tree_depth(tree)
    plt.figure(figsize=(12, depth*2))
    plot_node(tree, 0.5, depth, 1)
    plt.axis('off')
    plt.title('SPRINT Decision Tree Structure')
    
    # Ensure directory exists
    os.makedirs(os.path.dirname(save_path), exist_ok=True)
    plt.savefig(save_path)
    plt.close() 