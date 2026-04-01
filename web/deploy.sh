#!/bin/bash

echo "🚀 Deploying Education Platform to Railway..."
echo ""

# Check if railway CLI is installed
if ! command -v railway &> /dev/null; then
    echo "❌ Railway CLI not found. Installing..."
    npm install -g @railway/cli
fi

# Login to Railway (if not already logged in)
echo "🔐 Logging into Railway..."
railway login

# Create new project
echo "📦 Creating Railway project..."
railway init education-platform-web

# Link to current directory
echo "🔗 Linking to current directory..."
railway link

# Deploy
echo "🚀 Deploying..."
railway up

# Get the URL
echo "🌐 Getting deployment URL..."
railway domain

echo ""
echo "✅ Deployment complete!"
echo "🎓 Your education platform is now live!"
echo ""
echo "📚 Sample accounts:"
echo "Students: alice@student.edu / password123"
echo "Instructors: sarah@instructor.edu / password123"