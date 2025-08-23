# Machine Learning Basics: Concepts & Algorithms

## 1) Purpose & Scope

- Problems: classification, regression, clustering; data are features (X) and optional labels (y).
- Split into train/validation/test to estimate generalization and avoid overfitting.
- Example: Predict churn (classification), house price (regression), or group customers (clustering).

## 2) Quick Taxonomy

- Supervised: k-NN, Naive Bayes, Decision Trees, Random Forests, Support Vector Machines, Gradient Boosting, Neural Networks, Discriminant Analysis, Rule Induction, Fast Large-Margin.
- Unsupervised: k-means, x-means.
- Text mining preprocessing: tokenization, stop-word filtering, n-grams, stemming/lemmatization.
- Evaluation: cross-validation (e.g., k-fold, stratified), hold-out test.

## 3) High-Priority Highlights

### X-means clustering

- Extends k-means by automatically choosing k via information criteria (e.g., BIC) after splitting clusters.
- Pros: auto model selection; Cons: favors roughly spherical clusters, needs scaling.
- Example: Discover the number of customer segments from purchase embeddings without manually sweeping k.

### Fast large-margin classification

- Linear large-margin models (hinge loss) trained with efficient solvers (SGD, Pegasos, library-class linear solvers).
- Pros: very fast on high-dimensional sparse data; Cons: linear boundary unless kernelized or features engineered.
- Example: Classify spam vs. ham with a linear margin on TF-IDF; trains in seconds on hundreds of thousands of emails.

## 4) Core Algorithms (1–3 lines + example)

### k-means clustering

- Partitions data into k clusters by minimizing within-cluster variance; sensitive to initialization and scale.
- Pros: simple, fast; Cons: must choose k, assumes spherical clusters. Key: k, init (k-means++), scaling.
- Example: Group products by behavioral features; after standardization, k=8 yields coherent categories.

### k-nearest neighbors (k-NN)

- Predicts by majority vote/average of the k closest points under a distance metric.
- Pros: simple, non-parametric; Cons: slow at inference, sensitive to scaling. Key: k, metric (Euclidean/Cosine).
- Example: Classify flower species from sepal/petal measurements with k=5 after standard scaling.

### Naive Bayes

- Probabilistic classifier assuming conditional independence of features (Gaussian/Multinomial/Bernoulli).
- Pros: fast, robust with little data; Cons: independence often violated. Key: smoothing (Laplace/add-α).
- Example: Spam filtering using Multinomial NB on word counts with Laplace smoothing.

### Decision Trees

- Greedy splits to maximize purity (Gini, entropy) yielding human-readable rules; prune to prevent overfitting.
- Pros: interpretable; Cons: high variance if deep. Key: max_depth, min_samples_split/leaf, pruning/ccp_alpha.
- Example: A shallow tree for credit approval reveals a few key thresholds (e.g., income, delinquency rate).

### Random Forests

- Ensemble of bootstrapped trees with feature subsampling; aggregates votes/averages to reduce variance.
- Pros: strong baseline, robust; Cons: larger models, less interpretable. Key: n_estimators, max_features.
- Example: Customer churn prediction with 300 trees; permutation importance highlights tenure and contract type.

### Support Vector Machines (SVM)

- Maximizes margin; linear or kernelized (RBF/poly) for non-linear decision boundaries.
- Pros: strong with proper kernels; Cons: can be costly on very large datasets. Key: C, kernel, gamma.
- Example: Linear SVM on TF-IDF separates news topics; RBF SVM captures non-linear boundaries in small tabular sets.

### Gradient Boosting

- Sequential trees fit residuals of prior trees (e.g., gradient boosting decision trees).
- Pros: state-of-the-art on tabular data; Cons: tuning-sensitive. Key: learning_rate, n_estimators, max_depth.
- Example: Predict loan default with shallow boosted trees (depth 4, lr 0.05) using early stopping on validation.

### Neural Networks

- Compositions of linear layers and non-linear activations trained via backpropagation.
- Pros: flexible function approximators; Cons: data- and tuning-hungry. Key: architecture, regularization, optimizer.
- Example: Small MLP for tabular housing prices with batch norm and dropout to prevent overfitting.

### Deep Learning

- Deep architectures for unstructured data: CNNs (images), RNNs/Transformers (sequences/text), GNNs (graphs).
- Pros: best on large-scale unstructured data; Cons: compute-intensive. Key: depth, pretraining, augmentation.
- Example: Fine-tune a pretrained transformer for sentiment analysis or a CNN for defect detection in images.

### Discriminant Analysis (LDA/QDA)

- Assumes class-conditional Gaussians; LDA uses shared covariance (linear boundaries), QDA uses class-specific (quadratic).
- Pros: fast, interpretable; Cons: distributional assumptions may fail. Key: covariance estimator/shrinkage.
- Example: Use LDA as a fast baseline or as a dimensionality reducer before a classifier.

### Rule Induction

- Learns human-readable if–then rules (e.g., covering algorithms), often with pruning for generalization.
- Pros: interpretable, sparse; Cons: may underperform ensembles. Key: min support/confidence, pruning strength.
- Example: Generate audit rules for fraud that analysts can review and adjust.

## 5) Text Mining Essentials

- Tokenization: split text into tokens (word/char/subword). Example: Word-piece tokenization keeps "un-" + "known".
- Stop-word filtering: remove frequent non-informative words. Example: Drop "the", "is" to reduce noise in bag-of-words.
- n-gram construction: capture short context. Example: Bi-grams like "credit card" improve phrase modeling.
- Stemming/Lemmatization: reduce words to base form to normalize variants. Example: running→run; better: lemma "was"→"be".
- Vectorization: convert text to features via bag-of-words/TF-IDF or embeddings. Example: TF-IDF with min_df=5 for linear models.

## 6) Model Evaluation

- Cross-validation: k-fold (stratified for classification) to estimate generalization; use nested CV for tuning.
- Keep a hold-out test set for final evaluation; monitor metrics aligned with the task.
- Example: For imbalanced classification, use stratified 5-fold and ROC-AUC/PR-AUC; for regression, track MAE/MSE.

## 7) Practical Tips

- Always scale features for distance- and margin-based methods (k-NN, SVM, k-means); standardize or min–max.
- Start with simple baselines (logistic regression, small trees) and add complexity only if needed.
- Example: A scaled linear model with cross-validation is a strong baseline before trying boosted trees.

## 8) Further Reading (optional)

- Bishop, Pattern Recognition and Machine Learning; Hastie, Tibshirani, Friedman, The Elements of Statistical Learning.
- scikit-learn User Guide: concise recipes and API patterns across the above algorithms.
