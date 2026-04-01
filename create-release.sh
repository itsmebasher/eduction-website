#!/bin/bash
# GitHub Release Script - Education Platform v1.0.0
# This script helps create and publish a GitHub release

echo "📦 Education Platform Release Preparation"
echo "=========================================="
echo ""

# Check if gh CLI is installed
if ! command -v gh &> /dev/null; then
    echo "⚠️  GitHub CLI (gh) is not installed"
    echo "Install from: https://cli.github.com/"
    echo ""
    echo "Manual release steps:"
    echo "1. Go to: https://github.com/itsmebasher/eduction-website/releases"
    echo "2. Click 'Draft a new release'"
    echo "3. Tag: v1.0.0"
    echo "4. Title: Education Platform v1.0.0"
    echo "5. Upload files (see below)"
    echo ""
    exit 1
fi

# Check git status
echo "✓ Checking git status..."
if [ -z "$(git status --porcelain)" ]; then
    echo "✓ Working directory clean"
else
    echo "⚠️  Uncommitted changes detected"
    read -p "Continue anyway? (y/n) " -n 1 -r
    echo
    if [[ ! $REPLY =~ ^[Yy]$ ]]; then
        exit 1
    fi
fi

# Prepare release files
echo ""
echo "📁 Preparing release directory..."
RELEASE_DIR="release/v1.0.0"
mkdir -p "$RELEASE_DIR"

# Copy files
echo "📋 Copying release files..."
cp education-platform-v1.0.0.jar "$RELEASE_DIR/" 2>/dev/null && echo "✓ JAR" || echo "✗ JAR not found"
cp run.sh "$RELEASE_DIR/" 2>/dev/null && echo "✓ Linux/Mac launcher" || echo "! Script not found"
cp run.bat "$RELEASE_DIR/" 2>/dev/null && echo "✓ Windows launcher" || echo "! Script not found"
cp run.command "$RELEASE_DIR/" 2>/dev/null && echo "✓ macOS launcher" || echo "! Script not found"
cp RELEASE_NOTES.txt "$RELEASE_DIR/" 2>/dev/null && echo "✓ Release notes" || echo "! Notes not found"
cp README.md "$RELEASE_DIR/README-FULL.md" 2>/dev/null && echo "✓ Full documentation"

# Create release-specific README
cat > "$RELEASE_DIR/README.txt" << 'EOF'
Education Platform v1.0.0
=========================

REQUIREMENTS:
- Java 17 or higher
- Internet connection (first run only)

QUICK START:

Windows:
  Double-click: run.bat
  Or command:   java -jar education-platform-v1.0.0.jar

Linux:
  Terminal:     ./run.sh
  Or:           java -jar education-platform-v1.0.0.jar

macOS:
  Double-click: run.command
  Or terminal:  ./run.command
  Or:           java -jar education-platform-v1.0.0.jar

FEATURES:
- User Authentication with secure login
- Quiz System for testing knowledge
- Discussion Forums for community engagement
- Video Lessons for multimedia learning
- Local SQLite Database for data persistence

SUPPORT:
- GitHub: https://github.com/itsmebasher/eduction-website
- Issues: https://github.com/itsmebasher/eduction-website/issues

Happy Learning! 🎓
EOF

echo "✓ README created"

# Display summary
echo ""
echo "✅ Release files prepared in: $RELEASE_DIR/"
echo ""
echo "Files ready for release:"
ls -lh "$RELEASE_DIR/" | awk '{if(NR>1) printf "  %-40s %8s\n", $9, $5}'
echo ""

# Instructions for publishing
echo "📤 Ready to publish!"
echo ""
echo "Option 1: Using GitHub CLI (Recommended)"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
echo "cd $RELEASE_DIR"
echo ""
echo "gh release create v1.0.0 \\"
echo "  --title 'Education Platform v1.0.0' \\"
echo "  --notes 'Initial release with all features' \\"
echo "  education-platform-v1.0.0.jar \\"
echo "  run.sh \\"
echo "  run.bat \\"
echo "  run.command \\"
echo "  README.txt"
echo ""
echo "Option 2: Manual (via web browser)"
echo "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━"
echo ""
echo "1. Visit: https://github.com/itsmebasher/eduction-website/releases/new"
echo "2. Tag version: v1.0.0"
echo "3. Release title: Education Platform v1.0.0"
echo "4. Description: Initial release with all features"
echo "5. Drag & drop these files:"
echo "   - education-platform-v1.0.0.jar"
echo "   - run.sh"
echo "   - run.bat"
echo "   - run.command"
echo "   - README.txt"
echo "6. Click 'Publish Release'"
echo ""

# Ask to publish
read -p "Would you like to publish now using GitHub CLI? (y/n) " -n 1 -r
echo
if [[ $REPLY =~ ^[Yy]$ ]]; then
    cd "$RELEASE_DIR"
    gh release create v1.0.0 \
      --title "Education Platform v1.0.0" \
      --notes "Initial production release with all features:

✨ Features:
- User Authentication (Secure login/registration)
- Quiz System (Create and take quizzes)
- Discussion Forums (Community engagement)
- Video Lessons (Multimedia learning)
- Local Database (SQLite, all data persisted)

📋 System Requirements:
- Java 17 or higher
- Windows, macOS, or Linux
- 512MB RAM minimum

🚀 Quick Start:
1. Download education-platform-v1.0.0.jar
2. Run with: java -jar education-platform-v1.0.0.jar
3. Or use launcher scripts (run.sh, run.bat, run.command)

📚 Documentation:
- See README-FULL.md for complete documentation
- See RELEASE_NOTES.txt for detailed info

🎓 Happy Learning!" \
      education-platform-v1.0.0.jar \
      run.sh \
      run.bat \
      run.command \
      README.txt \
      RELEASE_NOTES.txt
    
    if [ $? -eq 0 ]; then
        echo ""
        echo "✅ Release published successfully!"
        echo "📍 Release URL: https://github.com/itsmebasher/eduction-website/releases/tag/v1.0.0"
    else
        echo "❌ Release failed. Please try manual method."
    fi
else
    echo "✓ Skipped automatic publishing"
    echo ""
    echo "When ready, navigate to $RELEASE_DIR and run:"
    echo "  gh release create v1.0.0 [files...]"
fi

echo ""
echo "Done! 🎉"
