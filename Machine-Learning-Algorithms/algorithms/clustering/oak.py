import numpy as np
import time
import matplotlib.pyplot as plt
import seaborn as sns
from sklearn.datasets import make_blobs
from sklearn.preprocessing import StandardScaler
from sklearn.metrics import silhouette_score
import os

# --- Cluster operations as functions with dicts ---
def create_cluster(point, current_time, track_points):
    return {
        'n': 1,
        'mu': point.copy(),
        'sigma': np.zeros((len(point), len(point))),
        't': current_time,
        'points': [point.copy()] if track_points else None
    }

def update_cluster(cluster, point, current_time):
    cluster['n'] += 1
    delta = point - cluster['mu']
    cluster['mu'] += delta / cluster['n']
    if cluster['n'] > 1:
        cluster['sigma'] = (cluster['n'] - 2) / (cluster['n'] - 1) * cluster['sigma'] + \
            np.outer(delta, delta) / cluster['n']
    cluster['t'] = current_time
    if cluster['points'] is not None:
        cluster['points'].append(point.copy())

def clusters_equal(c1, c2):
    return (
        c1['n'] == c2['n'] and
        np.allclose(c1['mu'], c2['mu']) and
        np.allclose(c1['sigma'], c2['sigma']) and
        c1['t'] == c2['t']
    )

# --- OAK model as a dict and functions ---
def create_oak_model(distance_threshold=0.5, time_threshold=3600, merge_threshold=0.3, split_threshold=0.7, max_clusters=100, track_points=False):
    return {
        'distance_threshold': distance_threshold,
        'time_threshold': time_threshold,
        'merge_threshold': merge_threshold,
        'split_threshold': split_threshold,
        'max_clusters': max_clusters,
        'track_points': track_points,
        'clusters': [],
        'labels': None
    }

def ensure_labels(model, X):
    if model['labels'] is None or len(model['labels']) != len(X):
        model['labels'] = np.full(len(X), -1)

def fit_oak(model, X):
    ensure_labels(model, X)
    current_time = time.time()
    for i, point in enumerate(X):
        process_point(model, point, current_time, i)
    return model

def fit_predict_oak(model, X):
    X_np = np.asarray(X)
    ensure_labels(model, X_np)
    fit_oak(model, X_np)
    return np.array(model['labels']) if model['labels'] is not None else np.full(len(X_np), -1)

def process_point(model, point, current_time, point_idx):
    closest_cluster = None
    min_distance = float('inf')
    for cluster in model['clusters']:
        if current_time - cluster['t'] > model['time_threshold']:
            continue
        distance = np.linalg.norm(point - cluster['mu'])
        if distance < min_distance:
            min_distance = distance
            closest_cluster = cluster
    if closest_cluster is not None and min_distance <= model['distance_threshold']:
        update_cluster(closest_cluster, point, current_time)
        if model['labels'] is not None:
            # Use identity-based index lookup
            for idx, c in enumerate(model['clusters']):
                if c is closest_cluster:
                    model['labels'][point_idx] = idx
                    break
    else:
        new_cluster = create_cluster(point, current_time, model['track_points'])
        model['clusters'].append(new_cluster)
        if model['labels'] is not None:
            model['labels'][point_idx] = len(model['clusters']) - 1
    manage_clusters(model, current_time)

def manage_clusters(model, current_time):
    # Remove inactive clusters
    model['clusters'] = [c for c in model['clusters'] if current_time - c['t'] <= model['time_threshold']]
    # Merge close clusters
    i = 0
    while i < len(model['clusters']):
        j = i + 1
        while j < len(model['clusters']):
            distance = np.linalg.norm(model['clusters'][i]['mu'] - model['clusters'][j]['mu'])
            if distance <= model['merge_threshold']:
                merge_clusters(model, i, j)
                j -= 1
            j += 1
        i += 1
    # Split large clusters
    i = 0
    while i < len(model['clusters']):
        if model['clusters'][i]['n'] > model['split_threshold'] * model['max_clusters']:
            split_cluster(model, i)
        i += 1
    # Maintain maximum number of clusters
    if len(model['clusters']) > model['max_clusters']:
        reduce_clusters(model)

def merge_clusters(model, i, j):
    c1, c2 = model['clusters'][i], model['clusters'][j]
    n_new = c1['n'] + c2['n']
    mu_new = (c1['n'] * c1['mu'] + c2['n'] * c2['mu']) / n_new
    sigma_new = (c1['n'] * c1['sigma'] + c2['n'] * c2['sigma']) / n_new + \
        (c1['n'] * c2['n'] / n_new**2) * np.outer(c1['mu'] - c2['mu'], c1['mu'] - c2['mu'])
    merged_points = (c1['points'] or []) + (c2['points'] or []) if model['track_points'] else None
    merged_cluster = {
        'n': n_new,
        'mu': mu_new,
        'sigma': sigma_new,
        't': max(c1['t'], c2['t']),
        'points': merged_points
    }
    model['clusters'][i] = merged_cluster
    model['clusters'].pop(j)
    if model['labels'] is not None:
        model['labels'][model['labels'] == j] = i
        model['labels'][model['labels'] > j] -= 1

