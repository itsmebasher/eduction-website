const express = require('express');
const path = require('path');
const cors = require('cors');
const sqlite3 = require('sqlite3').verbose();
const bodyParser = require('body-parser');
const crypto = require('crypto');

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware
app.use(cors());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(express.static(path.join(__dirname, 'public')));

// Database setup
const db = new sqlite3.Database('./education_platform.db');

// Initialize database
function initializeDatabase() {
  db.serialize(() => {
    // Users table
    db.run(`CREATE TABLE IF NOT EXISTS users (
      id TEXT PRIMARY KEY,
      name TEXT NOT NULL,
      email TEXT UNIQUE NOT NULL,
      password_hash TEXT NOT NULL,
      role TEXT NOT NULL,
      created_date DATETIME DEFAULT CURRENT_TIMESTAMP
    )`);

    // Courses table
    db.run(`CREATE TABLE IF NOT EXISTS courses (
      id TEXT PRIMARY KEY,
      title TEXT NOT NULL,
      description TEXT,
      instructor_id TEXT NOT NULL,
      created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
      enrolled_students INTEGER DEFAULT 0
    )`);

    // Quizzes table
    db.run(`CREATE TABLE IF NOT EXISTS quizzes (
      id TEXT PRIMARY KEY,
      title TEXT NOT NULL,
      description TEXT,
      course_id TEXT NOT NULL,
      instructor_id TEXT NOT NULL,
      total_questions INTEGER DEFAULT 0,
      passing_score INTEGER DEFAULT 70,
      created_date DATETIME DEFAULT CURRENT_TIMESTAMP
    )`);

    // Forums table
    db.run(`CREATE TABLE IF NOT EXISTS forums (
      id TEXT PRIMARY KEY,
      course_id TEXT NOT NULL,
      title TEXT NOT NULL,
      description TEXT,
      creator_id TEXT NOT NULL,
      view_count INTEGER DEFAULT 0,
      created_date DATETIME DEFAULT CURRENT_TIMESTAMP
    )`);

    // Forum posts table
    db.run(`CREATE TABLE IF NOT EXISTS forum_posts (
      id TEXT PRIMARY KEY,
      forum_id TEXT NOT NULL,
      author_id TEXT NOT NULL,
      title TEXT NOT NULL,
      content TEXT NOT NULL,
      likes INTEGER DEFAULT 0,
      created_date DATETIME DEFAULT CURRENT_TIMESTAMP
    )`);

    // Quiz questions table
    db.run(`CREATE TABLE IF NOT EXISTS quiz_questions (
      id TEXT PRIMARY KEY,
      quiz_id TEXT NOT NULL,
      question_text TEXT NOT NULL,
      option_a TEXT NOT NULL,
      option_b TEXT NOT NULL,
      option_c TEXT NOT NULL,
      option_d TEXT NOT NULL,
      correct_answer TEXT NOT NULL,
      created_date DATETIME DEFAULT CURRENT_TIMESTAMP
    )`);

    // Video lessons table
    db.run(`CREATE TABLE IF NOT EXISTS video_lessons (
      id TEXT PRIMARY KEY,
      course_id TEXT NOT NULL,
      title TEXT NOT NULL,
      description TEXT,
      video_url TEXT NOT NULL,
      instructor_id TEXT NOT NULL,
      duration_seconds LONG DEFAULT 0,
      view_count INTEGER DEFAULT 0,
      created_date DATETIME DEFAULT CURRENT_TIMESTAMP
    )`);

    // Insert sample data
    insertSampleData();

    console.log('Database initialized');
  });
}

