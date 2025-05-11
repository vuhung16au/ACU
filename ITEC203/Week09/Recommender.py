import numpy as np
import matplotlib.pyplot as plt
from sklearn.cluster import KMeans
from sklearn.preprocessing import MinMaxScaler # To scale data for better clustering

# 1. Create Sample Customer Data
# Each row represents a customer.
# Each column represents their 'interest level' or 'spending' in a product category (e.g., 0-10 scale)
# Let's use 3 categories: 'Electronics', 'Clothing', 'Groceries'
# We'll create some customers with similar interests to form clusters.

# Customer data: [Electronics_Interest, Clothing_Interest, Groceries_Interest]
customer_data = np.array([
    [8, 2, 3],  # Customer 1: High Electronics
    [7, 3, 2],  # Customer 2: High Electronics
    [2, 9, 4],  # Customer 3: High Clothing
    [3, 8, 3],  # Customer 4: High Clothing
    [9, 1, 1],  # Customer 5: High Electronics
    [1, 7, 5],  # Customer 6: High Clothing
    [4, 5, 8],  # Customer 7: High Groceries
    [3, 4, 7],  # Customer 8: High Groceries
    [5, 6, 6],  # Customer 9: Mixed interests
    [2, 3, 9]   # Customer 10: High Groceries
])

customer_ids = [f'Customer_{i+1}' for i in range(customer_data.shape[0])]

print("Sample Customer Data (Interest Levels):")
print(customer_data)

# 2. Preprocess Data (Scaling is important for K-Means)
# Scale features to be between 0 and 1
scaler = MinMaxScaler()
customer_data_scaled = scaler.fit_transform(customer_data)

print("\nScaled Customer Data:")
print(customer_data_scaled)

# 3. Apply K-Means Clustering
# Let's assume we want to find 3 customer segments (clusters)
n_clusters = 3
kmeans = KMeans(n_clusters=n_clusters, random_state=42, n_init=10) # n_init to avoid local minima

# Fit the model to the scaled data
kmeans.fit(customer_data_scaled)

# Get the cluster labels for each customer
cluster_labels = kmeans.labels_

# Get the centroids of the clusters (in the scaled space)
cluster_centroids = kmeans.cluster_centers_

print(f"\nCluster Labels for Each Customer (0 to {n_clusters-1}):")
print(cluster_labels)

print("\nCluster Centroids (in scaled space):")
print(cluster_centroids)

# 4. Illustrate Recommendations based on Clusters

print("\n--- Recommender System Illustration ---")

# Assign each customer to their cluster
customer_clusters = list(zip(customer_ids, cluster_labels))
print("\nCustomer Cluster Assignments:")
for customer_id, cluster_label in customer_clusters:
    print(f"{customer_id}: Cluster {cluster_label}")

# Example: Find customers in Cluster 0
cluster_0_customers = [customer_ids[i] for i, label in enumerate(cluster_labels) if label == 0]
print(f"\nCustomers in Cluster 0: {cluster_0_customers}")

# Based on the centroids or the average interests of customers in Cluster 0,
# we can infer what this cluster is interested in.
# Let's look at the centroid of Cluster 0 (in scaled space)
centroid_0_scaled = cluster_centroids[0]
# To interpret, we can inverse transform the centroid (though not strictly necessary for demo)
# centroid_0_original = scaler.inverse_transform(centroid_0_scaled.reshape(1, -1))
# print(f"Inferred interests for Cluster 0 (scaled): {centroid_0_scaled}")

# Let's assume Cluster 0 (based on our sample data inspection) is the 'Electronics' cluster.
# We can then recommend Electronics items to customers in this cluster.

print("\nExample Recommendations:")
for customer_id, cluster_label in customer_clusters:
    if cluster_label == 0:
        print(f"Suggestion for {customer_id}: You might like our new Smartphones or Headphones!")
    elif cluster_label == 1:
         print(f"Suggestion for {customer_id}: Check out our latest Fashion Collection or Shoes!")
    elif cluster_label == 2:
         print(f"Suggestion for {customer_id}: Don't miss our weekly specials on Fresh Produce or Dairy!")

# 5. (Optional) Visualize the Clusters (if using 2 or 3 features)
# Since we have 3 features, we can visualize in 3D.
# If you only had 2 features, a 2D scatter plot would be used.

# For 3D visualization, we need mplot3d
try:
    from mpl_toolkits.mplot3d import Axes3D
    fig = plt.figure(figsize=(10, 8))
    ax = fig.add_subplot(111, projection='3d')

    # Scatter plot of customers, colored by cluster label
    scatter = ax.scatter(customer_data_scaled[:, 0], customer_data_scaled[:, 1], customer_data_scaled[:, 2],
                         c=cluster_labels, cmap='viridis', s=50) # s is marker size

    # Plot the cluster centroids
    ax.scatter(cluster_centroids[:, 0], cluster_centroids[:, 1], cluster_centroids[:, 2],
               c='red', marker='X', s=200, label='Centroids') # s is marker size

    ax.set_xlabel('Scaled Electronics Interest')
    ax.set_ylabel('Scaled Clothing Interest')
    ax.set_zlabel('Scaled Groceries Interest')
    ax.set_title('Customer Clusters (3D Visualization)')
    ax.legend()
    plt.colorbar(scatter, label='Cluster Label')
    plt.show()

except ImportError:
    print("\nCould not import mplot3d for 3D visualization. Install with: pip install matplotlib")
except Exception as e:
    print(f"\nAn error occurred during 3D visualization: {e}")

# Note: The above code is a simplified example. In a real-world scenario, you would
# have more complex data, possibly with more features, and you would need to
# consider additional preprocessing steps, hyperparameter tuning, and evaluation metrics.