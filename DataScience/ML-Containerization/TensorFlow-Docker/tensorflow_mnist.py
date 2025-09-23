#!/usr/bin/env python3
"""
TensorFlow 2 MNIST Classification with Docker
=============================================

This script demonstrates a TensorFlow 2 quickstart for experts,
implementing a CNN model for MNIST digit classification.
"""

import tensorflow as tf
from tensorflow.keras.layers import Dense, Flatten, Conv2D
from tensorflow.keras import Model
import sys
import os

# Configure TensorFlow for better memory management
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'  # Reduce TensorFlow logging
tf.config.experimental.set_memory_growth(tf.config.list_physical_devices('GPU')[0], True) if tf.config.list_physical_devices('GPU') else None

def main():
    print("=== TensorFlow 2 MNIST Classification with Docker ===")
    print(f"TensorFlow version: {tf.__version__}")
    
    # Load and prepare the MNIST dataset
    print("\nLoading MNIST dataset...")
    mnist = tf.keras.datasets.mnist
    
    (x_train, y_train), (x_test, y_test) = mnist.load_data()
    x_train, x_test = x_train / 255.0, x_test / 255.0
    
    # Add a channels dimension
    x_train = x_train[..., tf.newaxis].astype("float32")
    x_test = x_test[..., tf.newaxis].astype("float32")
    
    print(f"Training data shape: {x_train.shape}")
    print(f"Test data shape: {x_test.shape}")
    print(f"Number of classes: {len(tf.unique(y_train)[0])}")
    
    # Use tf.data to batch and shuffle the dataset
    print("\nPreparing data pipelines...")
    # Reduce batch size and shuffle buffer for better memory management
    BATCH_SIZE = 16
    SHUFFLE_BUFFER = 5000
    
    train_ds = tf.data.Dataset.from_tensor_slices(
        (x_train, y_train)).shuffle(SHUFFLE_BUFFER).batch(BATCH_SIZE).prefetch(tf.data.AUTOTUNE)
    
    test_ds = tf.data.Dataset.from_tensor_slices((x_test, y_test)).batch(BATCH_SIZE).prefetch(tf.data.AUTOTUNE)
    
    # Build the tf.keras model using the Keras model subclassing API
    print("\nBuilding CNN model...")
    class MyModel(Model):
        def __init__(self):
            super().__init__()
            self.conv1 = Conv2D(32, 3, activation='relu')
            self.flatten = Flatten()
            self.d1 = Dense(128, activation='relu')
            self.d2 = Dense(10)
        
        def call(self, x):
            x = self.conv1(x)
            x = self.flatten(x)
            x = self.d1(x)
            return self.d2(x)
    
    # Create an instance of the model
    model = MyModel()
    
    # Build the model with a sample input to get parameter count
    sample_input = tf.random.normal([1, 28, 28, 1])
    _ = model(sample_input)  # Build the model
    print(f"Model created with {model.count_params():,} parameters")
    
    # Choose an optimizer and loss function for training
    loss_object = tf.keras.losses.SparseCategoricalCrossentropy(from_logits=True)
    optimizer = tf.keras.optimizers.Adam()
    
    # Select metrics to measure the loss and the accuracy of the model
    train_loss = tf.keras.metrics.Mean(name='train_loss')
    train_accuracy = tf.keras.metrics.SparseCategoricalAccuracy(name='train_accuracy')
    
    test_loss = tf.keras.metrics.Mean(name='test_loss')
    test_accuracy = tf.keras.metrics.SparseCategoricalAccuracy(name='test_accuracy')
    
    # Use tf.GradientTape to train the model
    @tf.function
    def train_step(images, labels):
        with tf.GradientTape() as tape:
            # training=True is only needed if there are layers with different
            # behavior during training versus inference (e.g. Dropout).
            predictions = model(images, training=True)
            loss = loss_object(labels, predictions)
        gradients = tape.gradient(loss, model.trainable_variables)
        optimizer.apply_gradients(zip(gradients, model.trainable_variables))
        
        train_loss(loss)
        train_accuracy(labels, predictions)
    
    # Test the model
    @tf.function
    def test_step(images, labels):
        # training=False is only needed if there are layers with different
        # behavior during training versus inference (e.g. Dropout).
        predictions = model(images, training=False)
        t_loss = loss_object(labels, predictions)
        
        test_loss(t_loss)
        test_accuracy(labels, predictions)
    
    # Training loop
    EPOCHS = 2
    print(f"\nStarting training for {EPOCHS} epochs...")
    
    try:
        for epoch in range(EPOCHS):
            # Reset the metrics at the start of the next epoch
            train_loss.reset_state()
            train_accuracy.reset_state()
            test_loss.reset_state()
            test_accuracy.reset_state()
            
            # Training phase
            for images, labels in train_ds:
                train_step(images, labels)
            
            # Testing phase
            for test_images, test_labels in test_ds:
                test_step(test_images, test_labels)
            
            print(
                f'Epoch {epoch + 1}, '
                f'Loss: {train_loss.result():0.4f}, '
                f'Accuracy: {train_accuracy.result() * 100:0.2f}%, '
                f'Test Loss: {test_loss.result():0.4f}, '
                f'Test Accuracy: {test_accuracy.result() * 100:0.2f}%'
            )
    except Exception as e:
        print(f"Training error: {e}")
        raise
    
    # Final evaluation
    print(f"\n=== Training Complete ===")
    print(f"Final Test Accuracy: {test_accuracy.result() * 100:.2f}%")
    print(f"Final Test Loss: {test_loss.result():.4f}")
    
    # Show some example predictions
    print("\nExample Predictions:")
    try:
        test_images_batch, test_labels_batch = next(iter(test_ds))
        predictions = model(test_images_batch[:5], training=False)
        predicted_labels = tf.argmax(predictions, axis=1)
        
        for i in range(5):
            true_label = test_labels_batch[i].numpy()
            pred_label = predicted_labels[i].numpy()
            confidence = tf.nn.softmax(predictions[i])[pred_label].numpy()
            print(f"Sample {i+1}: True={true_label}, Predicted={pred_label}, Confidence={confidence:.3f}")
    except Exception as e:
        print(f"Error during prediction: {e}")
        # Fallback: use a small subset of test data
        small_test_images = x_test[:5]
        small_test_labels = y_test[:5]
        predictions = model(small_test_images, training=False)
        predicted_labels = tf.argmax(predictions, axis=1)
        
        for i in range(5):
            true_label = small_test_labels[i]
            pred_label = predicted_labels[i].numpy()
            confidence = tf.nn.softmax(predictions[i])[pred_label].numpy()
            print(f"Sample {i+1}: True={true_label}, Predicted={pred_label}, Confidence={confidence:.3f}")
    
    print("\n=== Model Training Complete ===")

if __name__ == "__main__":
    try:
        main()
    except Exception as e:
        print(f"Error: {e}")
        sys.exit(1)