// Insert sample data for demonstration
function insertSampleData() {
  // Sample users
  const sampleUsers = [
    { id: 'user1', name: 'Dr. Sarah Johnson', email: 'sarah@instructor.edu', password: 'password123', role: 'INSTRUCTOR' },
    { id: 'user2', name: 'Prof. Michael Chen', email: 'michael@instructor.edu', password: 'password123', role: 'INSTRUCTOR' },
    { id: 'user3', name: 'Alice Student', email: 'alice@student.edu', password: 'password123', role: 'STUDENT' },
    { id: 'user4', name: 'Bob Student', email: 'bob@student.edu', password: 'password123', role: 'STUDENT' }
  ];

  // Sample courses
  const sampleCourses = [
    { id: 'course1', title: 'Introduction to Computer Science', description: 'Learn the fundamentals of programming and computer science concepts.', instructor_id: 'user1' },
    { id: 'course2', title: 'Web Development Fundamentals', description: 'Build modern web applications using HTML, CSS, and JavaScript.', instructor_id: 'user2' },
    { id: 'course3', title: 'Data Structures and Algorithms', description: 'Master essential data structures and algorithmic thinking.', instructor_id: 'user1' }
  ];

  // Sample quizzes
  const sampleQuizzes = [
    { id: 'quiz1', title: 'Programming Basics Quiz', description: 'Test your knowledge of basic programming concepts.', course_id: 'course1', instructor_id: 'user1', total_questions: 10, passing_score: 70 },
    { id: 'quiz2', title: 'HTML & CSS Quiz', description: 'Assess your understanding of web markup and styling.', course_id: 'course2', instructor_id: 'user2', total_questions: 8, passing_score: 75 },
    { id: 'quiz3', title: 'Algorithms Challenge', description: 'Apply your knowledge of sorting and searching algorithms.', course_id: 'course3', instructor_id: 'user1', total_questions: 12, passing_score: 80 }
  ];

  // Sample forums
  const sampleForums = [
    { id: 'forum1', course_id: 'course1', title: 'General Discussion', description: 'Discuss course topics and ask questions.', creator_id: 'user1', view_count: 45 },
    { id: 'forum2', course_id: 'course2', title: 'Project Help', description: 'Get help with your web development projects.', creator_id: 'user2', view_count: 32 },
    { id: 'forum3', course_id: 'course3', title: 'Algorithm Discussions', description: 'Share your thoughts on different algorithmic approaches.', creator_id: 'user1', view_count: 28 }
  ];

  // Sample forum posts
  const samplePosts = [
    { id: 'post1', forum_id: 'forum1', author_id: 'user3', title: 'Help with loops', content: 'I\'m having trouble understanding for loops. Can someone explain with an example?' },
    { id: 'post2', forum_id: 'forum1', author_id: 'user1', title: 'Re: Help with loops', content: 'Sure! A for loop repeats a block of code a certain number of times. Here\'s an example in JavaScript: for(let i = 0; i < 5; i++) { console.log(i); }' },
    { id: 'post3', forum_id: 'forum2', author_id: 'user4', title: 'CSS Grid vs Flexbox', content: 'When should I use CSS Grid versus Flexbox for layouts?' },
    { id: 'post4', forum_id: 'forum3', author_id: 'user3', title: 'Big O notation question', content: 'Can someone explain what O(n log n) means in simple terms?' }
  ];

  // Sample quiz questions
  const sampleQuestions = [
    { id: 'q1', quiz_id: 'quiz1', question_text: 'What is a variable in programming?', option_a: 'A type of loop', option_b: 'A container for storing data', option_c: 'A function definition', option_d: 'A comment in code', correct_answer: 'B' },
    { id: 'q2', quiz_id: 'quiz1', question_text: 'Which of these is NOT a programming language?', option_a: 'Python', option_b: 'JavaScript', option_c: 'HTML', option_d: 'Java', correct_answer: 'C' },
    { id: 'q3', quiz_id: 'quiz1', question_text: 'What does "if" statement do?', option_a: 'Repeats code', option_b: 'Makes decisions', option_c: 'Defines functions', option_d: 'Stores data', correct_answer: 'B' },
    { id: 'q4', quiz_id: 'quiz2', question_text: 'What does HTML stand for?', option_a: 'HyperText Markup Language', option_b: 'High Tech Modern Language', option_c: 'Home Tool Markup Language', option_d: 'Hyperlink and Text Markup Language', correct_answer: 'A' },
    { id: 'q5', quiz_id: 'quiz2', question_text: 'Which CSS property is used to change text color?', option_a: 'font-size', option_b: 'color', option_c: 'background-color', option_d: 'margin', correct_answer: 'B' },
    { id: 'q6', quiz_id: 'quiz3', question_text: 'What is the time complexity of binary search?', option_a: 'O(n)', option_b: 'O(log n)', option_c: 'O(n²)', option_d: 'O(1)', correct_answer: 'B' }
  ];
  const sampleVideos = [
    { id: 'video1', course_id: 'course1', title: 'What is Programming?', description: 'Introduction to programming concepts and why they matter.', video_url: 'https://www.youtube.com/embed/dQw4w9WgXcQ', instructor_id: 'user1', duration_seconds: 900, view_count: 125 },
    { id: 'video2', course_id: 'course1', title: 'Variables and Data Types', description: 'Learn about different data types and how to use variables.', video_url: 'https://www.youtube.com/embed/dQw4w9WgXcQ', instructor_id: 'user1', duration_seconds: 720, view_count: 98 },
    { id: 'video3', course_id: 'course2', title: 'HTML Basics', description: 'Structure your web pages with HTML elements.', video_url: 'https://www.youtube.com/embed/dQw4w9WgXcQ', instructor_id: 'user2', duration_seconds: 600, view_count: 87 },
    { id: 'video4', course_id: 'course2', title: 'CSS Styling', description: 'Make your websites beautiful with CSS.', video_url: 'https://www.youtube.com/embed/dQw4w9WgXcQ', instructor_id: 'user2', duration_seconds: 800, view_count: 76 },
    { id: 'video5', course_id: 'course3', title: 'Arrays and Lists', description: 'Understanding array data structures.', video_url: 'https://www.youtube.com/embed/dQw4w9WgXcQ', instructor_id: 'user1', duration_seconds: 650, view_count: 54 }
  ];

  // Insert users
  sampleUsers.forEach(user => {
    db.run(
      'INSERT OR IGNORE INTO users (id, name, email, password_hash, role) VALUES (?, ?, ?, ?, ?)',
      [user.id, user.name, user.email, hashPassword(user.password), user.role]
    );
  });

  // Insert courses
  sampleCourses.forEach(course => {
    db.run(
      'INSERT OR IGNORE INTO courses (id, title, description, instructor_id) VALUES (?, ?, ?, ?)',
      [course.id, course.title, course.description, course.instructor_id]
    );
  });

  // Insert quizzes
  sampleQuizzes.forEach(quiz => {
    db.run(
      'INSERT OR IGNORE INTO quizzes (id, title, description, course_id, instructor_id, total_questions, passing_score) VALUES (?, ?, ?, ?, ?, ?, ?)',
      [quiz.id, quiz.title, quiz.description, quiz.course_id, quiz.instructor_id, quiz.total_questions, quiz.passing_score]
    );
  });

  // Insert forums
  sampleForums.forEach(forum => {
    db.run(
      'INSERT OR IGNORE INTO forums (id, course_id, title, description, creator_id, view_count) VALUES (?, ?, ?, ?, ?, ?)',
      [forum.id, forum.course_id, forum.title, forum.description, forum.creator_id, forum.view_count]
    );
  });

  // Insert forum posts
  samplePosts.forEach(post => {
    db.run(
      'INSERT OR IGNORE INTO forum_posts (id, forum_id, author_id, title, content) VALUES (?, ?, ?, ?, ?)',
      [post.id, post.forum_id, post.author_id, post.title, post.content]
    );
  });

  // Insert video lessons
  sampleVideos.forEach(video => {
    db.run(
      'INSERT OR IGNORE INTO video_lessons (id, course_id, title, description, video_url, instructor_id, duration_seconds, view_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?)',
      [video.id, video.course_id, video.title, video.description, video.video_url, video.instructor_id, video.duration_seconds, video.view_count]
    );
  });

  // Insert quiz questions
  sampleQuestions.forEach(question => {
    db.run(
      'INSERT OR IGNORE INTO quiz_questions (id, quiz_id, question_text, option_a, option_b, option_c, option_d, correct_answer) VALUES (?, ?, ?, ?, ?, ?, ?, ?)',
      [question.id, question.quiz_id, question.question_text, question.option_a, question.option_b, question.option_c, question.option_d, question.correct_answer]
    );
  });
}

