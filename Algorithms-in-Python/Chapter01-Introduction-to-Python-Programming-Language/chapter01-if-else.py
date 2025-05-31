## Python If ... Else and Switch Tutorial for Beginners

## If ... Else Example
# If ... else statements let you run code only if a condition is true.

user_age = 18

if user_age >= 18:
    print("You are an adult.")
else:
    print("You are a minor.")

# You can use elif for more than two choices
score = 85

if score >= 90:
    print("Grade: A")
elif score >= 80:
    print("Grade: B")
elif score >= 70:
    print("Grade: C")
else:
    print("Grade: D or below")

## Python's version of 'switch': match-case (Python 3.10+) ---
# match-case is similar to switch-case in other languages
# Let's use it to print the day of the week

day_number = 3  # 1=Monday, 2=Tuesday, ...

match day_number:
    case 1:
        print("Monday")
    case 2:
        print("Tuesday")
    case 3:
        print("Wednesday")
    case 4:
        print("Thursday")
    case 5:
        print("Friday")
    case 6:
        print("Saturday")
    case 7:
        print("Sunday")
    case _:
        print("Invalid day number")

## Another match-case example
# Let's match a color and print a message
color = "red"

match color:
    case "red":
        print("Stop! The color is red.")
    case "yellow":
        print("Caution! The color is yellow.")
    case "green":
        print("Go! The color is green.")
    case _:
        print("Unknown color.")

# Summary:
# - Use if, elif, else for conditional logic
# - Use match-case (Python 3.10+) for switch-like behavior
