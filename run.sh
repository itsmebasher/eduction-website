#!/bin/bash
# Education Platform - Linux/Mac Launch Script
# This script runs the Education Platform JavaFX application

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed."
    echo "Please install Java 17 or higher from: https://www.oracle.com/java/technologies/downloads/"
    exit 1
fi

#!/bin/bash
# Education Platform - Linux/Mac Launch Script
# This script runs the Education Platform JavaFX application

echo "🎓 Education Platform Launcher"
echo "================================"

# Check if we're in a headless environment
if [ -n "$DISPLAY" ]; then
    echo "✅ GUI environment detected"
else
    echo "⚠️  Headless environment detected (no GUI)"
    echo ""
    echo "📋 JavaFX applications require a graphical desktop environment."
    echo ""
    echo "🔧 Solutions:"
    echo "1. Run locally on your computer with a GUI"
    echo "2. Use the web version: cd web && npm start"
    echo "3. Deploy web version to Railway (see DEPLOYMENT_GUIDE.md)"
    echo ""
    echo "🌐 Web version is running at: http://localhost:3000"
    echo "   (if you started it with: cd web && npm start)"
    echo ""
    echo "🚀 To deploy web version online:"
    echo "   1. Fork this repo on GitHub"
    echo "   2. Connect to Railway.app"
    echo "   3. Deploy from your forked repo"
    echo ""
    exit 1
fi

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed."
    echo "Please install Java 21 or higher from: https://adoptium.net/"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | grep -oP '(?<=")[0-9]+' | head -1)
if [ "$JAVA_VERSION" -lt 21 ]; then
    echo "❌ Error: Java 21 or higher is required (found Java $JAVA_VERSION)"
    echo "Please update Java from: https://adoptium.net/"
    exit 1
fi

# Get the directory where this script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

echo "📦 Building application..."
cd "$SCRIPT_DIR"
mvn clean compile -q

echo "🚀 Starting Education Platform..."
echo "   Close this window to stop the application"
echo ""

# Run with JavaFX Maven plugin
mvn javafx:run
