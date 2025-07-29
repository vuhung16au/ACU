# OpenCV Interactive Dashboard - Streamlit App

A comprehensive interactive dashboard for experimenting with OpenCV image processing techniques.

## 🚀 Live Demo

**Deployed on Streamlit Cloud**: [Your App URL will be here after deployment]

## 📋 Features

- **8 Interactive Sections**:
  - Basic Operations
  - Image Filtering
  - Transformations
  - Morphological Operations
  - Feature Detection
  - Color Processing
  - Advanced Techniques
  - Practical Applications

- **Interactive Widgets**: Real-time parameter adjustment
- **Image Upload**: Support for PNG, JPG, JPEG, BMP, TIFF
- **Side-by-side Comparison**: Original vs processed images
- **Responsive Design**: Works on desktop and mobile

## 🛠️ Local Development

### Prerequisites
- Python 3.8+
- pip

### Installation

1. **Clone the repository**:
   ```bash
   git clone <your-repo-url>
   cd OpenCV/streamlit
   ```

2. **Install dependencies**:
   ```bash
   pip install -r requirements.txt
   ```

3. **Run the app**:
   ```bash
   streamlit run app.py
   ```

4. **Open in browser**: http://localhost:8501

## 🌐 Deployment to Streamlit Cloud

### Option 1: Deploy from GitHub (Recommended)

1. **Push your code to GitHub**:
   ```bash
   git add .
   git commit -m "Add Streamlit app"
   git push origin main
   ```

2. **Deploy to Streamlit Cloud**:
   - Go to [share.streamlit.io](https://share.streamlit.io)
   - Sign in with GitHub
   - Click "New app"
   - Select your repository
   - Set the path to: `streamlit/app.py`
   - Click "Deploy"

### Option 2: Deploy from Local Files

1. **Install Streamlit CLI**:
   ```bash
   pip install streamlit
   ```

2. **Deploy directly**:
   ```bash
   streamlit deploy app.py
   ```

## 📁 Project Structure

```
streamlit/
├── app.py                 # Main Streamlit application
├── components.py          # Interactive components for each section
├── utils.py              # Utility functions
├── requirements.txt       # Python dependencies
├── .streamlit/           # Streamlit configuration
│   └── config.toml
└── README.md             # This file
```

## 🎯 Usage

1. **Upload an image** using the sidebar
2. **Select a section** from the navigation menu
3. **Adjust parameters** using the interactive widgets
4. **Compare results** with the original image
5. **Download processed images** if needed

## 🔧 Configuration

The app uses the following key dependencies:
- `streamlit>=1.28.0` - Web framework
- `opencv-python>=4.8.0` - Image processing
- `numpy>=1.21.0` - Numerical computing
- `matplotlib>=3.5.0` - Plotting
- `pillow>=8.3.0` - Image handling
- `plotly>=5.0.0` - Interactive charts

## 📝 Notes

- **Image Size**: Large images (>10MB) may take longer to process
- **Browser Compatibility**: Works best in Chrome, Firefox, Safari
- **Mobile**: Responsive design works on mobile devices
- **Performance**: Processing happens in real-time as you adjust parameters

## 🤝 Contributing

Feel free to submit issues and enhancement requests!

## 📄 License

MIT License - see LICENSE file for details.

## Live Demo 

![Live Demo](https://vuhung16au-acu-datascienceopencvstreamlitapp-t8yyrv.streamlit.app/)

---

**Author**: Vu Hung Nguyen  
**Built with**: Streamlit + OpenCV 