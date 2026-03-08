using System;
using System.IO;

namespace CSharpAdvanced
{
    /// <summary>
    /// Demonstrates advanced C# programming concepts for learners progressing beyond the basics.
    /// This program covers OOP, methods, classes, inheritance, interfaces, and exception handling.
    /// </summary>
    class Program
    {
        /// <summary>
        /// The main entry point of the application.
        /// Demonstrates advanced C# concepts step by step.
        /// </summary>
        static void Main(string[] args)
        {
            // Print a welcome message
            Console.WriteLine("==============================================");
            Console.WriteLine("Welcome to C# Advanced Concepts Demo!");
            Console.WriteLine("==============================================");
            Console.WriteLine();

            // Demonstrate each advanced concept
            DemonstrateMethods();
            DemonstrateMethodParameters();
            DemonstrateMethodOverloading();
            DemonstrateClassesAndObjects();
            DemonstrateConstructors();
            DemonstrateAccessModifiers();
            DemonstrateProperties();
            DemonstrateInheritance();
            DemonstratePolymorphism();
            DemonstrateAbstraction();
            DemonstrateInterfaces();
            DemonstrateEnums();
            DemonstrateFileOperations();
            DemonstrateExceptionHandling();

            // End of program
            Console.WriteLine();
            Console.WriteLine("==============================================");
            Console.WriteLine("End of C# Advanced Concepts Demo!");
            Console.WriteLine("==============================================");
        }

        #region Methods Demonstrations

        /// <summary>
        /// Demonstrates how to create and call methods in C#.
        /// Methods are reusable blocks of code that perform specific tasks.
        /// </summary>
        static void DemonstrateMethods()
        {
            Console.WriteLine("\n=== 1. Methods ===");
            Console.WriteLine("Methods are reusable blocks of code that perform specific tasks.");
            
            // Call a simple method
            PrintWelcomeMessage();
            
            // Call a method with a return value
            int sum = Add(10, 20);
            Console.WriteLine($"Add(10, 20) returns: {sum}");
            
            // Call a method that returns a string
            string greeting = GetGreeting("Alice");
            Console.WriteLine(greeting);
        }

        /// <summary>
        /// A simple method that prints a welcome message.
        /// This method has no parameters and no return value (void).
        /// </summary>
        static void PrintWelcomeMessage()
        {
            Console.WriteLine("Welcome! This is a simple method.");
        }

        /// <summary>
        /// Adds two numbers and returns the result.
        /// </summary>
        /// <param name="a">The first number</param>
        /// <param name="b">The second number</param>
        /// <returns>The sum of a and b</returns>
        static int Add(int a, int b)
        {
            return a + b;
        }

        /// <summary>
        /// Creates a personalized greeting message.
        /// </summary>
        /// <param name="name">The name to greet</param>
        /// <returns>A greeting string</returns>
        static string GetGreeting(string name)
        {
            return $"Hello, {name}! Nice to meet you.";
        }

        #endregion

        #region Method Parameters Demonstrations

        /// <summary>
        /// Demonstrates different types of method parameters: value, ref, and out.
        /// </summary>
        static void DemonstrateMethodParameters()
        {
            Console.WriteLine("\n=== 2. Method Parameters ===");
            Console.WriteLine("Parameters can be passed by value, by reference (ref), or as output (out).");
            
            // By value (default) - changes don't affect original
            int number = 10;
            Console.WriteLine($"Before ModifyByValue: {number}");
            ModifyByValue(number);
            Console.WriteLine($"After ModifyByValue: {number}"); // Still 10
            
            // By reference (ref) - changes affect original
            int refNumber = 10;
            Console.WriteLine($"Before ModifyByRef: {refNumber}");
            ModifyByRef(ref refNumber);
            Console.WriteLine($"After ModifyByRef: {refNumber}"); // Changed to 20
            
            // Output parameter (out) - must be assigned in method
            int result;
            MultiplyByTwo(5, out result);
            Console.WriteLine($"MultiplyByTwo(5) output: {result}");
            
            // Optional parameters with default values
            Console.WriteLine(Greet("Bob")); // Uses default greeting
            Console.WriteLine(Greet("Alice", "Good morning")); // Custom greeting
        }

        /// <summary>
        /// Modifies a parameter passed by value (original unchanged).
        /// </summary>
        static void ModifyByValue(int x)
        {
            x = x * 2;
            Console.WriteLine($"Inside ModifyByValue: {x}");
        }

