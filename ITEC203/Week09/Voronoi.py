import numpy as np
import matplotlib.pyplot as plt
from scipy.spatial import Voronoi, voronoi_plot_2d

# 1. Generate a set of random 2D points
np.random.seed(42) # for reproducibility
points = np.random.rand(15, 2) # 15 random points in 2D space (between 0 and 1)

# 2. Generate the Voronoi diagram
vor = Voronoi(points)

# 3. Plot the Voronoi diagram

fig, ax = plt.subplots(figsize=(8, 8))

# voronoi_plot_2d is a convenience function to plot the diagram
voronoi_plot_2d(vor, ax=ax, show_vertices=False, line_colors='orange',
                line_width=2, line_alpha=0.6, point_size=5)

# You can also plot the original points
ax.plot(points[:, 0], points[:, 1], 'bo', markersize=5)

ax.set_title('Voronoi Diagram')
ax.set_xlabel('X-coordinate')
ax.set_ylabel('Y-coordinate')
ax.set_aspect('equal', adjustable='box') # Keep equal scaling
plt.grid(True)
plt.show()

# You can access information about the diagram from the 'vor' object:
# print("Voronoi Vertices:\n", vor.vertices)
# print("\nVoronoi Ridges:\n", vor.ridge_vertices) # Indices of vertices for each ridge
# print("\nVoronoi Regions (indices of vertices):", vor.regions) # Vertices making up each region