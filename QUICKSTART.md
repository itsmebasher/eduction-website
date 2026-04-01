# Quick Start Guide

## Running the Application

### Step 1: Build the Project
```bash
cd /workspaces/eduction-website
mvn clean install
```

### Step 2: Run the Application
```bash
mvn javafx:run
```

## First Time User - Step by Step

### Registration
1. Application starts on the **Login** tab
2. Click the **Register** tab
3. Fill in the form:
   - Full Name: Your name
   - Email: Your email address
   - Password: Create a secure password (min 6 characters)
   - Confirm Password: Retype your password
   - Role: Select your role (STUDENT, INSTRUCTOR, or ADMIN)
4. Click **Register** button
5. You'll see a success message

### Login
1. Go back to the **Login** tab
2. Enter your email and password
3. Click **Login**
4. You'll be redirected to the main dashboard

## Using the Platform

### Dashboard Navigation
After login, you'll see the main dashboard with these options:

**Left Sidebar:**
- **Dashboard**: Home screen with overview
- **Courses**: View and manage courses
- **Quizzes**: Take quizzes for your courses
- **Videos**: Watch video lessons
- **Discussion Forum**: Participate in discussions

### Taking a Quiz
1. Click **Quizzes** in the sidebar
2. Select a quiz from the list
3. Answer each question
4. Click **Next Question** to proceed
5. Click **View Results** to see your score
6. Passing score is typically 70%

### Joining Forum Discussions
1. Click **Discussion Forum** in the sidebar
2. Select a forum from the list
3. Read existing posts
4. Write your reply in the **New Post** section
5. Click **Post Message** to publish
6. Click **Like Selected** to upvote posts

### Watching Video Lessons
1. Click **Videos** in the sidebar
2. Select a video from the list
3. The video player loads automatically
4. Use Play, Pause, and Download buttons
5. View the video duration and description

## Tips & Best Practices

✅ **Security**
- Use a strong password (uppercase, lowercase, numbers, symbols)
- Don't share your login credentials
- Log out when finished

✅ **Learning**
- Complete quizzes before moving to new topics
- Participate actively in discussion forums
- Watch videos at your own pace
- Take notes while learning

✅ **Course Management** (Instructors)
- Create well-structured courses with clear learning objectives
- Break down complex topics into shorter videos
- Design quizzes to reinforce key concepts
- Monitor student progress regularly

## Troubleshooting

### Application won't start
- Ensure Java 17+ is installed: `java -version`
- Check Maven is installed: `mvn -version`
- Try cleaning build: `mvn clean`

### Database errors
- Delete `education_platform.db` file (it will be recreated)
- Check file permissions in the application directory
- Ensure disk space is available

### Login issues
- Verify email is correct and matches registration
- Check Caps Lock is not on
- Reset password using "Forgot Password?" option

### Video player issues
- Verify internet connection for remote videos
- Check video URL is valid
- Try a different browser/video player

## Default Test Accounts

For testing purposes, you can register with these credentials:

**Student Account:**
- Email: student@example.com
- Password: test123
- Role: STUDENT

**Instructor Account:**
- Email: instructor@example.com
- Password: test123
- Role: INSTRUCTOR

## Keyboard Shortcuts

- `Ctrl+Q`: Exit application
- `Tab`: Navigate between fields
- `Enter`: Submit form or click focused button
- `Ctrl+A`: Select all text

## Getting Help

- Check the **Help** menu in the application
- Review this guide
- Check GitHub issues: https://github.com/itsmebasher/eduction-website/issues
- Contact support: itsmebasher@github.com

## Next Steps

1. Explore all features
2. Create your own courses (Instructors)
3. Try different quiz question types
4. Participate in forums
5. Provide feedback

Enjoy your learning journey! 📚🎓