import numpy as np

# 1. NumPy Arrays: Creating and Manipulating Arrays
print("\n--- Creating and Manipulating Arrays ---")
arr = np.array([1, 2, 3, 4, 5])
print("Basic array:", arr)

# Creating a 2D array
arr_2d = np.array([[1, 2], [3, 4]], dtype=np.float64)
print("2D array:\n", arr_2d)

# Reshaping an array
reshaped = arr.reshape(5, 1)
print("Reshaped array:\n", reshaped)

# Using np.arange
arange_arr = np.arange(0, 10, 2)
print("Arange array:", arange_arr)

# Using np.linspace
linspace_arr = np.linspace(0, 1, 5)
print("Linspace array:", linspace_arr)

# Using np.zeros and np.ones
zeros = np.zeros((2, 3))
ones = np.ones((3, 3))
print("Zeros:\n", zeros)
print("Ones:\n", ones)

# 2. NumPy Indexing & Slicing
print("\n--- Indexing & Slicing ---")
# Single element indexing
print("Element at index 2:", arr[2])

# Slicing
sliced = arr[1:4]
print("Sliced array:", sliced)

# 2D indexing
print("Element at row 0, column 1:", arr_2d[0, 1])

# Boolean indexing
bool_index = arr > 3
print("Boolean index:", bool_index)
filtered = arr[bool_index]
print("Filtered array:", filtered)

# Fancy indexing
indices = [0, 2, 4]
fancy_indexed = arr[indices]
print("Fancy indexed array:", fancy_indexed)

# 3. NumPy Array Attributes & Operations
print("\n--- Array Attributes & Operations ---")
# Shape, size, dimensions
print("Shape:", arr.shape)
print("Size:", arr.size)
print("Dimensions:", arr.ndim)

# Item size and data type
print("Item size:", arr.itemsize)
print("Data type:", arr.dtype)

# Basic operations
addition = arr + 2
multiplication = arr * 3
exponentiation = arr ** 2
print("Addition:", addition)
print("Multiplication:", multiplication)
print("Exponentiation:", exponentiation)

# 4. NumPy Advanced Array Operations
print("\n--- Advanced Array Operations ---")
# Broadcasting
a = np.array([[1, 2], [3, 4]])
b = np.array([5, 6])
broadcasted = a + b
print("Broadcasting result:\n", broadcasted)

# Vectorized operations
vectorized = np.vectorize(lambda x: x**2)
result = vectorized(arr)
print("Vectorized operation result:", result)

# 5. NumPy Sorting and Advanced Manipulation
print("\n--- Sorting and Advanced Manipulation ---")
# Sorting an array
sorted_arr = np.sort(arr)
print("Sorted array:", sorted_arr)

# Using where
condition = arr > 3
result_where = np.where(condition, arr * 2, arr)
print("Where result:", result_where)

# Using reshape and transpose
transposed = arr_2d.T
print("Transposed array:\n", transposed)

# 6. NumPy Handling Missing Data
print("\n--- Handling Missing Data ---")
# Creating an array with NaNs
missing_data = np.array([1, 2, np.nan, 4, 5])
print("Array with NaNs:", missing_data)

# Checking for NaNs
has_nan = np.isnan(missing_data)
print("NaN check:", has_nan)

# Handling NaNs in calculations
mean_without_nan = np.nanmean(missing_data)
print("Mean without NaN:", mean_without_nan)

# 7. NumPy Performance Optimization
print("\n--- Performance Optimization ---")
# Vectorized operations are faster than loops
# Example: Adding two arrays element-wise
a = np.array([1, 2, 3])
b = np.array([4, 5, 6])
vectorized_addition = a + b
print("Vectorized addition:", vectorized_addition)

# Avoiding loops using broadcasting
matrix = np.random.rand(3, 3)
vector = np.array([1, 2, 3])
broadcasted_result = matrix + vector[:, np.newaxis]
print("Broadcasting result:\n", broadcasted_result)

# 8. NumPy Linear Algebra
print("\n--- Linear Algebra ---")
# Matrix multiplication
a = np.array([[1, 2], [3, 4]])
b = np.array([[5, 6], [7, 8]])
dot_product = np.dot(a, b)
print("Dot product:\n", dot_product)

# Solving a system of linear equations
coefficients = np.array([[3, 1], [1, 2]])
constants = np.array([9, 8])
solution = np.linalg.solve(coefficients, constants)
print("Solution:", solution)

# Eigenvalues and eigenvectors
eigenvalues, eigenvectors = np.linalg.eig(a)
print("Eigenvalues:", eigenvalues)
print("Eigenvectors:\n", eigenvectors)

# 9. NumPy Element-wise Matrix Operations
print("\n--- Element-wise Matrix Operations ---")
# Element-wise multiplication
element_mult = np.multiply(a, b)
print("Element-wise multiplication:\n", element_mult)

