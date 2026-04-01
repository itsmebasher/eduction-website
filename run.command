#!/bin/zsh
# Education Platform - macOS Launch Script
# This script runs the Education Platform JavaFX application
# For macOS with better error handling

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if Java is installed
if ! command -v java &> /dev/null; then
    echo -e "${RED}❌ Error: Java is not installed.${NC}"
    echo "Please install Java 17 or higher from: https://www.oracle.com/java/technologies/downloads/"
    echo ""
    echo "Or using Homebrew:"
    echo "  brew install openjdk@17"
    exit 1
fi

# Check Java version
JAVA_VERSION=$(java -version 2>&1 | grep -oP '(?<=")[0-9]+' | head -1)
if [ "$JAVA_VERSION" -lt 17 ]; then
    echo -e "${RED}❌ Error: Java 17 or higher is required (found Java $JAVA_VERSION)${NC}"
    exit 1
fi

# Get the directory where this script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

# Launch the application
echo -e "${GREEN}🚀 Starting Education Platform...${NC}"
java -jar "$SCRIPT_DIR/education-platform-v1.0.0.jar"
