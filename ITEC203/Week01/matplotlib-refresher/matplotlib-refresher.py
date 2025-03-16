import numpy as np
import matplotlib.pyplot as plt
from matplotlib.animation import FuncAnimation

# --- Basic Plotting ---
# Plot a simple line graph with given points: y = x^2
x = np.array([1, 2, 3, 4])  # Define x-values as a NumPy array
y = x**2  # Compute y = x^2
plt.plot(x, y, label='y = x^2')  # Plot with a label
plt.xlabel('x')  # Label for x-axis
plt.ylabel('y')  # Label for y-axis
plt.title('Basic Plot: y = x^2')  # Title of the plot
plt.legend()  # Display the legend
plt.show()  # Show the plot

# --- Multiple Figures and Axes ---
# Create a figure with two subplots side by side
fig, (ax1, ax2) = plt.subplots(1, 2, figsize=(10, 4))  # 1 row, 2 columns, set figure size

# Plot y = sin(x) on the first subplot
x = np.linspace(0, 2*np.pi, 100)  # 100 points from 0 to 2π
y_sin = np.sin(x)  # Compute y = sin(x)
ax1.plot(x, y_sin, color='blue')  # Plot with blue line
ax1.set_title('y = sin(x)')  # Title for first subplot

# Plot y = x^2 - 3x + 2 on the second subplot
y_poly = x**2 - 3*x + 2  # Compute y = x^2 - 3x + 2
ax2.plot(x, y_poly, color='red')  # Plot with red line
ax2.set_title('y = x^2 - 3x + 2')  # Title for second subplot
plt.show()  # Display both subplots

# --- Formatting Plot Style ---
# Customize a plot with colors, markers, and line styles
x = np.linspace(0, 10, 20)  # 20 evenly spaced points from 0 to 10
y = np.sin(x)  # Compute y = sin(x) for styling
plt.plot(x, y, color='green', marker='o', linestyle='--', linewidth=2, markersize=8)  # Styled plot
plt.xlabel('X-axis')  # X-axis label
plt.ylabel('Y-axis')  # Y-axis label
plt.title('Styled Plot')  # Plot title
plt.grid(True)  # Add a grid
plt.show()  # Display the styled plot

# --- Animated Plot: Moving y = sin(x) ---
# Set up a figure and axis for animation
fig, ax = plt.subplots()  # Create figure and axis
x = np.linspace(0, 2*np.pi, 100)  # 100 points from 0 to 2π
line, = ax.plot(x, np.sin(x))  # Initial plot of y = sin(x)

# Define the update function for animation
def update(frame):
    line.set_ydata(np.sin(x + frame / 10.0))  # Shift sine wave by frame
    return line,

# Create and save the animation
ani = FuncAnimation(fig, update, frames=100, interval=50)  # 100 frames, 50ms interval
ani.save('sine_wave.gif', writer='pillow')  # Save as GIF
plt.show()  # Display the animation

# --- Random Data and Histogram ---
# Generate random data with a normal distribution
mu, sigma = 100, 15  # Mean (mu) and standard deviation (sigma)
x = mu + sigma * np.random.randn(10000)  # 10,000 random points

# Create a histogram of the data
n, bins, patches = plt.hist(x, 50, density=True, facecolor='g', alpha=0.75)  # 50 bins, green color
plt.xlabel('Smarts')  # X-axis label
plt.ylabel('Probability')  # Y-axis label
plt.title('Histogram of IQ')  # Plot title
plt.text(60, 0.025, r'$\mu=100,\ \sigma=15$')  # Add text with mean and sigma
plt.axis([40, 160, 0, 0.03])  # Set axis limits: [xmin, xmax, ymin, ymax]
plt.grid(True)  # Add a grid
plt.show()  # Display the histogram