def split_cluster(model, i):
    cluster = model['clusters'][i]
    if cluster['n'] < 2:
        return
    eigenvalues, eigenvectors = np.linalg.eigh(cluster['sigma'])
    principal_direction = eigenvectors[:, np.argmax(eigenvalues)]
    if model['track_points'] and cluster['points'] is not None:
        projections = np.array([np.dot(p - cluster['mu'], principal_direction) for p in cluster['points']])
        median = np.median(projections)
        left_points = [p for p, proj in zip(cluster['points'], projections) if proj < median]
        right_points = [p for p, proj in zip(cluster['points'], projections) if proj >= median]
        left_cluster = {
            'n': len(left_points),
            'mu': np.mean(left_points, axis=0),
            'sigma': np.cov(left_points, rowvar=False),
            't': cluster['t'],
            'points': left_points if model['track_points'] else None
        }
        right_cluster = {
            'n': len(right_points),
            'mu': np.mean(right_points, axis=0),
            'sigma': np.cov(right_points, rowvar=False),
            't': cluster['t'],
            'points': right_points if model['track_points'] else None
        }
    else:
        left_cluster = {
            'n': cluster['n'] // 2,
            'mu': cluster['mu'] - 0.1 * principal_direction,
            'sigma': cluster['sigma'],
            't': cluster['t'],
            'points': None
        }
        right_cluster = {
            'n': cluster['n'] - cluster['n'] // 2,
            'mu': cluster['mu'] + 0.1 * principal_direction,
            'sigma': cluster['sigma'],
            't': cluster['t'],
            'points': None
        }
    model['clusters'][i] = left_cluster
    model['clusters'].append(right_cluster)

def reduce_clusters(model):
    while len(model['clusters']) > model['max_clusters']:
        min_dist = float('inf')
        merge_pair = None
        for i in range(len(model['clusters'])):
            for j in range(i + 1, len(model['clusters'])):
                dist = np.linalg.norm(model['clusters'][i]['mu'] - model['clusters'][j]['mu'])
                if dist < min_dist:
                    min_dist = dist
                    merge_pair = (i, j)
        if merge_pair is None:
            break
        merge_clusters(model, *merge_pair)

# --- Plotting functions (unchanged) ---
def plot_clusters(X, labels, title="OAK Clustering Results"):
    os.makedirs('algorithms/clustering/oak', exist_ok=True)
    plt.figure(figsize=(10, 8))
    palette = sns.color_palette("bright", len(np.unique(labels)))
    for i, label in enumerate(np.unique(labels)):
        if label == -1:
            plt.scatter(X[labels == label, 0], X[labels == label, 1],
                        label='Noise', color='gray', alpha=0.3, s=20)
        else:
            plt.scatter(X[labels == label, 0], X[labels == label, 1],
                        label=f"Cluster {label}", alpha=0.7, color=palette[i], s=20)
    plt.xlabel('Feature 1')
    plt.ylabel('Feature 2')
    plt.title(title)
    plt.legend()
    filename = 'algorithms/clustering/oak/oak-clusters.png'
    plt.savefig(filename)
    plt.close()

def plot_cluster_evolution(X, n_steps=5):
    os.makedirs('algorithms/clustering/oak', exist_ok=True)
    fig, axes = plt.subplots(1, n_steps, figsize=(5*n_steps, 5))
    if n_steps == 1:
        axes = [axes]
    chunk_size = len(X) // n_steps
    for i in range(n_steps):
        start_idx = i * chunk_size
        end_idx = start_idx + chunk_size if i < n_steps - 1 else len(X)
        chunk = X[start_idx:end_idx]
        chunk_model = create_oak_model(distance_threshold=0.5, time_threshold=3600,
                                       merge_threshold=0.3, split_threshold=0.7,
                                       max_clusters=100, track_points=False)
        chunk_labels = fit_predict_oak(chunk_model, chunk)
        chunk_labels = np.array(chunk_labels) if chunk_labels is not None else np.full(len(chunk), -1)
        ax = axes[i]
        palette = sns.color_palette("bright", len(np.unique(chunk_labels)))
        for j, label in enumerate(np.unique(chunk_labels)):
            if label == -1:
                ax.scatter(chunk[chunk_labels == label, 0],
                           chunk[chunk_labels == label, 1],
                           label='Noise', color='gray', alpha=0.3, s=20)
            else:
                ax.scatter(chunk[chunk_labels == label, 0],
                           chunk[chunk_labels == label, 1],
                           label=f"Cluster {label}", alpha=0.7,
                           color=palette[j], s=20)
        ax.set_title(f'Step {i+1}')
        ax.legend()
    plt.tight_layout()
    filename = 'algorithms/clustering/oak/oak-cluster_evolution.png'
    plt.savefig(filename)
    plt.close()

# --- Main logic ---
blobs = make_blobs(n_samples=1000, centers=4, cluster_std=0.60, random_state=42)
if len(blobs) == 2:
    X, _ = blobs
else:
    X, _, _ = blobs
scaler = StandardScaler()
X_scaled = scaler.fit_transform(X)
print("\nTraining OAK Clustering...")
model = create_oak_model(distance_threshold=0.5, time_threshold=3600,
                        merge_threshold=0.3, split_threshold=0.7,
                        max_clusters=100, track_points=False)
labels = fit_predict_oak(model, X_scaled)
labels = np.array(labels) if labels is not None else np.full(len(X_scaled), -1)
plot_clusters(X_scaled, labels)
plot_cluster_evolution(X_scaled)
if labels is not None and len(np.unique(labels)) > 1:
    score = silhouette_score(X_scaled, labels)
    print(f"Silhouette Score: {score:.3f}")