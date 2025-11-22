#!/bin/bash
# Train ML models on sample data (optional pre-training)

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/utils.sh"

echo "╔════════════════════════════════════════╗"
echo "║     ML Model Training Script           ║"
echo "╚════════════════════════════════════════╝"
echo ""

log_info "This script generates sample data and trains ML models."
log_info "Note: Models also train automatically on first run of ML service."
echo ""

# Generate sample datasets
log_info "Step 1/2: Generating sample datasets..."
python3 "$SCRIPT_DIR/generate_datasets.py"

if [ $? -ne 0 ]; then
    log_error "Failed to generate datasets"
    exit 1
fi

echo ""
log_success "Sample datasets created in data/ directory"
echo ""

# Models will be trained by the ML service on first run
log_info "Step 2/2: Model Training"
log_info "Models will be trained automatically when the ML service starts."
log_info "The service will use the first batch of Elasticsearch data for training."
echo ""

log_info "To manually train models:"
echo "  1. Start services: make up"
echo "  2. Wait 2-3 minutes for data collection"
echo "  3. Models train automatically on first detection cycle"
echo "  4. Check logs: docker-compose logs ml-detector"
echo ""

log_success "Training setup complete!"
echo ""
echo "Next steps:"
echo "  - Start services: make up"
echo "  - Monitor training: docker-compose logs -f ml-detector"
echo "  - Models will be saved to: data/models/"