// Helper functions
function hashPassword(password) {
  return crypto.createHash('sha256').update(password).digest('hex');
}

function generateId() {
  return Date.now().toString(36) + Math.random().toString(36).substr(2);
}

// Routes

// Auth Routes
app.post('/api/auth/register', (req, res) => {
  const { name, email, password, role } = req.body;

  if (!name || !email || !password) {
    return res.status(400).json({ error: 'Missing required fields' });
  }

  const userId = generateId();
  const passwordHash = hashPassword(password);

  db.run(
    'INSERT INTO users (id, name, email, password_hash, role) VALUES (?, ?, ?, ?, ?)',
    [userId, name, email, passwordHash, role || 'STUDENT'],
    function(err) {
      if (err) {
        return res.status(400).json({ error: 'Email already registered' });
      }
      res.json({ success: true, userId, message: 'Registration successful' });
    }
  );
});

app.post('/api/auth/login', (req, res) => {
  const { email, password } = req.body;

  db.get(
    'SELECT id, name, role, password_hash FROM users WHERE email = ?',
    [email],
    (err, user) => {
      if (err || !user) {
        return res.status(401).json({ error: 'Invalid email or password' });
      }

      const passwordHash = hashPassword(password);
      if (passwordHash !== user.password_hash) {
        return res.status(401).json({ error: 'Invalid email or password' });
      }

      res.json(
        { success: true, user: { id: user.id, name: user.name, role: user.role, email } }
      );
    }
  );
});

