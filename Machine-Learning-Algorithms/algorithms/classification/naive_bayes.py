import numpy as np
from sklearn.base import BaseEstimator, ClassifierMixin
from sklearn.utils.validation import check_X_y, check_array, check_is_fitted
from collections import Counter

class NaiveBayes(BaseEstimator, ClassifierMixin):
    """
    Naive Bayes classifier implementation.
    
    Parameters
    ----------
    alpha : float, default=1.0
        Smoothing parameter (Laplace smoothing).
    
    Attributes
    ----------
    class_prior_ : array of shape (n_classes,)
        Probability of each class.
    feature_prob_ : array of shape (n_classes, n_features)
        Probability of features given each class.
    classes_ : array of shape (n_classes,)
        Unique classes in the training data.
    """
    
    def __init__(self, alpha=1.0):
        self.alpha = alpha
        self.class_prior_ = None
        self.feature_prob_ = None
        self.classes_ = None
        
    def _compute_class_prior(self, y):
        """Compute prior probability of each class."""
        class_counts = Counter(y)
        total_samples = len(y)
        return np.array([class_counts[c] / total_samples for c in self.classes_])
    
    def _compute_feature_prob(self, X, y):
        """Compute conditional probability of features given each class."""
        n_classes = len(self.classes_)
        n_features = X.shape[1]
        feature_prob = np.zeros((n_classes, n_features))
        
        for i, c in enumerate(self.classes_):
            # Get samples for current class
            X_c = X[y == c]
            
            # Compute mean and variance for each feature
            mean = np.mean(X_c, axis=0)
            var = np.var(X_c, axis=0) + self.alpha
            
            # Store probabilities
            feature_prob[i] = mean / var
            
        return feature_prob
    
    def fit(self, X, y):
        """
        Fit Naive Bayes classifier.
        
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
        
        # Get unique classes
        self.classes_ = np.unique(y)
        
        # Compute class prior probabilities
        self.class_prior_ = self._compute_class_prior(y)
        
        # Compute feature probabilities
        self.feature_prob_ = self._compute_feature_prob(X, y)
        
        return self
    
    def _compute_log_likelihood(self, X):
        """Compute log likelihood for each class."""
        n_samples = X.shape[0]
        n_classes = len(self.classes_)
        log_likelihood = np.zeros((n_samples, n_classes))
        
        for i in range(n_classes):
            # Compute log probability for each feature
            log_prob = X * np.log(self.feature_prob_[i]) + \
                      (1 - X) * np.log(1 - self.feature_prob_[i])
            
            # Sum over features and add class prior
            log_likelihood[:, i] = np.sum(log_prob, axis=1) + np.log(self.class_prior_[i])
            
        return log_likelihood
    
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
        
        # Compute log likelihood
        log_likelihood = self._compute_log_likelihood(X)
        
        # Convert to probabilities using softmax
        exp_log_likelihood = np.exp(log_likelihood - np.max(log_likelihood, axis=1, keepdims=True))
        proba = exp_log_likelihood / np.sum(exp_log_likelihood, axis=1, keepdims=True)
        
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