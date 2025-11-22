# Anomaly Detection - Quick Demo Guide

## What I Fixed

### Problems Found:
1. **No anomaly-scores index existed** - ML detector wasn't writing results to Elasticsearch
2. **Training threshold too high** - Needed 50 samples, but only getting 5-6 per iteration
3. **Detection thresholds too strict** - Made it hard to detect anomalies
4. **Only writing 1 result per iteration** - Now writes all time bucket results

### Changes Made:

#### 1. Lowered Detection Thresholds (`config/ml-config.yaml`)
```yaml
# More sensitive anomaly detection
isolation_forest: 0.2      # was 0.4
autoencoder: 0.04          # was 0.08
contamination: 0.15        # was 0.05
min_samples: 5             # was 10
```

#### 2. Fixed ML Detector (`ml-detector/app.py`)
- Lowered training threshold from 50 to 10 samples
- Now writes ALL time bucket results (not just the first one)
- Better anomaly score persistence

#### 3. Created New Injection Script
Added `inject-extreme` target for easier demonstration with higher intensity

---

## How to Demonstrate Anomalies

### Method 1: Extreme Anomalies (RECOMMENDED)
```bash
# This will inject very obvious anomalies
make inject-extreme

# Wait 60 seconds for ML detector to process
sleep 60

# Generate report
make report
```

### Method 2: Individual Anomaly Types
```bash
# Burst attack (volume spike)
make inject-burst

# Wait and check
sleep 30
make report
```

### Method 3: Multiple Waves
```bash
# Inject multiple anomaly types in sequence
make inject-burst
sleep 30
make inject-errors
sleep 30
make inject-slow
sleep 30
make report
```

---

## Monitoring & Verification

### Check if ML Detector is Working
```bash
# Watch ML detector logs
docker-compose logs -f ml-detector

# Look for:
# - "Training models on X samples..."
# - "Models trained successfully!"
# - "Detection results: X anomalies"
```

### Check Elasticsearch Directly
```bash
# Check if anomaly-scores index exists
curl -s "http://localhost:9200/_cat/indices" | grep anomaly

# Count anomaly scores
curl -s "http://localhost:9200/anomaly-scores/_count" | jq .

# View recent anomaly scores
curl -s "http://localhost:9200/anomaly-scores/_search?size=5" | jq '.hits.hits[]._source'

# Count detected anomalies (combined_label=1)
curl -s "http://localhost:9200/anomaly-scores/_search?q=combined_label:1" | jq '.hits.total.value'
```

### Real-time Monitoring
```bash
# Monitor everything
make monitor

# Or watch health
watch -n 5 'make health'
```

---

## Expected Results

After injecting anomalies and waiting ~60 seconds, you should see:

1. **In ML Detector Logs:**
   ```
   [ML Service] Detection results:
     - Isolation Forest: 3/6 anomalies
     - Autoencoder: 2/6 anomalies
     - Combined: 4/6 anomalies
   [ML Service] Wrote 6 anomaly score records to ES
   ```

2. **In Report:**
   - Total Logs: thousands
   - Anomalies Detected: 4+ (should be > 0 now!)

3. **In Elasticsearch:**
   ```bash
   curl -s "http://localhost:9200/anomaly-scores/_search?size=1" | jq .
   # Should return documents with combined_label: 1
   ```

---

## Troubleshooting

### Still Showing 0 Anomalies?

1. **Check ML detector is trained:**
   ```bash
   docker-compose logs ml-detector | grep -i trained
   ```

2. **Check models exist:**
   ```bash
   docker-compose exec ml-detector ls -la models/
   ```

3. **Inject MORE extreme anomalies:**
   ```bash
   # Max intensity, longer duration
   docker-compose run --rm anomaly-injector python injector.py \
     --type burst --duration 120 --intensity 10
   ```

4. **Lower thresholds even more:**
   Edit `config/ml-config.yaml`:
   ```yaml
   thresholds:
     isolation_forest: 0.1    # Even lower
     autoencoder_reconstruction: 0.02  # Even lower
   ```
   Then restart: `docker-compose restart ml-detector`

5. **Force retrain models:**
   ```bash
   docker-compose exec ml-detector rm -rf models/*
   docker-compose restart ml-detector
   # Wait for it to retrain on next batch
   ```

---

## Understanding the Algorithms

### Isolation Forest (Volume-based)
- Detects: Traffic spikes, burst attacks, rate anomalies
- Best for: `inject-burst`, `inject-errors`
- Threshold: Lower = more sensitive

### Autoencoder (Pattern-based)
- Detects: Unusual patterns, latency spikes, behavioral changes
- Best for: `inject-slow`, `inject-scan`
- Threshold: Lower = more sensitive

### Combined Detection
- Uses OR logic: Anomaly if EITHER algorithm detects it
- Provides comprehensive coverage

---

## Quick Demo Workflow

```bash
# 1. Start fresh
make down
make clean-logs
make up
make health

# 2. Wait for services to stabilize (60 seconds)
sleep 60

# 3. Inject EXTREME anomalies
make inject-extreme

# 4. Wait for detection
sleep 60

# 5. Generate report
make report

# 6. View in browser (opens automatically)

# 7. Check Kibana dashboard
open http://localhost:5601
```

---

## Advanced: Custom Anomaly Injection

```bash
# Very intense burst
docker-compose run --rm anomaly-injector python injector.py \
  --type burst --duration 90 --intensity 10

# Sustained error flood
docker-compose run --rm anomaly-injector python injector.py \
  --type errors --duration 120 --intensity 10

# Extreme slow requests
docker-compose run --rm anomaly-injector python injector.py \
  --type slow --duration 60 --intensity 10
```

---

## Summary

The system is now **much more sensitive** to anomalies:
- ✅ Lower thresholds (0.2 instead of 0.4)
- ✅ Lower training requirement (10 instead of 50)
- ✅ Writes ALL detection results (not just first)
- ✅ New `inject-extreme` command for easy demos
- ✅ More aggressive contamination setting

**Try it now:** `make inject-extreme` → wait 60s → `make report`