# Element-wise division
element_div = np.divide(a, b)
print("Element-wise division:\n", element_div)

# Element-wise square root
sqrt_arr = np.sqrt(a)
print("Square root:\n", sqrt_arr)

# 10. NumPy Set Operations
print("\n--- Set Operations ---")
# Unique elements
unique_elements = np.unique(arr)
print("Unique elements:", unique_elements)

# Checking membership
membership = np.in1d([1, 3, 5], arr)
print("Membership check:", membership)

# Intersection of arrays
arr1 = np.array([1, 2, 3])
arr2 = np.array([3, 4, 5])
intersection = np.intersect1d(arr1, arr2)
print("Intersection:", intersection)

# 11. NumPy Random Number Generation
print("\n--- Random Number Generation ---")
# Generating random integers
random_integers = np.random.randint(1, 10, size=5)
print("Random integers:", random_integers)

# Generating random floats
random_floats = np.random.rand(3, 3)
print("Random floats:\n", random_floats)

# Using seed for reproducibility
np.random.seed(42)
random_with_seed = np.random.rand(3)
print("Random with seed:", random_with_seed)

# 12. NumPy File Input & Output
print("\n--- File Input & Output ---")
# Saving an array to a text file
np.savetxt('array.txt', arr, delimiter=',')
# Loading the array back
loaded_arr = np.loadtxt('array.txt', delimiter=',')
print("Loaded array:", loaded_arr)

# Saving and loading with np.savez
np.savez('arrays.npz', a=a, b=b)
# Loading the arrays back
loaded = np.load('arrays.npz')
print("Loaded a:\n", loaded['a'])
print("Loaded b:\n", loaded['b'])

# 13. NumPy Mathematical Functions
print("\n--- Mathematical Functions ---")
# Trigonometric functions
sin_values = np.sin(np.array([0, np.pi/2, np.pi]))
print("Sine values:", sin_values)

# Exponential and logarithmic functions
exp_values = np.exp(np.array([0, 1, 2]))
log_values = np.log(exp_values)
print("Exponential values:", exp_values)
print("Logarithmic values:", log_values)

# 14. NumPy Statistics
print("\n--- Statistics ---")
# Mean, median, standard deviation
mean = np.mean(arr)
median = np.median(arr)
std_dev = np.std(arr)
print("Mean:", mean)
print("Median:", median)
print("Standard deviation:", std_dev)

# Min, max, and percentiles
min_val = np.min(arr)
max_val = np.max(arr)
percentile_50 = np.percentile(arr, 50)
print("Min:", min_val)
print("Max:", max_val)
print("50th percentile:", percentile_50)

# 15. NumPy Datetime
print("\n--- Datetime ---")
# Creating datetime64 array
dates = np.array(['2023-10-01', '2023-10-02', '2023-10-03'], dtype='datetime64')
print("Datetime array:", dates)

# Current date and time
now = np.datetime64('now')
print("Current datetime:", now)

# Calculating differences between dates
delta = np.datetime64('2023-10-05') - now
print("Time difference:", delta)

# Extracting components
year = dates.astype('datetime64[Y]')
day_of_week = np.datetime64(dates[0], 'W')
print("Year component:", year)
print("Day of week:", day_of_week)

# 16. NumPy Boolean Arrays
print("\n--- Boolean Arrays ---")
# Creating a boolean array
bool_arr = np.array([True, False, True], dtype=bool)
print("Boolean array:", bool_arr)

# Logical operations
logical_and = np.logical_and(arr > 1, arr < 5)
print("Logical AND:", logical_and)

# Using boolean arrays for indexing
filtered = arr[logical_and]
print("Filtered array:", filtered)

# 17. NumPy Structured Arrays
print("\n--- Structured Arrays ---")
# Creating a structured array
dtype = [('name', 'U10'), ('age', int), ('weight', float)]
data = np.array([('Alice', 30, 65.2), ('Bob', 25, 70.5)], dtype=dtype)
print("Structured array:\n", data)

# Accessing fields
names = data['name']
ages = data['age']
print("Names:", names)
print("Ages:", ages)

# Modifying fields
data['age'] = [31, 26]
print("Updated ages:", data['age'])

# 18. NumPy Broadcasting
print("\n--- Broadcasting ---")
a = np.array([[1, 2], [3, 4]])
b = np.array([5, 6])
broadcasted_result = a + b
print("Broadcasting result:\n", broadcasted_result)

# Broadcasting with different shapes
c = np.array([[1, 2], [3, 4]])
d = np.array([5])
broadcasted_result_2 = c + d
print("Broadcasting with scalar:\n", broadcasted_result_2)

# 19. NumPy Vectorization
print("\n--- Vectorization ---")
def square(x):
    return x ** 2

vectorized_square = np.vectorize(square)
result = vectorized_square(arr)
print("Vectorized square:", result)

# Using universal functions (ufuncs)
result_ufunc = np.square(arr)
print("Ufunc square:", result_ufunc)

