import numpy as np
from typing import List, Tuple, Union, Optional
from scipy.spatial.distance import cdist
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.metrics import silhouette_score, calinski_harabasz_score
from sklearn.datasets import make_moons
import os

class GeneticClustering:
    def __init__(self, n_clusters: int = 2, population_size: int = 50, 
                 n_generations: int = 100, mutation_rate: float = 0.1,
                 elite_size: int = 2, fitness_metric: str = 'silhouette',
                 random_state: Optional[int] = None):
        """
        Initialize Genetic Algorithm for Clustering
        
        Args:
            n_clusters (int): Number of clusters to form
            population_size (int): Size of the population
            n_generations (int): Number of generations
            mutation_rate (float): Probability of mutation
            elite_size (int): Number of best solutions to preserve
            fitness_metric (str): Metric to use for fitness ('silhouette' or 'calinski_harabasz')
            random_state (int): Random state for reproducibility
        """
        self.n_clusters = n_clusters
        self.population_size = population_size
        self.n_generations = n_generations
        self.mutation_rate = mutation_rate
        self.elite_size = elite_size
        self.fitness_metric = fitness_metric
        self.random_state = random_state
        self.labels_ = None
        self.best_fitness_ = None
        self.fitness_history_ = []
        
    def _initialize_population(self, n_samples: int) -> np.ndarray:
        """Initialize population with random cluster assignments"""
        if self.random_state is not None:
            np.random.seed(self.random_state)
            
        return np.random.randint(0, self.n_clusters, 
                               size=(self.population_size, n_samples))
    
    def _compute_fitness(self, X: np.ndarray, labels: np.ndarray) -> float:
        """Compute fitness of a clustering solution"""
        if self.fitness_metric == 'silhouette':
            return silhouette_score(X, labels)
        elif self.fitness_metric == 'calinski_harabasz':
            return calinski_harabasz_score(X, labels)
        else:
            raise ValueError(f"Unknown fitness metric: {self.fitness_metric}")
    
    def _select_parents(self, fitness_scores: np.ndarray) -> Tuple[int, int]:
        """Select parents using tournament selection"""
        # Tournament selection
        def tournament_select():
            candidates = np.random.choice(len(fitness_scores), size=3, replace=False)
            return candidates[np.argmax(fitness_scores[candidates])]
        
        return tournament_select(), tournament_select()
    
    def _crossover(self, parent1: np.ndarray, parent2: np.ndarray) -> np.ndarray:
        """Perform crossover between two parents"""
        # Single-point crossover
        point = np.random.randint(1, len(parent1))
        child = np.concatenate([parent1[:point], parent2[point:]])
        return child
    
    def _mutate(self, chromosome: np.ndarray) -> np.ndarray:
        """Apply mutation to a chromosome"""
        if np.random.random() < self.mutation_rate:
            # Randomly change some cluster assignments
            n_mutations = int(len(chromosome) * 0.1)  # Mutate 10% of genes
            mutation_points = np.random.choice(len(chromosome), n_mutations, replace=False)
            chromosome[mutation_points] = np.random.randint(0, self.n_clusters, size=n_mutations)
        return chromosome
    
    def _create_next_generation(self, population: np.ndarray, 
                              fitness_scores: np.ndarray) -> np.ndarray:
        """Create the next generation of solutions"""
        next_generation = []
        
        # Elitism: Keep best solutions
        elite_indices = np.argsort(fitness_scores)[-self.elite_size:]
        next_generation.extend(population[elite_indices])
        
        # Create rest of population through selection, crossover, and mutation
        while len(next_generation) < self.population_size:
            # Select parents
            parent1_idx, parent2_idx = self._select_parents(fitness_scores)
            parent1, parent2 = population[parent1_idx], population[parent2_idx]
            
            # Crossover
            child = self._crossover(parent1, parent2)
            
            # Mutation
            child = self._mutate(child)
            
            next_generation.append(child)
            
        return np.array(next_generation)
    
    def fit(self, X: np.ndarray) -> 'GeneticClustering':
        """
        Fit the Genetic Algorithm clustering on the data
        
        Args:
            X (np.ndarray): Training data of shape (n_samples, n_features)
            
        Returns:
            self: The fitted model
        """
        if self.random_state is not None:
            np.random.seed(self.random_state)
            
        n_samples = X.shape[0]
        
        # Initialize population
        population = self._initialize_population(n_samples)
        
        # Evolution loop
        for generation in range(self.n_generations):
            # Compute fitness for each solution
            fitness_scores = np.array([self._compute_fitness(X, labels) 
                                     for labels in population])
            
            # Store best fitness
            best_idx = np.argmax(fitness_scores)
            self.best_fitness_ = fitness_scores[best_idx]
            self.fitness_history_.append(self.best_fitness_)
            
            # Create next generation
            population = self._create_next_generation(population, fitness_scores)
            
            # Early stopping if fitness plateaus
            if len(self.fitness_history_) > 10:
                if np.std(self.fitness_history_[-10:]) < 1e-6:
                    break
        
        # Get best solution
        final_fitness = np.array([self._compute_fitness(X, labels) 
                                for labels in population])
        best_idx = np.argmax(final_fitness)
        self.labels_ = population[best_idx]
        
        return self
    
    def fit_predict(self, X: np.ndarray) -> np.ndarray:
        """
        Fit the model and predict cluster labels
        
        Args:
            X (np.ndarray): Training data of shape (n_samples, n_features)
            
        Returns:
            np.ndarray: Cluster labels
        """
        self.fit(X)
        return self.labels_
    
    def plot_fitness_history(self):
        """Plot the fitness history during evolution and save to PNG file"""
        os.makedirs('algorithms/clustering/genetic_clustering', exist_ok=True)
        plt.figure(figsize=(10, 7))
        sns.lineplot(data=self.fitness_history_)
        plt.xlabel('Generation')
        plt.ylabel('Fitness')
        plt.title('Fitness History')
        filename = 'algorithms/clustering/genetic_clustering/genetic_clustering-fitness_history.png'
        plt.savefig(filename)
        plt.close()
    
    def plot_clusters(self, X: np.ndarray):
        """
        Plot the clustering results and save to PNG file
        Args:
            X (np.ndarray): Input data
        """
        if X.shape[1] != 2:
            raise ValueError("Plotting is only supported for 2D data")
        os.makedirs('algorithms/clustering/genetic_clustering', exist_ok=True)
        plt.figure(figsize=(10, 7))
        palette = sns.color_palette("bright", len(np.unique(self.labels_)))
        for i, label in enumerate(np.unique(self.labels_)):
            plt.scatter(X[self.labels_ == label, 0], X[self.labels_ == label, 1],
                       label=f"Cluster {label}", alpha=0.7, color=palette[i], s=20)
        plt.title('Genetic Algorithm Clustering Results')
        plt.legend()
        filename = 'algorithms/clustering/genetic_clustering/genetic_clustering-clusters.png'
        plt.savefig(filename)
        plt.close()

