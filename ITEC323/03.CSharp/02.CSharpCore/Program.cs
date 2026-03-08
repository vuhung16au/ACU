using System;

namespace CSharpCore
{
    /// <summary>
    /// Demonstrates fundamental C# programming concepts for beginners.
    /// This program covers variables, data types, operators, control flow, loops, and arrays.
    /// </summary>
    class Program
    {
        /// <summary>
        /// The main entry point of the application.
        /// Demonstrates core C# concepts step by step.
        /// </summary>
        /// <param name="args">Command line arguments</param>
        static void Main(string[] args)
        {
            // Print a welcome message
            Console.WriteLine("==============================================");
            Console.WriteLine("Welcome to C# Core Concepts Demo!");
            Console.WriteLine("==============================================");
            Console.WriteLine(); // Empty line for readability

            // Demonstrate each core concept
            DemonstrateOutput();
            DemonstrateComments();
            DemonstrateVariables();
            DemonstrateDataTypes();
            DemonstrateTypeCasting();
            DemonstrateOperators();
            DemonstrateMath();
            DemonstrateStrings();
            DemonstrateBooleans();
            DemonstrateIfElse();
            DemonstrateSwitch();
            DemonstrateWhileLoop();
            DemonstrateForLoop();
            DemonstrateBreakContinue();
            DemonstrateArrays();

            // Optional: User input demonstration (commented out to avoid blocking in automated runs)
            // DemonstrateUserInput();

            // End of program
            Console.WriteLine();
            Console.WriteLine("==============================================");
            Console.WriteLine("End of C# Core Concepts Demo!");
            Console.WriteLine("==============================================");
        }

        /// <summary>
        /// Demonstrates different ways to output text to the console.
        /// WriteLine adds a new line, Write does not.
        /// </summary>
        static void DemonstrateOutput()
        {
            Console.WriteLine("\n--- C# Output ---");
            
            // WriteLine adds a newline at the end
            Console.WriteLine("This uses Console.WriteLine()");
            Console.WriteLine("Each WriteLine starts on a new line");
            
            // Write does not add a newline
            Console.Write("This uses Console.Write() ");
            Console.Write("and continues on the same line.");
            Console.WriteLine(); // Add newline after Write statements
        }

        /// <summary>
        /// Demonstrates single-line and multi-line comments in C#.
        /// </summary>
        static void DemonstrateComments()
        {
            Console.WriteLine("\n--- C# Comments ---");
            
            // This is a single-line comment
            // Single-line comments start with //
            
            /*
             * This is a multi-line comment
             * Multi-line comments start with slash-asterisk and end with asterisk-slash
             * They can span multiple lines
             */
            
            Console.WriteLine("Comments help explain code without affecting execution");
            Console.WriteLine("Single-line: // comment");
            Console.WriteLine("Multi-line: /* comment */");
        }

        /// <summary>
        /// Demonstrates how to declare and use variables in C#.
        /// Variables are containers for storing data values.
        /// </summary>
        static void DemonstrateVariables()
        {
            Console.WriteLine("\n--- C# Variables ---");
            
            // Declare variables
            int age = 25;
            string name = "Alice";
            double price = 19.99;
            
            // Display variable values
            Console.WriteLine($"Name: {name}");
            Console.WriteLine($"Age: {age}");
            Console.WriteLine($"Price: ${price}");
            
            // Variables can be reassigned
            age = 26;
            Console.WriteLine($"Updated Age: {age}");
        }

        /// <summary>
        /// Demonstrates the main data types in C#.
        /// Each type stores different kinds of data.
        /// </summary>
        static void DemonstrateDataTypes()
        {
            Console.WriteLine("\n--- C# Data Types ---");
            
            // Integer (whole numbers)
            int myNum = 5;
            Console.WriteLine($"int: {myNum} (whole number)");
            
            // Double (floating point numbers with decimals)
            double myDoubleNum = 5.99;
            Console.WriteLine($"double: {myDoubleNum} (decimal number)");
            
            // Character (single character in single quotes)
            char myLetter = 'D';
            Console.WriteLine($"char: {myLetter} (single character)");
            
            // Boolean (true or false)
            bool myBool = true;
            Console.WriteLine($"bool: {myBool} (true or false)");
            
            // String (text in double quotes)
            string myText = "Hello";
            Console.WriteLine($"string: {myText} (text)");
            
            // Additional numeric types
            long bigNumber = 9999999999L;
            float smallDecimal = 3.14F;
            Console.WriteLine($"long: {bigNumber} (large whole number)");
            Console.WriteLine($"float: {smallDecimal} (smaller decimal)");
        }

