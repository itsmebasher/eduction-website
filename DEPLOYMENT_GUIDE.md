# 🚀 Easy Deployment Guide

## Option 1: Railway (Recommended - 2 minutes)

### Step 1: Fork this repository
1. Go to https://github.com/itsmebasher/eduction-website
2. Click "Fork" in the top right
3. This creates your own copy of the project

### Step 2: Deploy to Railway
1. Go to [Railway.app](https://railway.app) and sign up/login
2. Click "New Project" → "Deploy from GitHub repo"
3. Search for your forked repository: `your-username/eduction-website`
4. Click "Deploy"
5. Wait 2-3 minutes for deployment
6. Get your live URL from the dashboard

### Step 3: Access your site
Your education platform will be live at: `https://your-project-name.up.railway.app`

## Option 2: Manual Railway CLI

```bash
# Install Railway CLI
npm install -g @railway/cli

# Login
railway login

# Deploy
cd web
./deploy.sh
```

## Option 3: Other Platforms

### Vercel (Static version)
```bash
# For static deployment (limited functionality)
npm install -g vercel
vercel --prod
```

### Heroku
```bash
# Install Heroku CLI
# Create heroku app
heroku create your-app-name
git push heroku main
```

## 🎓 Test Your Live Site

Use these sample accounts:
- **Students**: `alice@student.edu` / `password123`
- **Instructors**: `sarah@instructor.edu` / `password123`

## 📱 Features Available
- ✅ User registration/login
- ✅ Browse courses
- ✅ Take interactive quizzes
- ✅ Participate in forums
- ✅ Watch video lessons
- ✅ Mobile responsive

Your education platform is now live and accessible worldwide! 🌍