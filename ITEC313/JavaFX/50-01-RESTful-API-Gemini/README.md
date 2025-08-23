# Cryptocurrency Price Tracker

A JavaFX desktop application that fetches and displays real-time cryptocurrency prices using the Gemini API.

## 🚀 Features

- **Real-time Price Tracking**: Fetches live cryptocurrency prices from Gemini exchange
- **Multiple Cryptocurrencies**: Displays prices for 8 cryptocurrencies
- **Clean UI**: Simple, modern interface with a table view
- **Auto-refresh**: Manual refresh capability to get latest prices
- **Error Handling**: Graceful handling of API failures and network issues

## 📊 Supported Cryptocurrencies

The application tracks the following cryptocurrencies:
- **BTC** - Bitcoin
- **ETH** - Ethereum
- **XRP** - Ripple
- **SOL** - Solana
- **DOGE** - Dogecoin
- **BCH** - Bitcoin Cash
- **LTC** - Litecoin
- **TRUMP** - Trump Token

## 🛠️ Tech Stack

- **Java 21**: Modern Java with latest features
- **JavaFX 21**: Desktop GUI framework
- **Maven**: Build tool and dependency management
- **OkHttp 4.12.0**: HTTP client for API calls
- **Jackson 2.16.0**: JSON parsing library
- **Gemini API**: Cryptocurrency price data source

## 📦 Dependencies

### Core Dependencies (from `pom.xml`)

#### **OkHttp 4.12.0**
```xml
<dependency>
    <groupId>com.squareup.okhttp3</groupId>
    <artifactId>okhttp</artifactId>
    <version>4.12.0</version>
</dependency>
```
- **Purpose**: HTTP client library for making REST API calls
- **Usage**: Used in `MainViewController` to fetch cryptocurrency prices from Gemini API
- **Features**: Modern HTTP client with connection pooling, request/response caching, and automatic GZIP

#### **Jackson Databind 2.16.0**
```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.16.0</version>
</dependency>
```
- **Purpose**: JSON parsing and serialization library
- **Usage**: Converts JSON responses from Gemini API into Java objects
- **Features**: High-performance JSON processing with streaming API support

#### **JavaFX Controls 21**
```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>21</version>
</dependency>
```
- **Purpose**: JavaFX UI controls (buttons, tables, labels, etc.)
- **Usage**: Provides the table view and refresh button in the application
- **Features**: Modern Java GUI toolkit with rich UI components

#### **JavaFX FXML 21**
```xml
<dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-fxml</artifactId>
    <version>21</version>
</dependency>
```
- **Purpose**: FXML (FX Markup Language) support for declarative UI design
- **Usage**: Loads the UI layout from `mainView.fxml` file
- **Features**: Separates UI design from Java code for better maintainability

### Build Plugins

#### **Maven Compiler Plugin 3.11.0**
```xml
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.11.0</version>
    <configuration>
        <source>21</source>
        <target>21</target>
    </configuration>
</plugin>
```
- **Purpose**: Compiles Java source code
- **Configuration**: Set to Java 21 for modern language features

#### **JavaFX Maven Plugin 0.0.8**
```xml
<plugin>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-maven-plugin</artifactId>
    <version>0.0.8</version>
    <configuration>
        <mainClass>com.acu.javafx.coins/com.acu.javafx.coins.CryptoPriceTrackerApp</mainClass>
    </configuration>
</plugin>
```
- **Purpose**: Runs JavaFX applications
- **Configuration**: Specifies the main class and creates executable JARs

## 📁 Project Structure

```
50-01-RESTful-API-Gemini/
├── pom.xml                                    # Maven configuration
├── README.md                                  # This file
├── src/
│   └── main/
│       ├── java/
│       │   ├── module-info.java              # Java module configuration
│       │   └── com/
│       │       └── acu/
│       │           └── javafx/
│       │               └── coins/
│       │                   ├── CryptoPriceTrackerApp.java    # Main application class
│       │                   ├── controllers/
│       │                   │   └── MainViewController.java   # UI controller
│       │                   └── models/
│       │                       └── CoinPrice.java            # Price data model
│       └── resources/
│           └── com/
│               └── acu/
│                   └── javafx/
│                       └── coins/
│                           ├── mainView.fxml                 # UI layout
│                           └── css/
│                               └── styles.css                # UI styling
```

## 🏗️ Architecture

### **MVC Pattern**
- **Models**: `CoinPrice` for data representation
- **Views**: FXML-based UI with custom CSS styling
- **Controllers**: `MainViewController` handles business logic and API calls

### **Key Components**
- **CryptoPriceTrackerApp**: JavaFX application entry point
- **MainViewController**: Manages API calls, UI updates, and user interactions
- **CoinPrice**: Record class for cryptocurrency price data

