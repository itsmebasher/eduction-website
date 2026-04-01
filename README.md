# Education Platform - JavaFX Application

A comprehensive JavaFX-based education platform designed to support **SDG 4: Quality Education** by providing students and instructors with easy access to learning resources anytime and anywhere.

## Features

### ✅ User Authentication & Management
- **User Registration**: Create accounts with role-based access (Student, Instructor, Admin)
- **Secure Login**: Password hashing using SHA-256 with salt
- **User Profiles**: Manage user information and account settings
- **Email Validation**: Built-in email format validation
- **Session Management**: Track logged-in users and current sessions

### ✅ Quiz System
- **Create & Manage Quizzes**: Instructors can create multiple-choice, true/false, and short-answer quizzes
- **Question Banking**: Add questions with multiple options
- **Quiz Attempts**: Students can take quizzes and receive immediate feedback
- **Scoring**: Automatic score calculation and pass/fail determination
- **Performance Tracking**: View quiz attempt history and progress

### ✅ Discussion Forums
- **Create Forums**: Organize discussions by course or topic
- **Post Messages**: Students and instructors can participate in discussions
- **Like Posts**: Community engagement features
- **Question Threads**: Mark threads as questions for better organization
- **Reply Tracking**: Monitor discussion replies and engagement

### ✅ Video Lessons
- **Upload Videos**: Add high-quality video lessons to courses
- **Video Metadata**: Include title, description, duration, and thumbnail
- **View Tracking**: Monitor video view counts and student engagement
- **Video Organization**: Organize videos by course
- **Download Support**: Allow students to download videos for offline viewing

### ✅ Database Connectivity
- **SQLite Integration**: Lightweight, file-based database
- **Automatic Schema Creation**: Database tables created on first run
- **CRUD Operations**: Full support for Create, Read, Update, Delete operations
- **Data Persistence**: All user data, quizzes, and forum posts persisted to database
- **Transaction Support**: Ensure data consistency

### ✅ Additional Features
- **Course Management**: Create and manage courses
- **Course Enrollment**: Students can enroll in courses
- **User Roles**: Role-based access control (STUDENT, INSTRUCTOR, ADMIN)
- **Dashboard**: Personal dashboard for each user
- **Responsive UI**: Professional, user-friendly JavaFX interface

## Project Structure

```
education-platform/
├── src/
│   ├── main/
│   │   ├── java/com/education/
│   │   │   ├── MainApplication.java          # Entry point
│   │   │   ├── controllers/
│   │   │   │   ├── MainViewController.java
│   │   │   │   ├── AuthenticationController.java
│   │   │   │   ├── QuizViewController.java
│   │   │   │   ├── ForumViewController.java
│   │   │   │   └── VideoViewController.java
│   │   │   ├── models/
│   │   │   │   ├── User.java
│   │   │   │   ├── Course.java
│   │   │   │   ├── Quiz.java
│   │   │   │   ├── Question.java
│   │   │   │   ├── QuizAttempt.java
│   │   │   │   ├── Forum.java
│   │   │   │   ├── ForumPost.java
│   │   │   │   └── VideoLesson.java
│   │   │   └── utils/
│   │   │       ├── DatabaseManager.java
│   │   │       ├── AuthenticationService.java
│   │   │       ├── AuthenticationUtil.java
│   │   │       ├── QuizService.java
│   │   │       ├── ForumService.java
│   │   │       └── VideoService.java
│   │   └── resources/
│   │       ├── com/education/views/
│   │       │   ├── main-view.fxml
│   │       │   ├── auth-view.fxml
│   │       │   ├── quiz-view.fxml
│   │       │   ├── forum-view.fxml
│   │       │   └── video-view.fxml
│   │       └── css/
│   │           └── styles.css
│   └── test/
│       └── java/com/education/
│           ├── models/
│           │   ├── CourseTest.java
│           │   ├── QuizTest.java
│           │   └── QuestionTest.java
│           └── utils/
│               └── AuthenticationUtilTest.java
├── pom.xml
├── .gitignore
└── README.md
```

## Technology Stack

- **JavaFX 21.0.2**: Modern UI framework for desktop applications
- **Maven 3.9+**: Build and dependency management
- **SQLite**: File-based relational database
- **JUnit 4**: Unit testing framework
- **Java 17+**: Programming language
- **GSON**: JSON processing
- **Apache Commons Codec**: Password hashing utilities

## Installation & Setup

### Prerequisites
- Java 17 or higher
- Maven 3.9 or higher
- SQLite (included via JDBC driver)

### Build Instructions

```bash
# Clone the repository
git clone https://github.com/itsmebasher/eduction-website.git
cd eduction-website

# Build the project
mvn clean install

# Run the application
mvn javafx:run
```

## Usage

### First Time Setup
1. Run the application
2. Navigate to the **Register** tab
3. Create a new account with your desired role
4. Log in with your credentials

### Student Workflow
1. **Login** with your account
2. **Enroll** in available courses
3. **Take Quizzes**: Complete quizzes to evaluate your understanding
4. **Watch Videos**: Access video lessons for each topic
5. **Participate**: Join discussion forums to ask questions and collaborate

### Instructor Workflow
1. **Login** with instructor credentials
2. **Create Courses**: Set up new courses
3. **Add Content**: Upload video lessons and create quizzes
4. **Monitor Progress**: Track student quiz scores and engagement
5. **Facilitate Discussions**: Participate in forums to support student learning

## Database Schema

The application automatically creates the following tables:

- **users**: User accounts and authentication
- **courses**: Course information
- **quizzes**: Quiz definitions and metadata
- **questions**: Quiz questions with options
- **quiz_attempts**: Student quiz submissions and scores
- **forums**: Discussion forum threads
- **forum_posts**: Forum messages
- **video_lessons**: Video lesson metadata
- **course_enrollments**: Course enrollment records

## Security Features

- ✅ SHA-256 password hashing with salt
- ✅ Input validation
- ✅ Role-based access control
- ✅ Session management
- ✅ Email validation

## Testing

Run unit tests with Maven:

```bash
mvn test
```

### Test Coverage
- User authentication and password hashing
- Quiz and question models
- Database operations
- Data validation

## Future Enhancements

- [ ] Video player integration (JavaFX Media API)
- [ ] Advanced analytics and reporting
- [ ] Certificate generation
- [ ] Mobile app (JavaFX Mobile)
- [ ] Cloud database synchronization
- [ ] Peer-to-peer collaboration tools
- [ ] AI-powered tutoring
- [ ] Real-time notifications
- [ ] Bulk user import
- [ ] Advanced search functionality

## Contributing

Contributions are welcome! Please:
1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Submit a pull request

## License

This project is licensed under the MIT License - see LICENSE file for details.

## Support SDG 4

This project contributes to the United Nations Sustainable Development Goal 4 (Quality Education) by:
- Providing free access to quality learning resources
- Enabling collaborative learning communities
- Supporting diverse learning styles (video, text, interactive quizzes)
- Empowering educators with course management tools
- Making education accessible anytime, anywhere

## Contact & Support

For issues, questions, or suggestions:
- Open an issue on GitHub
- Contact: itsmebasher@github.com

---

**Version**: 1.0.0  
**Last Updated**: 2026  
**Maintained by**: itsmebasher
