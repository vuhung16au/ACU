#!/bin/bash
# Generate video tutorial with narration script

set -e

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "╔════════════════════════════════════════╗"
echo "║     Video Tutorial Generator           ║"
echo "╚════════════════════════════════════════╝"
echo ""

# Recording mode and scenario selection
RECORDING_MODE="${1:-terminal}"  # Default to terminal mode
SCENARIO="${2:-demo}"  # Default to demo scenario
if [[ "$1" == "--help" ]] || [[ "$1" == "-h" ]]; then
    echo "Usage: $0 [terminal|fullscreen|both] [demo|tests|anomaly|all]"
    echo ""
    echo "Recording Modes:"
    echo "  terminal    - Record terminal session only (default)"
    echo "  fullscreen  - Record full screen using macOS native tools"
    echo "  both        - Record both terminal and full screen"
    echo ""
    echo "Scenarios:"
    echo "  demo        - Basic demonstration (default)"
    echo "  tests       - All testing scenarios"
    echo "  anomaly     - Anomaly injection scenarios"
    echo "  all         - Comprehensive demo with all scenarios"
    echo ""
    echo "Examples:"
    echo "  $0 terminal demo     # Terminal recording with demo scenario"
    echo "  $0 fullscreen tests  # Full screen recording with test scenarios"
    echo "  $0 both anomaly      # Both recordings with anomaly scenarios"
    exit 0
fi

echo "Recording mode: $RECORDING_MODE"
echo "Scenario: $SCENARIO"
echo ""

# Function to record full screen on macOS
record_fullscreen() {
    echo "Starting full screen recording..."
    echo "Recording will start in 3 seconds. Press Ctrl+C to stop recording."
    
    # Check if ffmpeg is available
    if command -v ffmpeg &> /dev/null; then
        echo "Using ffmpeg for full screen recording..."
        sleep 3
        # Record full screen at 30fps, audio optional
        ffmpeg -f avfoundation -i "1:0" -r 30 -vcodec libx264 -preset ultrafast -crf 18 tutorial_fullscreen.mp4 2>/dev/null &
        FFMPEG_PID=$!
        
        # Run demo commands
        bash "$SCRIPT_DIR/video_demo_commands.sh" "$SCENARIO"
        
        # Stop recording
        kill $FFMPEG_PID 2>/dev/null || true
        wait $FFMPEG_PID 2>/dev/null || true
        echo "✓ Full screen recording saved: tutorial_fullscreen.mp4"
    else
        echo "ffmpeg not found. Using macOS QuickTime Player method..."
        echo "Please start QuickTime Player and use 'File > New Screen Recording'"
        echo "Press Enter when you've started QuickTime recording..."
        read -r
        
        # Run demo commands
        bash "$SCRIPT_DIR/video_demo_commands.sh" "$SCENARIO"
        
        echo ""
        echo "Recording complete. Please stop QuickTime recording and save the file."
        echo "Recommended filename: tutorial_fullscreen.mov"
    fi
}

# Function to record terminal session
record_terminal() {
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
    asciinema rec tutorial.cast --command="bash $SCRIPT_DIR/video_demo_commands.sh $SCENARIO" --overwrite
    echo "✓ Tutorial recorded: tutorial.cast"
}

# Execute recording based on mode
case "$RECORDING_MODE" in
    "terminal")
        record_terminal
        ;;
    "fullscreen")
        record_fullscreen
        ;;
    "both")
        echo "Recording both terminal and full screen..."
        record_terminal
        echo ""
        record_fullscreen
        ;;
    *)
        echo "Invalid mode: $RECORDING_MODE"
        echo "Use: terminal, fullscreen, or both"
        exit 1
        ;;
esac

echo ""

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
00:00:00,000 --> 00:00:05,000
Welcome to the ELK Stack Anomaly Detection Lab.
In this tutorial, we'll demonstrate multiple types of network anomaly detection.

2
00:00:06,000 --> 00:00:15,000
First, we build our environment with 'make build'.
This sets up Elasticsearch, Kibana, Logstash, and ML detection services.

3
00:00:16,000 --> 00:00:25,000
Starting all services with 'make start'. Launching Elasticsearch,
Kibana, log generator, and ML detector containers.

4
00:00:26,000 --> 00:00:35,000
Monitoring baseline traffic behavior. Notice normal request rates
and low anomaly scores indicating healthy network behavior.

5
00:00:36,000 --> 00:00:50,000
Injecting burst anomaly with 'make inject-burst'.
Simulating traffic spike like DDoS attack with 1000 requests.

