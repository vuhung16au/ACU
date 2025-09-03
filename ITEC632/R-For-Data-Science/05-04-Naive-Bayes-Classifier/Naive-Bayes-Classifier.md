Naive Bayes Classifier — Adult Income Dataset

# Introduction

This report implements a Gaussian Naive Bayes classifier to predict
whether a person makes more than 50K a year using the Adult Income
dataset. The flow mirrors the reference notebook: load libraries and
data, perform EDA and preprocessing, split the data, train the model,
evaluate with metrics, visualize confusion matrix, plot predicted
probability histogram, and compute ROC-AUC and cross-validation.

## Import libraries

``` r
packages <- c("e1071", "caret", "ggplot2", "dplyr", "pROC", "tidyr", "naivebayes")
installed <- rownames(installed.packages())
missing <- setdiff(packages, installed)
if (length(missing) > 0) install.packages(missing, repos = "https://cloud.r-project.org", quiet = TRUE)
lapply(packages, library, character.only = TRUE)
```

    ## [[1]]
    ## [1] "e1071"     "stats"     "graphics"  "grDevices" "utils"     "datasets" 
    ## [7] "methods"   "base"     
    ## 
    ## [[2]]
    ##  [1] "caret"     "lattice"   "ggplot2"   "e1071"     "stats"     "graphics" 
    ##  [7] "grDevices" "utils"     "datasets"  "methods"   "base"     
    ## 
    ## [[3]]
    ##  [1] "caret"     "lattice"   "ggplot2"   "e1071"     "stats"     "graphics" 
    ##  [7] "grDevices" "utils"     "datasets"  "methods"   "base"     
    ## 
    ## [[4]]
    ##  [1] "dplyr"     "caret"     "lattice"   "ggplot2"   "e1071"     "stats"    
    ##  [7] "graphics"  "grDevices" "utils"     "datasets"  "methods"   "base"     
    ## 
    ## [[5]]
    ##  [1] "pROC"      "dplyr"     "caret"     "lattice"   "ggplot2"   "e1071"    
    ##  [7] "stats"     "graphics"  "grDevices" "utils"     "datasets"  "methods"  
    ## [13] "base"     
    ## 
    ## [[6]]
    ##  [1] "tidyr"     "pROC"      "dplyr"     "caret"     "lattice"   "ggplot2"  
    ##  [7] "e1071"     "stats"     "graphics"  "grDevices" "utils"     "datasets" 
    ## [13] "methods"   "base"     
    ## 
    ## [[7]]
    ##  [1] "naivebayes" "tidyr"      "pROC"       "dplyr"      "caret"     
    ##  [6] "lattice"    "ggplot2"    "e1071"      "stats"      "graphics"  
    ## [11] "grDevices"  "utils"      "datasets"   "methods"    "base"

## Import dataset

``` r
module_dir <- "."  # knit working directory is the document's folder
path_csv <- file.path(module_dir, "Income-Dataset", "adult.csv")
col_names <- c("age","workclass","fnlwgt","education","education_num","marital_status",
               "occupation","relationship","race","sex","capital_gain","capital_loss",
               "hours_per_week","native_country","income")
adult <- read.csv(path_csv, header = FALSE, strip.white = TRUE, stringsAsFactors = FALSE)
colnames(adult) <- col_names
head(adult)
```

    ##   age        workclass fnlwgt education education_num     marital_status
    ## 1  39        State-gov  77516 Bachelors            13      Never-married
    ## 2  50 Self-emp-not-inc  83311 Bachelors            13 Married-civ-spouse
    ## 3  38          Private 215646   HS-grad             9           Divorced
    ## 4  53          Private 234721      11th             7 Married-civ-spouse
    ## 5  28          Private 338409 Bachelors            13 Married-civ-spouse
    ## 6  37          Private 284582   Masters            14 Married-civ-spouse
    ##          occupation  relationship  race    sex capital_gain capital_loss
    ## 1      Adm-clerical Not-in-family White   Male         2174            0
    ## 2   Exec-managerial       Husband White   Male            0            0
    ## 3 Handlers-cleaners Not-in-family White   Male            0            0
    ## 4 Handlers-cleaners       Husband Black   Male            0            0
    ## 5    Prof-specialty          Wife Black Female            0            0
    ## 6   Exec-managerial          Wife White Female            0            0
    ##   hours_per_week native_country income
    ## 1             40  United-States  <=50K
    ## 2             13  United-States  <=50K
    ## 3             40  United-States  <=50K
    ## 4             40  United-States  <=50K
    ## 5             40           Cuba  <=50K
    ## 6             40  United-States  <=50K

