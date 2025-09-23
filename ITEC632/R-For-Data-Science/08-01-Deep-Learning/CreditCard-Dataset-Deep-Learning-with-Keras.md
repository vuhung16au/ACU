# Credit Card Dataset — Description and Columns

This dataset contains information on default payments, demographics, credit data, payment history, bill statements, and previous payments for credit card clients. The task is to predict `default.payment.next.month`.

## Source Files
- Data: `08-01-Deep-Learning/CreditCard-Dataset/CreditCard.csv`

## Columns
- `ID`: Unique identifier
- `LIMIT_BAL`: Credit limit (NT dollar)
- `SEX`: Gender (1 = male, 2 = female)
- `EDUCATION`: Education level (1 = graduate school, 2 = university, 3 = high school, 4 = others; sometimes 0, 5, 6 may appear and can be grouped as "other")
- `MARRIAGE`: Marital status (1 = married, 2 = single, 3 = others)
- `AGE`: Age in years
- `PAY_0`, `PAY_2`, `PAY_3`, `PAY_4`, `PAY_5`, `PAY_6`: Repayment status for the past months (−2 = no consumption/zero bill, −1 = pay duly, 0 = partial payment, 1–9 = months of delay)
- `BILL_AMT1`..`BILL_AMT6`: Amount of bill statements for the past 6 months (NT dollar)
- `PAY_AMT1`..`PAY_AMT6`: Amounts of previous payments for the past 6 months (NT dollar)
- `default.payment.next.month`: Default status in the following month (1 = default, 0 = not default)

## Notes
- Payment status coding nuances: metadata often notes −1 as "pay duly"; exploratory analysis also shows −2 as frequently associated with zero bill amount and 0 with partial payment.
- There is class imbalance; roughly 22% defaults in typical versions of this dataset.


