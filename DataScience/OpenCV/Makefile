# Makefile for OpenCV notebook, example, and verification

.PHONY: help verify-notebooks verify-notebooks-verbose verify-notebooks-quick verify-examples test-setup clean

help:
	@echo "OpenCV Notebooks Verification"
	@echo "=============================="
	@echo ""
	@echo "Available targets:"
	@echo "  verify-notebooks        - Verify all notebooks can execute without errors"
	@echo "  verify-notebooks-verbose - Verify with verbose output"
	@echo "  verify-notebooks-quick  - Quick verification with shorter timeout"
	@echo "  verify-examples         - Verify all example scripts can execute without errors"
	@echo "  test-setup              - Test if all dependencies are installed"
	@echo "  clean                   - Clean up temporary files"
	@echo ""
	@echo "Options for Python script:"
	@echo "  make verify-notebooks EXCLUDE='notebook1.ipynb,notebook2.ipynb'"
	@echo "  make verify-notebooks TIMEOUT=600"

# Default notebook verification using Python script
verify-notebooks:
	@echo "🔍 Verifying notebooks with Python script..."
	python3 verify_notebooks.py $(if $(TIMEOUT),--timeout $(TIMEOUT)) $(if $(EXCLUDE),--exclude $(EXCLUDE))

# Verbose verification
verify-notebooks-verbose:
	@echo "🔍 Verifying notebooks with verbose output..."
	python3 verify_notebooks.py --verbose $(if $(TIMEOUT),--timeout $(TIMEOUT)) $(if $(EXCLUDE),--exclude $(EXCLUDE))

# Quick verification with shorter timeout
verify-notebooks-quick:
	@echo "🔍 Quick notebook verification..."
	python3 verify_notebooks.py --timeout 120 $(if $(EXCLUDE),--exclude $(EXCLUDE))

# Verify examples using Python script
verify-examples:
	@echo "🔍 Verifying examples with Python script..."
	python3 verify_examples.py $(if $(TIMEOUT),--timeout $(TIMEOUT)) $(if $(EXCLUDE),--exclude $(EXCLUDE))

# Alternative using bash script
verify-notebooks-bash:
	@echo "🔍 Verifying notebooks with bash script..."
	./verify_notebooks.sh $(if $(TIMEOUT),--timeout $(TIMEOUT))

# Test setup and dependencies
test-setup:
	@echo "🧪 Testing setup and dependencies..."
	@python3 -c "import sys; print(f'Python version: {sys.version}')"
	@python3 -c "import jupyter; print('✅ Jupyter installed')" || (echo "❌ Jupyter not installed" && exit 1)
	@python3 -c "import nbconvert; print('✅ nbconvert installed')" || (echo "❌ nbconvert not installed" && exit 1)
	@python3 -c "import cv2; print(f'✅ OpenCV {cv2.__version__} installed')" || (echo "❌ OpenCV not installed" && exit 1)
	@python3 -c "import numpy; print(f'✅ NumPy {numpy.__version__} installed')" || (echo "❌ NumPy not installed" && exit 1)
	@python3 -c "import matplotlib; print(f'✅ Matplotlib {matplotlib.__version__} installed')" || (echo "❌ Matplotlib not installed" && exit 1)
	@echo "🎉 All dependencies are properly installed!"

# Install dependencies if missing
install-deps:
	@echo "Deactivate any existing virtual environment..."
	deactivate || true

	@echo "Remove any existing .venv directory..."
	rm -rf .venv || true

	@echo "Building virtual environment..."
	python3.13 -m venv .venv
	source .venv/bin/activate

	@echo "Installing dependencies..."
	pip install -r requirements.txt

	@echo "📦 Installing notebook verification dependencies..."
	pip install jupyter nbconvert
	@echo "✅ Dependencies installed!"

# Clean up temporary files
clean:
	@echo "🧹 Cleaning up temporary files..."
	find . -name "*.tmp" -delete
	find . -name "*_temp.ipynb" -delete
	find . -name "notebook_verification_report.json" -delete

	@echo "Delete all files in sample_images/processed/ (if any)"
	rm -rf sample_images/processed/*

	@echo "✅ Cleanup completed!"

# Example targets for specific scenarios
verify-basic-only:
	python3 verify_notebooks.py --exclude "07_advanced_techniques.ipynb,08_practical_applications.ipynb"

verify-with-report:
	python3 verify_notebooks.py --save-report notebook_verification_report.json --verbose