        /// <summary>
        /// Demonstrates type casting - converting one data type to another.
        /// Implicit casting is automatic, explicit casting is manual.
        /// </summary>
        static void DemonstrateTypeCasting()
        {
            Console.WriteLine("\n--- C# Type Casting ---");
            
            // Implicit Casting (automatic) - smaller to larger type
            // char -> int -> long -> float -> double
            int myInt = 9;
            double myDouble = myInt; // Automatic casting: int to double
            Console.WriteLine($"Implicit Casting: int {myInt} to double {myDouble}");
            
            // Explicit Casting (manual) - larger to smaller type
            // double -> float -> long -> int -> char
            double myDoubleValue = 9.78;
            int myIntValue = (int)myDoubleValue; // Manual casting: double to int
            Console.WriteLine($"Explicit Casting: double {myDoubleValue} to int {myIntValue}");
            
            // Type conversion methods
            int num = 10;
            string numString = num.ToString(); // Convert int to string
            Console.WriteLine($"Convert int to string: '{numString}'");
            
            string numberText = "123";
            int convertedNum = Convert.ToInt32(numberText); // Convert string to int
            Console.WriteLine($"Convert string to int: {convertedNum}");
        }

        /// <summary>
        /// Demonstrates user input using Console.ReadLine().
        /// Note: This method is commented out in Main() to avoid blocking automated tests.
        /// </summary>
        static void DemonstrateUserInput()
        {
            Console.WriteLine("\n--- C# User Input ---");
            
            Console.Write("Enter your name: ");
            string? userName = Console.ReadLine();
            Console.WriteLine($"Hello, {userName}!");
            
            Console.Write("Enter your age: ");
            string? ageInput = Console.ReadLine();
            
            // Convert string input to integer
            if (int.TryParse(ageInput, out int userAge))
            {
                Console.WriteLine($"You are {userAge} years old.");
            }
            else
            {
                Console.WriteLine("Invalid age entered.");
            }
        }

        /// <summary>
        /// Demonstrates arithmetic, assignment, comparison, and logical operators.
        /// </summary>
        static void DemonstrateOperators()
        {
            Console.WriteLine("\n--- C# Operators ---");
            
            // Arithmetic Operators
            int a = 10;
            int b = 3;
            Console.WriteLine("Arithmetic Operators:");
            Console.WriteLine($"{a} + {b} = {a + b} (addition)");
            Console.WriteLine($"{a} - {b} = {a - b} (subtraction)");
            Console.WriteLine($"{a} * {b} = {a * b} (multiplication)");
            Console.WriteLine($"{a} / {b} = {a / b} (division)");
            Console.WriteLine($"{a} % {b} = {a % b} (modulus/remainder)");
            
            // Increment and Decrement
            int x = 5;
            x++; // Increment by 1
            Console.WriteLine($"After x++: {x}");
            x--; // Decrement by 1
            Console.WriteLine($"After x--: {x}");
            
            // Assignment Operators
            int c = 10;
            c += 5; // Same as c = c + 5
            Console.WriteLine($"After c += 5: {c}");
            
            // Comparison Operators
            Console.WriteLine("\nComparison Operators:");
            Console.WriteLine($"10 == 10: {10 == 10} (equal to)");
            Console.WriteLine($"10 != 5: {10 != 5} (not equal to)");
            Console.WriteLine($"10 > 5: {10 > 5} (greater than)");
            Console.WriteLine($"10 < 5: {10 < 5} (less than)");
            Console.WriteLine($"10 >= 10: {10 >= 10} (greater than or equal)");
            Console.WriteLine($"5 <= 10: {5 <= 10} (less than or equal)");
            
            // Logical Operators
            Console.WriteLine("\nLogical Operators:");
            bool isTrue = true;
            bool isFalse = false;
            Console.WriteLine($"true && false: {isTrue && isFalse} (AND)");
            Console.WriteLine($"true || false: {isTrue || isFalse} (OR)");
            Console.WriteLine($"!true: {!isTrue} (NOT)");
        }

        /// <summary>
        /// Demonstrates the Math class for mathematical operations.
        /// </summary>
        static void DemonstrateMath()
        {
            Console.WriteLine("\n--- C# Math ---");
            
            // Math.Max - returns the larger of two numbers
            int maxValue = Math.Max(10, 20);
            Console.WriteLine($"Math.Max(10, 20) = {maxValue}");
            
            // Math.Min - returns the smaller of two numbers
            int minValue = Math.Min(10, 20);
            Console.WriteLine($"Math.Min(10, 20) = {minValue}");
            
            // Math.Sqrt - returns the square root of a number
            double sqrtValue = Math.Sqrt(64);
            Console.WriteLine($"Math.Sqrt(64) = {sqrtValue}");
            
            // Math.Abs - returns the absolute (positive) value
            int absValue = Math.Abs(-15);
            Console.WriteLine($"Math.Abs(-15) = {absValue}");
            
            // Math.Round - rounds a decimal number
            double roundValue = Math.Round(4.7);
            Console.WriteLine($"Math.Round(4.7) = {roundValue}");
            
            // Math.Pow - returns base to the power of exponent
            double powValue = Math.Pow(2, 3); // 2^3 = 8
            Console.WriteLine($"Math.Pow(2, 3) = {powValue}");
        }

