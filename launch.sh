#!/bin/bash
# Education Platform - Smart Launcher
# Automatically chooses the best version for your environment

echo "🎓 Education Platform Launcher"
echo "================================"

# Check if we're in a headless environment
if [ -n "$DISPLAY" ]; then
    echo "✅ GUI environment detected - launching JavaFX desktop app..."
    echo ""
    ./run.sh
else
    echo "🌐 Headless environment detected - launching web version..."
    echo ""
    echo "📱 The web version provides all features:"
    echo "   • User authentication"
    echo "   • Course browsing"
    echo "   • Interactive quizzes"
    echo "   • Discussion forums"
    echo "   • Video lessons"
    echo "   • Mobile responsive"
    echo ""

    # Check if web server is already running
    if pgrep -f "node server.js" > /dev/null; then
        echo "✅ Web server is already running!"
        echo "🌐 Access at: http://localhost:3000"
        echo ""
        echo "📚 Sample accounts:"
        echo "   Students: alice@student.edu / password123"
        echo "   Instructors: sarah@instructor.edu / password123"
    else
        echo "🚀 Starting web server..."
        cd web
        npm start &
        sleep 3
        echo ""
        echo "✅ Web server started!"
        echo "🌐 Access at: http://localhost:3000"
        echo ""
        echo "📚 Sample accounts:"
        echo "   Students: alice@student.edu / password123"
        echo "   Instructors: sarah@instructor.edu / password123"
        echo ""
        echo "💡 To stop: Ctrl+C or 'pkill -f \"node server.js\"'"
    fi
fi