#!/bin/bash
# Automated command sequence for video recording

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
source "$SCRIPT_DIR/video_helpers.sh"

# Get scenario from parameter
SCENARIO="${1:-demo}"

# Title screen
show_title "ELK Stack Anomaly Detection Lab - $SCENARIO"
sleep 3

# Scenario functions
run_demo_scenario() {
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
}

run_tests_scenario() {
    # Initial setup
    show_section "1. Environment Setup"
    run_with_pause "make build" 3
    run_with_pause "make start" 5
    
    # Section 2: Baseline Testing
    show_section "2. Testing Baseline Traffic"
    run_with_pause "make test-baseline" 3
    echo "✓ Normal traffic generation verified"
    sleep 2

    # Section 3: Isolation Forest Test
    show_section "3. Testing Isolation Forest Algorithm"
    run_with_pause "make test-isolation-forest" 5
    echo "✓ Isolation Forest detection working"
    sleep 3

    # Section 4: Autoencoder Test
    show_section "4. Testing Autoencoder Algorithm"
    run_with_pause "make test-autoencoder" 5
    echo "✓ Autoencoder detection working"
    sleep 3

    # Section 5: Algorithm Comparison
    show_section "5. Comparing Both Algorithms"
    run_with_pause "make test-comparison" 3
    echo "✓ Algorithm comparison complete"
    sleep 2

    # Section 6: Dashboard Verification
    show_section "6. Testing Kibana Dashboard"
    run_with_pause "make test-dashboard" 2
    echo "✓ Dashboard accessibility verified"
    sleep 2

    # Section 7: Export Results
    show_section "7. Exporting Test Results"
    run_with_pause "make export" 2
    run_with_pause "make report" 3
    
    # Cleanup
    show_section "8. Cleanup"
    run_with_pause "make down" 2
}

run_anomaly_scenario() {
    # Initial setup
    show_section "1. Environment Setup"
    run_with_pause "make build" 3
    run_with_pause "make start" 5

    # Section 2: Monitor Normal Traffic
    show_section "2. Monitoring Normal Traffic"
    echo "Observing baseline behavior..."
    timeout 15 bash "$SCRIPT_DIR/monitor.sh" 2>/dev/null || true
    sleep 2

    # Section 3: Burst Anomaly
    show_section "3. Injecting Burst Anomaly"
    run_with_pause "make inject-burst" 10
    echo "✓ Burst anomaly detected"
    sleep 3

    # Section 4: Error Flood
    show_section "4. Injecting Error Flood"
    run_with_pause "make inject-errors" 10
    echo "✓ Error pattern detected"
    sleep 3

    # Section 5: Slow Requests
    show_section "5. Injecting Slow Request Pattern"
    run_with_pause "make inject-slow" 10
    echo "✓ Slow request pattern detected"
    sleep 3

    # Section 6: Scan Pattern
    show_section "6. Injecting Scan Pattern"
    run_with_pause "make inject-scan" 10
    echo "✓ Scan pattern detected"
    sleep 3

    # Section 7: All Anomalies
    show_section "7. Injecting All Anomaly Types"
    run_with_pause "make inject-all" 15
    echo "✓ Multiple anomaly types detected simultaneously"
    sleep 3

    # Section 8: Export Results
    show_section "8. Exporting Anomaly Results"
    run_with_pause "make export" 2
    
    # Cleanup
    show_section "9. Cleanup"
    run_with_pause "make down" 2
}

run_all_scenarios() {
    show_section "Comprehensive ELK Anomaly Detection Demo"
    echo "Running all scenarios: Demo, Tests, and Anomaly Injection"
    sleep 3
    
    show_title "Part 1: Basic Demo"
    run_demo_scenario
    sleep 3
    
    show_title "Part 2: Testing Suite"
    run_tests_scenario
    sleep 3
    
    show_title "Part 3: Anomaly Injection"
    run_anomaly_scenario
}

# Execute the appropriate scenario
case "$SCENARIO" in
    "demo")
        run_demo_scenario
        ;;
    "tests")
        run_tests_scenario
        ;;
    "anomaly")
        run_anomaly_scenario
        ;;
    "all")
        run_all_scenarios
        ;;
    *)
        echo "Unknown scenario: $SCENARIO"
        echo "Available scenarios: demo, tests, anomaly, all"
        exit 1
        ;;
esac

# Outro
show_title "$SCENARIO Scenario Complete!"
sleep 3

