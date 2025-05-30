Backpropagation is the core algorithm for training artificial neural networks. It's an efficient method to compute the gradients of the network's loss function with respect to its weights and biases. These gradients are then used by an optimization algorithm (like Gradient Descent) to update the weights and minimize the loss.

Here's a summary of the steps in backpropagation calculus:

1.  **Forward Pass:**
    * **Input Propagation:** Input data is fed into the network.
    * **Layer-by-Layer Computation:** Each neuron calculates its weighted sum of inputs ($z = \mathbf{w}^T \mathbf{x} + b$) and then applies an activation function ($a = f(z)$).
    * **Prediction:** This continues until the network produces an output prediction ($\hat{y}$) from the final layer.

2.  **Calculate Loss:**
    * The difference between the network's prediction ($\hat{y}$) and the true target value ($y$) is quantified using a **loss function**. This loss indicates how "wrong" the network's prediction is.

3.  **Backward Pass (Gradient Computation):**
    * This is the "backpropagation" phase, where the error signal is propagated backward through the network.
    * **Output Layer Gradients:** The gradients of the loss with respect to the weights and biases of the output layer are calculated first. This involves using the chain rule, starting with the derivative of the loss function with respect to the output layer's activation.
    * **Hidden Layer Gradients:** The "error" (gradient) from the subsequent layer is propagated backward to the current hidden layer. Using the chain rule again, the gradients of the loss with respect to the weights and biases of that hidden layer are computed. This process repeats for all hidden layers, moving from the output towards the input.

4.  **Parameter Update:**
    * Once all the gradients for all weights and biases in the network are known, an **optimizer** (e.g., Gradient Descent) uses these gradients to adjust the weights and biases.
    * The parameters are updated in a direction that is opposite to the gradient, effectively moving towards a lower loss value. The `learning_rate` controls the size of these adjustment steps.

These steps are repeated iteratively over many training examples (or batches) and multiple epochs until the network learns to make accurate predictions.