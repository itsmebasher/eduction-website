#!/bin/bash
# Education Platform - Linux/Mac Launch Script
# This script runs the Education Platform JavaFX application

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo "❌ Error: Java is not installed."
    echo "Please install Java 17 or higher from: https://www.oracle.com/java/technologies/downloads/"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | grep -oP '(?<=")[0-9]+' | head -1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo "❌ Error: Java 17 or higher is required (found Java $JAVA_VERSION)"
    exit 1
fi

# Get the directory where this script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Launch the application
echo "🚀 Starting Education Platform..."
java -jar "$SCRIPT_DIR/education-platform-v1.0.0.jar"
