#!/usr/bin/env python3
# -*- coding: utf-8 -*-

## Genetic Algorithm Implementation
## This notebook demonstrates a functional implementation of genetic algorithms.
## Genetic algorithms are optimization algorithms inspired by natural selection.
## They use techniques like selection, crossover, and mutation to evolve solutions.

## 1. Import Required Libraries
import numpy as np
import pandas as pd
import seaborn as sns
import matplotlib.pyplot as plt
from typing import List, Tuple, Optional, Callable, Dict

## 2. Set Random Seed
np.random.seed(2220)

## 3. Core Genetic Algorithm Functions
def initialize_population(
    population_size: int,
    n_genes: int
) -> np.ndarray:
    """
    Initialize a random population.
    
    Args:
        population_size: Size of the population
        n_genes: Number of genes in each individual
        
    Returns:
        Initial population
    """
    return np.random.randn(population_size, n_genes)

def evaluate_fitness(
    population: np.ndarray,
    fitness_func: Callable[[np.ndarray], float]
) -> np.ndarray:
    """
    Evaluate fitness of all individuals in the population.
    
    Args:
        population: Current population
        fitness_func: Fitness function to evaluate individuals
        
    Returns:
        Array of fitness values
    """
    return np.array([
        fitness_func(individual)
        for individual in population
    ])

def tournament_selection(
    population: np.ndarray,
    fitness_values: np.ndarray,
    tournament_size: int
) -> np.ndarray:
    """
    Select an individual using tournament selection.
    
    Hyperparameters:
    - tournament_size (int): Number of individuals in each tournament.
      Larger values increase selection pressure.
    
    Args:
        population: Current population
        fitness_values: Fitness values of the population
        tournament_size: Size of tournament
        
    Returns:
        Selected individual
    """
    tournament_indices = np.random.choice(
        len(population),
        size=tournament_size,
        replace=False
    )
    winner_idx = tournament_indices[np.argmax(fitness_values[tournament_indices])]
    return population[winner_idx]

def roulette_selection(
    population: np.ndarray,
    fitness_values: np.ndarray
) -> np.ndarray:
    """
    Select an individual using roulette wheel selection.
    
    Args:
        population: Current population
        fitness_values: Fitness values of the population
        
    Returns:
        Selected individual
    """
    probs = fitness_values / fitness_values.sum()
    selected_idx = np.random.choice(len(population), p=probs)
    return population[selected_idx]

def crossover(
    parent1: np.ndarray,
    parent2: np.ndarray,
    crossover_rate: float
) -> Tuple[np.ndarray, np.ndarray]:
    """
    Perform crossover between parents.
    
    Hyperparameters:
    - crossover_rate (float): Probability of crossover occurring.
      Higher values increase exploration but may disrupt good solutions.
    
    Args:
        parent1: First parent
        parent2: Second parent
        crossover_rate: Probability of crossover
        
    Returns:
        Tuple of (child1, child2)
    """
    if np.random.random() < crossover_rate:
        # Single-point crossover
        point = np.random.randint(1, len(parent1))
        child1 = np.concatenate([parent1[:point], parent2[point:]])
        child2 = np.concatenate([parent2[:point], parent1[point:]])
    else:
        child1, child2 = parent1.copy(), parent2.copy()
    
    return child1, child2

def mutate(
    individual: np.ndarray,
    mutation_rate: float
) -> np.ndarray:
    """
    Mutate an individual.
    
    Hyperparameters:
    - mutation_rate (float): Probability of mutation for each gene.
      Higher values increase exploration but may disrupt good solutions.
    
    Args:
        individual: Individual to mutate
        mutation_rate: Probability of mutation
        
    Returns:
        Mutated individual
    """
    mutated = individual.copy()
    mask = np.random.random(len(individual)) < mutation_rate
    mutated[mask] += np.random.randn(np.sum(mask)) * 0.1
    return mutated

def create_next_generation(
    population: np.ndarray,
    fitness_values: np.ndarray,
    population_size: int,
    mutation_rate: float,
    crossover_rate: float,
    selection_method: str,
    tournament_size: int,
    elitism: bool
) -> np.ndarray:
    """
    Create the next generation.
    
    Args:
        population: Current population
        fitness_values: Fitness values of the population
        population_size: Size of the population
        mutation_rate: Probability of mutation
        crossover_rate: Probability of crossover
        selection_method: Selection method ('tournament' or 'roulette')
        tournament_size: Size of tournament for tournament selection
        elitism: Whether to use elitism
        
    Returns:
        Next generation
    """
    new_population = []
    
    # Elitism: keep best individual
    if elitism:
        best_idx = np.argmax(fitness_values)
        new_population.append(population[best_idx])
    
    # Create rest of the population
    while len(new_population) < population_size:
        # Select parents
        if selection_method == 'tournament':
            parent1 = tournament_selection(population, fitness_values, tournament_size)
            parent2 = tournament_selection(population, fitness_values, tournament_size)
        else:  # roulette wheel selection
            parent1 = roulette_selection(population, fitness_values)
            parent2 = roulette_selection(population, fitness_values)
        
        # Create children
        child1, child2 = crossover(parent1, parent2, crossover_rate)
        
        # Mutate children
        child1 = mutate(child1, mutation_rate)
        child2 = mutate(child2, mutation_rate)
        
        new_population.extend([child1, child2])
    
    # Trim if we have too many individuals
    if len(new_population) > population_size:
        new_population = new_population[:population_size]
    
    return np.array(new_population)

