# ELK Stack Anomaly Detection Lab - FAQ

## Question 1: Why I don't see any anomaly logs in reports?

**Answer:**

If your lab report shows "0 Anomalies Detected" despite having processed logs, this is typically expected behavior. Here's why and how to fix it:

### 🎯 **Expected Behavior**

By default, the ELK Stack lab only generates **normal baseline traffic**. The system doesn't automatically create anomalies - you need to actively inject them to test the ML detection algorithms.

### 🚨 **How to Generate Anomalies**

#### **Quick Test (Recommended):**
```bash
# Inject burst anomaly and wait for detection
make inject-burst
sleep 30
make report  # Generate new report to see results
```

#### **All Anomaly Types:**
```bash
# Inject all types of anomalies
make inject-all
sleep 30
make report
```

#### **Individual Anomaly Types:**
```bash
make inject-burst      # High volume traffic spike
make inject-errors     # Error flood (500 errors)  
make inject-slow       # Slow response times
make inject-scan       # Port scanning pattern
```

### 🔍 **What to Expect After Injection**

After running anomaly injection, your next report should show:

- **Anomalies Detected**: 10-50+ anomalies (depending on type)
- **Algorithm Performance**: Different detection rates for Isolation Forest vs Autoencoder
- **Recent Detections**: Table with timestamps and anomaly details

### 🛠️ **Troubleshooting Steps**

1. **Verify Services Are Running:**
   ```bash
   make health
   ```

2. **Check ML Detector Status:**
   ```bash
   make logs | grep ml-detector
   ```

3. **Monitor Real-time Detection:**
   ```bash
   make monitor  # Shows live anomaly detection
   ```

4. **Force Model Retraining (if needed):**
   ```bash
   docker-compose restart ml-detector
   sleep 60  # Wait for models to retrain
   ```

### 📊 **Real-time Verification**

To see anomalies being detected live:

```bash
# Terminal 1: Monitor detection
make monitor

# Terminal 2: Inject anomalies
make inject-burst
```

### 🎨 **Visual Verification**

Open Kibana to see anomalies visually:
```bash
open http://localhost:5601
```

Navigate to the pre-configured dashboards to see:
- Time series graphs with anomaly markers
- Isolation Forest vs Autoencoder comparison charts
- Detailed anomaly tables

### ⚡ **Quick Validation Workflow**

```bash
# Complete test sequence (5 minutes)
make inject-all          # Inject all anomaly types
sleep 30                 # Wait for ML processing
make report              # Generate updated report  
make export              # Export results to CSV
open http://localhost:5601  # View in Kibana
```

**Expected Result:** Your report will show detected anomalies with algorithm performance metrics and recent detection details.