## 🚀 How to Run the Project

### Prerequisites
- **Java 21** or higher
- **Maven 3.6** or higher
- **Internet connection** (for API calls)

### Step-by-Step Instructions

#### 1. **Clone the Repository**
```bash
git clone <repository-url>
cd 50-01-RESTful-API-Gemini
```

#### 2. **Verify Java Installation**
```bash
java --version
# Should show Java 21 or higher
```

#### 3. **Verify Maven Installation**
```bash
mvn --version
# Should show Maven 3.6 or higher
```

#### 4. **Build and Run the Application**

**Option A: Single Command (Recommended)**
```bash
mvn clean compile javafx:run
```

**Option B: Step by Step**
```bash
# Clean previous builds
mvn clean

# Compile the project
mvn compile

# Run the JavaFX application
mvn javafx:run
```

**Option C: Just Compile**
```bash
mvn clean compile
```

**Option D: Just Run (if already compiled)**
```bash
mvn javafx:run
```

### Expected Output

When you run the application successfully, you should see:
1. **Maven build output** showing successful compilation
2. **JavaFX application window** opening with the cryptocurrency price table
3. **Price data** loading in the table (some symbols may show errors due to API limitations)

### Troubleshooting

#### **Common Issues:**

1. **Java Version Error**
   ```bash
   # Error: "release version 21 not supported"
   # Solution: Install Java 21
   brew install openjdk@21  # macOS
   # or download from Oracle/OpenJDK website
   ```

2. **Maven Not Found**
   ```bash
   # Error: "mvn: command not found"
   # Solution: Install Maven
   brew install maven  # macOS
   # or download from Apache Maven website
   ```

3. **JavaFX Runtime Error**
   ```bash
   # Error: "JavaFX runtime components are missing"
   # Solution: Ensure JavaFX dependencies are properly configured
   # The pom.xml should handle this automatically
   ```

4. **API Connection Issues**
   ```bash
   # Error: "Error fetching price for [SYMBOL]"
   # This is normal for some symbols not available on Gemini
   # The application will continue to work with available symbols
   ```

## 🎨 UI Features

### **Main Interface**
- **Table View**: Displays cryptocurrency symbols, prices, and timestamps
- **Refresh Button**: Updates all prices with latest data
- **Responsive Design**: Adapts to window resizing
- **Professional Styling**: Clean, modern appearance

### **Data Display**
- **Symbol Column**: Cryptocurrency ticker symbols
- **Price Column**: Current price in USD with currency formatting
- **Timestamp Column**: Last update time in readable format

## 🔧 Configuration

### **API Endpoints**
The application uses Gemini's public API:
- Base URL: `https://api.gemini.com/v1/pubticker/`
- Format: `{symbol}usd` (e.g., `btcusd`, `ethusd`)

### **Supported Symbols**
The application is configured to fetch prices for:
```java
private final String[] symbols = {"BTC", "ETH", "XRP", "SOL", "DOGE", "BCH", "LTC", "TRUMP"};
```

## 🐛 Error Handling

- **API Failures**: Individual symbol failures don't affect others
- **Network Issues**: Graceful degradation with error logging
- **Invalid Data**: Robust JSON parsing with fallbacks
- **UI Responsiveness**: Non-blocking API calls using JavaFX Tasks

## 📈 Performance Features

- **Concurrent API Calls**: Parallel fetching for faster loading
- **Background Processing**: Non-blocking UI during API calls
- **Efficient Updates**: Only refreshes changed data
- **Memory Management**: Proper resource cleanup

## 🔄 Build Process

The project uses Maven with the following key plugins:
- **maven-compiler-plugin**: Java 21 compilation
- **javafx-maven-plugin**: JavaFX application packaging and running

## 📝 Development Notes

### **Module System**
The project uses Java 9+ module system for better encapsulation:
```java
open module com.acu.javafx.coins {
    requires javafx.controls;      // JavaFX UI controls
    requires javafx.fxml;          // JavaFX FXML support
    requires okhttp3;              // HTTP client for API calls
    requires com.fasterxml.jackson.databind;  // JSON parsing library
}
```

### **Dependency Management**
- **OkHttp**: HTTP client library for making REST API calls to the Gemini exchange
- **Jackson**: JSON parsing library for converting API responses into Java objects
- **JavaFX Controls**: UI components like buttons, tables, and labels for the desktop interface
- **JavaFX FXML**: Declarative UI framework for defining the application layout in XML format

## 🎯 Future Enhancements

Potential improvements for the application:
- Real-time price updates with WebSocket
- Price alerts and notifications
- Historical price charts
- Additional cryptocurrency exchanges
- Price comparison features