        /// <summary>
        /// Modifies a parameter passed by reference (original changed).
        /// </summary>
        static void ModifyByRef(ref int x)
        {
            x = x * 2;
            Console.WriteLine($"Inside ModifyByRef: {x}");
        }

        /// <summary>
        /// Uses an output parameter to return a calculated value.
        /// </summary>
        static void MultiplyByTwo(int input, out int output)
        {
            output = input * 2;
        }

        /// <summary>
        /// Demonstrates optional parameters with default values.
        /// </summary>
        static string Greet(string name, string greeting = "Hello")
        {
            return $"{greeting}, {name}!";
        }

        #endregion

        #region Method Overloading Demonstrations

        /// <summary>
        /// Demonstrates method overloading - multiple methods with the same name but different parameters.
        /// </summary>
        static void DemonstrateMethodOverloading()
        {
            Console.WriteLine("\n=== 3. Method Overloading ===");
            Console.WriteLine("Method overloading allows multiple methods with the same name but different parameters.");
            
            // Call different overloaded versions of Calculate
            Console.WriteLine($"Calculate(5, 3) = {Calculate(5, 3)}");
            Console.WriteLine($"Calculate(5.5, 3.2) = {Calculate(5.5, 3.2)}");
            Console.WriteLine($"Calculate(5, 3, 2) = {Calculate(5, 3, 2)}");
            
            // Call overloaded Display methods
            Display(42);
            Display("Hello World");
            Display(3.14, "Pi");
        }

        /// <summary>
        /// Calculates sum of two integers.
        /// </summary>
        static int Calculate(int a, int b)
        {
            return a + b;
        }

        /// <summary>
        /// Calculates sum of two doubles.
        /// </summary>
        static double Calculate(double a, double b)
        {
            return a + b;
        }

        /// <summary>
        /// Calculates sum of three integers.
        /// </summary>
        static int Calculate(int a, int b, int c)
        {
            return a + b + c;
        }

        /// <summary>
        /// Displays an integer value.
        /// </summary>
        static void Display(int value)
        {
            Console.WriteLine($"Integer: {value}");
        }

        /// <summary>
        /// Displays a string value.
        /// </summary>
        static void Display(string value)
        {
            Console.WriteLine($"String: {value}");
        }

        /// <summary>
        /// Displays a double with a label.
        /// </summary>
        static void Display(double value, string label)
        {
            Console.WriteLine($"{label}: {value}");
        }

        #endregion

        #region Classes and Objects Demonstrations

        /// <summary>
        /// Demonstrates creating and using classes and objects.
        /// Classes are blueprints for objects.
        /// </summary>
        static void DemonstrateClassesAndObjects()
        {
            Console.WriteLine("\n=== 4. Classes and Objects ===");
            Console.WriteLine("Classes are blueprints, objects are instances of classes.");
            
            // Create an object (instance) of the Car class
            Car myCar = new Car();
            myCar.brand = "Toyota";
            myCar.model = "Camry";
            myCar.year = 2022;
            myCar.color = "Blue";
            
            // Use the object's method
            myCar.DisplayInfo();
            myCar.StartEngine();
            
            // Create another car object
            Car yourCar = new Car();
            yourCar.brand = "Honda";
            yourCar.model = "Civic";
            yourCar.year = 2023;
            yourCar.color = "Red";
            
            yourCar.DisplayInfo();
            yourCar.StartEngine();
        }

        #endregion

        #region Constructors Demonstrations

        /// <summary>
        /// Demonstrates constructors - special methods that initialize objects.
        /// </summary>
        static void DemonstrateConstructors()
        {
            Console.WriteLine("\n=== 5. Constructors ===");
            Console.WriteLine("Constructors initialize objects when they are created.");
            
            // Default constructor
            Person person1 = new Person();
            person1.Introduce();
            
            // Parameterized constructor
            Person person2 = new Person("Alice", 25);
            person2.Introduce();
            
            Person person3 = new Person("Bob", 30);
            person3.Introduce();
        }

        #endregion

        #region Access Modifiers Demonstrations