6
00:00:51,000 --> 00:01:05,000
Isolation Forest algorithm detects volume spike immediately.
Anomaly score jumps, flagging suspicious behavior.

7
00:01:06,000 --> 00:01:20,000
Injecting error flood with 'make inject-errors'.
Simulating application failures and high error rates.

8
00:01:21,000 --> 00:01:35,000
Testing slow request patterns with 'make inject-slow'.
Mimicking performance degradation and resource exhaustion.

9
00:01:36,000 --> 00:01:50,000
Scan pattern injection demonstrates reconnaissance behavior.
'make inject-scan' simulates attacker probing endpoints.

10
00:01:51,000 --> 00:02:10,000
Injecting all anomaly types with 'make inject-all'.
Complex attack scenario with multiple simultaneous vectors.

11
00:02:11,000 --> 00:02:25,000
Machine learning adapts to combined threats. Isolation Forest
excels at volumes, Autoencoder identifies pattern changes.

12
00:02:26,000 --> 00:02:40,000
Exporting detection results for analysis. CSV reports with
timestamps, scores, and patterns for incident response.

13
00:02:41,000 --> 00:03:00,000
Kibana dashboard at localhost:5601 shows real-time visualizations,
traffic patterns, and algorithm comparisons.

14
00:03:01,000 --> 00:03:20,000
All code is editable live! Modify ML algorithms, traffic patterns,
or create custom anomaly types with immediate hot-reload.

15
00:03:21,000 --> 00:03:40,000
Isolation Forest excels at volume anomalies. Autoencoder neural
network superior at subtle pattern and sequence deviations.

16
00:03:41,000 --> 00:04:00,000
Real-world scenarios: network intrusions, DDoS attacks,
application failures, and reconnaissance activities.

17
00:04:01,000 --> 00:04:20,000
Containerized architecture ensures reproducible results.
Docker Compose orchestrates Elasticsearch, Kibana, and ML engines.

18
00:04:21,000 --> 00:04:40,000
Production deployment: scale components, add ML models,
integrate SIEM systems, customize alerting thresholds.

19
00:04:41,000 --> 00:05:00,000
Exported data integrates with security workflows. CSV reports
for trend analysis, compliance, and threat hunting.

20
00:05:01,000 --> 00:05:20,000
Cleaning up with 'make down'. Stops services while preserving
data and configurations for quick restart.

21
00:05:21,000 --> 00:05:26,000
Complete anomaly detection lab! Experiment with thresholds,
create custom attacks, enhance ML models!
SUBTITLE_EOF

    # Create version with subtitles
    ffmpeg -i tutorial.mp4 -vf "subtitles=tutorial_subtitles.srt:force_style='Fontsize=16,PrimaryColour=&Hffffff&,OutlineColour=&H000000&,Outline=2'" tutorial_with_narration.mp4 -y
    echo "✓ Video with narration created: tutorial_with_narration.mp4"
fi

echo ""
echo "════════════════════════════════════════"
echo "Video files generated:"
if [ -f "tutorial.cast" ]; then
    echo "  - tutorial.cast (asciinema format)"
fi
if [ -f "tutorial_fullscreen.mp4" ]; then
    echo "  - tutorial_fullscreen.mp4 (full screen MP4)"
fi
if [ -f "tutorial_fullscreen.mov" ]; then
    echo "  - tutorial_fullscreen.mov (full screen QuickTime)"
fi
if [ -f "tutorial.gif" ]; then
    echo "  - tutorial.gif (animated GIF)"
fi
if [ -f "tutorial.mp4" ]; then
    echo "  - tutorial.mp4 (terminal MP4 video file)"
fi
if [ -f "tutorial_with_narration.mp4" ]; then
    echo "  - tutorial_with_narration.mp4 (MP4 with subtitles)"
fi
if [ -f "tutorial_subtitles.srt" ]; then
    echo "  - tutorial_subtitles.srt (subtitle file)"
fi
echo "  - video_narration.txt (voiceover script)"
echo ""
echo "Usage examples:"
echo "  Terminal only:    ./video_tutorial.sh terminal"
echo "  Full screen:      ./video_tutorial.sh fullscreen"
echo "  Both recordings:  ./video_tutorial.sh both"
echo ""
echo "To embed in README:"
if [ -f "tutorial.cast" ]; then
    echo "  [![asciicast](https://asciinema.org/a/XXXXX.png)](https://asciinema.org/a/XXXXX)"
fi

