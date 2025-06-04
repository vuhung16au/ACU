#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Fuzzy Logic Implementation
## This notebook demonstrates a functional implementation of fuzzy logic systems.
## Fuzzy logic is a form of many-valued logic that deals with approximate reasoning.
## It allows for partial truth values between completely true and completely false.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from typing import List, Dict, Tuple, Optional, Union, Callable

## 2. Set Random Seed
np.random.seed(2220)

## 3. Membership Functions
def triangular_membership(x: Union[float, np.ndarray], a: float, b: float, c: float) -> Union[float, np.ndarray]:
    """
    Calculate triangular membership function.
    
    Hyperparameters:
    - a (float): Left base point
    - b (float): Peak point
    - c (float): Right base point
    
    Args:
        x: Input value(s)
        a: Left base point
        b: Peak point
        c: Right base point
        
    Returns:
        Membership value(s)
    """
    return np.maximum(0, np.minimum(
        (x - a) / (b - a),
        (c - x) / (c - b)
    ))

def trapezoidal_membership(x: Union[float, np.ndarray], a: float, b: float, c: float, d: float) -> Union[float, np.ndarray]:
    """
    Calculate trapezoidal membership function.
    
    Hyperparameters:
    - a (float): Left base point
    - b (float): Left peak point
    - c (float): Right peak point
    - d (float): Right base point
    
    Args:
        x: Input value(s)
        a: Left base point
        b: Left peak point
        c: Right peak point
        d: Right base point
        
    Returns:
        Membership value(s)
    """
    return np.maximum(0, np.minimum(
        np.minimum((x - a) / (b - a), 1),
        (d - x) / (d - c)
    ))

def gaussian_membership(x: Union[float, np.ndarray], mu: float, sigma: float) -> Union[float, np.ndarray]:
    """
    Calculate Gaussian membership function.
    
    Hyperparameters:
    - mu (float): Mean/center of the Gaussian
    - sigma (float): Standard deviation, controls the width of the Gaussian
    
    Args:
        x: Input value(s)
        mu: Mean/center
        sigma: Standard deviation
        
    Returns:
        Membership value(s)
    """
    return np.exp(-0.5 * ((x - mu) / sigma) ** 2)

def calculate_membership(
    x: Union[float, np.ndarray],
    func_type: str,
    params: Dict[str, float]
) -> Union[float, np.ndarray]:
    """
    Calculate membership value using specified function type.
    
    Args:
        x: Input value(s)
        func_type: Type of function ('triangular', 'trapezoidal', 'gaussian')
        params: Parameters for the function
        
    Returns:
        Membership value(s)
    """
    if func_type == 'triangular':
        return triangular_membership(x, params['a'], params['b'], params['c'])
    elif func_type == 'trapezoidal':
        return trapezoidal_membership(x, params['a'], params['b'], params['c'], params['d'])
    elif func_type == 'gaussian':
        return gaussian_membership(x, params['mu'], params['sigma'])
    else:
        raise ValueError(f"Unknown function type: {func_type}")

## 4. Fuzzy System Functions
def create_fuzzy_variable(name: str) -> Dict:
    """
    Create a fuzzy variable.
    
    Args:
        name: Name of the variable
        
    Returns:
        Dictionary containing variable information
    """
    return {
        'name': name,
        'membership_functions': {}
    }

def add_membership_function(
    variable: Dict,
    name: str,
    func_type: str,
    params: Dict[str, float]
) -> None:
    """
    Add membership function to variable.
    
    Args:
        variable: Fuzzy variable dictionary
        name: Name of the membership function
        func_type: Type of function
        params: Parameters for the function
    """
    variable['membership_functions'][name] = {
        'type': func_type,
        'params': params
    }

def get_membership(
    variable: Dict,
    x: float
) -> Dict[str, float]:
    """
    Get membership values for all functions in a variable.
    
    Args:
        variable: Fuzzy variable dictionary
        x: Input value
        
    Returns:
        Dictionary of membership values
    """
    return {
        name: float(calculate_membership(x, func['type'], func['params']))
        for name, func in variable['membership_functions'].items()
    }

def evaluate_rule(
    rule: Dict,
    input_memberships: Dict[str, Dict[str, float]]
) -> float:
    """
    Evaluate a fuzzy rule.
    
    Args:
        rule: Rule dictionary containing antecedents and consequent
        input_memberships: Dictionary of input membership values
        
    Returns:
        Rule strength
    """
    return min(
        input_memberships[var][mf]
        for var, mf in rule['antecedents']
    )

