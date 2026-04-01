#!/bin/bash

# Education Platform - Quick Start Script
# This script starts the web server for development or production

set -e

echo "🎓 Education Platform - Web Server Startup"
echo "==========================================="

# Check Node.js version
if ! command -v node &> /dev/null; then
  echo "❌ Node.js is not installed"
  echo "📦 Install Node.js 18+ from https://nodejs.org"
  exit 1
fi

NODE_VERSION=$(node -v)
echo "✅ Node.js version: $NODE_VERSION"

# Check npm installation
if ! command -v npm &> /dev/null; then
  echo "❌ npm is not installed"
  exit 1
fi

NPM_VERSION=$(npm -v)
echo "✅ npm version: $NPM_VERSION"

# Install dependencies if needed
if [ ! -d "node_modules" ]; then
  echo "📦 Installing dependencies..."
  npm install
fi

# Start server
echo ""
echo "🚀 Starting server..."
echo "────────────────────"

npm start
