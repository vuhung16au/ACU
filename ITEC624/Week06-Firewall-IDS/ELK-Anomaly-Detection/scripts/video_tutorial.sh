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
    asciicast2gif -s 2 -t monokai tutorial.cast tutorial.gif
    echo "✓ GIF created: tutorial.gif"
else
    echo "ℹ Install asciicast2gif for GIF conversion: npm install -g asciicast2gif"
fi

# Convert to GIF first with agg, then to proper MP4 with ffmpeg
if command -v agg &> /dev/null; then
    echo "Converting to GIF with agg..."
    agg tutorial.cast tutorial_temp.gif
    
    # Convert GIF to proper MP4 with ffmpeg
    if command -v ffmpeg &> /dev/null; then
        echo "Converting GIF to MP4 with ffmpeg..."
        ffmpeg -i tutorial_temp.gif -movflags faststart -pix_fmt yuv420p -vf "scale=trunc(iw/2)*2:trunc(ih/2)*2" tutorial.mp4 -y
        rm tutorial_temp.gif
        echo "✓ MP4 created: tutorial.mp4"
    else
        mv tutorial_temp.gif tutorial.mp4
        echo "ℹ Install ffmpeg for proper MP4 conversion: brew install ffmpeg"
    fi
else
    echo "ℹ Install agg for video conversion: cargo install --git https://github.com/asciinema/agg"
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

# Create version with narration as subtitles (optional)
if command -v ffmpeg &> /dev/null && [ -f "tutorial.mp4" ] && [ -f "video_narration.txt" ]; then
    echo "Creating version with narration text overlay..."
    
    # Create a simple SRT subtitle file from narration
    cat > tutorial_subtitles.srt << 'SUBTITLE_EOF'
1
00:00:00,000 --> 00:00:03,000
Welcome to the ELK Stack Anomaly Detection Lab.
In this tutorial, we'll demonstrate how machine learning detects network anomalies in real-time.

2
00:00:04,000 --> 00:00:10,000
First, we build our environment with 'make build'.
This sets up Elasticsearch, Kibana, Logstash, and our custom ML detection service.

3
00:00:11,000 --> 00:00:15,000
Now we start all services with 'make up'.
Our containers are launching: Elasticsearch, Kibana, the log generator, and the ML detector.

4
00:00:16,000 --> 00:00:20,000
Let's verify everything is healthy with 'make health'.
All services are running and ready.

5
00:00:21,000 --> 00:00:30,000
We can monitor live traffic with 'make monitor'.
Notice the normal request rate and low anomaly scores.

6
00:00:31,000 --> 00:00:40,000
Now for the interesting part: let's inject an anomaly.
We'll use 'make inject-burst' to simulate a burst attack.

7
00:00:41,000 --> 00:00:50,000
The Isolation Forest algorithm quickly detects this volume spike.
We can export the results to CSV format for analysis.

8
00:00:51,000 --> 00:01:00,000
Open the Kibana dashboard in your browser to see beautiful visualizations.
The spike is clearly visible with detection scores.

9
00:01:01,000 --> 00:01:10,000
One powerful feature: all code is editable live.
You can modify algorithms, traffic generators, or anomaly types.

10
00:01:11,000 --> 00:01:15,000
Finally, clean up with 'make down'.
This stops all services while preserving your data.

11
00:01:16,000 --> 00:01:20,000
That's it! You now have a complete anomaly detection lab.
Try modifying the algorithms and creating custom anomaly types!
SUBTITLE_EOF

    # Create version with subtitles
    ffmpeg -i tutorial.mp4 -vf "subtitles=tutorial_subtitles.srt:force_style='Fontsize=16,PrimaryColour=&Hffffff&,OutlineColour=&H000000&,Outline=2'" tutorial_with_narration.mp4 -y
    echo "✓ Video with narration created: tutorial_with_narration.mp4"
fi

echo ""
echo "════════════════════════════════════════"
echo "Video files generated:"
echo "  - tutorial.cast (asciinema format)"
if [ -f "tutorial.gif" ]; then
    echo "  - tutorial.gif (animated GIF)"
fi
if [ -f "tutorial.mp4" ]; then
    echo "  - tutorial.mp4 (MP4 video file)"
fi
if [ -f "tutorial_with_narration.mp4" ]; then
    echo "  - tutorial_with_narration.mp4 (MP4 with subtitles)"
fi
if [ -f "tutorial_subtitles.srt" ]; then
    echo "  - tutorial_subtitles.srt (subtitle file)"
fi
echo "  - video_narration.txt (voiceover script)"
echo ""
echo "To embed in README:"
echo "  [![asciicast](https://asciinema.org/a/XXXXX.png)](https://asciinema.org/a/XXXXX)"