        /// <summary>
        /// Demonstrates access modifiers that control visibility of class members.
        /// </summary>
        static void DemonstrateAccessModifiers()
        {
            Console.WriteLine("\n=== 6. Access Modifiers ===");
            Console.WriteLine("Access modifiers control who can access class members.");
            Console.WriteLine("public: accessible everywhere");
            Console.WriteLine("private: accessible only within the class");
            Console.WriteLine("protected: accessible within class and derived classes");
            
            BankAccount account = new BankAccount("Alice", 1000);
            
            // Can access public members
            Console.WriteLine($"Account holder: {account.GetAccountHolder()}");
            Console.WriteLine($"Balance: ${account.GetBalance()}");
            
            // Can use public methods
            account.Deposit(500);
            account.Withdraw(200);
            
            // Cannot access private fields directly (would cause compilation error)
            // Console.WriteLine(account.balance); // ERROR!
        }

        #endregion

        #region Properties Demonstrations

        /// <summary>
        /// Demonstrates properties with get and set accessors.
        /// Properties provide controlled access to private fields.
        /// </summary>
        static void DemonstrateProperties()
        {
            Console.WriteLine("\n=== 7. Properties (Get and Set) ===");
            Console.WriteLine("Properties provide controlled access to private fields.");
            
            Student student = new Student();
            
            // Use property setters
            student.Name = "Charlie";
            student.Age = 20;
            student.Grade = "A";
            
            // Use property getters
            Console.WriteLine($"Student: {student.Name}");
            Console.WriteLine($"Age: {student.Age}");
            Console.WriteLine($"Grade: {student.Grade}");
            
            // Try to set invalid age (validation in setter)
            Console.WriteLine("\nTrying to set invalid age (-5):");
            student.Age = -5; // Setter will prevent this
            Console.WriteLine($"Age after invalid set: {student.Age}");
            
            // Auto-implemented property
            student.StudentId = "S12345";
            Console.WriteLine($"Student ID: {student.StudentId}");
        }

        #endregion

        #region Inheritance Demonstrations

        /// <summary>
        /// Demonstrates inheritance - deriving new classes from existing ones.
        /// Child classes inherit properties and methods from parent classes.
        /// </summary>
        static void DemonstrateInheritance()
        {
            Console.WriteLine("\n=== 8. Inheritance ===");
            Console.WriteLine("Child classes inherit properties and methods from parent classes.");
            
            // Create objects of derived classes
            Dog myDog = new Dog("Buddy", 3);
            Cat myCat = new Cat("Whiskers", 2);
            
            // Use inherited methods
            myDog.Eat();
            myDog.Sleep();
            myDog.Bark(); // Dog-specific method
            
            Console.WriteLine();
            
            myCat.Eat();
            myCat.Sleep();
            myCat.Meow(); // Cat-specific method
        }

        #endregion

        #region Polymorphism Demonstrations

        /// <summary>
        /// Demonstrates polymorphism - objects of different types can be treated uniformly.
        /// Method overriding allows derived classes to provide specific implementations.
        /// </summary>
        static void DemonstratePolymorphism()
        {
            Console.WriteLine("\n=== 9. Polymorphism ===");
            Console.WriteLine("Polymorphism allows objects of different types to be treated uniformly.");
            
            // Polymorphism: parent reference can hold child objects
            Animal animal1 = new Dog("Max", 4);
            Animal animal2 = new Cat("Luna", 3);
            Animal animal3 = new Bird("Tweety", 1);
            
            // Each calls its own overridden MakeSound method
            Console.WriteLine("All animals making sounds:");
            animal1.MakeSound(); // Dog barks
            animal2.MakeSound(); // Cat meows
            animal3.MakeSound(); // Bird chirps
            
            // Array of animals (polymorphic collection)
            Console.WriteLine("\nAnimals in array:");
            Animal[] animals = { animal1, animal2, animal3 };
            foreach (Animal animal in animals)
            {
                animal.MakeSound();
            }
        }

        #endregion

        #region Abstraction Demonstrations

        /// <summary>
        /// Demonstrates abstraction using abstract classes.
        /// Abstract classes cannot be instantiated and can contain abstract methods.
        /// </summary>
        static void DemonstrateAbstraction()
        {
            Console.WriteLine("\n=== 10. Abstraction ===");
            Console.WriteLine("Abstract classes provide a template for derived classes.");
            
            // Cannot create instance of abstract class: new Shape() - ERROR!
            
            // Create instances of concrete classes
            Shape circle = new Circle(5);
            Shape rectangle = new Rectangle(4, 6);
            Shape triangle = new Triangle(3, 4);
            
            // Call abstract methods (implemented in derived classes)
            Console.WriteLine($"Circle area: {circle.CalculateArea():F2}");
            Console.WriteLine($"Rectangle area: {rectangle.CalculateArea():F2}");
            Console.WriteLine($"Triangle area: {triangle.CalculateArea():F2}");
            
            // Call concrete method from abstract class
            circle.DisplayShapeType();
            rectangle.DisplayShapeType();
            triangle.DisplayShapeType();
        }

