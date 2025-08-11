# Load the libraries
from sklearn.datasets import load_iris
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score, classification_report
import numpy as np

def main():
    print("=== Iris Classification with Docker ===")
    print("Loading iris dataset...")
    
    # Load the iris dataset
    iris = load_iris()
    X = iris.data
    y = iris.target
    
    print(f"Dataset shape: {X.shape}")
    print(f"Target classes: {np.unique(y)}")
    print(f"Feature names: {iris.feature_names}")
    print(f"Target names: {iris.target_names}")
    
    # Split the data
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=42)
    
    print(f"\nTraining set size: {X_train.shape[0]}")
    print(f"Test set size: {X_test.shape[0]}")
    
    # Train a logistic regression model
    print("\nTraining Logistic Regression model...")
    clf = LogisticRegression(random_state=42, max_iter=1000)
    clf.fit(X_train, y_train)
    
    # Make predictions
    print("Making predictions...")
    y_pred = clf.predict(X_test)
    
    # Print the accuracy of the model
    accuracy = accuracy_score(y_test, y_pred)
    print(f'\nModel Accuracy: {accuracy:.4f} ({accuracy*100:.2f}%)')
    
    # Print detailed classification report
    print("\nClassification Report:")
    print(classification_report(y_test, y_pred, target_names=iris.target_names))
    
    # Show some example predictions
    print("\nExample Predictions:")
    for i in range(min(5, len(X_test))):
        true_label = iris.target_names[y_test[i]]
        pred_label = iris.target_names[y_pred[i]]
        print(f"Sample {i+1}: True={true_label}, Predicted={pred_label}")
    
    print("\n=== Model Training Complete ===")

if __name__ == "__main__":
    main()