# 20. NumPy Memory Management
print("\n--- Memory Management ---")
# Checking memory usage
memory_usage = arr.nbytes
print("Memory usage:", memory_usage, "bytes")

# Using views to save memory
view = arr.view()
print("View of array:", view)

# Copying arrays
copy = arr.copy()
print("Copy of array:", copy)

# 21. NumPy Matrix Operations
print("\n--- Matrix Operations ---")
matrix_a = np.array([[1, 2], [3, 4]])
matrix_b = np.array([[5, 6], [7, 8]])

# Matrix multiplication
matrix_product = np.matmul(matrix_a, matrix_b)
print("Matrix product:\n", matrix_product)

# Matrix inverse
matrix_inv = np.linalg.inv(matrix_a)
print("Matrix inverse:\n", matrix_inv)

# Matrix determinant
matrix_det = np.linalg.det(matrix_a)
print("Matrix determinant:", matrix_det)

# 22. NumPy Polynomial Operations
print("\n--- Polynomial Operations ---")
coefficients = np.array([1, -3, 2])  # Represents x^2 - 3x + 2
roots = np.roots(coefficients)
print("Roots of polynomial:", roots)

# Evaluating a polynomial
x = 2
polynomial_value = np.polyval(coefficients, x)
print("Polynomial value at x=2:", polynomial_value)

# Fitting a polynomial to data
x_data = np.array([1, 2, 3])
y_data = np.array([2, 4, 6])
fitted_coeffs = np.polyfit(x_data, y_data, 1)
print("Fitted coefficients:", fitted_coeffs)

# 23. NumPy Fourier Transform
print("\n--- Fourier Transform ---")
signal = np.array([1, 2, 3, 4])
fourier_transform = np.fft.fft(signal)
print("Fourier transform:", fourier_transform)

# Inverse Fourier transform
inverse_transform = np.fft.ifft(fourier_transform)
print("Inverse Fourier transform:", inverse_transform)

# 24. NumPy Image Processing
print("\n--- Image Processing ---")
# Creating a simple image array
image = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9]], dtype=np.uint8)
print("Image array:\n", image)

# Resizing the image
resized_image = np.resize(image, (6, 6))
print("Resized image:\n", resized_image)


# 25. NumPy Performance Tips
print("\n--- Performance Tips ---")
# Avoiding loops using vectorized operations
a = np.random.rand(1000)
b = np.random.rand(1000)
result_vectorized = a + b
print("Vectorized result:", result_vectorized)

# Using in-place operations
a += b  # In-place addition
print("In-place result:", a)

# Using efficient data types
arr_int8 = np.array([1, 2, 3], dtype=np.int8)
print("Int8 array:", arr_int8)

# 26. NumPy Error Handling
print("\n--- Error Handling ---")
try:
    # Trying to divide by zero
    result = np.divide(1, 0)
except FloatingPointError as e:
    print("Floating point error:", e)

# Setting error handling
np.seterr(divide='ignore')
result_ignore = np.divide(1, 0)
print("Result with ignored division error:", result_ignore)

# 27. NumPy Profiling
print("\n--- Profiling ---")
import timeit

# Timing a NumPy operation
setup = "import numpy as np; arr = np.random.rand(1000)"
stmt = "np.sort(arr)"
execution_time = timeit.timeit(stmt, setup, number=100)
print("Execution time:", execution_time)

# Using np.show_config to display NumPy configuration
np.show_config()

# 28. NumPy Custom Functions
print("\n--- Custom Functions ---")
def custom_function(x, y):
    return x + y

# Vectorizing the function
vectorized_func = np.vectorize(custom_function)
result = vectorized_func(arr, arr * 2)
print("Custom function result:", result)

# Using np.frompyfunc to create a ufunc
ufunc_func = np.frompyfunc(custom_function, 2, 1)
result_ufunc = ufunc_func(arr, arr * 2)
print("Ufunc result:", result_ufunc)

# 29. NumPy Memory Mapping
print("\n--- Memory Mapping ---")
# Creating a memory-mapped array
filename = 'mmap_array.dat'
dtype = np.float64
shape = (1000, 1000)
mmapped_arr = np.memmap(filename, dtype=dtype, mode='w+', shape=shape)
print("Memory-mapped array created:", mmapped_arr)

# Writing to the array
mmapped_arr[:] = np.random.rand(*shape)
print("Data written to file:", filename)

# Reading from the array
mmapped_arr_read = np.memmap(filename, dtype=dtype, mode='r', shape=shape)
print("Data read from file:\n", mmapped_arr_read[:5, :5])

# 30. NumPy Parallel Computing
print("\n--- Parallel Computing ---")
from numpy import linalg as LA

# Example with multi-threaded SVD
matrix = np.random.rand(1000, 1000)
u, s, vh = LA.svd(matrix)
print("SVD decomposition completed.")