        /// <summary>
        /// Demonstrates string operations including concatenation, interpolation,
        /// accessing characters, and special characters.
        /// </summary>
        static void DemonstrateStrings()
        {
            Console.WriteLine("\n--- C# Strings ---");
            
            // String concatenation
            string firstName = "John";
            string lastName = "Doe";
            string fullName = firstName + " " + lastName;
            Console.WriteLine($"Concatenation: {fullName}");
            
            // String interpolation (preferred method)
            string greeting = $"Hello, {firstName} {lastName}!";
            Console.WriteLine($"Interpolation: {greeting}");
            
            // String.Concat method
            string concatResult = string.Concat(firstName, " ", lastName);
            Console.WriteLine($"String.Concat: {concatResult}");
            
            // Accessing string characters (strings are arrays of characters)
            string text = "Hello";
            char firstChar = text[0]; // Index starts at 0
            Console.WriteLine($"First character of '{text}': {firstChar}");
            
            // String length
            Console.WriteLine($"Length of '{text}': {text.Length}");
            
            // String methods
            Console.WriteLine($"Uppercase: {text.ToUpper()}");
            Console.WriteLine($"Lowercase: {text.ToLower()}");
            
            // Special characters (escape sequences)
            Console.WriteLine("\nSpecial Characters:");
            Console.WriteLine("New line: Line 1\\nLine 2");
            Console.WriteLine("Tab: Column1\\tColumn2");
            Console.WriteLine("Backslash: C:\\\\Users\\\\Name");
            Console.WriteLine("Quote: He said \\\"Hello\\\"");
        }

        /// <summary>
        /// Demonstrates boolean values and boolean expressions.
        /// Booleans can only have two values: true or false.
        /// </summary>
        static void DemonstrateBooleans()
        {
            Console.WriteLine("\n--- C# Booleans ---");
            
            // Boolean variables
            bool isStudent = true;
            bool isWorking = false;
            
            Console.WriteLine($"Is student? {isStudent}");
            Console.WriteLine($"Is working? {isWorking}");
            
            // Boolean expressions (results in true or false)
            int age = 18;
            bool isAdult = age >= 18;
            Console.WriteLine($"Age {age} is adult? {isAdult}");
            
            // Using booleans in conditions
            int score = 85;
            bool passed = score >= 60;
            Console.WriteLine($"Score {score}, Passed? {passed}");
        }

        /// <summary>
        /// Demonstrates if, else if, and else statements for conditional logic.
        /// </summary>
        static void DemonstrateIfElse()
        {
            Console.WriteLine("\n--- C# If...Else ---");
            
            int temperature = 25;
            
            // Simple if statement
            if (temperature > 30)
            {
                Console.WriteLine("It's hot outside!");
            }
            else if (temperature > 20)
            {
                Console.WriteLine("It's nice weather!");
            }
            else if (temperature > 10)
            {
                Console.WriteLine("It's cool outside.");
            }
            else
            {
                Console.WriteLine("It's cold outside!");
            }
            
            // Ternary operator (shorthand if-else)
            int number = 10;
            string result = (number % 2 == 0) ? "even" : "odd";
            Console.WriteLine($"{number} is {result}");
            
            // Nested if statements
            int age = 20;
            bool hasLicense = true;
            
            if (age >= 18)
            {
                if (hasLicense)
                {
                    Console.WriteLine("You can drive!");
                }
                else
                {
                    Console.WriteLine("You need a license to drive.");
                }
            }
            else
            {
                Console.WriteLine("You're too young to drive.");
            }
        }

