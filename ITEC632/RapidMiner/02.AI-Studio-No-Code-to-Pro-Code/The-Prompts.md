# Sample prompt to talk with AIs.

## The dataset

Given the dataset below in `supermarket.csv`

Filename: `supermarket.csv`
```csv
receipt_id	desserts	meats	juices	paper_goods	frozen_foods	snack_foods	canned goods	beer_wine_spirits	dairy	breads	produce
1	0	1	1	0	1	0	0	0	0	0	1
2	1	0	1	1	0	0	0	0	1	0	0
3	1	1	1	1	1	0	1	1	1	1	1
4	1	1	0	1	1	0	0	0	0	0	1
5	0	0	0	0	0	1	0	1	0	0	0
6	1	0	1	0	0	0	0	0	0	0	0
7	1	0	0	1	0	0	0	0	0	0	0
8	0	0	0	0	0	1	0	0	1	0	0
9	1	0	0	1	0	0	0	0	0	0	0
10	0	1	0	0	0	0	0	0	0	0	1
11	0	0	0	0	1	0	0	0	0	0	0
12	1	0	0	1	1	0	0	0	1	0	0
13	1	0	0	0	0	0	0	0	0	1	0
...
```

The actual dataset contains 100K rows.

My objective here is to identify frequent product combinations and generate actionable association rules from supermarket receipt data using the FP-Growth algorithm in Altair AI Studio (we are using verion 2025). 

Please suggest me a sequence of operators (in Altair AI Studio)
For each operators, suggest me what parameters should I set in order to get a good result. 

The input: 
- dataset: `inputs/supermarket.csv`
- RapidMiner AI Studio process file: `inputs/supermarket.rmp`

## The expected output

A `R` script that implements the operators in Altair AI Studio.

Save your output in `outputs/supermarket.R`

## Additional requirements

The tasks I need to follow in Altair AI Studio looks like:

```
Tasks:
Data Preparation: 
Load the supermarket receipt dataset with binary (0/1) product indicators.
Use Set Role to assign the transaction ID column as id. 
Convert numeric attributes to binominal using Numerical to Binominal.

FP-Growth:
Apply FP-Growth, experimenting with different minimum support values (e.g., 0.1 to 0.3) to find optimal frequent item sets. 

Generate rules using Create Association Rules while adjusting confidence thresholds (e.g., 0.6 to 0.9) for meaningful results.

```