        #endregion

        #region Interface Demonstrations

        /// <summary>
        /// Demonstrates interfaces - contracts that classes must implement.
        /// Interfaces define what methods a class must have, but not how they work.
        /// </summary>
        static void DemonstrateInterfaces()
        {
            Console.WriteLine("\n=== 11. Interfaces ===");
            Console.WriteLine("Interfaces define contracts that classes must implement.");
            
            // Create objects implementing interfaces
            IPlayable guitar = new Guitar();
            IPlayable piano = new Piano();
            IPlayable drums = new Drums();
            
            // All implement the same interface methods
            guitar.Play();
            guitar.Stop();
            
            Console.WriteLine();
            
            piano.Play();
            piano.Stop();
            
            Console.WriteLine();
            
            drums.Play();
            drums.Stop();
            
            // Multiple interface implementation
            Console.WriteLine("\nMultiple interface implementation:");
            SmartPhone phone = new SmartPhone();
            phone.MakeCall("555-1234");
            phone.Connect();
            phone.Disconnect();
        }

        #endregion

        #region Enum Demonstrations

        /// <summary>
        /// Demonstrates enums - named constants for better code readability.
        /// </summary>
        static void DemonstrateEnums()
        {
            Console.WriteLine("\n=== 12. Enums ===");
            Console.WriteLine("Enums are named constants that make code more readable.");
            
            // Use enum values
            Day today = Day.Saturday;
            Console.WriteLine($"Today is: {today}");
            
            // Enum in conditionals
            if (today == Day.Saturday || today == Day.Sunday)
            {
                Console.WriteLine("It's the weekend!");
            }
            
            // Loop through all enum values
            Console.WriteLine("\nDays of the week:");
            foreach (Day day in Enum.GetValues(typeof(Day)))
            {
                Console.WriteLine($"{(int)day}: {day}");
            }
            
            // Enum with explicit values
            Console.WriteLine("\nOrder status:");
            OrderStatus status = OrderStatus.Shipped;
            Console.WriteLine($"Current status: {status} (value: {(int)status})");
            
            // Switch with enum
            string message = GetStatusMessage(status);
            Console.WriteLine(message);
        }

        /// <summary>
        /// Gets a descriptive message for an order status.
        /// </summary>
        static string GetStatusMessage(OrderStatus status)
        {
            switch (status)
            {
                case OrderStatus.Pending:
                    return "Your order is being processed.";
                case OrderStatus.Confirmed:
                    return "Your order has been confirmed.";
                case OrderStatus.Shipped:
                    return "Your order has been shipped!";
                case OrderStatus.Delivered:
                    return "Your order has been delivered.";
                case OrderStatus.Cancelled:
                    return "Your order was cancelled.";
                default:
                    return "Unknown status.";
            }
        }

        #endregion

        #region File Operations Demonstrations

