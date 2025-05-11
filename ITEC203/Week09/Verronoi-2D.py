import numpy as np
from scipy.spatial import Voronoi
import matplotlib.pyplot as plt
from scipy.spatial import voronoi_plot_2d

# 1. Generate a set of random 2D points
np.random.seed(42) # for reproducibility
points_2d = np.random.rand(20, 2) # 20 random points in 2D space (x, y)

print("Generated 2D points shape:", points_2d.shape)

# 2. Generate the Voronoi diagram in 2D
# Pass the 2D points to the Voronoi class
vor_2d = Voronoi(points_2d)

print("\nVoronoi diagram generated for 2D points.")

# The 'vor_2d' object now contains information about the 2D Voronoi diagram:
# vor_2d.vertices: Coordinates of the Voronoi vertices (where ridges meet)
# vor_2d.ridge_vertices: Indices of vertices forming each ridge (boundary line between regions)
# vor_2d.ridge_points: The pairs of input points that define each ridge
# vor_2d.regions: Lists of vertex indices for each Voronoi region

# You can inspect some of the results:
print("\nFirst 5 Voronoi vertices (2D coordinates):\n", vor_2d.vertices[:5])
print("\nFirst 5 ridge vertices (indices of vertices):\n", vor_2d.ridge_vertices[:5])

# --- Visualization in 2D ---
# Create a figure for 2D visualization
fig = plt.figure(figsize=(10, 8))

# Plot the Voronoi diagram
voronoi_plot_2d(vor_2d, show_points=True, show_vertices=True, point_size=5)
plt.title('2D Voronoi Diagram')
plt.xlabel('X')
plt.ylabel('Y')

# Display the plot
plt.show()
