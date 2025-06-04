import numpy as np
from sklearn.base import BaseEstimator, ClassifierMixin
from sklearn.utils.validation import check_X_y, check_array, check_is_fitted
from tensorflow.keras.models import Sequential
from tensorflow.keras.layers import Dense, InputLayer
from tensorflow.keras.utils import to_categorical
from tensorflow.keras.optimizers import Adam

class MLPClassifier(BaseEstimator, ClassifierMixin):
    """
    Multilayer Perceptron classifier implementation using TensorFlow/Keras.
    
    Parameters
    ----------
    hidden_layer_sizes : tuple, default=(100,)
        Number of neurons in each hidden layer.
    learning_rate : float, default=0.01
        Learning rate for weight updates.
    max_iter : int, default=1000
        Maximum number of iterations (epochs).
    random_state : int, default=None
        Random seed for reproducibility.
    activation : {'relu', 'sigmoid'}, default='relu'
        Activation function for hidden layers.
    
    Attributes
    ----------
    model_ : Keras model
        The underlying Keras model.
    n_iter_ : int
        Number of iterations (epochs) until convergence.
    """
    
    def __init__(self, hidden_layer_sizes=(100,), learning_rate=0.01, max_iter=1000,
                 random_state=None, activation='relu'):
        self.hidden_layer_sizes = hidden_layer_sizes
        self.learning_rate = learning_rate
        self.max_iter = max_iter
        self.random_state = random_state
        self.activation = activation
        self.model_ = None
        self.n_iter_ = 0

    def _build_model(self, n_features, n_classes):
        model = Sequential()
        model.add(InputLayer(input_shape=(n_features,)))
        for units in self.hidden_layer_sizes:
            model.add(Dense(units, activation=self.activation))
        model.add(Dense(n_classes, activation='softmax'))
        optimizer = Adam(learning_rate=self.learning_rate)
        model.compile(optimizer=optimizer, loss='categorical_crossentropy', metrics=['accuracy'])
        return model

    def fit(self, X, y):
        """
        Fit MLP classifier using Keras.
        
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
        n_classes = len(np.unique(y))
        y_onehot = to_categorical(y, num_classes=n_classes)
        self.model_ = self._build_model(X.shape[1], n_classes)
        # Set random seed if provided
        if self.random_state is not None:
            import tensorflow as tf
            tf.random.set_seed(self.random_state)
        history = self.model_.fit(X, y_onehot, epochs=self.max_iter, verbose=0)
        self.n_iter_ = len(history.epoch)
        return self

    def predict_proba(self, X):
        """
        Predict class probabilities using Keras model.
        
        Parameters
        ----------
        X : array-like of shape (n_samples, n_features)
            Samples
        
        Returns
        -------
        proba : array of shape (n_samples, n_classes)
            Probability estimates
        """
        check_is_fitted(self, attributes=["model_"])
        X = check_array(X)
        return self.model_.predict(X, verbose=0)

    def predict(self, X):
        """
        Predict class labels using Keras model.
        
        Parameters
        ----------
        X : array-like of shape (n_samples, n_features)
            Samples
        
        Returns
        -------
        y_pred : array of shape (n_samples,)
            Predicted class labels
        """
        proba = self.predict_proba(X)
        return np.argmax(proba, axis=1)

if __name__ == "__main__":
    from sklearn.datasets import make_classification
    from sklearn.model_selection import train_test_split
    from sklearn.metrics import accuracy_score

    # Generate a synthetic dataset
    X, y = make_classification(n_samples=1000, n_features=20, n_informative=15, n_classes=3, random_state=42)
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)

    # Instantiate and train the MLPClassifier
    clf = MLPClassifier(hidden_layer_sizes=(64, 32), learning_rate=0.001, max_iter=30, activation='relu', random_state=42)
    clf.fit(X_train, y_train)

    # Predict and evaluate
    y_pred = clf.predict(X_test)
    acc = accuracy_score(y_test, y_pred)
    print(f"Test Accuracy: {acc:.4f}") 