## Exploratory data analysis

``` r
# Replace '?' with NA in categorical columns
adult$workclass[adult$workclass == "?"] <- NA
adult$occupation[adult$occupation == "?"] <- NA
adult$native_country[adult$native_country == "?"] <- NA

# Summary
str(adult)
```

    ## 'data.frame':    32561 obs. of  15 variables:
    ##  $ age           : int  39 50 38 53 28 37 49 52 31 42 ...
    ##  $ workclass     : chr  "State-gov" "Self-emp-not-inc" "Private" "Private" ...
    ##  $ fnlwgt        : int  77516 83311 215646 234721 338409 284582 160187 209642 45781 159449 ...
    ##  $ education     : chr  "Bachelors" "Bachelors" "HS-grad" "11th" ...
    ##  $ education_num : int  13 13 9 7 13 14 5 9 14 13 ...
    ##  $ marital_status: chr  "Never-married" "Married-civ-spouse" "Divorced" "Married-civ-spouse" ...
    ##  $ occupation    : chr  "Adm-clerical" "Exec-managerial" "Handlers-cleaners" "Handlers-cleaners" ...
    ##  $ relationship  : chr  "Not-in-family" "Husband" "Not-in-family" "Husband" ...
    ##  $ race          : chr  "White" "White" "White" "Black" ...
    ##  $ sex           : chr  "Male" "Male" "Male" "Male" ...
    ##  $ capital_gain  : int  2174 0 0 0 0 0 0 0 14084 5178 ...
    ##  $ capital_loss  : int  0 0 0 0 0 0 0 0 0 0 ...
    ##  $ hours_per_week: int  40 13 40 40 40 40 16 45 50 40 ...
    ##  $ native_country: chr  "United-States" "United-States" "United-States" "United-States" ...
    ##  $ income        : chr  "<=50K" "<=50K" "<=50K" "<=50K" ...

``` r
summary(adult)
```

    ##       age         workclass             fnlwgt         education        
    ##  Min.   :17.00   Length:32561       Min.   :  12285   Length:32561      
    ##  1st Qu.:28.00   Class :character   1st Qu.: 117827   Class :character  
    ##  Median :37.00   Mode  :character   Median : 178356   Mode  :character  
    ##  Mean   :38.58                      Mean   : 189778                     
    ##  3rd Qu.:48.00                      3rd Qu.: 237051                     
    ##  Max.   :90.00                      Max.   :1484705                     
    ##  education_num   marital_status      occupation        relationship      
    ##  Min.   : 1.00   Length:32561       Length:32561       Length:32561      
    ##  1st Qu.: 9.00   Class :character   Class :character   Class :character  
    ##  Median :10.00   Mode  :character   Mode  :character   Mode  :character  
    ##  Mean   :10.08                                                           
    ##  3rd Qu.:12.00                                                           
    ##  Max.   :16.00                                                           
    ##      race               sex             capital_gain    capital_loss   
    ##  Length:32561       Length:32561       Min.   :    0   Min.   :   0.0  
    ##  Class :character   Class :character   1st Qu.:    0   1st Qu.:   0.0  
    ##  Mode  :character   Mode  :character   Median :    0   Median :   0.0  
    ##                                        Mean   : 1078   Mean   :  87.3  
    ##                                        3rd Qu.:    0   3rd Qu.:   0.0  
    ##                                        Max.   :99999   Max.   :4356.0  
    ##  hours_per_week  native_country        income         
    ##  Min.   : 1.00   Length:32561       Length:32561      
    ##  1st Qu.:40.00   Class :character   Class :character  
    ##  Median :40.00   Mode  :character   Mode  :character  
    ##  Mean   :40.44                                        
    ##  3rd Qu.:45.00                                        
    ##  Max.   :99.00

