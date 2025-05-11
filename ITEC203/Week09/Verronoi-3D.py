import numpy as np
from scipy.spatial import Voronoi
# Voronoi Diagram in 2D and 3D 
import matplotlib.pyplot as plt

# 1. Generate a set of random 3D points
np.random.seed(42) # for reproducibility
points_3d = np.random.rand(20, 3) # 20 random points in 3D space (x, y, z)

print("Generated 3D points shape:", points_3d.shape)

# 2. Generate the Voronoi diagram in 3D
# Pass the 3D points to the Voronoi class
vor_3d = Voronoi(points_3d)

print("\nVoronoi diagram generated for 3D points.")

# The 'vor_3d' object now contains information about the 3D Voronoi diagram:
# vor_3d.vertices: Coordinates of the Voronoi vertices (where multiple ridges meet)
# vor_3d.ridge_vertices: Indices of vertices forming each ridge (boundary line between faces)
# vor_3d.ridge_points: The pairs of input points that define each ridge
# vor_3d.regions: Lists of vertex indices for each Voronoi region (polyhedron)
# vor_3d.point_region: Index of the region for each input point

# You can inspect some of the results:
print("\nFirst 5 Voronoi vertices (3D coordinates):\n", vor_3d.vertices[:5])
print("\nFirst 5 ridge vertices (indices of vertices):\n", vor_3d.ridge_vertices[:5])

# --- Visualization in 3D ---
# Visualizing 3D Voronoi diagrams is more complex than 2D.
# SciPy's voronoi_plot_2d is only for 2D.
# You would typically use libraries like Matplotlib's mplot3d, Plotly, or Mayavi
# to plot the points, vertices, and potentially the ridges or faces.
# Plotting the full polyhedral regions is significantly more involved.

# Example of plotting just the points in 3D using Matplotlib
from mpl_toolkits.mplot3d import Axes3D # Import the 3D axes module
fig = plt.figure(figsize=(10, 8))
ax = fig.add_subplot(111, projection='3d') # Create a 3D subplot
ax.scatter(points_3d[:, 0], points_3d[:, 1], points_3d[:, 2], c='b', marker='o')
ax.set_title('3D Points for Voronoi Diagram')
ax.set_xlabel('X')
ax.set_ylabel('Y')
ax.set_zlabel('Z')
plt.show()

# Plotting the ridges or faces requires iterating through vor_3d.ridge_vertices
# and vor_3d.regions and plotting the corresponding lines or polygons in 3D space.
# This is non-trivial and often requires handling infinite regions carefully.
