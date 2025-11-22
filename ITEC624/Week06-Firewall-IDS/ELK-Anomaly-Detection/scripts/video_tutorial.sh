#!/bin/bash
# Generate video tutorial with narration script

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "╔════════════════════════════════════════╗"
echo "║     Video Tutorial Generator           ║"
echo "╚════════════════════════════════════════╝"
echo ""

# Check for asciinema
if ! command -v asciinema &> /dev/null; then
    echo "Installing asciinema..."
    if [[ "$OSTYPE" == "darwin"* ]]; then
        brew install asciinema
    elif [[ "$OSTYPE" == "linux-gnu"* ]]; then
        sudo apt-get update && sudo apt-get install -y asciinema
    else
        echo "Please install asciinema manually: https://asciinema.org/docs/installation"
        exit 1
    fi
fi

# Record terminal session
echo "Recording terminal session..."
asciinema rec tutorial.cast --command="bash $SCRIPT_DIR/video_demo_commands.sh" --overwrite

echo ""
echo "✓ Tutorial recorded: tutorial.cast"

# Try to convert to GIF (optional)
if command -v asciicast2gif &> /dev/null; then
    echo "Converting to GIF..."
    asciicast2gif tutorial.cast tutorial.gif -s 2 -t monokai
    echo "✓ GIF created: tutorial.gif"
else
    echo "ℹ Install asciicast2gif for GIF conversion: npm install -g asciicast2gif"
fi

# Try to convert to MP4 (optional)
if command -v agg &> /dev/null; then
    echo "Converting to MP4..."
    agg tutorial.cast tutorial.mp4
    echo "✓ MP4 created: tutorial.mp4"
else
    echo "ℹ Install agg for MP4 conversion: cargo install --git https://github.com/asciinema/agg"
fi

# Create narration script
cat > video_narration.txt << 'EOF'
ELK Stack Anomaly Detection Lab - Video Narration Script
========================================================

[00:00-00:03] 
"Welcome to the ELK Stack Anomaly Detection Lab. 
In this tutorial, we'll demonstrate how machine learning 
detects network anomalies in real-time."

[00:04-00:10]
"First, we build our environment with 'make build'. 
This sets up Elasticsearch, Kibana, Logstash, and our 
custom ML detection service with both Isolation Forest 
and Autoencoder algorithms."

[00:11-00:15]
"Now we start all services with 'make up'. 
Our containers are launching: Elasticsearch, Kibana, 
the log generator, and the ML detector."

[00:16-00:20]
"Let's verify everything is healthy with 'make health'. 
All services are running and ready."

[00:21-00:30]
"We can monitor live traffic with 'make monitor'. 
Notice the normal request rate around 145 per minute. 
Both anomaly scores are low, indicating normal behavior."

[00:31-00:40]
"Now for the interesting part: let's inject an anomaly. 
We'll use 'make inject-burst' to simulate 
a burst attack with 1000 requests in 30 seconds."

[00:41-00:50]
"The Isolation Forest algorithm quickly detects this 
volume spike. We can export the results to CSV format 
for analysis."

[00:51-01:00]
"Open the Kibana dashboard in your browser to see 
beautiful visualizations. The spike is clearly visible, 
along with the detection scores from both algorithms."

[01:01-01:10]
"One powerful feature: all code is editable live. 
You can modify the ML algorithms, traffic generators, 
or anomaly types. Changes apply immediately without 
rebuilding containers."

[01:11-01:15]
"Finally, clean up with 'make down'. 
This stops all services while preserving your data."

[01:16-01:20]
"That's it! You now have a complete anomaly detection lab. 
Try modifying the algorithms, adjusting thresholds, 
or creating custom anomaly types. Happy learning!"
EOF

echo "✓ Narration script created: video_narration.txt"

echo ""
echo "════════════════════════════════════════"
echo "Video files generated:"
echo "  - tutorial.cast (asciinema format)"
if [ -f "tutorial.gif" ]; then
    echo "  - tutorial.gif (animated GIF)"
fi
if [ -f "tutorial.mp4" ]; then
    echo "  - tutorial.mp4 (video file)"
fi
echo "  - video_narration.txt (voiceover script)"
echo ""
echo "To embed in README:"
echo "  [![asciicast](https://asciinema.org/a/XXXXX.png)](https://asciinema.org/a/XXXXX)"

