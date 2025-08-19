# Deployment Guide

This guide provides comprehensive information about building, packaging, and deploying the JavaFX Employee Database Management System.

## Table of Contents

- [Build Configuration](#build-configuration)
- [Packaging Options](#packaging-options)
- [Distribution Methods](#distribution-methods)
- [Installation Procedures](#installation-procedures)
- [Runtime Requirements](#runtime-requirements)
- [Configuration Management](#configuration-management)
- [Monitoring and Logging](#monitoring-and-logging)
- [Troubleshooting](#troubleshooting)

## Build Configuration

### Maven Build Configuration

The project uses Maven for build management with the following key configurations:

#### Core Dependencies
```xml
<dependencies>
    <!-- JavaFX Controls -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-controls</artifactId>
        <version>24.0.2</version>
    </dependency>
    
    <!-- JavaFX FXML -->
    <dependency>
        <groupId>org.openjfx</groupId>
        <artifactId>javafx-fxml</artifactId>
        <version>24.0.2</version>
    </dependency>
    
    <!-- SQLite JDBC Driver -->
    <dependency>
        <groupId>org.xerial</groupId>
        <artifactId>sqlite-jdbc</artifactId>
        <version>3.45.1.0</version>
    </dependency>
</dependencies>
```

#### Build Plugins
```xml
<build>
    <plugins>
        <!-- Maven Compiler Plugin -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.13.0</version>
            <configuration>
                <release>21</release>
            </configuration>
        </plugin>
        
        <!-- Maven Shade Plugin (Fat JAR) -->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-shade-plugin</artifactId>
            <version>3.5.1</version>
            <executions>
                <execution>
                    <phase>package</phase>
                    <goals>
                        <goal>shade</goal>
                    </goals>
                    <configuration>
                        <shadedArtifactAttached>true</shadedArtifactAttached>
                        <shadedClassifierName>fat</shadedClassifierName>
                        <createDependencyReducedPom>true</createDependencyReducedPom>
                        <transformers>
                            <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                <mainClass>com.acu.javafx.database.EmployeeDatabaseApp</mainClass>
                            </transformer>
                        </transformers>
                    </configuration>
                </execution>
            </executions>
        </plugin>
        
        <!-- JavaFX Maven Plugin -->
        <plugin>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-maven-plugin</artifactId>
            <version>0.0.8</version>
            <configuration>
                <mainClass>com.acu.javafx.database.EmployeeDatabaseApp</mainClass>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### Build Commands

#### Development Build
```bash
# Clean and compile
mvn clean compile

# Run tests
mvn test

# Package without dependencies
mvn package
```

#### Production Build
```bash
# Clean build with tests
mvn clean verify

# Create fat JAR with dependencies
mvn clean package

# Create distribution package
mvn clean package assembly:single
```

## Packaging Options

### 1. Fat JAR (Recommended)

#### Advantages
- Single executable file
- Includes all dependencies
- Easy distribution
- No external dependencies required

#### Build Command
```bash
mvn clean package
```

#### Output
- `target/JavaFX-Database-1.0-fat.jar`

#### Usage
```bash
java -jar target/JavaFX-Database-1.0-fat.jar
```

### 2. JLink Custom Runtime

#### Advantages
- Smaller runtime footprint
- Optimized for specific modules
- Faster startup time

#### Build Process
```bash
# Create custom runtime image
jlink --module-path $JAVA_HOME/jmods \
      --add-modules java.base,java.sql,javafx.controls,javafx.fxml \
      --output custom-runtime

# Run with custom runtime
./custom-runtime/bin/java -jar target/JavaFX-Database-1.0.jar
```

### 3. Native Image (Experimental)

#### Using GraalVM Native Image
```bash
# Install GraalVM and native-image
gu install native-image

# Build native image
native-image -jar target/JavaFX-Database-1.0-fat.jar \
             --no-fallback \
             --initialize-at-build-time=javafx \
             employeedb
```

## Distribution Methods

### 1. Direct JAR Distribution

#### Package Structure
```
JavaFX-Database-1.0/
├── JavaFX-Database-1.0-fat.jar
├── run.sh
├── run.bat
├── README.md
├── LICENSE
└── docs/
    ├── README.md
    ├── API_REFERENCE.md
    └── DEPLOYMENT_GUIDE.md
```

#### Distribution Scripts

##### Unix/Linux/macOS (run.sh)
```bash
#!/bin/bash

# JavaFX Employee Database Management System Launcher

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "Error: Java is not installed or not in PATH"
    echo "Please install Java 21 or higher"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
if [ "$JAVA_VERSION" -lt 21 ]; then
    echo "Error: Java 21 or higher is required"
    echo "Current version: $JAVA_VERSION"
    exit 1
fi

# Set JavaFX module path (if needed)
JAVAFX_PATH=""
if [ -d "/usr/share/openjfx/lib" ]; then
    JAVAFX_PATH="/usr/share/openjfx/lib"
elif [ -d "/opt/openjfx/lib" ]; then
    JAVAFX_PATH="/opt/openjfx/lib"
fi

# Launch application
if [ -n "$JAVAFX_PATH" ]; then
    java --module-path "$JAVAFX_PATH" \
         --add-modules javafx.controls,javafx.fxml \
         -jar JavaFX-Database-1.0-fat.jar
else
    java -jar JavaFX-Database-1.0-fat.jar
fi
```

##### Windows (run.bat)
```batch
@echo off
REM JavaFX Employee Database Management System Launcher

REM Check if Java is installed
java -version >nul 2>&1
if errorlevel 1 (
    echo Error: Java is not installed or not in PATH
    echo Please install Java 21 or higher
    pause
    exit /b 1
)

REM Check Java version
for /f "tokens=3" %%g in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set JAVA_VERSION=%%g
    goto :check_version
)

:check_version
set JAVA_VERSION=%JAVA_VERSION:~1,2%
if %JAVA_VERSION% LSS 21 (
    echo Error: Java 21 or higher is required
    echo Current version: %JAVA_VERSION%
    pause
    exit /b 1
)

REM Launch application
java -jar JavaFX-Database-1.0-fat.jar
pause
```

### 2. Installer Packages

#### Linux DEB Package
```bash
# Create DEB package structure
mkdir -p employeedb_1.0-1/usr/local/bin
mkdir -p employeedb_1.0-1/usr/share/applications
mkdir -p employeedb_1.0-1/usr/share/employeedb

# Copy files
cp JavaFX-Database-1.0-fat.jar employeedb_1.0-1/usr/share/employeedb/
cp run.sh employeedb_1.0-1/usr/local/bin/employeedb
chmod +x employeedb_1.0-1/usr/local/bin/employeedb

# Create desktop entry
cat > employeedb_1.0-1/usr/share/applications/employeedb.desktop << EOF
[Desktop Entry]
Version=1.0
Type=Application
Name=Employee Database
Comment=Employee Database Management System
Exec=/usr/local/bin/employeedb
Icon=applications-office
Terminal=false
Categories=Office;
EOF

# Create control file
mkdir -p employeedb_1.0-1/DEBIAN
cat > employeedb_1.0-1/DEBIAN/control << EOF
Package: employeedb
Version: 1.0-1
Section: office
Priority: optional
Architecture: all
Depends: openjdk-21-jre | java-runtime
Maintainer: Developer <developer@example.com>
Description: Employee Database Management System
 A JavaFX application for managing employee records
 with SQLite database backend.
EOF

# Build DEB package
dpkg-deb --build employeedb_1.0-1
```

#### Windows MSI Package
```xml
<!-- Using WiX Toolset -->
<?xml version="1.0" encoding="UTF-8"?>
<Wix xmlns="http://schemas.microsoft.com/wix/2006/wi">
    <Product Id="*" 
             Name="Employee Database Management System" 
             Language="1033" 
             Version="1.0.0.0" 
             Manufacturer="ACU" 
             UpgradeCode="PUT-GUID-HERE">
        
        <Package InstallerVersion="200" 
                 Compressed="yes" 
                 InstallScope="perMachine" />
        
        <MajorUpgrade DowngradeErrorMessage="A newer version is already installed." />
        <MediaTemplate EmbedCab="yes" />
        
        <Feature Id="ProductFeature" Title="Employee Database" Level="1">
            <ComponentGroupRef Id="ProductComponents" />
        </Feature>
        
        <UIRef Id="WixUI_Minimal" />
    </Product>
    
    <Fragment>
        <Directory Id="TARGETDIR" Name="SourceDir">
            <Directory Id="ProgramFilesFolder">
                <Directory Id="INSTALLFOLDER" Name="EmployeeDB" />
            </Directory>
        </Directory>
    </Fragment>
    
    <Fragment>
        <ComponentGroup Id="ProductComponents" Directory="INSTALLFOLDER">
            <Component Id="ApplicationExecutable" Guid="*">
                <File Id="ApplicationFile" 
                      Name="JavaFX-Database-1.0-fat.jar" 
                      Source="JavaFX-Database-1.0-fat.jar" />
            </Component>
            <Component Id="ApplicationLauncher" Guid="*">
                <File Id="LauncherFile" 
                      Name="employeedb.bat" 
                      Source="run.bat" />
                <Shortcut Id="StartMenuShortcut" 
                          Directory="ProgramMenuDir" 
                          Name="Employee Database" 
                          WorkingDirectory="INSTALLFOLDER" 
                          Icon="ApplicationIcon.ico" 
                          IconIndex="0" 
                          Advertise="yes" />
            </Component>
        </ComponentGroup>
    </Fragment>
</Wix>
```

### 3. Docker Container

#### Dockerfile
```dockerfile
# Use OpenJDK 21 with JavaFX
FROM openjdk:21-jdk

# Install JavaFX
RUN apt-get update && apt-get install -y \
    openjfx \
    && rm -rf /var/lib/apt/lists/*

# Create application directory
WORKDIR /app

# Copy application JAR
COPY target/JavaFX-Database-1.0-fat.jar app.jar

# Create non-root user
RUN useradd -r -s /bin/false appuser && chown -R appuser:appuser /app
USER appuser

# Expose port (if needed for web interface)
EXPOSE 8080

# Set entrypoint
ENTRYPOINT ["java", "-jar", "app.jar"]
```

#### Docker Compose
```yaml
version: '3.8'

services:
  employeedb:
    build: .
    container_name: employee-database
    volumes:
      - ./data:/app/data
      - ./logs:/app/logs
    environment:
      - JAVA_OPTS=-Xmx512m
    restart: unless-stopped
```

## Installation Procedures

### System Requirements

#### Minimum Requirements
- **Operating System**: Windows 10+, macOS 10.14+, or Linux (Ubuntu 18.04+)
- **Java Runtime**: OpenJDK 21 or Oracle JDK 21
- **Memory**: 512 MB RAM
- **Storage**: 100 MB free space
- **Display**: 1024x768 resolution

#### Recommended Requirements
- **Operating System**: Latest stable version
- **Java Runtime**: OpenJDK 21 with JavaFX
- **Memory**: 2 GB RAM
- **Storage**: 1 GB free space
- **Display**: 1920x1080 resolution

### Installation Steps

#### 1. Prerequisites Installation

##### Windows
```powershell
# Install Chocolatey (if not installed)
Set-ExecutionPolicy Bypass -Scope Process -Force
[System.Net.ServicePointManager]::SecurityProtocol = [System.Net.ServicePointManager]::SecurityProtocol -bor 3072
iex ((New-Object System.Net.WebClient).DownloadString('https://community.chocolatey.org/install.ps1'))

# Install Java 21
choco install openjdk21

# Install JavaFX (if needed)
choco install openjfx
```

##### macOS
```bash
# Install Homebrew (if not installed)
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# Install Java 21
brew install openjdk@21

# Link Java
sudo ln -sfn /opt/homebrew/opt/openjdk@21/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-21.jdk
```

##### Linux (Ubuntu/Debian)
```bash
# Update package list
sudo apt update

# Install Java 21
sudo apt install openjdk-21-jdk

# Install JavaFX
sudo apt install openjfx
```

#### 2. Application Installation

##### Manual Installation
```bash
# Download and extract
wget https://github.com/your-repo/JavaFX-Database/releases/download/v1.0/JavaFX-Database-1.0.zip
unzip JavaFX-Database-1.0.zip
cd JavaFX-Database-1.0

# Make executable
chmod +x run.sh

# Run application
./run.sh
```

##### Package Manager Installation

###### Ubuntu/Debian
```bash
# Install DEB package
sudo dpkg -i employeedb_1.0-1_all.deb

# Fix dependencies (if needed)
sudo apt-get install -f

# Run application
employeedb
```

###### Windows
```cmd
# Install MSI package
msiexec /i EmployeeDB-1.0.msi /quiet

# Run from Start Menu or
"C:\Program Files\EmployeeDB\employeedb.bat"
```

## Runtime Requirements

### Java Runtime Configuration

#### JVM Options
```bash
# Basic JVM options
java -Xms256m -Xmx1024m -jar JavaFX-Database-1.0-fat.jar

# With garbage collection tuning
java -Xms512m -Xmx2048m \
     -XX:+UseG1GC \
     -XX:MaxGCPauseMillis=200 \
     -jar JavaFX-Database-1.0-fat.jar

# With JavaFX modules
java --module-path /path/to/javafx/lib \
     --add-modules javafx.controls,javafx.fxml \
     -jar JavaFX-Database-1.0-fat.jar
```

#### Environment Variables
```bash
# Set Java home
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk

# Set JavaFX path
export JAVAFX_PATH=/usr/share/openjfx/lib

# Set application data directory
export EMPLOYEE_DB_DATA_DIR=/var/lib/employeedb

# Set log level
export LOG_LEVEL=INFO
```

### Database Configuration

#### SQLite Configuration
```java
// Database file location
private static final String DB_URL = "jdbc:sqlite:employee.sqlite";

// For production, use absolute path
private static final String DB_URL = "jdbc:sqlite:/var/lib/employeedb/employee.sqlite";

// With additional SQLite options
private static final String DB_URL = "jdbc:sqlite:/var/lib/employeedb/employee.sqlite" +
    "?journal_mode=WAL" +
    "&synchronous=NORMAL" +
    "&cache_size=10000" +
    "&temp_store=MEMORY";
```

## Configuration Management

### Application Properties

#### Configuration File (application.properties)
```properties
# Database Configuration
database.url=jdbc:sqlite:employee.sqlite
database.username=
database.password=

# Application Configuration
app.title=Employee Database Management System
app.version=1.0
app.debug=false

# UI Configuration
ui.window.width=900
ui.window.height=600
ui.theme=default

# Logging Configuration
logging.level=INFO
logging.file=employeedb.log
logging.max.size=10MB
logging.max.files=5
```

#### Configuration Loading
```java
public class ConfigurationManager {
    private static Properties properties = new Properties();
    
    public static void loadConfiguration() {
        try (InputStream input = new FileInputStream("application.properties")) {
            properties.load(input);
        } catch (IOException e) {
            // Use default configuration
            loadDefaultConfiguration();
        }
    }
    
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
    
    private static void loadDefaultConfiguration() {
        properties.setProperty("database.url", "jdbc:sqlite:employee.sqlite");
        properties.setProperty("app.title", "Employee Database Management System");
        properties.setProperty("logging.level", "INFO");
    }
}
```

### Environment-Specific Configuration

#### Development Configuration
```properties
# Development settings
database.url=jdbc:sqlite:dev_employee.sqlite
logging.level=DEBUG
app.debug=true
```

#### Production Configuration
```properties
# Production settings
database.url=jdbc:sqlite:/var/lib/employeedb/employee.sqlite
logging.level=WARN
app.debug=false
```

## Monitoring and Logging

### Logging Configuration

#### Logback Configuration (logback.xml)
```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <appender name="CONSOLE" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <appender name="FILE" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>logs/employeedb.log</file>
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <fileNamePattern>logs/employeedb.%d{yyyy-MM-dd}.%i.log</fileNamePattern>
            <timeBasedFileNamingAndTriggeringPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedFNATP">
                <maxFileSize>10MB</maxFileSize>
            </timeBasedFileNamingAndTriggeringPolicy>
            <maxHistory>30</maxHistory>
        </rollingPolicy>
        <encoder>
            <pattern>%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>
    
    <root level="INFO">
        <appender-ref ref="CONSOLE" />
        <appender-ref ref="FILE" />
    </root>
</configuration>
```

### Health Monitoring

#### Health Check Endpoint
```java
public class HealthMonitor {
    private static final Logger logger = LoggerFactory.getLogger(HealthMonitor.class);
    
    public static boolean isDatabaseHealthy() {
        try (Connection conn = DriverManager.getConnection(DatabaseManager.DB_URL)) {
            return conn.isValid(5);
        } catch (SQLException e) {
            logger.error("Database health check failed", e);
            return false;
        }
    }
    
    public static boolean isApplicationHealthy() {
        // Check application state
        return true;
    }
    
    public static void logSystemInfo() {
        Runtime runtime = Runtime.getRuntime();
        logger.info("System Information:");
        logger.info("  Total Memory: {} MB", runtime.totalMemory() / 1024 / 1024);
        logger.info("  Free Memory: {} MB", runtime.freeMemory() / 1024 / 1024);
        logger.info("  Max Memory: {} MB", runtime.maxMemory() / 1024 / 1024);
        logger.info("  Available Processors: {}", runtime.availableProcessors());
    }
}
```

## Troubleshooting

### Common Issues

#### Java Version Issues
```bash
# Check Java version
java -version

# If wrong version, set JAVA_HOME
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk
export PATH=$JAVA_HOME/bin:$PATH
```

#### JavaFX Module Issues
```bash
# Check if JavaFX is installed
ls /usr/share/openjfx/lib/

# If not installed, install JavaFX
sudo apt install openjfx  # Ubuntu/Debian
brew install openjfx      # macOS
```

#### Database Permission Issues
```bash
# Check database file permissions
ls -la employee.sqlite

# Fix permissions if needed
chmod 644 employee.sqlite
chown $USER:$USER employee.sqlite
```

#### Memory Issues
```bash
# Increase heap size
java -Xmx2048m -jar JavaFX-Database-1.0-fat.jar

# Monitor memory usage
jstat -gc <pid>
```

### Debug Mode

#### Enable Debug Logging
```bash
# Set debug level
export LOG_LEVEL=DEBUG

# Run with debug options
java -Dlog.level=DEBUG -jar JavaFX-Database-1.0-fat.jar
```

#### Database Debugging
```bash
# Use SQLite command line tool
sqlite3 employee.sqlite

# Check database schema
.schema

# Check data
SELECT * FROM employees;

# Check database integrity
PRAGMA integrity_check;
```

### Performance Tuning

#### JVM Tuning
```bash
# Optimize for desktop application
java -XX:+UseG1GC \
     -XX:MaxGCPauseMillis=200 \
     -XX:+UseStringDeduplication \
     -jar JavaFX-Database-1.0-fat.jar
```

#### SQLite Tuning
```java
// Enable WAL mode for better concurrency
String sql = "PRAGMA journal_mode=WAL;";
stmt.execute(sql);

// Increase cache size
sql = "PRAGMA cache_size=10000;";
stmt.execute(sql);

// Use memory for temporary storage
sql = "PRAGMA temp_store=MEMORY;";
stmt.execute(sql);
```

---

This deployment guide should be updated as the project evolves. For specific deployment questions or issues, refer to the troubleshooting section or contact the development team.