        /// <summary>
        /// Demonstrates the switch statement for selecting one of many code blocks to execute.
        /// </summary>
        static void DemonstrateSwitch()
        {
            Console.WriteLine("\n--- C# Switch ---");
            
            int dayNumber = 3;
            string dayName;
            
            // Switch statement
            switch (dayNumber)
            {
                case 1:
                    dayName = "Monday";
                    break;
                case 2:
                    dayName = "Tuesday";
                    break;
                case 3:
                    dayName = "Wednesday";
                    break;
                case 4:
                    dayName = "Thursday";
                    break;
                case 5:
                    dayName = "Friday";
                    break;
                case 6:
                    dayName = "Saturday";
                    break;
                case 7:
                    dayName = "Sunday";
                    break;
                default:
                    dayName = "Invalid day";
                    break;
            }
            
            Console.WriteLine($"Day {dayNumber} is {dayName}");
            
            // Switch with string
            string fruit = "apple";
            
            switch (fruit)
            {
                case "apple":
                    Console.WriteLine("Apples are red or green");
                    break;
                case "banana":
                    Console.WriteLine("Bananas are yellow");
                    break;
                case "orange":
                    Console.WriteLine("Oranges are orange");
                    break;
                default:
                    Console.WriteLine("Unknown fruit");
                    break;
            }
        }

        /// <summary>
        /// Demonstrates while and do-while loops for repeating code.
        /// </summary>
        static void DemonstrateWhileLoop()
        {
            Console.WriteLine("\n--- C# While Loop ---");
            
            // While loop - checks condition before executing
            Console.WriteLine("While loop (counting 1 to 5):");
            int i = 1;
            while (i <= 5)
            {
                Console.WriteLine($"  Count: {i}");
                i++;
            }
            
            // Do-while loop - executes at least once, then checks condition
            Console.WriteLine("Do-While loop (counting 1 to 3):");
            int j = 1;
            do
            {
                Console.WriteLine($"  Count: {j}");
                j++;
            } while (j <= 3);
        }

        /// <summary>
        /// Demonstrates for loops for repeating code a specific number of times.
        /// </summary>
        static void DemonstrateForLoop()
        {
            Console.WriteLine("\n--- C# For Loop ---");
            
            // Basic for loop
            Console.WriteLine("For loop (0 to 4):");
            for (int i = 0; i < 5; i++)
            {
                Console.WriteLine($"  Iteration: {i}");
            }
            
            // For loop with different increment
            Console.WriteLine("Even numbers (0 to 10):");
            for (int i = 0; i <= 10; i += 2)
            {
                Console.WriteLine($"  {i}");
            }
            
            // Countdown
            Console.WriteLine("Countdown (5 to 1):");
            for (int i = 5; i >= 1; i--)
            {
                Console.WriteLine($"  {i}");
            }
            Console.WriteLine("  Blast off!");
        }

        /// <summary>
        /// Demonstrates break and continue statements in loops.
        /// break exits the loop, continue skips to the next iteration.
        /// </summary>
        static void DemonstrateBreakContinue()
        {
            Console.WriteLine("\n--- C# Break and Continue ---");
            
            // Break - exits the loop entirely
            Console.WriteLine("Break example (stop at 5):");
            for (int i = 1; i <= 10; i++)
            {
                if (i == 5)
                {
                    Console.WriteLine("  Breaking at 5");
                    break;
                }
                Console.WriteLine($"  {i}");
            }
            
            // Continue - skips the current iteration
            Console.WriteLine("Continue example (skip even numbers):");
            for (int i = 1; i <= 10; i++)
            {
                if (i % 2 == 0)
                {
                    continue; // Skip even numbers
                }
                Console.WriteLine($"  {i}");
            }
        }

        /// <summary>
        /// Demonstrates arrays for storing multiple values in a single variable.
        /// </summary>
        static void DemonstrateArrays()
        {
            Console.WriteLine("\n--- C# Arrays ---");
            
            // Declare and initialize an array
            string[] fruits = { "Apple", "Banana", "Orange", "Mango" };
            
            // Access array elements (index starts at 0)
            Console.WriteLine($"First fruit: {fruits[0]}");
            Console.WriteLine($"Second fruit: {fruits[1]}");
            
            // Get array length
            Console.WriteLine($"Number of fruits: {fruits.Length}");
            
            // Loop through an array
            Console.WriteLine("All fruits:");
            for (int i = 0; i < fruits.Length; i++)
            {
                Console.WriteLine($"  {i}: {fruits[i]}");
            }
            
            // Foreach loop (simpler way to iterate)
            Console.WriteLine("Using foreach:");
            foreach (string fruit in fruits)
            {
                Console.WriteLine($"  - {fruit}");
            }
            
            // Numeric array
            int[] numbers = { 10, 20, 30, 40, 50 };
            
            // Calculate sum of array elements
            int sum = 0;
            foreach (int num in numbers)
            {
                sum += num;
            }
            Console.WriteLine($"Sum of numbers: {sum}");
            
            // Multi-dimensional array
            int[,] matrix = { { 1, 2, 3 }, { 4, 5, 6 } };
            Console.WriteLine($"Matrix [0,0]: {matrix[0, 0]}");
            Console.WriteLine($"Matrix [1,2]: {matrix[1, 2]}");
        }
    }
}
