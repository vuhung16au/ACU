import pandas as pd
import pandasql as ps

# Load the data from the Google Sheets URL,
# which is publicly accessible as a CSV file
# The URL is for a Google Sheets document
# The URL is for a specific sheet within the document
# This may take a while due to the size of the data
sheet_url = "https://docs.google.com/spreadsheets/d/1-IYkJsbKEilqwiyHUx43QvYXtlLvmcyuAEphriv52sM/edit?gid=418081796"
csv_url = sheet_url.replace('/edit?gid=', '/export?format=csv&gid=')
pd_acf_fide = pd.read_csv(csv_url)

# Display the first few rows of the DataFrame
print(pd_acf_fide.head())
# Display the column names
print(pd_acf_fide.columns)
# Display the data types of the columns
print(pd_acf_fide.dtypes)
# Display the number of rows and columns
print(pd_acf_fide.shape)
# Display the summary statistics of the DataFrame
print(pd_acf_fide.describe())

# select all players from pd_acf_fide where "fide_id" is "12424986"
query = "SELECT * FROM pd_acf_fide WHERE fide_id = '12424986'"
result = ps.sqldf(query, locals())
print(result)

# Select all players where "SURNAME" contains "nguyen"
query = "SELECT * FROM pd_acf_fide WHERE SURNAME LIKE '%nguyen%'"
result = ps.sqldf(query, locals())
print(result)

# Select all players where "ACF_CLASSICAL_RTG" is greater or equal to 2000
query = "SELECT * FROM pd_acf_fide WHERE ACF_CLASSICAL_RTG >= 2000"
result = ps.sqldf(query, locals())
print(result)

# Select top 10 players from "pd_acf_fide" where "FEDERATION" is "NSW" sorted by "ACF_CLASSICAL_RTG"
query = """
SELECT * 
FROM pd_acf_fide 
WHERE FEDERATION = 'NSW' 
ORDER BY ACF_CLASSICAL_RTG DESC 
LIMIT 10
"""
top_10_players = ps.sqldf(query, locals())
print(top_10_players)

# Calculate the average of "ACF_CLASSICAL_RTG" for the top 10 players
avg_rating = top_10_players['ACF_CLASSICAL_RTG'].mean()
print(f"Average ACF_CLASSICAL_RTG of top 10 players: {avg_rating}")

# Select top 30 players from "pd_acf_fide" where "FEDERATION" is "NSW" sorted by "ACF_QUICK_RTG"
query = """
SELECT * 
FROM pd_acf_fide 
WHERE FEDERATION = 'NSW' 
ORDER BY ACF_QUICK_RTG DESC 
LIMIT 30
"""
top_30_players = ps.sqldf(query, locals())
print(top_30_players)

# Calculate the average of "ACF_QUICK_RTG" for the top 30 players
avg_quick_rating = top_30_players['ACF_QUICK_RTG'].mean()
print(f"Average ACF_QUICK_RTG of top 30 players: {avg_quick_rating}")

# Select top 30 players where "FEDERATION" and "CLUB" is "St George" 
# and "ConfidenceLevel" is "Reliable" or "Very Unreliable", sorted by "ACF_CLASSICAL_RTG"
query = """
SELECT * 
FROM pd_acf_fide 
WHERE FEDERATION = 'St George' 
    AND CLUB = 'St George' 
    AND (ConfidenceLevel = 'Reliable' OR ConfidenceLevel = 'Very Unreliable') 
ORDER BY ACF_CLASSICAL_RTG DESC 
LIMIT 30
"""
top_30_st_george_players = ps.sqldf(query, locals())
print(top_30_st_george_players)

# Select all players from New Zealand where "FEDERATION" is "OS" and "CLUB" is "New Zealand", sorted by "ACF_CLASSICAL_RTG"
query = """
SELECT * 
FROM pd_acf_fide 
WHERE FEDERATION = 'OS' 
    AND CLUB = 'New Zealand' 
ORDER BY ACF_CLASSICAL_RTG DESC
"""
nz_players = ps.sqldf(query, locals())
print(nz_players)

# Count the number of players
nz_player_count = len(nz_players)
print(f"Number of players from New Zealand: {nz_player_count}")

# Count the number of male and female players
query = """
SELECT GENDER, COUNT(*) as count
FROM pd_acf_fide
WHERE GENDER IN ('Male', 'Female')
GROUP BY GENDER
"""
gender_count = ps.sqldf(query, locals())
print(gender_count)

# Calculate the proportion of Male/Female players
total_players = gender_count['count'].sum()
gender_count['proportion'] = gender_count['count'] / total_players
print(gender_count)

# Select all players who have a "FIDE_TITLE" (not blank) and "FEDERATION" is not "OS", sorted by "ACF_CLASSICAL_RTG" and print the first 100 players
query = """
SELECT * 
FROM pd_acf_fide 
WHERE FIDE_TITLE IS NOT NULL AND FIDE_TITLE != '' AND FEDERATION != 'OS'
ORDER BY ACF_CLASSICAL_RTG DESC
LIMIT 100
"""
titled_players = ps.sqldf(query, locals())
print(titled_players)