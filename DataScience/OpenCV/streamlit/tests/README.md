# Streamlit Component Tests

This directory contains comprehensive unit tests for all Streamlit components in the OpenCV project.

## Test Structure

- `conftest.py` - Pytest configuration and common fixtures
- `test_basic_operations.py` - Tests for BasicOperationsComponent
- `test_image_filtering.py` - Tests for ImageFilteringComponent
- `test_transform_operations.py` - Tests for TransformOperationsComponent
- `test_morphological_operations.py` - Tests for MorphologicalOperationsComponent
- `test_feature_detection.py` - Tests for FeatureDetectionComponent
- `test_color_processing.py` - Tests for ColorProcessingComponent
- `test_advanced_techniques.py` - Tests for AdvancedTechniquesComponent
- `test_practical_applications.py` - Tests for PracticalApplicationsComponent
- `run_tests.py` - Test runner script
- `requirements-test.txt` - Testing dependencies

## Running Tests

### Prerequisites

1. Install testing dependencies:
```bash
pip install -r requirements-test.txt
```

2. Ensure you have the sample image:
```bash
# The test will use sample_images/original/demo_image.jpg
# If the file doesn't exist, a test image will be generated
```

### Run All Tests

```bash
# Using the test runner
python run_tests.py

# Or using pytest directly
pytest -v

# With coverage
pytest --cov=components --cov-report=html --cov-report=term
```

### Run Specific Test Files

```bash
# Run tests for a specific component
pytest test_basic_operations.py -v

# Run tests with specific markers
pytest -m "not slow" -v
```

### Run Tests with Coverage

```bash
# Generate HTML coverage report
pytest --cov=components --cov-report=html

# View coverage in terminal
pytest --cov=components --cov-report=term
```

## Test Features

### Common Test Patterns

Each test file follows these patterns:

1. **Component Initialization Tests**
   - Test component name and description
   - Test input validation

2. **Render Method Tests**
   - Test rendering with valid images
   - Test rendering with invalid images
   - Mock Streamlit functions

3. **Operation Tests**
   - Test all component operations
   - Test error handling with invalid inputs
   - Test utility methods

4. **Base Class Tests**
   - Test display methods
   - Test warning/error/success methods
   - Test download functionality

### Mocking Strategy

- **Streamlit Functions**: All Streamlit UI functions are mocked
- **Display Functions**: Utility display functions are mocked
- **Image Processing**: Real OpenCV operations are tested with sample images

### Fixtures

- `sample_image`: Loads or creates a test image
- `grayscale_image`: Creates a grayscale test image
- `mock_streamlit`: Mocks all Streamlit functions
- `mock_display_functions`: Mocks display utilities

## Test Coverage

The tests cover:

- ✅ Component initialization
- ✅ Input validation
- ✅ Render method functionality
- ✅ All image processing operations
- ✅ Error handling
- ✅ Utility methods
- ✅ Base class functionality

## Adding New Tests

To add tests for a new component:

1. Create a new test file following the naming convention
2. Import the component class
3. Create test class with component fixture
4. Add tests for initialization, rendering, and operations
5. Use the existing fixtures and mocking patterns

## Troubleshooting

### Common Issues

1. **Import Errors**: Ensure the components directory is in the Python path
2. **Missing Dependencies**: Install requirements-test.txt
3. **Image Loading Errors**: The test will create a test image if demo_image.jpg is missing

### Debug Mode

Run tests with more verbose output:

```bash
pytest -v -s --tb=long
```

This will show print statements and full tracebacks for debugging. 