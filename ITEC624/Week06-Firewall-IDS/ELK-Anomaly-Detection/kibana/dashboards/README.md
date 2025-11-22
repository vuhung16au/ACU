# Kibana Dashboards

## Creating the Anomaly Detection Dashboard

Since Kibana dashboards are best created interactively through the Kibana UI, follow these steps to create the dashboard:

### 1. Access Kibana

Open http://localhost:5601 in your browser (no login required).

### 2. Create Index Pattern

1. Go to **Management** → **Stack Management** → **Index Patterns**
2. Click **Create index pattern**
3. Enter pattern: `logs-*`
4. Select time field: `@timestamp`
5. Click **Create index pattern**

### 3. Create Visualizations

#### A. Time Series: Requests Per Minute

1. Go to **Visualize Library** → **Create visualization**
2. Select **Line** chart
3. Data source: `logs-*`
4. Metrics:
   - Y-axis: Count
   - X-axis: Date Histogram on `@timestamp` (1 minute intervals)
5. Save as: "Requests Per Minute"

#### B. Anomaly Scores (Dual Axis)

1. Create new **Line** visualization
2. Add to index pattern: `anomaly-scores`
3. Metrics:
   - Y-axis 1: Average of `isolation_forest_score`
   - Y-axis 2: Average of `autoencoder_score`
   - X-axis: Date Histogram on `@timestamp`
4. Save as: "Anomaly Scores - Dual Algorithms"

#### C. Response Time Heatmap

1. Create **Heat map** visualization
2. Data source: `logs-*`
3. Metrics: Average of `response_time`
4. Buckets:
   - X-axis: Date Histogram (5 minute intervals)
   - Y-axis: Range on `response_time` (0-100, 100-500, 500-1000, 1000+)
5. Save as: "Response Time Distribution"

#### D. Status Code Breakdown

1. Create **Pie** chart
2. Data source: `logs-*`
3. Metrics: Count
4. Buckets: Split slices by `status` (terms)
5. Save as: "HTTP Status Codes"

#### E. Top Anomalous Requests

1. Create **Data table**
2. Data source: `logs-*`
3. Filter: `is_anomaly: true`
4. Columns: `@timestamp`, `ip`, `path`, `status`, `response_time`, `anomaly_type`
5. Sort by: `@timestamp` descending
6. Save as: "Anomalous Requests"

### 4. Create Dashboard

1. Go to **Dashboard** → **Create dashboard**
2. Add all saved visualizations
3. Arrange in grid:
   ```
   +----------------------+----------------------+
   | Requests Per Minute  | Anomaly Scores       |
   +----------------------+----------------------+
   | Response Time Heat   | Status Code Pie      |
   +----------------------+----------------------+
   | Anomalous Requests Table (full width)      |
   +---------------------------------------------+
   ```
4. Add Markdown widget with instructions:
   ```markdown
   # Anomaly Detection Dashboard
   
   Real-time ML-powered anomaly detection using:
   - **Isolation Forest**: Volume/rate anomalies
   - **Autoencoder**: Pattern anomalies
   
   **Normal**: Scores < 0.5
   **Suspicious**: Scores 0.5-0.7
   **Anomaly**: Scores > 0.7
   ```
5. Save dashboard as: "Anomaly Detection Lab"

### 5. Export Dashboard (Optional)

1. Go to **Stack Management** → **Saved Objects**
2. Select your dashboard and visualizations
3. Click **Export**
4. Save as: `anomaly-detection.ndjson`
5. Copy to this directory for version control

### 6. Import Dashboard (For Distribution)

To import a pre-created dashboard:

1. Go to **Stack Management** → **Saved Objects**
2. Click **Import**
3. Select `anomaly-detection.ndjson`
4. Click **Import**

## Dashboard Features

The completed dashboard should show:

- **Real-time metrics**: Live update every 30 seconds
- **Time range selector**: Last 15m, 1h, 24h, 7d, or custom
- **Filters**: Filter by anomaly type, IP, status code
- **Drill-down**: Click any visualization to explore details
- **Auto-refresh**: Enable for live monitoring
- **Full-screen mode**: For presentations

## Best Practices

1. **Use relative time ranges** (e.g., "Last 15 minutes") for live monitoring
2. **Enable auto-refresh** at 30-second intervals
3. **Add filters** for specific anomaly types during demos
4. **Use dark theme** for better visibility of anomaly spikes
5. **Save time range** with dashboard for consistent views

## Troubleshooting

**No data showing?**
- Ensure index pattern `logs-*` exists
- Check time range (try "Last 24 hours")
- Verify logs are being generated: `make health`

**Anomaly scores missing?**
- ML detector needs 2-3 minutes to initialize
- Check: `docker-compose logs ml-detector`
- Create `anomaly-scores` index pattern if needed

**Visualizations not loading?**
- Refresh browser
- Clear Kibana cache: `docker-compose restart kibana`
- Check Elasticsearch health: `curl http://localhost:9200/_cluster/health`