## 4. Main Genetic Algorithm Function
def run_genetic_algorithm(
    fitness_func: Callable[[np.ndarray], float],
    n_genes: int,
    population_size: int = 100,
    n_generations: int = 100,
    mutation_rate: float = 0.1,
    crossover_rate: float = 0.8,
    selection_method: str = 'tournament',
    tournament_size: int = 3,
    elitism: bool = True
) -> Dict:
    """
    Run the genetic algorithm.
    
    Hyperparameters:
    - population_size (int): Number of individuals in the population.
      Larger values increase diversity but require more computation.
    - n_generations (int): Number of generations to evolve.
      More generations may find better solutions but take longer.
    - mutation_rate (float): Probability of mutation for each gene.
      Higher values increase exploration but may disrupt good solutions.
    - crossover_rate (float): Probability of crossover occurring.
      Higher values increase exploration but may disrupt good solutions.
    - selection_method (str): Method for selecting parents ('tournament' or 'roulette').
      Tournament selection provides stronger selection pressure.
    - tournament_size (int): Size of tournament for tournament selection.
      Larger values increase selection pressure.
    - elitism (bool): Whether to keep the best individual in each generation.
      Helps preserve good solutions.
    
    Args:
        fitness_func: Fitness function to evaluate individuals
        n_genes: Number of genes in each individual
        population_size: Size of the population
        n_generations: Number of generations
        mutation_rate: Probability of mutation
        crossover_rate: Probability of crossover
        selection_method: Selection method ('tournament' or 'roulette')
        tournament_size: Size of tournament for tournament selection
        elitism: Whether to use elitism
        
    Returns:
        Dictionary containing results and history
    """
    # Initialize population
    population = initialize_population(population_size, n_genes)
    
    # Initialize history
    best_fitness_history = []
    avg_fitness_history = []
    
    # Run generations
    for generation in range(n_generations):
        # Evaluate fitness
        fitness_values = evaluate_fitness(population, fitness_func)
        
        # Record statistics
        best_fitness_history.append(np.max(fitness_values))
        avg_fitness_history.append(np.mean(fitness_values))
        
        # Create next generation
        population = create_next_generation(
            population,
            fitness_values,
            population_size,
            mutation_rate,
            crossover_rate,
            selection_method,
            tournament_size,
            elitism
        )
        
        if (generation + 1) % 10 == 0:
            print(f"Generation {generation + 1}/{n_generations}")
            print(f"Best fitness: {best_fitness_history[-1]:.4f}")
            print(f"Average fitness: {avg_fitness_history[-1]:.4f}")
    
    # Get best solution
    final_fitness_values = evaluate_fitness(population, fitness_func)
    best_idx = np.argmax(final_fitness_values)
    best_solution = population[best_idx]
    best_fitness = final_fitness_values[best_idx]
    
    return {
        'best_solution': best_solution,
        'best_fitness': best_fitness,
        'best_fitness_history': best_fitness_history,
        'avg_fitness_history': avg_fitness_history
    }

## 5. Visualization Functions
def plot_fitness_history(
    best_fitness_history: List[float],
    avg_fitness_history: List[float]
) -> None:
    """
    Plot fitness history using seaborn.
    
    Args:
        best_fitness_history: History of best fitness values
        avg_fitness_history: History of average fitness values
    """
    plt.figure(figsize=(10, 6))
    
    # Create DataFrame for seaborn
    data = pd.DataFrame({
        'Generation': np.arange(len(best_fitness_history)),
        'Best Fitness': best_fitness_history,
        'Average Fitness': avg_fitness_history
    })
    
    # Plot using seaborn
    sns.lineplot(data=data.melt('Generation', var_name='Metric', value_name='Fitness'))
    
    plt.title('Fitness History')
    plt.xlabel('Generation')
    plt.ylabel('Fitness')
    plt.legend(title='Metric')
    plt.grid(True)
    plt.savefig('algorithms/specialized_models/genetic_algorithm-fitness-history.png')
    plt.close()

## 6. Example Usage
# Example fitness function: negative sum of squares
def fitness_function(x: np.ndarray) -> float:
    """
    Example fitness function: negative sum of squares.
    The goal is to minimize the sum of squares.
    
    Args:
        x: Individual to evaluate
        
    Returns:
        Negative sum of squares (to be maximized)
    """
    return -np.sum(x ** 2)

# Run genetic algorithm
results = run_genetic_algorithm(
    fitness_func=fitness_function,
    n_genes=10,
    population_size=100,
    n_generations=100,
    mutation_rate=0.1,
    crossover_rate=0.8,
    selection_method='tournament',
    tournament_size=3,
    elitism=True
)

# Print results
print("\nBest solution found:")
print(f"Genes: {results['best_solution']}")
print(f"Fitness: {results['best_fitness']:.4f}")

# Plot fitness history
plot_fitness_history(
    results['best_fitness_history'],
    results['avg_fitness_history']
)