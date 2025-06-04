import numpy as np
from sklearn.base import BaseEstimator, ClassifierMixin
from sklearn.utils.validation import check_X_y, check_array, check_is_fitted
from sklearn.metrics.pairwise import euclidean_distances

class KNeighborsClassifier(BaseEstimator, ClassifierMixin):
    """
    K-Nearest Neighbors classifier implementation.
    
    Parameters
    ----------
    n_neighbors : int, default=5
        Number of neighbors to use.
    weights : {'uniform', 'distance'}, default='uniform'
        Weight function used in prediction.
    metric : str, default='euclidean'
        Distance metric to use.
    
    Attributes
    ----------
    X_ : array of shape (n_samples, n_features)
        Training data.
    y_ : array of shape (n_samples,)
        Target values.
    classes_ : array of shape (n_classes,)
        Unique classes in the training data.
    """
    
    def __init__(self, n_neighbors=5, weights='uniform', metric='euclidean'):
        self.n_neighbors = n_neighbors
        self.weights = weights
        self.metric = metric
        self.X_ = None
        self.y_ = None
        self.classes_ = None
        
    def fit(self, X, y):
        """
        Fit KNN classifier.
        
        Parameters
        ----------
        X : array-like of shape (n_samples, n_features)
            Training data
        y : array-like of shape (n_samples,)
            Target values
            
        Returns
        -------
        self : returns an instance of self
        """
        X, y = check_X_y(X, y)
        self.X_ = X
        self.y_ = y
        self.classes_ = np.unique(y)
        return self
    
    def _compute_distances(self, X):
        """Compute distances between test points and training points."""
        if self.metric == 'euclidean':
            return euclidean_distances(X, self.X_)
        else:
            raise ValueError(f"Unsupported metric: {self.metric}")
    
    def _get_weights(self, distances):
        """Get weights for neighbors based on distance."""
        if self.weights == 'uniform':
            return np.ones_like(distances)
        elif self.weights == 'distance':
            # Avoid division by zero
            return 1 / (distances + 1e-10)
        else:
            raise ValueError(f"Unsupported weights: {self.weights}")
    
    def predict_proba(self, X):
        """
        Predict class probabilities.
        
        Parameters
        ----------
        X : array-like of shape (n_samples, n_features)
            Samples
            
        Returns
        -------
        proba : array of shape (n_samples, n_classes)
            Probability estimates
        """
        check_is_fitted(self)
        X = check_array(X)
        
        # Compute distances
        distances = self._compute_distances(X)
        
        # Get k nearest neighbors
        k_indices = np.argpartition(distances, self.n_neighbors, axis=1)[:, :self.n_neighbors]
        k_distances = np.take_along_axis(distances, k_indices, axis=1)
        
        # Get weights
        weights = self._get_weights(k_distances)
        
        # Initialize probability array
        n_samples = X.shape[0]
        n_classes = len(self.classes_)
        proba = np.zeros((n_samples, n_classes))
        
        # Compute probabilities for each class
        for i in range(n_samples):
            for j, c in enumerate(self.classes_):
                # Get weights for current class
                class_mask = self.y_[k_indices[i]] == c
                class_weights = weights[i][class_mask]
                
                # Sum weights for current class
                proba[i, j] = np.sum(class_weights)
        
        # Normalize probabilities
        row_sums = proba.sum(axis=1)
        proba = proba / row_sums[:, np.newaxis]
        
        return proba
    
    def predict(self, X):
        """
        Predict class labels.
        
        Parameters
        ----------
        X : array-like of shape (n_samples, n_features)
            Samples
            
        Returns
        -------
        y_pred : array of shape (n_samples,)
            Predicted class labels
        """
        return self.classes_[np.argmax(self.predict_proba(X), axis=1)] 