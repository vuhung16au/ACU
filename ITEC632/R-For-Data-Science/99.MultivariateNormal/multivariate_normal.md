### Multivariate Normal in R (MASS + mvtnorm)

This note accompanies `multivariate_normal.R`. It shows how to:

- Simulate from a multivariate normal using **MASS::mvrnorm**
- Compute joint density and probabilities using **mvtnorm::dmvnorm** and **mvtnorm::pmvnorm**
- Work with conditional distributions (e.g., \(X_1 \mid X_2 = x\))
- Visualize the joint shape, samples, and conditional slices

#### Why multivariate normal?

- **Captures correlations** via the covariance matrix
- **Supports multivariate inference** (hypothesis testing, estimation)
- **Broad applications**: finance, genetics, ML, signal processing
- **Conditional distributions** allow prediction/imputation

Caveats:

- **Assumption sensitivity**: normality and linear relationships
- **Computational cost** in high dimensions
- **Multicollinearity risk** with strong correlations
- **Outlier sensitivity** affecting mean/covariance

---

### Mathematical formulation

Let \( X \in \mathbb{R}^k \) be multivariate normal with mean vector \( \mu \in \mathbb{R}^k \) and positive-definite covariance matrix \( \Sigma \in \mathbb{R}^{k\times k} \), written as \( X \sim \mathcal{N}_k(\mu, \Sigma) \). The density is
\[\
f_X(x) 
:= \frac{1}{(2\pi)^{k/2} \lvert \Sigma \rvert^{1/2}}
  \exp\!\left( -\tfrac{1}{2} (x-\mu)^\top \Sigma^{-1} (x-\mu) \right).
\]

For the bivariate case \(k=2\), we often write
\[\
\mu = \begin{bmatrix} \mu_1 \\ \mu_2 \end{bmatrix},\qquad
\Sigma = \begin{bmatrix}
\sigma_1^2 & \rho\,\sigma_1\sigma_2 \\
\rho\,\sigma_1\sigma_2 & \sigma_2^2
\end{bmatrix},
\]
where \(\rho\) is the correlation between \(X_1\) and \(X_2\).

Conditional distribution for a partition \( X = (X_1, X_2) \) with corresponding partitions
\( \mu = (\mu_1, \mu_2) \) and
\( \Sigma = \begin{bmatrix} \Sigma_{11} & \Sigma_{12} \\ \Sigma_{21} & \Sigma_{22} \end{bmatrix} \):
\[\
X_1\,|\,(X_2 = x_2) \;\sim\; \mathcal{N}\!\Big(\, \mu_1 + \Sigma_{12}\,\Sigma_{22}^{-1}(x_2 - \mu_2),\; \Sigma_{11} - \Sigma_{12}\,\Sigma_{22}^{-1}\,\Sigma_{21} \,\Big).
\]

A rectangular probability used in the script is
\[\
\mathbb{P}(a_1 \le X_1 \le b_1,\; a_2 \le X_2 \le b_2)
:= \int_{a_1}^{b_1} \int_{a_2}^{b_2} f_{X_1,X_2}(x_1, x_2)\, dx_2\, dx_1,
\]
which is evaluated numerically by `mvtnorm::pmvnorm`.

---

### What the script does

1) Simulation
- Defines a mean vector and positive-definite covariance matrix
- Draws `n = 5000` samples using `MASS::mvrnorm`
- Prints sample mean and covariance to compare with targets

2) Density and probability
- Builds an \((X_1, X_2)\) grid and computes `dmvnorm` to produce contour and surface plots
- Computes a rectangle probability \(\mathbb{P}(a_1 \le X_1 \le b_1,\; a_2 \le X_2 \le b_2)\) with `pmvnorm`

3) Conditional distributions
- Uses closed-form MVN conditionals to derive \(X_1 \mid X_2 = x_2\)
- Plots several conditional densities for different fixed `x2` values

4) Visualization outputs (saved to `00.MultivariateNormal/`)
- `bivariate_density_contour.png`
- `bivariate_density_surface.png`
- `conditional_densities_X1_given_X2.png`
- `samples_scatter.png`

---

### Key functions used

- **MASS::mvrnorm(n, mu, Sigma)**: simulate from MVN
- **mvtnorm::dmvnorm(x, mean, sigma)**: joint density
- **mvtnorm::pmvnorm(lower, upper, mean, sigma)**: multivariate probability over a region

#### Details and formulas

- \(\mathbf{X} \sim \mathcal{N}_k(\mu, \Sigma)\) sampling (used by `MASS::mvrnorm`):
  - Generate \(\mathbf{Z} \sim \mathcal{N}_k(\mathbf{0}, I_k)\)
  - Compute a matrix square root of \(\Sigma\) (e.g., Cholesky factorization \(\Sigma = LL^\top\) with \(L\) lower-triangular)
  - Set
    \[\
    \mathbf{X} = \mu + L\,\mathbf{Z}.
    \]
    This yields the desired covariance: \(\operatorname{Cov}(\mathbf{X}) = L I_k L^\top = LL^\top = \Sigma\).

- Density at a point \(x \in \mathbb{R}^k\) (computed by `mvtnorm::dmvnorm(x, mean, sigma)`):
  \[\
  f_X(x) = \frac{1}{(2\pi)^{k/2} \lvert \Sigma \rvert^{1/2}}\, \exp\!\left( -\tfrac{1}{2} (x-\mu)^\top \Sigma^{-1} (x-\mu) \right).
  \]
  - In 2D with correlation \(\rho\), the quadratic term expands to
    \[\
    (x-\mu)^\top \Sigma^{-1} (x-\mu)
    = \frac{1}{1-\rho^2}\left(\frac{(x_1-\mu_1)^2}{\sigma_1^2} - 2\rho\,\frac{(x_1-\mu_1)(x_2-\mu_2)}{\sigma_1\sigma_2} + \frac{(x_2-\mu_2)^2}{\sigma_2^2}\right).
    \]

- Region probability (computed by `mvtnorm::pmvnorm(lower, upper, mean, sigma)`): for vectors \(\ell, u \in (\mathbb{R}\cup\{-\infty, +\infty\})^k\),
  \[\
  \mathbb{P}(\ell \le \mathbf{X} \le u)
  = \int_{\ell_1}^{u_1} \cdots \int_{\ell_k}^{u_k} f_X(x)\, dx_k\cdots dx_1.
  \]
  - Unbounded sides use \(\ell_i = -\infty\) or \(u_i = +\infty\)
  - For the 2D rectangle \([a_1,b_1]\times[a_2,b_2]\):
    \[\
    \mathbb{P}(a_1 \le X_1 \le b_1,\; a_2 \le X_2 \le b_2)
    = \int_{a_1}^{b_1} \int_{a_2}^{b_2} f_{X_1,X_2}(x_1, x_2)\, dx_2\, dx_1.
    \]

---

### Notes

- Ensure `MASS` and `mvtnorm` are installed. The script attempts to install them from CRAN if missing.
- All plots are generated deterministically with `set.seed(632)`.
- For higher dimensions, avoid plotting dense grids; consider pairwise plots or dimension reduction.
