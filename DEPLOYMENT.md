# 🚀 Deployment Guide - Education Platform

## Quick Start - Download & Run

### Prerequisites
- **Java 17 or higher** installed
- Check: `java -version`

### On Linux/Mac
```bash
# Download the JAR
wget https://github.com/itsmebasher/eduction-website/releases/download/v1.0.0/education-platform-v1.0.0.jar

# Run it
java -jar education-platform-v1.0.0.jar

# OR use the launch script
./run.sh
```

### On Windows
```cmd
# Download education-platform-v1.0.0.jar and run.bat

# Double-click run.bat
# OR from command line:
java -jar education-platform-v1.0.0.jar
```

### On macOS
```bash
# Download and run
java -jar education-platform-v1.0.0.jar

# OR use the launch script
./run.command
```

---

## Installation Methods

### Method 1: Direct JAR Execution (Simplest)
```bash
# Just run the JAR file
java -jar education-platform-v1.0.0.jar
```

**Advantages:**
- No installation needed
- Works on Windows, Mac, Linux
- Portable - copy anywhere

### Method 2: Using Launch Scripts

**Linux/Mac:**
```bash
chmod +x run.sh
./run.sh
```

**Windows:**
- Double-click `run.bat`

**macOS:**
- Double-click `run.command`
- Or: `chmod +x run.command && ./run.command`

### Method 3: Create Desktop Shortcut

**Windows:**
1. Create shortcut to `run.bat`
2. Right-click → Properties
3. Set working directory to the folder location
4. Pin to Start Menu

**Linux (GNOME):**
```bash
cat > ~/.local/share/applications/education-platform.desktop << EOF
[Desktop Entry]
Version=1.0
Type=Application
Name=Education Platform
Exec=/path/to/run.sh
Icon=application-x-executable
Categories=Education;Application
EOF
```

**macOS:**
- Drag `run.command` to Applications folder
- Or create an alias

---

## Deploying to GitHub Releases

### Step 1: Prepare Release Files
```bash
# Create a release directory
mkdir -p release/v1.0.0
cd release/v1.0.0

# Copy the JAR
cp ../../education-platform-v1.0.0.jar .

# Copy launch scripts
cp ../../run.sh .
cp ../../run.bat .
cp ../../run.command .

# Make scripts executable
chmod +x run.sh run.command

# Create README for release
cat > README.txt << 'EOF'
Education Platform v1.0.0

SYSTEM REQUIREMENTS:
- Java 17 or higher
- Download from: https://www.oracle.com/java/technologies/downloads/

HOW TO RUN:

Linux/Mac:
  ./run.sh

Windows:
  Double-click run.bat
  Or: java -jar education-platform-v1.0.0.jar

macOS:
  ./run.command
  Or: java -jar education-platform-v1.0.0.jar

Direct command (all platforms):
  java -jar education-platform-v1.0.0.jar

For more info: https://github.com/itsmebasher/eduction-website
EOF
```

### Step 2: Create GitHub Release

```bash
# Using GitHub CLI (gh)
cd release/v1.0.0

gh release create v1.0.0 \
  --title "Education Platform v1.0.0" \
  --notes "Initial release with all features" \
  education-platform-v1.0.0.jar \
  run.sh \
  run.bat \
  run.command \
  README.txt
```

Or manually:
1. Go to GitHub: https://github.com/itsmebasher/eduction-website
2. Click "Releases" → "Draft a new release"
3. Tag: `v1.0.0`
4. Title: "Education Platform v1.0.0"
5. Description: "Initial release with all features"
6. Upload files:
   - education-platform-v1.0.0.jar
   - run.sh
   - run.bat
   - run.command
   - README.txt
7. Publish

---

## Deployment Checklist

- [ ] Java 17+ installed on target system
- [ ] JAR file copied (62MB)
- [ ] Launch scripts copied and executable
- [ ] README with instructions created
- [ ] GitHub release created with all files
- [ ] Database created on first run
- [ ] Test login works
- [ ] Test all features (quizzes, forums, videos)

