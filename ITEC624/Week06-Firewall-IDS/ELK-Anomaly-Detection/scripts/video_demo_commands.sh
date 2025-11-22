#!/bin/bash
# Automated command sequence for video recording

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/video_helpers.sh"

# Title screen
show_title "ELK Stack Anomaly Detection Lab"
sleep 3

# Section 1: Setup
show_section "1. Building the Environment"
sleep 1
run_with_pause "make build" 3

# Section 2: Starting Services
show_section "2. Starting All Services"
run_with_pause "make up" 3
sleep 5

# Section 3: Health Check
show_section "3. Verifying System Health"
run_with_pause "make health" 3

# Section 4: Normal Traffic
show_section "4. Observing Normal Traffic"
echo "Monitoring for 10 seconds..."
timeout 10 bash "$SCRIPT_DIR/monitor.sh" 2>/dev/null || true
sleep 2

# Section 5: Inject Anomaly
show_section "5. Injecting Volume Anomaly"
run_with_pause "make inject-burst" 5

# Section 6: Results
show_section "6. Viewing Detection Results"
echo "Waiting for ML detection..."
sleep 30
run_with_pause "make export" 2

# Section 7: Dashboard
show_section "7. Kibana Dashboard"
echo "Open http://localhost:5601 in your browser"
echo "View real-time anomaly visualizations"
sleep 3

# Section 8: Code Editing Demo
show_section "8. Live Code Editing"
echo "All Python code is editable from your IDE:"
echo "  - ml-detector/isolation_forest.py"
echo "  - log-generator/generator.py"
echo "  - anomaly-injector/injector.py"
echo ""
echo "Changes apply immediately with hot-reload!"
sleep 3

# Section 9: Cleanup
show_section "9. Cleanup"
run_with_pause "make down" 2

# Outro
show_title "Demo Complete! Try it yourself."
sleep 3

