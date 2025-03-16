import pandas as pd
import numpy as np

# --- Introduction to Data Structures ---
# Create a sample DataFrame with mixed data types
data = {
    'Name': ['Alice', 'Bob', 'Charlie', 'David', 'Eve'],
    'Age': [25, 30, 35, 40, 45],
    'Gender': ['Female', 'Male', 'Male', 'Male', 'Female'],
    'Score': [85.5, 90.0, 78.5, 88.0, 92.5],
    'Date': pd.to_datetime(['2023-01-01', '2023-01-02', '2023-01-03', '2023-01-04', '2023-01-05'])
}
df = pd.DataFrame(data)

# Create a Series with custom index
s = pd.Series([1, 2, 3, 4, 5], index=['a', 'b', 'c', 'd', 'e'])

# --- Basic Functionality & Descriptive Statistics ---
print("DataFrame shape:", df.shape)  # Display shape (rows, columns)
print("Descriptive stats:\n", df.describe())  # Summary statistics (count, mean, etc.)

# --- Indexing & Selecting Data ---
print("First row:\n", df.iloc[0])  # Position-based indexing
print("Names:", df['Name'])  # Column selection
print("Scores > 85:\n", df[df['Score'] > 85])  # Boolean indexing

# --- Series Operations ---
print("Series:", s)
print("Sliced Series:", s['b':'d'])  # Label-based slicing
print("Series index:", s.index, "values:", s.values)  # Attributes
s2 = s * 2  # Arithmetic operation
print("Series * 2:", s2)
print("Series to dict:", s.to_dict())  # Convert to dictionary

# --- DataFrame Modifications ---
df['Age'] += 1  # Modify 'Age' column
print("Modified Age:\n", df)
df_dropped = df.drop(0)  # Remove row with index 0
print("After dropping row 0:\n", df_dropped)
df['Score'] += 5  # Arithmetic operation on DataFrame
print("Score +5:\n", df)

# --- IO Tools (Syntax Examples) ---
# df.to_csv('data.csv')  # Write to CSV
# df_csv = pd.read_csv('data.csv')  # Read from CSV
# df.to_json('data.json')  # Write to JSON
# df_json = pd.read_json('data.json')  # Read from JSON

# --- Sorting & Reindexing ---
df_sorted = df.sort_values(by='Score', ascending=False)  # Sort by 'Score'
print("Sorted by Score:\n", df_sorted)
df_reindexed = df.reindex([4, 3, 2, 1, 0])  # Reorder rows
print("Reindexed:\n", df_reindexed)

# --- Concatenation ---
df2 = pd.DataFrame({'Name': ['Frank'], 'Age': [50], 'Gender': ['Male'], 'Score': [80.0], 'Date': pd.to_datetime(['2023-01-06'])})
df_concat = pd.concat([df, df2], ignore_index=True)  # Append DataFrames
print("Concatenated:\n", df_concat)

# --- Statistical Functions ---
print("Mean Age:", df['Age'].mean())  # Average age
print("Max Score:", df['Score'].max())  # Maximum score

# --- Working with Text Data ---
df['Name_lower'] = df['Name'].str.lower()  # Convert names to lowercase
print("Lowercased names:\n", df)

# --- Function Application ---
def categorize_age(age): return 'Young' if age < 35 else 'Senior'
df['Age_Group'] = df['Age'].apply(categorize_age)  # Apply custom function
print("With Age_Group:\n", df)

# --- Window Functions ---
df['Score_Rolling'] = df['Score'].rolling(window=2).mean()  # Rolling mean
print("Rolling mean:\n", df)

# --- MultiIndex ---
index = pd.MultiIndex.from_arrays([['A', 'A', 'B', 'B'], ['one', 'two', 'one', 'two']], names=('Letter', 'Number'))
df_multi = pd.DataFrame({'Value': [1, 2, 3, 4]}, index=index)
print("MultiIndex DF:\n", df_multi)
print("Select 'A':\n", df_multi.loc['A'])  # MultiIndex indexing

# --- Binary Operations & Boolean Masking ---
df3 = pd.DataFrame({'Age': [26, 31, 36, 41, 46]}, index=[0, 1, 2, 3, 4])
df['Age_Diff'] = df['Age'] - df3['Age']  # Binary subtraction
print("Age difference:\n", df)
print("Score > 90:\n", df[df['Score'] > 90])  # Boolean mask

# --- Data Reshaping & Pivoting ---
df_pivot = pd.DataFrame({'Date': ['2023-01-01', '2023-01-01', '2023-01-02', '2023-01-02'],
                         'Category': ['A', 'B', 'A', 'B'], 'Value': [10, 20, 30, 40]})
df_pivoted = df_pivot.pivot(index='Date', columns='Category', values='Value')  # Pivot data
print("Pivoted:\n", df_pivoted)
df_stacked = df_pivoted.stack()  # Stack columns into rows
print("Stacked:\n", df_stacked)
df_melted = pd.melt(df, id_vars=['Name'], value_vars=['Age', 'Score'])  # Melt wide to long
print("Melted:\n", df_melted)

# --- Categorical Data ---
df['Gender'] = pd.Categorical(df['Gender'])  # Convert to categorical
print("Gender categories:", df['Gender'].unique())

# --- Handling Missing Data ---
df.loc[2, 'Score'] = np.nan  # Introduce NaN
print("With NaN:\n", df)
df_filled = df.fillna({'Score': df['Score'].mean()})  # Fill NaN with mean
print("NaN filled:\n", df_filled)
df_dropped_na = df.dropna()  # Drop rows with NaN
print("NaN dropped:\n", df_dropped_na)

# --- Handling Duplicates ---
df_duplicates = pd.concat([df, df.iloc[0:1]], ignore_index=True)  # Add duplicate row
print("With duplicates:\n", df_duplicates)
df_no_duplicates = df_duplicates.drop_duplicates()  # Remove duplicates
print("No duplicates:\n", df_no_duplicates)

# --- Grouping & Aggregation ---
grouped = df.groupby('Gender')  # Group by 'Gender'
print("Mean Score by Gender:\n", grouped['Score'].mean())

# --- Time-series Data ---
df.set_index('Date', inplace=True)  # Use 'Date' as index
print("Time-series DF:\n", df)

# --- Visualization (Syntax Example) ---
# df['Score'].plot(kind='bar')  # Bar plot (requires matplotlib)