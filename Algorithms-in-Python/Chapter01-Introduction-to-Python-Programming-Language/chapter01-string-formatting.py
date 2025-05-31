## Python String Formatting Tutorial for Beginners

## Using f-strings (recommended, Python 3.6+)
name = "Alice"
age = 20
print(f"Hello, my name is {name} and I am {age} years old.")

## Using format() method
price = 19.99
item = "book"
print("The price of the {} is ${:.2f}".format(item, price))

## Old style with % operator (not recommended for new code)
score = 95
print("Your score is %d points." % score)

## Formatting numbers
pi = 3.1415926535
print(f"Pi rounded to 2 decimal places: {pi:.2f}")

## Padding and alignment
for fruit in ["apple", "banana", "cherry"]:
    print(f"|{fruit:<10}| left aligned |{fruit:^10}| centered |{fruit:>10}| right aligned")

## Multi-line f-string example
user = "Bob"
city = "Paris"
print(f"""
User Information:
  Name: {user}
  City: {city}
""")

# Summary:
# - Use f-strings for easy and readable string formatting
# - You can format numbers, align text, and insert variables into strings