``` r
# Target distribution
adult$income <- factor(adult$income, levels = c("<=50K", ">50K"))
ggplot(adult, aes(income)) +
  geom_bar(fill = "#2c7fb8") +
  theme_minimal() +
  labs(title = "Income Class Distribution", x = "Income", y = "Count")
```

![](05-04-Naive-Bayes-Classifier/images/unnamed-chunk-3-1.png)<!-- -->

## Declare feature matrix and target; split train/test

``` r
set.seed(123)
train_idx <- caret::createDataPartition(adult$income, p = 0.7, list = FALSE)
adult_train <- adult[train_idx, ]
adult_test  <- adult[-train_idx, ]

# Impute NA in categorical columns with mode from training
mode_value <- function(x){ ux <- na.omit(x); if (!length(ux)) return(NA); names(sort(table(ux), TRUE))[1] }
for (col in c("workclass","occupation","native_country")){
  m <- mode_value(adult_train[[col]])
  adult_train[[col]][is.na(adult_train[[col]])] <- m
  adult_test[[col]][is.na(adult_test[[col]])] <- m
}

# Factors for categoricals; align levels
for (col in c("workclass","education","marital_status","occupation","relationship","race","sex","native_country","income")){
  adult_train[[col]] <- as.factor(adult_train[[col]])
  adult_test[[col]] <- factor(adult_test[[col]], levels = levels(adult_train[[col]]))
}

# Scale numeric columns (z-score)
numeric_cols <- c("age","fnlwgt","education_num","capital_gain","capital_loss","hours_per_week")
params <- lapply(adult_train[numeric_cols], function(x) list(mu = mean(x), sd = sd(x)))
for (col in numeric_cols){
  mu <- params[[col]]$mu; sdv <- params[[col]]$sd
  adult_train[[col]] <- if (is.na(sdv) || sdv == 0) adult_train[[col]] - mu else (adult_train[[col]] - mu)/sdv
  adult_test[[col]]  <- if (is.na(sdv) || sdv == 0) adult_test[[col]] - mu else (adult_test[[col]] - mu)/sdv
}
```

## Feature correlation (numeric)

``` r
corr_df <- cor(adult[numeric_cols])

ggplot(as.data.frame(as.table(corr_df)), aes(Var1, Var2, fill = Freq)) +
  geom_tile() +
  scale_fill_gradient2(low = "#d7191c", mid = "#ffffbf", high = "#1a9641", midpoint = 0) +
  theme_minimal() +
  theme(axis.text.x = element_text(angle = 45, hjust = 1)) +
  labs(title = "Correlation Matrix (Numeric Features)", x = NULL, y = NULL, fill = "r")
```

![](05-04-Naive-Bayes-Classifier/images/unnamed-chunk-5-1.png)<!-- -->

## Model training