---

## Troubleshooting

### Issue: "Java not found"
**Solution:**
```bash
# Check Java installation
java -version

# Install Java 17 if needed
# Windows/Mac: https://www.oracle.com/java/technologies/downloads/
# Linux (Ubuntu/Debian):
sudo apt install openjdk-17-jre-headless
```

### Issue: "Permission denied" (Linux/Mac)
**Solution:**
```bash
chmod +x run.sh run.command
./run.sh
```

### Issue: Application won't start
**Solution:**
- Check Java version: `java -version`
- Delete `education_platform.db` to reset database
- Run with verbose logging: `java -verbose:gc -jar education-platform-v1.0.0.jar`

### Issue: Out of Memory
**Solution:**
```bash
# Allocate more memory
java -Xmx2048m -jar education-platform-v1.0.0.jar
```

### Issue: Database locked error
**Solution:**
- Close any running instances
- Delete `education_platform.db`
- Restart application

---

## System Requirements

| Component | Requirement |
|-----------|-------------|
| Java | 17 or higher |
| RAM | 512MB minimum, 2GB recommended |
| Disk Space | 100MB for application + database |
| OS | Windows 7+, Mac OS 10.12+, Linux (any) |

---

## File Sizes

| File | Size |
|------|------|
| education-platform-v1.0.0.jar | 62 MB |
| run.sh | 805 B |
| run.bat | 977 B |
| run.command | 1.1 KB |
| Total with data | ~70 MB |

---

## Updating to New Version

```bash
# Download new JAR
wget https://github.com/itsmebasher/eduction-website/releases/download/v1.1.0/education-platform-v1.1.0.jar

# Keep old database (optional)
# Or delete to start fresh:
# rm education_platform.db

# Run new version
java -jar education-platform-v1.1.0.jar
```

---

## Creating Standalone Distribution Package

### ZIP package (all platforms)
```bash
mkdir -p education-platform-v1.0.0
cd education-platform-v1.0.0

cp /path/to/education-platform-v1.0.0.jar .
cp /path/to/run.sh .
cp /path/to/run.bat .
cp /path/to/run.command .

cat > README.txt << 'EOF'
Education Platform v1.0.0
Requirements: Java 17+

To run:
- Linux/Mac: ./run.sh
- Windows: run.bat
EOF

cd ..
zip -r education-platform-v1.0.0.zip education-platform-v1.0.0/
```

### TAR package (Linux/Mac)
```bash
tar -czf education-platform-v1.0.0.tar.gz education-platform-v1.0.0/
```

---

## Deployment Architecture

```
Distribution Package:
├── education-platform-v1.0.0.jar    (62 MB - Main application)
├── run.sh                            (Linux/Mac launcher)
├── run.bat                           (Windows launcher)
├── run.command                       (macOS double-click launcher)
└── README.txt                        (Instructions)

User Installation:
├── Extract/Download package
├── Ensure Java 17+ is installed
├── Run launcher script or JAR
└── Database created automatically: education_platform.db
```

---

## Deployment Diagram

```
GitHub Repository
        ↓
  GitHub Release v1.0.0
        ↓
  Upload JAR + Scripts
        ↓
  User Downloads Package
        ↓
  User Runs: java -jar education-platform-v1.0.0.jar
        ↓
  Application Starts
        ↓
  Database initialized on first run
        ↓
  User registers/logs in
```

---

## Support & Feedback

- **GitHub Issues**: https://github.com/itsmebasher/eduction-website/issues
- **Email**: itsmebasher@github.com
- **Documentation**: See README.md and QUICKSTART.md

---

## Versioning

Current Version: **1.0.0**  
Release Date: April 1, 2026  
Status: Production Ready ✅

---

*For development/building from source, see README.md*