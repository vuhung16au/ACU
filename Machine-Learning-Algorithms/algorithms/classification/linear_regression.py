import numpy as np
from sklearn.base import BaseEstimator, RegressorMixin
from sklearn.utils.validation import check_X_y, check_array, check_is_fitted

class LinearRegression(BaseEstimator, RegressorMixin):
    """
    Linear Regression implementation using Ordinary Least Squares (OLS).
    
    Parameters
    ----------
    fit_intercept : bool, default=True
        Whether to calculate the intercept for this model.
    
    Attributes
    ----------
    coef_ : array of shape (n_features,)
        Estimated coefficients for the linear regression problem.
    intercept_ : float
        Independent term in the linear model.
    """
    
    def __init__(self, fit_intercept=True):
        self.fit_intercept = fit_intercept
        self.coef_ = None
        self.intercept_ = None
        
    def fit(self, X, y):
        """
        Fit linear model.
        
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
        
        if self.fit_intercept:
            X = np.column_stack([np.ones(X.shape[0]), X])
            
        # Calculate coefficients using OLS
        self.coef_ = np.linalg.inv(X.T @ X) @ X.T @ y
        
        if self.fit_intercept:
            self.intercept_ = self.coef_[0]
            self.coef_ = self.coef_[1:]
        else:
            self.intercept_ = 0
            
        return self
    
    def predict(self, X):
        """
        Predict using the linear model.
        
        Parameters
        ----------
        X : array-like of shape (n_samples, n_features)
            Samples
            
        Returns
        -------
        y_pred : array of shape (n_samples,)
            Predicted values
        """
        check_is_fitted(self)
        X = check_array(X)
        
        if self.fit_intercept:
            X = np.column_stack([np.ones(X.shape[0]), X])
            return X @ np.concatenate([[self.intercept_], self.coef_])
        return X @ self.coef_ 