## Python Datetime Tutorial for Beginners

## Import the datetime module
import datetime

## Get the current date and time
current_datetime = datetime.datetime.now()
print("Current date and time:", current_datetime)

## Get only the current date
current_date = datetime.date.today()
print("Today's date:", current_date)

## Create a specific date (e.g., 1 June 2025)
specific_date = datetime.date(2025, 6, 1)
print("Specific date:", specific_date)

## Format a date as a string
formatted_date = current_date.strftime("%d/%m/%Y")
print("Formatted date:", formatted_date)

## Calculate the difference between two dates
birthday = datetime.date(2025, 12, 25)
days_until_birthday = (birthday - current_date).days
print("Days until birthday:", days_until_birthday)

## Parse a date from a string
# Example: convert '2025-06-01' to a date object
string_date = "2025-06-01"
parsed_date = datetime.datetime.strptime(string_date, "%Y-%m-%d").date()
print("Parsed date from string:", parsed_date)

## Summary:
# - Use the datetime module to work with dates and times in Python
# - You can get the current date/time, create specific dates, format, parse, and do date arithmetic
