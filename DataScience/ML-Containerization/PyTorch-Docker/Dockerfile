FROM python:3.9-slim

# Set working directory
WORKDIR /src

# Set environment variables for better memory management
ENV PYTHONUNBUFFERED=1

# Copy requirements first for better caching
COPY requirements.txt .

# Install dependencies
RUN pip install --no-cache-dir -r requirements.txt

# Copy the rest of the application
COPY . .

# Run the application
CMD ["python", "pytorch_mnist.py"]