def defuzzify(
    output_memberships: Dict[str, Dict[str, float]],
    output_variables: Dict[str, Dict]
) -> Dict[str, float]:
    """
    Defuzzify output membership values.
    
    Args:
        output_memberships: Dictionary of output membership values
        output_variables: Dictionary of output variables
        
    Returns:
        Dictionary of defuzzified output values
    """
    outputs = {}
    for var_name, var in output_variables.items():
        numerator = 0.0
        denominator = 0.0
        
        for mf_name, membership in output_memberships[var_name].items():
            mf = var['membership_functions'][mf_name]
            if mf['type'] == 'triangular':
                center = mf['params']['b']
            elif mf['type'] == 'trapezoidal':
                center = (mf['params']['b'] + mf['params']['c']) / 2
            elif mf['type'] == 'gaussian':
                center = mf['params']['mu']
            
            numerator += center * membership
            denominator += membership
        
        outputs[var_name] = numerator / denominator if denominator > 0 else 0.0
    
    return outputs

def evaluate_fuzzy_system(
    system: Dict,
    inputs: Dict[str, float]
) -> Dict[str, float]:
    """
    Evaluate a fuzzy system with given inputs.
    
    Args:
        system: Fuzzy system dictionary
        inputs: Dictionary of input values
        
    Returns:
        Dictionary of output values
    """
    # Calculate membership values for inputs
    input_memberships = {
        var_name: get_membership(var, inputs[var_name])
        for var_name, var in system['input_variables'].items()
    }
    
    # Initialize output membership values
    output_memberships = {
        var_name: {mf_name: 0.0 for mf_name in var['membership_functions']}
        for var_name, var in system['output_variables'].items()
    }
    
    # Evaluate rules
    for rule in system['rules']:
        strength = evaluate_rule(rule, input_memberships)
        var_name, mf_name = rule['consequent']
        output_memberships[var_name][mf_name] = max(
            output_memberships[var_name][mf_name],
            strength
        )
    
    # Defuzzify outputs
    return defuzzify(output_memberships, system['output_variables'])

## 5. Visualization Functions
def plot_membership_function(
    func_type: str,
    params: Dict[str, float],
    x_range: Tuple[float, float],
    title: str,
    n_points: int = 100
) -> None:
    """
    Plot membership function using seaborn.
    
    Args:
        func_type: Type of function
        params: Parameters for the function
        x_range: Range of x values to plot
        title: Plot title
        n_points: Number of points to plot
    """
    x = np.linspace(x_range[0], x_range[1], n_points)
    y = calculate_membership(x, func_type, params)
    
    plt.figure(figsize=(10, 6))
    sns.lineplot(x=x, y=y)
    plt.title(title)
    plt.xlabel('Input')
    plt.ylabel('Membership')
    plt.grid(True)
    plt.savefig('algorithms/specialized_models/fuzzy_logic-membership-function.png')
    plt.close()

## 6. Example Usage
# Create a fuzzy system for temperature control
system = {
    'input_variables': {},
    'output_variables': {},
    'rules': []
}

# Add input variable (temperature)
temp_var = create_fuzzy_variable('temperature')
add_membership_function(temp_var, 'cold', 'triangular', {'a': 0, 'b': 10, 'c': 20})
add_membership_function(temp_var, 'warm', 'triangular', {'a': 15, 'b': 25, 'c': 35})
add_membership_function(temp_var, 'hot', 'triangular', {'a': 30, 'b': 40, 'c': 50})
system['input_variables']['temperature'] = temp_var

# Add output variable (fan_speed)
fan_var = create_fuzzy_variable('fan_speed')
add_membership_function(fan_var, 'low', 'triangular', {'a': 0, 'b': 2, 'c': 4})
add_membership_function(fan_var, 'medium', 'triangular', {'a': 3, 'b': 5, 'c': 7})
add_membership_function(fan_var, 'high', 'triangular', {'a': 6, 'b': 8, 'c': 10})
system['output_variables']['fan_speed'] = fan_var

# Add rules
system['rules'].extend([
    {
        'antecedents': [('temperature', 'cold')],
        'consequent': ('fan_speed', 'low')
    },
    {
        'antecedents': [('temperature', 'warm')],
        'consequent': ('fan_speed', 'medium')
    },
    {
        'antecedents': [('temperature', 'hot')],
        'consequent': ('fan_speed', 'high')
    }
])

# Plot membership functions
plot_membership_function(
    'triangular',
    {'a': 0, 'b': 10, 'c': 20},
    (-5, 55),
    'Temperature Membership Functions'
)

# Evaluate system
test_temperatures = [5, 25, 45]
for temp in test_temperatures:
    output = evaluate_fuzzy_system(system, {'temperature': temp})
    print(f"\nTemperature: {temp}°C")
    print(f"Fan Speed: {output['fan_speed']:.2f}")