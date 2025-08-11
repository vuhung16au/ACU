FROM python:3.9-slim

# Set working directory
WORKDIR /src

# Copy requirements first for better caching
COPY requirements.txt .

# Install dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of the application
COPY . .

# Run the application
CMD ["python", "iris_classification.py"]
