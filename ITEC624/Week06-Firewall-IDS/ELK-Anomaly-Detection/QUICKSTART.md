# Quick Start (3 minutes)

Get up and running with the ELK Stack Anomaly Detection Lab in under 3 minutes!

## Prerequisites

- **Docker Desktop** with 4GB+ RAM allocated
- **10GB** free disk space
- **Git** installed

## Steps

### 1. Clone the Repository

```bash
git clone <repository-url>
cd ELK-Anomaly-Detection
```

### 2. Build Containers

```bash
make build
```

*First-time build takes ~3 minutes. ML models will be trained on first run.*

### 3. Start Services

```bash
make up
```

*Services start in ~30 seconds.*

### 4. Verify Health

```bash
make health
```

Wait until all services show as healthy (✓).

### 5. Open Kibana

Visit [http://localhost:5601](http://localhost:5601) in your browser.

**No login required!** Security is disabled for this educational lab.

### 6. Run Demo

```bash
make demo
```

This automated 5-minute demonstration will:
- Observe baseline traffic
- Inject an anomaly
- Show ML detection results

## Editing Code (Hot-Reload Enabled!)

Open the project in VS Code or Cursor:

```bash
code .
```

Edit any Python file in these directories:
- `ml-detector/` - ML algorithms
- `log-generator/` - Traffic generator  
- `anomaly-injector/` - Attack simulator

**Changes apply immediately** without rebuilding containers!

### Example: Tune Detection Threshold

1. Open `ml-detector/isolation_forest.py`
2. Change `self.contamination = 0.05` to `0.10`
3. Save (Cmd+S / Ctrl+S)
4. Container auto-restarts
5. Test: `make test-isolation-forest`

## Next Steps

### Explore

- **Monitor live**: `make monitor`
- **Test Isolation Forest**: `make test-isolation-forest`
- **Test Autoencoder**: `make test-autoencoder`
- **Inject custom anomaly**: `make inject-custom`

### Learn

- **Theory**: See [README.md](README.md#theory) for how the algorithms work
- **Architecture**: View the Mermaid diagram in [README.md](README.md#architecture)
- **Troubleshooting**: Common issues and solutions in [README.md](README.md#troubleshooting)

### Experiment

Try modifying:
- Algorithm parameters in `config/ml-config.yaml`
- Traffic patterns in `log-generator/generator.py`
- Anomaly types in `anomaly-injector/injector.py`

## Common Commands

```bash
make help           # Show all available commands
make up             # Start services
make down           # Stop services
make logs           # View logs
make monitor        # Real-time monitoring
make export         # Export results to CSV
make report         # Generate HTML report
make clean          # Remove all data
```

## Troubleshooting

**Services won't start?**
```bash
# Check Docker is running
docker ps

# Check available memory (needs 4GB+)
docker stats

# Restart everything
make restart
```

**No logs appearing?**
```bash
# Wait 30 seconds for log generation
sleep 30 && make health
```

**Kibana not loading?**
```bash
# Wait for Kibana to initialize (can take 60s)
make health

# Check Kibana logs
docker-compose logs kibana
```

## What's Next?

Read the full [README.md](README.md) for:
- Detailed architecture explanation
- ML theory (how Isolation Forest and Autoencoder work)
- Advanced configuration
- Real-world applications
- VS Code/Cursor integration guide

---

**Need help?** Check the [README.md](README.md#troubleshooting) troubleshooting section or open an issue.

