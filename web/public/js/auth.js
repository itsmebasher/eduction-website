// Authentication
let currentUser = null;
let currentForumId = null;
let currentQuizId = null;
let currentQuestions = [];
let userAnswers = {};

function toggleAuthForm() {
  document.getElementById('loginForm').classList.toggle('active');
  document.getElementById('registerForm').classList.toggle('active');
}

// Show login form by default
document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('loginForm').classList.add('active');
  setupEventListeners();
  checkAuthStatus();
});

function setupEventListeners() {
  // Login Form
  document.getElementById('loginForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    try {
      const result = await api.login(email, password);
      if (result.success) {
        currentUser = result.user;
        localStorage.setItem('user', JSON.stringify(result.user));
        updateUI();
        showPage('dashboard');
      } else {
        alert(result.error || 'Login failed');
      }
    } catch (error) {
      handleApiError(error);
    }
  });

  // Register Form
  document.getElementById('registerForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const name = document.getElementById('registerName').value;
    const email = document.getElementById('registerEmail').value;
    const password = document.getElementById('registerPassword').value;
    const confirm = document.getElementById('registerConfirm').value;
    const role = document.getElementById('registerRole').value;

    if (password !== confirm) {
      alert('Passwords do not match');
      return;
    }

    try {
      const result = await api.register(name, email, password, role);
      if (result.success) {
        alert('Registration successful! Please log in.');
        toggleAuthForm();
        document.getElementById('loginForm').reset();
        document.getElementById('registerForm').reset();
      } else {
        alert(result.error || 'Registration failed');
      }
    } catch (error) {
      handleApiError(error);
    }
  });
}

function updateUI() {
  if (currentUser) {
    document.getElementById('userInfo').textContent = `👤 ${currentUser.name}`;
    document.getElementById('navbar').style.display = 'block';
    document.getElementById('authPage').classList.remove('active');
  } else {
    document.getElementById('userInfo').textContent = '';
    document.getElementById('navbar').style.display = 'none';
    document.getElementById('authPage').classList.add('active');
  }
}

function logout() {
  currentUser = null;
  localStorage.removeItem('user');
  updateUI();
  document.getElementById('loginForm').reset();
  document.getElementById('registerForm').reset();
}

function checkAuthStatus() {
  const savedUser = localStorage.getItem('user');
  if (savedUser) {
    currentUser = JSON.parse(savedUser);
    updateUI();
    showPage('dashboard');
  } else {
    updateUI();
  }
}

function showPage(pageName) {
  if (!currentUser && pageName !== 'auth') {
    showPage('auth');
    return;
  }

  // Hide all pages
  document.querySelectorAll('.page').forEach(p => p.classList.remove('active'));

  // Show selected page
  const pageMap = {
    'dashboard': 'dashboardPage',
    'courses': 'coursesPage',
    'quizzes': 'quizzesPage',
    'quizTaking': 'quizTakingPage',
    'forums': 'forumsPage',
    'forumDetails': 'forumDetailsPage',
    'videos': 'videosPage',
    'auth': 'authPage'
  };

  const pageId = pageMap[pageName];
  if (pageId) {
    document.getElementById(pageId).classList.add('active');
    
    // Load data for the page
    if (pageName === 'courses') loadCourses();
    if (pageName === 'quizzes') loadQuizzes();
    if (pageName === 'quizTaking') loadQuizTaking(currentQuizId);
    if (pageName === 'forums') loadForums();
    if (pageName === 'forumDetails') loadForumDetails(currentForumId);
    if (pageName === 'videos') loadVideos();
  }
}

async function loadCourses() {
  try {
    const courses = await api.getCourses();
    const html = courses.length > 0
      ? courses.map(c => `
          <div class="item">
            <h4>${c.title}</h4>
            <p>${c.description}</p>
            <p><small>👨‍🏫 Instructor: ${c.instructor_id} | 👥 Enrolled: ${c.enrolled_students}</small></p>
          </div>
        `).join('')
      : '<p class="loading">No courses available</p>';
    document.getElementById('coursesList').innerHTML = html;
  } catch (error) {
    handleApiError(error);
  }
}
async function loadQuizzes() {
  try {
    const quizzes = await api.getQuizzes();
    const html = quizzes.length > 0
      ? quizzes.map(q => `
          <div class="item" onclick="takeQuiz('${q.id}')">
            <h4>${q.title}</h4>
            <p>${q.description}</p>
            <p><small>❓ ${q.total_questions} questions | 💯 Pass Score: ${q.passing_score}%</small></p>
          </div>
        `).join('')
      : '<p class="loading">No quizzes available</p>';
    document.getElementById('quizzesList').innerHTML = html;
  } catch (error) {
    handleApiError(error);
  }
}
}

