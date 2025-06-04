import numpy as np
import pandas as pd
from typing import List, Dict, Set, Tuple, Optional
from sklearn.base import BaseEstimator, ClusterMixin
from collections import defaultdict
from heapq import heappush, heappop
from scipy.sparse import csr_matrix

class ROCK(BaseEstimator, ClusterMixin):
    """
    ROCK (RObust Clustering using linKs) clustering algorithm.
    
    Parameters
    ----------
    n_clusters : int, default=2
        The number of clusters to form.
    theta : float, default=0.5
        Similarity threshold for neighborhood computation.
    """
    
    def __init__(
        self,
        n_clusters: int = 2,
        theta: float = 0.5
    ):
        self.n_clusters = n_clusters
        self.theta = theta
        self.labels_ = None
        self.clusters_ = None
        self.link_matrix_ = None
        
    def fit(self, X: pd.DataFrame) -> 'ROCK':
        """
        Perform ROCK clustering from categorical data.
        
        Parameters
        ----------
        X : DataFrame of shape (n_samples, n_features)
            Training instances to cluster. All features must be categorical.
            
        Returns
        -------
        self : ROCK
            The fitted estimator.
        """
        # Validate input
        if not isinstance(X, pd.DataFrame):
            raise TypeError("Input must be a pandas DataFrame")
            
        # Compute similarity matrix
        similarity_matrix = self._compute_similarity_matrix(X)
        
        # Compute link matrix
        self.link_matrix_ = self._compute_link_matrix(similarity_matrix)
        
        # Initialize clusters
        self.clusters_ = self._initialize_clusters(X)
        
        # Perform hierarchical clustering
        self._hierarchical_clustering()
        
        # Assign labels
        self.labels_ = self._assign_labels(X)
        
        return self
    
    def _compute_similarity_matrix(self, X: pd.DataFrame) -> np.ndarray:
        """
        Compute similarity matrix between points.
        
        Parameters
        ----------
        X : DataFrame
            Input data.
            
        Returns
        -------
        similarity_matrix : ndarray
            Matrix of similarities between points.
        """
        n_samples = len(X)
        similarity_matrix = np.zeros((n_samples, n_samples))
        
        for i in range(n_samples):
            for j in range(i + 1, n_samples):
                # Compute Jaccard similarity for categorical data
                intersection = sum(X.iloc[i] == X.iloc[j])
                union = len(X.columns)
                similarity = intersection / union
                similarity_matrix[i, j] = similarity_matrix[j, i] = similarity
                
        return similarity_matrix
    
    def _compute_link_matrix(self, similarity_matrix: np.ndarray) -> csr_matrix:
        """
        Compute link matrix based on similarity matrix.
        
        Parameters
        ----------
        similarity_matrix : ndarray
            Matrix of similarities between points.
            
        Returns
        -------
        link_matrix : csr_matrix
            Sparse matrix of links between points.
        """
        n_samples = len(similarity_matrix)
        links = defaultdict(int)
        
        # Find neighbors for each point
        neighbors = {}
        for i in range(n_samples):
            neighbors[i] = set(np.where(similarity_matrix[i] >= self.theta)[0])
            
        # Compute links
        for i in range(n_samples):
            for j in range(i + 1, n_samples):
                if similarity_matrix[i, j] >= self.theta:
                    common_neighbors = len(neighbors[i] & neighbors[j])
                    links[(i, j)] = links[(j, i)] = common_neighbors
                    
        # Convert to sparse matrix
        rows, cols, data = [], [], []
        for (i, j), link in links.items():
            rows.append(i)
            cols.append(j)
            data.append(link)
            
        return csr_matrix((data, (rows, cols)), shape=(n_samples, n_samples))
    
    def _initialize_clusters(self, X: pd.DataFrame) -> List[Set[int]]:
        """
        Initialize clusters with individual points.
        
        Parameters
        ----------
        X : DataFrame
            Input data.
            
        Returns
        -------
        clusters : list
            List of sets containing point indices.
        """
        return [{i} for i in range(len(X))]
    
    def _compute_goodness(self, cluster1: Set[int], cluster2: Set[int]) -> float:
        """
        Compute goodness measure for merging two clusters.
        
        Parameters
        ----------
        cluster1 : set
            First cluster.
        cluster2 : set
            Second cluster.
            
        Returns
        -------
        goodness : float
            Goodness measure for merging.
        """
        # Compute total links between clusters
        total_links = 0
        for i in cluster1:
            for j in cluster2:
                total_links += self.link_matrix_[i, j]
                
        # Compute denominator
        n1, n2 = len(cluster1), len(cluster2)
        denominator = (n1 + n2) ** (1 + 2 * self.theta) - n1 ** (1 + 2 * self.theta) - n2 ** (1 + 2 * self.theta)
        
        return total_links / denominator if denominator > 0 else 0
    
    def _hierarchical_clustering(self) -> None:
        """
        Perform hierarchical clustering using goodness measure.
        """
        # Initialize priority queue with all possible merges
        queue = []
        for i in range(len(self.clusters_)):
            for j in range(i + 1, len(self.clusters_)):
                goodness = self._compute_goodness(self.clusters_[i], self.clusters_[j])
                heappush(queue, (-goodness, i, j))
                
        # Merge clusters until desired number is reached
        while len(self.clusters_) > self.n_clusters and queue:
            _, i, j = heappop(queue)
            
            # Skip if clusters no longer exist
            if i >= len(self.clusters_) or j >= len(self.clusters_):
                continue
                
            # Merge clusters
            self.clusters_[i].update(self.clusters_[j])
            self.clusters_.pop(j)
            
            # Update queue with new possible merges
            for k in range(len(self.clusters_)):
                if k != i:
                    goodness = self._compute_goodness(self.clusters_[i], self.clusters_[k])
                    heappush(queue, (-goodness, min(i, k), max(i, k)))
    
    def _assign_labels(self, X: pd.DataFrame) -> np.ndarray:
        """
        Assign cluster labels to data points.
        
        Parameters
        ----------
        X : DataFrame
            Input data.
            
        Returns
        -------
        labels : ndarray
            Cluster labels for each point.
        """
        labels = np.full(len(X), -1)
        for i, cluster in enumerate(self.clusters_):
            for point in cluster:
                labels[point] = i
        return labels
    
    def fit_predict(self, X: pd.DataFrame) -> np.ndarray:
        """
        Perform clustering and return cluster labels.
        
        Parameters
        ----------
        X : DataFrame of shape (n_samples, n_features)
            Training instances to cluster.
            
        Returns
        -------
        labels : ndarray of shape (n_samples,)
            Cluster labels for each point in the dataset.
        """
        self.fit(X)
        return self.labels_ 