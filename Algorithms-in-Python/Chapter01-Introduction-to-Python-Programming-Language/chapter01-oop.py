## Python Object-Oriented Programming (OOP) Tutorial for Beginners

## Python Classes and Objects
# A class is like a blueprint for creating objects (instances).
# An object is an instance of a class.

# Define a simple class called Animal
class Animal:
    # The __init__ method is called when a new object is created
    def __init__(self, name, sound):
        self.name = name  # attribute to store the animal's name
        self.sound = sound  # attribute to store the animal's sound

    # Method to make the animal speak
    def speak(self):
        print(f"{self.name} says {self.sound}")

# Create objects (instances) of the Animal class
cat = Animal("Cat", "Meow")
dog = Animal("Dog", "Woof")

# Call the speak method for each object
cat.speak()  # Output: Cat says Meow
dog.speak()  # Output: Dog says Woof

## Python Polymorphism
# Polymorphism allows different classes to be used with the same interface.
# For example, different animal classes can have their own speak() method.

class Bird:
    def __init__(self, name):
        self.name = name
    def speak(self):
        print(f"{self.name} says Tweet")

# Create a list of different animal objects
animals = [cat, dog, Bird("Parrot")]

# Use a for loop to call the speak() method for each animal
for animal in animals:
    animal.speak()  # Each animal speaks in its own way (polymorphism)

# Output:
# Cat says Meow
# Dog says Woof
# Parrot says Tweet

# Polymorphism makes code flexible and easy to extend!

## Python Inheritance Example
# Inheritance allows a class (child) to inherit attributes and methods from another class (parent).

# Parent class
class Vehicle:
    def __init__(self, brand, wheels):
        self.brand = brand
        self.wheels = wheels
    def describe(self):
        print(f"This is a {self.brand} vehicle with {self.wheels} wheels.")

# Child class (inherits from Vehicle)
class Car(Vehicle):
    def __init__(self, brand, wheels, model):
        super().__init__(brand, wheels)  # Call the parent class constructor
        self.model = model
    def describe(self):
        print(f"This is a {self.brand} {self.model} car with {self.wheels} wheels.")

# Create an object of the Car class
my_car = Car("Toyota", 4, "Corolla")
my_car.describe()  # Output: This is a Toyota Corolla car with 4 wheels.

# You can also use the parent class
my_vehicle = Vehicle("Honda", 2)
my_vehicle.describe()  # Output: This is a Honda vehicle with 2 wheels.

# Inheritance helps you reuse code and organize related classes!