def compute_silhouette_score(X: np.ndarray, labels: np.ndarray) -> float:
    """
    Compute the silhouette score for clustering evaluation
    
    Args:
        X (np.ndarray): Input data
        labels (np.ndarray): Cluster labels
        
    Returns:
        float: Silhouette score
    """
    return silhouette_score(X, labels)

# Run code sequentially
# Generate sample data
X, _ = make_moons(n_samples=200, noise=0.1, random_state=42)

# Create and fit the model
print("\nTraining Genetic Clustering Algorithm...")
model = GeneticClustering(n_clusters=2, population_size=50, n_generations=100,
                         mutation_rate=0.1, elite_size=2, fitness_metric='silhouette',
                         random_state=42)
labels = model.fit_predict(X)

# Plot results
model.plot_fitness_history()
model.plot_clusters(X)

# Compute silhouette score
score = compute_silhouette_score(X, labels)
print(f"Silhouette Score: {score:.3f}")

# Try different fitness metrics
metrics = ['silhouette', 'calinski_harabasz']
scores = []

for metric in metrics:
    model = GeneticClustering(n_clusters=2, fitness_metric=metric, random_state=42)
    labels = model.fit_predict(X)
    score = compute_silhouette_score(X, labels)
    scores.append(score)

# Plot comparison of fitness metrics
plt.figure(figsize=(10, 7))
sns.barplot(x=metrics, y=scores)
plt.xlabel('Fitness Metric')
plt.ylabel('Silhouette Score')
plt.title('Comparison of Fitness Metrics')
os.makedirs('algorithms/clustering/genetic_clustering', exist_ok=True)
filename = 'algorithms/clustering/genetic_clustering/genetic_clustering-metrics_comparison.png'
plt.savefig(filename)
plt.close()