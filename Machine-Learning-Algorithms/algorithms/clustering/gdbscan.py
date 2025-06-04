import numpy as np
from typing import Callable, List, Set, Dict, Any, Optional
from sklearn.base import BaseEstimator, ClusterMixin
from sklearn.neighbors import NearestNeighbors

class GDBSCAN(BaseEstimator, ClusterMixin):
    """
    Generalized DBSCAN clustering algorithm.
    
    Parameters
    ----------
    eps : float, default=0.5
        The maximum distance between two samples for them to be considered
        as part of the same neighborhood.
    min_samples : int, default=5
        The number of samples in a neighborhood for a point to be considered
        as a core point.
    metric : str or callable, default='euclidean'
        The metric to use when calculating distance between instances.
    metric_params : dict, default=None
        Additional keyword arguments for the metric function.
    algorithm : str, default='auto'
        The algorithm to use for computing nearest neighbors.
    leaf_size : int, default=30
        Leaf size passed to BallTree or KDTree.
    p : float, default=None
        The power of the Minkowski metric to be used.
    n_jobs : int, default=None
        The number of parallel jobs to run.
    """
    
    def __init__(
        self,
        eps: float = 0.5,
        min_samples: int = 5,
        metric: str = 'euclidean',
        metric_params: Optional[Dict[str, Any]] = None,
        algorithm: str = 'auto',
        leaf_size: int = 30,
        p: Optional[float] = None,
        n_jobs: Optional[int] = None
    ):
        self.eps = eps
        self.min_samples = min_samples
        self.metric = metric
        self.metric_params = metric_params
        self.algorithm = algorithm
        self.leaf_size = leaf_size
        self.p = p
        self.n_jobs = n_jobs
        self.labels_ = None
        self.core_sample_indices_ = None
        self.components_ = None
        
    def fit(self, X: np.ndarray) -> 'GDBSCAN':
        """
        Perform GDBSCAN clustering from features or distance matrix.
        
        Parameters
        ----------
        X : {array-like, sparse matrix} of shape (n_samples, n_features)
            Training instances to cluster.
            
        Returns
        -------
        self : GDBSCAN
            The fitted estimator.
        """
        # Initialize nearest neighbors
        self.nbrs_ = NearestNeighbors(
            n_neighbors=self.min_samples,
            metric=self.metric,
            metric_params=self.metric_params,
            algorithm=self.algorithm,
            leaf_size=self.leaf_size,
            p=self.p,
            n_jobs=self.n_jobs
        )
        
        # Fit the nearest neighbors
        self.nbrs_.fit(X)
        
        # Get core samples
        self.core_sample_indices_ = self._get_core_samples(X)
        
        # Initialize labels
        self.labels_ = np.full(X.shape[0], -1)
        
        # Perform clustering
        self._cluster(X)
        
        return self
    
    def _get_core_samples(self, X: np.ndarray) -> np.ndarray:
        """
        Identify core samples based on neighborhood size.
        
        Parameters
        ----------
        X : array-like of shape (n_samples, n_features)
            Training instances.
            
        Returns
        -------
        core_samples : ndarray of shape (n_core_samples,)
            Indices of core samples.
        """
        # Get distances to k-th nearest neighbor
        distances, _ = self.nbrs_.kneighbors(X)
        
        # Core samples are those with k-th neighbor within eps
        core_samples = np.where(distances[:, -1] <= self.eps)[0]
        
        return core_samples
    
    def _cluster(self, X: np.ndarray) -> None:
        """
        Perform the actual clustering.
        
        Parameters
        ----------
        X : array-like of shape (n_samples, n_features)
            Training instances.
        """
        # Initialize cluster label
        cluster_label = 0
        
        # Process each core sample
        for core_idx in self.core_sample_indices_:
            if self.labels_[core_idx] != -1:
                continue
                
            # Start new cluster
            self._expand_cluster(X, core_idx, cluster_label)
            cluster_label += 1
    
    def _expand_cluster(self, X: np.ndarray, core_idx: int, cluster_label: int) -> None:
        """
        Expand a cluster from a core sample.
        
        Parameters
        ----------
        X : array-like of shape (n_samples, n_features)
            Training instances.
        core_idx : int
            Index of the core sample.
        cluster_label : int
            Label to assign to the cluster.
        """
        # Get neighbors of core sample
        neighbors = self._get_neighbors(X, core_idx)
        
        # Assign cluster label to core sample
        self.labels_[core_idx] = cluster_label
        
        # Process neighbors
        i = 0
        while i < len(neighbors):
            neighbor_idx = neighbors[i]
            
            if self.labels_[neighbor_idx] == -1:
                self.labels_[neighbor_idx] = cluster_label
                
                # If neighbor is a core sample, expand cluster
                if neighbor_idx in self.core_sample_indices_:
                    new_neighbors = self._get_neighbors(X, neighbor_idx)
                    neighbors.extend(new_neighbors)
            
            i += 1
    
    def _get_neighbors(self, X: np.ndarray, idx: int) -> List[int]:
        """
        Get neighbors of a sample within eps distance.
        
        Parameters
        ----------
        X : array-like of shape (n_samples, n_features)
            Training instances.
        idx : int
            Index of the sample.
            
        Returns
        -------
        neighbors : list
            Indices of neighbors.
        """
        # Get distances to all points
        distances, indices = self.nbrs_.kneighbors(X[idx:idx+1])
        
        # Return indices of points within eps
        return indices[0][distances[0] <= self.eps].tolist()
    
    def fit_predict(self, X: np.ndarray) -> np.ndarray:
        """
        Perform clustering and return cluster labels.
        
        Parameters
        ----------
        X : {array-like, sparse matrix} of shape (n_samples, n_features)
            Training instances to cluster.
            
        Returns
        -------
        labels : ndarray of shape (n_samples,)
            Cluster labels for each point in the dataset.
        """
        self.fit(X)
        return self.labels_ 