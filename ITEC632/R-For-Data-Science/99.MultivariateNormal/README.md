### Multivariate Normal: R Examples with MASS and mvtnorm

This folder contains an end-to-end R example of the multivariate normal (MVN) distribution:

- Sampling with `MASS::mvrnorm`
- Density and probability with `mvtnorm::dmvnorm` and `mvtnorm::pmvnorm`
- Conditional distributions and visualizations

Files:

- `multivariate_normal.R` — main R script
- `multivariate_normal.md` — explanation of concepts and what the script does

#### Prerequisites

- R (4.x recommended)
- Internet access to install packages from CRAN if missing

The script will automatically install `MASS` and `mvtnorm` if they are not present.

#### How to run

From the repository root:

```r
# In an interactive R session
source("00.MultivariateNormal/multivariate_normal.R")
```

Or from shell (macOS/Linux):

```bash
Rscript 00.MultivariateNormal/multivariate_normal.R
```

#### What to expect

- Console output comparing target vs. sample mean/covariance
- Four figures written to `00.MultivariateNormal/`:
  - `bivariate_density_contour.png`
  - `bivariate_density_surface.png`
  - `conditional_densities_X1_given_X2.png`
  - `samples_scatter.png`