        /// <summary>
        /// Demonstrates file operations: creating, writing, reading, and deleting files.
        /// </summary>
        static void DemonstrateFileOperations()
        {
            Console.WriteLine("\n=== 13. File Operations ===");
            Console.WriteLine("C# provides classes for working with files.");
            
            string fileName = "demo.txt";
            
            try
            {
                // Write to a file
                Console.WriteLine($"\nWriting to file: {fileName}");
                File.WriteAllText(fileName, "Hello from C#!\n");
                File.AppendAllText(fileName, "This is line 2.\n");
                File.AppendAllText(fileName, "This is line 3.\n");
                Console.WriteLine("File written successfully.");
                
                // Read from a file
                Console.WriteLine($"\nReading from file: {fileName}");
                string content = File.ReadAllText(fileName);
                Console.WriteLine("File contents:");
                Console.WriteLine(content);
                
                // Read all lines
                Console.WriteLine("Reading line by line:");
                string[] lines = File.ReadAllLines(fileName);
                for (int i = 0; i < lines.Length; i++)
                {
                    Console.WriteLine($"Line {i + 1}: {lines[i]}");
                }
                
                // Check if file exists
                Console.WriteLine($"\nDoes {fileName} exist? {File.Exists(fileName)}");
                
                // Delete the file
                Console.WriteLine($"\nDeleting file: {fileName}");
                File.Delete(fileName);
                Console.WriteLine($"Does {fileName} exist? {File.Exists(fileName)}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"File operation error: {ex.Message}");
            }
        }

        #endregion

        #region Exception Handling Demonstrations

        /// <summary>
        /// Demonstrates exception handling with try-catch-finally blocks.
        /// </summary>
        static void DemonstrateExceptionHandling()
        {
            Console.WriteLine("\n=== 14. Exception Handling ===");
            Console.WriteLine("Exception handling prevents crashes and handles errors gracefully.");
            
            // Try-Catch example
            Console.WriteLine("\n--- Try-Catch Example ---");
            try
            {
                int[] numbers = { 1, 2, 3 };
                Console.WriteLine($"Accessing index 5: {numbers[5]}"); // Will throw exception
            }
            catch (IndexOutOfRangeException ex)
            {
                Console.WriteLine($"Caught exception: {ex.Message}");
            }
            
            // Multiple catch blocks
            Console.WriteLine("\n--- Multiple Catch Blocks ---");
            ExecuteDivision(10, 0);
            ExecuteDivision(10, 2);
            
            // Try-Catch-Finally
            Console.WriteLine("\n--- Try-Catch-Finally Example ---");
            DemonstrateFinally();
            
            // Throwing exceptions
            Console.WriteLine("\n--- Throwing Exceptions ---");
            DemonstrateThrowingExceptions();
        }

        /// <summary>
        /// Demonstrates division with exception handling.
        /// </summary>
        static void ExecuteDivision(int a, int b)
        {
            try
            {
                int result = a / b;
                Console.WriteLine($"{a} / {b} = {result}");
            }
            catch (DivideByZeroException ex)
            {
                Console.WriteLine($"Error: Cannot divide by zero! ({ex.Message})");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"General error: {ex.Message}");
            }
        }

        /// <summary>
        /// Demonstrates the finally block which always executes.
        /// </summary>
        static void DemonstrateFinally()
        {
            try
            {
                Console.WriteLine("Inside try block");
                int result = 10 / 2;
                Console.WriteLine($"Result: {result}");
            }
            catch (Exception ex)
            {
                Console.WriteLine($"Inside catch block: {ex.Message}");
            }
            finally
            {
                Console.WriteLine("Inside finally block (always executes)");
            }
        }

        /// <summary>
        /// Demonstrates throwing custom exceptions.
        /// </summary>
        static void DemonstrateThrowingExceptions()
        {
            try
            {
                ValidateAge(15); // Will throw exception
            }
            catch (ArgumentException ex)
            {
                Console.WriteLine($"Validation failed: {ex.Message}");
            }
            
            try
            {
                ValidateAge(25); // Will succeed
            }
            catch (ArgumentException ex)
            {
                Console.WriteLine($"Validation failed: {ex.Message}");
            }
        }

        /// <summary>
        /// Validates age and throws exception if invalid.
        /// </summary>
        static void ValidateAge(int age)
        {
            if (age < 18)
            {
                throw new ArgumentException("Age must be 18 or older.");
            }
            Console.WriteLine($"Age {age} is valid.");
        }

        #endregion
    }

    #region Supporting Classes

    // ====================================
    // Car class for Classes and Objects demo
    // ====================================
    
    /// <summary>
    /// Represents a car with basic properties.
    /// Demonstrates a simple class structure.
    /// </summary>
    class Car
    {
        // Fields (class members)
        public string brand;
        public string model;
        public int year;
        public string color;

        /// <summary>
        /// Displays information about the car.
        /// </summary>
        public void DisplayInfo()
        {
            Console.WriteLine($"Car: {year} {brand} {model} ({color})");
        }

        /// <summary>
        /// Simulates starting the car's engine.
        /// </summary>
        public void StartEngine()
        {
            Console.WriteLine($"The {brand} {model}'s engine is starting... Vroom!");
        }
    }

    // ====================================
    // Person class for Constructor demo
    // ====================================
    
    /// <summary>
    /// Represents a person with constructors.
    /// Demonstrates default and parameterized constructors.
    /// </summary>
    class Person
    {
        public string Name;
        public int Age;