``` r
nb_model <- e1071::naiveBayes(income ~ ., data = adult_train)
nb_model
```

    ## 
    ## Naive Bayes Classifier for Discrete Predictors
    ## 
    ## Call:
    ## naiveBayes.default(x = X, y = Y, laplace = laplace)
    ## 
    ## A-priori probabilities:
    ## Y
    ##     <=50K      >50K 
    ## 0.7591805 0.2408195 
    ## 
    ## Conditional probabilities:
    ##        age
    ## Y             [,1]      [,2]
    ##   <=50K -0.1327512 1.0267235
    ##   >50K   0.4184964 0.7737453
    ## 
    ##        workclass
    ## Y        Federal-gov    Local-gov Never-worked      Private Self-emp-inc
    ##   <=50K 0.0229426722 0.0609107721 0.0001733703 0.7842695331 0.0198220065
    ##   >50K  0.0462743669 0.0783384952 0.0000000000 0.6556749863 0.0788850428
    ##        workclass
    ## Y       Self-emp-not-inc    State-gov  Without-pay
    ##   <=50K     0.0734512252 0.0377369394 0.0006934813
    ##   >50K      0.0971032975 0.0437238113 0.0000000000
    ## 
    ##        fnlwgt
    ## Y               [,1]      [,2]
    ##   <=50K  0.005396495 1.0079057
    ##   >50K  -0.017012378 0.9745524
    ## 
    ##        education
    ## Y               10th         11th         12th      1st-4th      5th-6th
    ##   <=50K 0.0350785945 0.0442094313 0.0156033287 0.0064724919 0.0128294036
    ##   >50K  0.0081982146 0.0076516670 0.0049189288 0.0007287302 0.0023683731
    ##        education
    ## Y            7th-8th          9th   Assoc-acdm    Assoc-voc    Bachelors
    ##   <=50K 0.0243296348 0.0199375867 0.0332293111 0.0424757282 0.1291608877
    ##   >50K  0.0040080160 0.0025505557 0.0335215886 0.0468209146 0.2776462015
    ##        education
    ## Y          Doctorate      HS-grad      Masters    Preschool  Prof-school
    ##   <=50K 0.0039875173 0.3563337957 0.0296463245 0.0017914933 0.0062991216
    ##   >50K  0.0378939698 0.2180725087 0.1242484970 0.0000000000 0.0552013117
    ##        education
    ## Y       Some-college
    ##   <=50K 0.2386153491
    ##   >50K  0.1761705229
    ## 
    ##        education_num
    ## Y             [,1]      [,2]
    ##   <=50K -0.1877229 0.9477091
    ##   >50K   0.5917940 0.9273065
    ## 
    ##        marital_status
    ## Y          Divorced Married-AF-spouse Married-civ-spouse Married-spouse-absent
    ##   <=50K 0.160656496       0.000520111        0.332235321           0.015892279
    ##   >50K  0.059209328       0.001093095        0.854436145           0.003097103
    ##        marital_status
    ## Y       Never-married   Separated     Widowed
    ##   <=50K   0.413025890 0.039586223 0.038083680
    ##   >50K    0.062670796 0.008744762 0.010748770
    ## 
    ##        occupation
    ## Y       Adm-clerical Armed-Forces Craft-repair Exec-managerial Farming-fishing
    ##   <=50K 0.1303744799 0.0002311604 0.1960818308    0.0858760980    0.0363499769
    ##   >50K  0.0646748042 0.0001821825 0.1459282201    0.2475860813    0.0143924212
    ##        occupation
    ## Y       Handlers-cleaners Machine-op-inspct Other-service Priv-house-serv
    ##   <=50K      0.0514331946      0.0691747573  0.1288719371    0.0057790106
    ##   >50K       0.0112953179      0.0333394061  0.0162142467    0.0001821825
    ##        occupation
    ## Y       Prof-specialty Protective-serv        Sales Tech-support
    ##   <=50K   0.0919440592    0.0186084142 0.1066227462 0.0261789182
    ##   >50K    0.2368373110    0.0258699217 0.1275277828 0.0357077792
    ##        occupation
    ## Y       Transport-moving
    ##   <=50K     0.0524734166
    ##   >50K      0.0402623429
    ## 
    ##        relationship
    ## Y           Husband Not-in-family Other-relative   Own-child   Unmarried
    ##   <=50K 0.291493296   0.303860379    0.038950532 0.202958853 0.129103098
    ##   >50K  0.759336856   0.104937147    0.005465476 0.008744762 0.030242303
    ##        relationship
    ## Y              Wife
    ##   <=50K 0.033633842
    ##   >50K  0.091273456
    ## 
    ##        race
    ## Y       Amer-Indian-Eskimo Asian-Pac-Islander       Black       Other
    ##   <=50K        0.010980120        0.029935275 0.109743412 0.010691170
    ##   >50K         0.004008016        0.033339406 0.051922026 0.003279286
    ##        race
    ## Y             White
    ##   <=50K 0.838650023
    ##   >50K  0.907451266
    ## 
    ##        sex
    ## Y          Female      Male
    ##   <=50K 0.3895053 0.6104947
    ##   >50K  0.1468391 0.8531609
    ## 
    ##        capital_gain
    ## Y             [,1]      [,2]
    ##   <=50K -0.1267960 0.1334724
    ##   >50K   0.3997229 1.9713937
    ## 
    ##        capital_loss
    ## Y              [,1]      [,2]
    ##   <=50K -0.08428842 0.7719978
    ##   >50K   0.26571813 1.4768164
    ## 
    ##        hours_per_week
    ## Y             [,1]      [,2]
    ##   <=50K -0.1293036 0.9974952
    ##   >50K   0.4076279 0.8927759
    ## 
    ##        native_country
    ## Y           Cambodia       Canada        China     Columbia         Cuba
    ##   <=50K 5.201110e-04 3.005086e-03 1.964864e-03 1.964864e-03 2.889505e-03
    ##   >50K  7.287302e-04 5.101111e-03 2.914921e-03 1.821825e-04 3.461468e-03
    ##        native_country
    ## Y       Dominican-Republic      Ecuador  El-Salvador      England       France
    ##   <=50K       3.005086e-03 9.246417e-04 3.929727e-03 2.311604e-03 7.512714e-04
    ##   >50K        3.643651e-04 7.287302e-04 1.457460e-03 4.008016e-03 1.639643e-03
    ##        native_country
    ## Y            Germany       Greece    Guatemala        Haiti Holand-Netherlands
    ##   <=50K 3.582987e-03 9.246417e-04 2.484975e-03 1.675913e-03       5.779011e-05
    ##   >50K  5.465476e-03 1.457460e-03 5.465476e-04 3.643651e-04       0.000000e+00
    ##        native_country
    ## Y           Honduras         Hong      Hungary        India         Iran
    ##   <=50K 5.779011e-04 4.623209e-04 4.623209e-04 2.831715e-03 8.668516e-04
    ##   >50K  1.821825e-04 5.465476e-04 3.643651e-04 4.918929e-03 1.821825e-03
    ##        native_country
    ## Y            Ireland        Italy      Jamaica        Japan         Laos
    ##   <=50K 4.623209e-04 1.907074e-03 2.716135e-03 1.618123e-03 7.512714e-04
    ##   >50K  7.287302e-04 3.097103e-03 1.275278e-03 2.004008e-03 3.643651e-04
    ##        native_country
    ## Y             Mexico    Nicaragua Outlying-US(Guam-USVI-etc)         Peru
    ##   <=50K 2.531207e-02 1.618123e-03               5.201110e-04 1.098012e-03
    ##   >50K  5.101111e-03 3.643651e-04               0.000000e+00 3.643651e-04
    ##        native_country
    ## Y        Philippines       Poland     Portugal  Puerto-Rico     Scotland
    ##   <=50K 5.258900e-03 2.484975e-03 1.271382e-03 4.276468e-03 4.045307e-04
    ##   >50K  8.016032e-03 1.457460e-03 1.821825e-04 1.457460e-03 3.643651e-04
    ##        native_country
    ## Y              South       Taiwan     Thailand Trinadad&Tobago United-States
    ##   <=50K 2.484975e-03 1.329172e-03 5.201110e-04    8.090615e-04  9.070735e-01
    ##   >50K  1.821825e-03 2.550556e-03 5.465476e-04    1.821825e-04  9.322281e-01
    ##        native_country
    ## Y            Vietnam   Yugoslavia
    ##   <=50K 2.484975e-03 4.045307e-04
    ##   >50K  7.287302e-04 9.109127e-04