async function loadForums() {
  try {
    const forums = await api.getForums();
    const html = forums.length > 0
      ? forums.map(f => `
          <div class="item" onclick="showForumDetails('${f.id}')">
            <h4>${f.title}</h4>
            <p>${f.description}</p>
            <p><small>👁️ Views: ${f.view_count}</small></p>
          </div>
        `).join('')
      : '<p class="loading">No forums available</p>';
    document.getElementById('forumsList').innerHTML = html;
  } catch (error) {
    handleApiError(error);
  }
}

async function loadVideos() {
  try {
    const videos = await api.getVideos();
    const html = videos.length > 0
      ? videos.map(v => `
          <div class="item video-item">
            <h4>${v.title}</h4>
            <p>${v.description}</p>
            <div class="video-container">
              <iframe width="100%" height="315" src="${v.video_url}" 
                      frameborder="0" allowfullscreen></iframe>
            </div>
            <p><small>⏱️ Duration: ${Math.floor(v.duration_seconds / 60)} min | 👁️ Views: ${v.view_count}</small></p>
          </div>
        `).join('')
      : '<p class="loading">No videos available</p>';
    document.getElementById('videosList').innerHTML = html;
  } catch (error) {
    handleApiError(error);
  }
}

function takeQuiz(quizId) {
  currentQuizId = quizId;
  userAnswers = {};
  showPage('quizTaking');
}

async function loadQuizTaking(quizId) {
  if (!quizId) return;

  try {
    const quiz = await api.getQuiz(quizId);
    const questions = await api.getQuizQuestions(quizId);
    
    currentQuestions = questions;
    
    document.getElementById('quizTitle').textContent = quiz.title;
    
    const html = questions.length > 0
      ? questions.map((q, index) => `
          <div class="question-item">
            <h4>Question ${index + 1}</h4>
            <p>${q.question_text}</p>
            <div class="options">
              <label><input type="radio" name="q${index}" value="A"> ${q.option_a}</label><br>
              <label><input type="radio" name="q${index}" value="B"> ${q.option_b}</label><br>
              <label><input type="radio" name="q${index}" value="C"> ${q.option_c}</label><br>
              <label><input type="radio" name="q${index}" value="D"> ${q.option_d}</label>
            </div>
          </div>
        `).join('')
      : '<p>No questions available</p>';
    
    document.getElementById('quizQuestions').innerHTML = html;
    document.getElementById('submitQuiz').style.display = questions.length > 0 ? 'block' : 'none';
    
    // Setup submit handler
    document.getElementById('submitQuiz').onclick = submitQuiz;
  } catch (error) {
    handleApiError(error);
  }
}

function submitQuiz() {
  // Collect answers
  const questions = document.querySelectorAll('.question-item');
  let correctAnswers = 0;
  
  questions.forEach((q, index) => {
    const selected = q.querySelector(`input[name="q${index}"]:checked`);
    if (selected) {
      userAnswers[index] = selected.value;
      if (selected.value === currentQuestions[index].correct_answer) {
        correctAnswers++;
      }
    }
  });
  
  const score = Math.round((correctAnswers / currentQuestions.length) * 100);
  const passed = score >= 70; // Assuming 70% is passing
  
  alert(`Quiz completed!\nScore: ${score}%\n${passed ? '🎉 Passed!' : '❌ Try again!'}`);
  
  showPage('quizzes');
}

async function loadForumDetails(forumId) {
  if (!forumId) return;

  try {
    const posts = await api.getForumPosts(forumId);
    
    // Get forum info (we'll need to add this to the API)
    const forums = await api.getForums();
    const forum = forums.find(f => f.id === forumId);
    
    if (forum) {
      document.getElementById('forumTitle').textContent = forum.title;
      document.getElementById('forumDescription').textContent = forum.description;
    }

    const html = posts.length > 0
      ? posts.map(p => `
          <div class="post-item">
            <h4>${p.title}</h4>
            <p>${p.content}</p>
            <small>Posted by User ${p.author_id} on ${new Date(p.created_date).toLocaleDateString()}</small>
            ${p.likes > 0 ? `<span class="likes">👍 ${p.likes} likes</span>` : ''}
          </div>
        `).join('')
      : '<p>No discussions yet. Be the first to start one!</p>';
    
    document.getElementById('forumPosts').innerHTML = html;
    
    // Show new post form for logged in users
    if (currentUser) {
      document.getElementById('newPostForm').style.display = 'block';
      setupPostForm();
    } else {
      document.getElementById('newPostForm').style.display = 'none';
    }
  } catch (error) {
    handleApiError(error);
  }
}

function setupPostForm() {
  const form = document.getElementById('postForm');
  form.onsubmit = async (e) => {
    e.preventDefault();
    const title = document.getElementById('postTitle').value;
    const content = document.getElementById('postContent').value;

    try {
      const result = await api.postMessage(currentForumId, currentUser.id, title, content);
      if (result.success) {
        alert('Post created successfully!');
        form.reset();
        loadForumDetails(currentForumId); // Refresh posts
      } else {
        alert(result.error || 'Failed to create post');
      }
    } catch (error) {
      handleApiError(error);
    }
  };
}