        // Default constructor (no parameters)
        public Person()
        {
            Name = "Unknown";
            Age = 0;
            Console.WriteLine("Default constructor called");
        }

        // Parameterized constructor
        public Person(string name, int age)
        {
            Name = name;
            Age = age;
            Console.WriteLine($"Parameterized constructor called for {name}");
        }

        public void Introduce()
        {
            Console.WriteLine($"Hi, I'm {Name} and I'm {Age} years old.");
        }
    }

    // ====================================
    // BankAccount class for Access Modifiers demo
    // ====================================
    
    /// <summary>
    /// Represents a bank account with private fields and public methods.
    /// Demonstrates encapsulation with access modifiers.
    /// </summary>
    class BankAccount
    {
        // Private fields (cannot be accessed directly from outside)
        private string accountHolder;
        private double balance;

        // Public constructor
        public BankAccount(string holder, double initialBalance)
        {
            accountHolder = holder;
            balance = initialBalance;
            Console.WriteLine($"Account created for {holder} with balance ${balance}");
        }

        // Public methods to access private fields
        public string GetAccountHolder()
        {
            return accountHolder;
        }

        public double GetBalance()
        {
            return balance;
        }

        public void Deposit(double amount)
        {
            if (amount > 0)
            {
                balance += amount;
                Console.WriteLine($"Deposited ${amount}. New balance: ${balance}");
            }
        }

        public void Withdraw(double amount)
        {
            if (amount > 0 && amount <= balance)
            {
                balance -= amount;
                Console.WriteLine($"Withdrew ${amount}. New balance: ${balance}");
            }
            else
            {
                Console.WriteLine("Insufficient funds or invalid amount.");
            }
        }
    }

    // ====================================
    // Student class for Properties demo
    // ====================================
    
    /// <summary>
    /// Represents a student with properties.
    /// Demonstrates get and set accessors for controlled field access.
    /// </summary>
    class Student
    {
        // Private field
        private string name;
        private int age;
        private string grade;

        // Property with validation in setter
        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        // Property with validation
        public int Age
        {
            get { return age; }
            set
            {
                if (value > 0 && value < 120)
                {
                    age = value;
                }
                else
                {
                    Console.WriteLine("Invalid age! Age must be between 1 and 119.");
                }
            }
        }

        // Property with full accessor
        public string Grade
        {
            get { return grade; }
            set { grade = value; }
        }

        // Auto-implemented property (C# creates backing field automatically)
        public string StudentId { get; set; }
    }

    // ====================================
    // Animal inheritance hierarchy for Inheritance and Polymorphism demos
    // ====================================
    
    /// <summary>
    /// Base class representing a generic animal.
    /// Parent class for Dog, Cat, and Bird.
    /// </summary>
    class Animal
    {
        public string Name;
        public int Age;

        public Animal(string name, int age)
        {
            Name = name;
            Age = age;
        }

        // Method that can be inherited
        public void Eat()
        {
            Console.WriteLine($"{Name} is eating.");
        }

        public void Sleep()
        {
            Console.WriteLine($"{Name} is sleeping.");
        }

        // Virtual method that can be overridden
        public virtual void MakeSound()
        {
            Console.WriteLine($"{Name} makes a sound.");
        }
    }

    /// <summary>
    /// Dog class inherits from Animal.
    /// Demonstrates inheritance and method overriding.
    /// </summary>
    class Dog : Animal
    {
        // Constructor calls base class constructor
        public Dog(string name, int age) : base(name, age)
        {
        }

        // Dog-specific method
        public void Bark()
        {
            Console.WriteLine($"{Name} barks: Woof! Woof!");
        }

        // Override parent method
        public override void MakeSound()
        {
            Console.WriteLine($"{Name} barks: Woof! Woof!");
        }
    }

    /// <summary>
    /// Cat class inherits from Animal.
    /// </summary>
    class Cat : Animal
    {
        public Cat(string name, int age) : base(name, age)
        {
        }

        public void Meow()
        {
            Console.WriteLine($"{Name} meows: Meow! Meow!");
        }

        public override void MakeSound()
        {
            Console.WriteLine($"{Name} meows: Meow! Meow!");
        }
    }

    /// <summary>
    /// Bird class inherits from Animal.
    /// </summary>
    class Bird : Animal
    {
        public Bird(string name, int age) : base(name, age)
        {
        }