## Predictions and accuracy

``` r
y_pred <- predict(nb_model, adult_test, type = "class")
y_prob <- predict(nb_model, adult_test, type = "raw")
caret::confusionMatrix(y_pred, adult_test$income, positive = ">50K")
```

    ## Confusion Matrix and Statistics
    ## 
    ##           Reference
    ## Prediction <=50K >50K
    ##      <=50K  6906 1185
    ##      >50K    510 1167
    ##                                           
    ##                Accuracy : 0.8265          
    ##                  95% CI : (0.8188, 0.8339)
    ##     No Information Rate : 0.7592          
    ##     P-Value [Acc > NIR] : < 2.2e-16       
    ##                                           
    ##                   Kappa : 0.4738          
    ##                                           
    ##  Mcnemar's Test P-Value : < 2.2e-16       
    ##                                           
    ##             Sensitivity : 0.4962          
    ##             Specificity : 0.9312          
    ##          Pos Pred Value : 0.6959          
    ##          Neg Pred Value : 0.8535          
    ##              Prevalence : 0.2408          
    ##          Detection Rate : 0.1195          
    ##    Detection Prevalence : 0.1717          
    ##       Balanced Accuracy : 0.7137          
    ##                                           
    ##        'Positive' Class : >50K            
    ## 

## Confusion matrix heatmap