// Quiz Routes
app.get('/api/quizzes', (req, res) => {
  db.all('SELECT * FROM quizzes', (err, quizzes) => {
    if (err) return res.status(500).json({ error: err.message });
    res.json(quizzes || []);
  });
});

app.get('/api/quizzes/:quizId', (req, res) => {
  const { quizId } = req.params;
  db.get('SELECT * FROM quizzes WHERE id = ?', [quizId], (err, quiz) => {
    if (err) return res.status(500).json({ error: err.message });
    if (!quiz) return res.status(404).json({ error: 'Quiz not found' });
    res.json(quiz);
  });
});

app.get('/api/quizzes/:quizId/questions', (req, res) => {
  const { quizId } = req.params;
  db.all('SELECT * FROM quiz_questions WHERE quiz_id = ?', [quizId], (err, questions) => {
    if (err) return res.status(500).json({ error: err.message });
    res.json(questions || []);
  });
});

// Forum Routes
app.get('/api/forums', (req, res) => {
  db.all('SELECT * FROM forums', (err, forums) => {
    if (err) return res.status(500).json({ error: err.message });
    res.json(forums || []);
  });
});

app.get('/api/forums/:forumId/posts', (req, res) => {
  const { forumId } = req.params;
  db.all('SELECT * FROM forum_posts WHERE forum_id = ? ORDER BY created_date DESC', [forumId], (err, posts) => {
    if (err) return res.status(500).json({ error: err.message });
    res.json(posts || []);
  });
});

app.post('/api/forums/:forumId/posts', (req, res) => {
  const { forumId } = req.params;
  const { authorId, title, content } = req.body;
  const postId = generateId();

  db.run(
    'INSERT INTO forum_posts (id, forum_id, author_id, title, content) VALUES (?, ?, ?, ?, ?)',
    [postId, forumId, authorId, title, content],
    function(err) {
      if (err) return res.status(500).json({ error: err.message });
      res.json({ success: true, postId });
    }
  );
});

// Video Routes
app.get('/api/videos', (req, res) => {
  db.all('SELECT * FROM video_lessons', (err, videos) => {
    if (err) return res.status(500).json({ error: err.message });
    res.json(videos || []);
  });
});

// Course Routes
app.get('/api/courses', (req, res) => {
  db.all('SELECT * FROM courses', (err, courses) => {
    if (err) return res.status(500).json({ error: err.message });
    res.json(courses || []);
  });
});

// Serve index.html for SPA routing
app.get('/*', (req, res) => {
  res.sendFile(path.join(__dirname, 'public', 'index.html'));
});

// Error handling
app.use((err, req, res, next) => {
  console.error(err);
  res.status(500).json({ error: 'Internal server error' });
});

// Start server
initializeDatabase();
app.listen(PORT, () => {
  console.log(`🚀 Education Platform web server running on http://localhost:${PORT}`);
  console.log(`📱 Open http://localhost:${PORT} in your browser`);
});

module.exports = app;