        public void Chirp()
        {
            Console.WriteLine($"{Name} chirps: Tweet! Tweet!");
        }

        public override void MakeSound()
        {
            Console.WriteLine($"{Name} chirps: Tweet! Tweet!");
        }
    }

    // ====================================
    // Abstract class for Abstraction demo
    // ====================================
    
    /// <summary>
    /// Abstract base class for shapes.
    /// Cannot be instantiated, must be inherited.
    /// </summary>
    abstract class Shape
    {
        // Abstract method (no implementation in base class)
        public abstract double CalculateArea();

        // Concrete method (has implementation)
        public void DisplayShapeType()
        {
            Console.WriteLine($"This is a {this.GetType().Name}");
        }
    }

    /// <summary>
    /// Circle shape inheriting from abstract Shape.
    /// </summary>
    class Circle : Shape
    {
        private double radius;

        public Circle(double radius)
        {
            this.radius = radius;
        }

        // Must implement abstract method
        public override double CalculateArea()
        {
            return Math.PI * radius * radius;
        }
    }

    /// <summary>
    /// Rectangle shape inheriting from abstract Shape.
    /// </summary>
    class Rectangle : Shape
    {
        private double width;
        private double height;

        public Rectangle(double width, double height)
        {
            this.width = width;
            this.height = height;
        }

        public override double CalculateArea()
        {
            return width * height;
        }
    }

    /// <summary>
    /// Triangle shape inheriting from abstract Shape.
    /// </summary>
    class Triangle : Shape
    {
        private double baseLength;
        private double height;

        public Triangle(double baseLength, double height)
        {
            this.baseLength = baseLength;
            this.height = height;
        }

        public override double CalculateArea()
        {
            return 0.5 * baseLength * height;
        }
    }

    // ====================================
    // Interfaces for Interface demo
    // ====================================
    
    /// <summary>
    /// Interface defining playable behavior.
    /// Any class implementing this must have Play and Stop methods.
    /// </summary>
    interface IPlayable
    {
        void Play();
        void Stop();
    }

    /// <summary>
    /// Guitar implements IPlayable interface.
    /// </summary>
    class Guitar : IPlayable
    {
        public void Play()
        {
            Console.WriteLine("Playing guitar: Strum... strum...");
        }

        public void Stop()
        {
            Console.WriteLine("Guitar stopped.");
        }
    }

    /// <summary>
    /// Piano implements IPlayable interface.
    /// </summary>
    class Piano : IPlayable
    {
        public void Play()
        {
            Console.WriteLine("Playing piano: ♪ ♫ ♪ ♫");
        }

        public void Stop()
        {
            Console.WriteLine("Piano stopped.");
        }
    }

    /// <summary>
    /// Drums implements IPlayable interface.
    /// </summary>
    class Drums : IPlayable
    {
        public void Play()
        {
            Console.WriteLine("Playing drums: Boom! Crash! Boom!");
        }

        public void Stop()
        {
            Console.WriteLine("Drums stopped.");
        }
    }

    /// <summary>
    /// Interface for devices that can make calls.
    /// </summary>
    interface ICallable
    {
        void MakeCall(string number);
    }

    /// <summary>
    /// Interface for internet connectivity.
    /// </summary>
    interface IConnectable
    {
        void Connect();
        void Disconnect();
    }

    /// <summary>
    /// SmartPhone implements multiple interfaces.
    /// Demonstrates multiple interface implementation.
    /// </summary>
    class SmartPhone : ICallable, IConnectable
    {
        public void MakeCall(string number)
        {
            Console.WriteLine($"Calling {number}...");
        }

        public void Connect()
        {
            Console.WriteLine("Connected to internet.");
        }

        public void Disconnect()
        {
            Console.WriteLine("Disconnected from internet.");
        }
    }

    // ====================================
    // Enums for Enum demo
    // ====================================
    
    /// <summary>
    /// Enum representing days of the week.
    /// </summary>
    enum Day
    {
        Sunday,    // 0
        Monday,    // 1
        Tuesday,   // 2
        Wednesday, // 3
        Thursday,  // 4
        Friday,    // 5
        Saturday   // 6
    }

    /// <summary>
    /// Enum with explicit values representing order status.
    /// </summary>
    enum OrderStatus
    {
        Pending = 1,
        Confirmed = 2,
        Shipped = 3,
        Delivered = 4,
        Cancelled = 5
    }

    #endregion
}