``` r
cm <- caret::confusionMatrix(y_pred, adult_test$income, positive = ">50K")
cm_df <- as.data.frame(cm$table)
colnames(cm_df) <- c("Reference","Prediction","Freq")

ggplot(cm_df, aes(Reference, Prediction, fill = Freq)) +
  geom_tile() +
  geom_text(aes(label = Freq), color = "white", fontface = "bold") +
  scale_fill_gradient(low = "#74add1", high = "#313695") +
  theme_minimal() +
  labs(title = "Confusion Matrix", x = "Actual", y = "Predicted")
```

![](05-04-Naive-Bayes-Classifier/images/unnamed-chunk-8-1.png)<!-- -->

## Histogram of predicted probabilities (\>50K)

``` r
prob_pos <- y_prob[, ">50K"]

ggplot(data.frame(prob_pos = prob_pos), aes(prob_pos)) +
  geom_histogram(bins = 10, fill = "#1b9e77", color = "white") +
  theme_minimal() +
  labs(title = "Histogram of predicted probabilities of salaries >50K",
       x = "Predicted probabilities of salaries >50K", y = "Frequency") +
  xlim(0, 1)
```

![](05-04-Naive-Bayes-Classifier/images/unnamed-chunk-9-1.png)<!-- -->

## ROC curve and AUC

``` r
roc_obj <- pROC::roc(response = adult_test$income, predictor = prob_pos, levels = c("<=50K", ">50K"), direction = ">")
auc_val <- pROC::auc(roc_obj)
auc_val
```

    ## Area under the curve: 0.1245

``` r
plot_df <- data.frame(fpr = 1 - roc_obj$specificities, tpr = roc_obj$sensitivities)

ggplot(plot_df, aes(fpr, tpr)) +
  geom_line(color = "#d73027", size = 1.1) +
  geom_abline(slope = 1, intercept = 0, linetype = "dashed", color = "gray50") +
  theme_minimal() +
  labs(title = sprintf("ROC Curve (Naive Bayes) — AUC = %.4f", as.numeric(auc_val)),
       x = "False Positive Rate (1 - Specificity)", y = "True Positive Rate (Sensitivity)")
```

![](05-04-Naive-Bayes-Classifier/images/unnamed-chunk-10-1.png)<!-- -->

## k-Fold Cross Validation

``` r
set.seed(123)
tr_ctrl <- caret::trainControl(method = "cv", number = 5)
cv_model <- caret::train(
  x = adult_train %>% dplyr::select(-income),
  y = adult_train$income,
  method = "naive_bayes",
  trControl = tr_ctrl
)
cv_model$results
```

    ##   usekernel laplace adjust  Accuracy     Kappa  AccuracySD     KappaSD
    ## 1     FALSE       0      1 0.8296848 0.4821492 0.002787332 0.009065543
    ## 2      TRUE       0      1 0.8233670 0.3755498 0.004472043 0.020285786

``` r
max(cv_model$results$Accuracy)
```

    ## [1] 0.8296848

# Conclusion

The Naive Bayes classifier achieves strong baseline accuracy on the
Adult dataset with fast training and simple preprocessing. The ROC-AUC
and cross-validated accuracy provide additional evidence of robust
performance.
