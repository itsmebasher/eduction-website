// API Service Layer
const API_BASE = '/api';

const api = {
  // Auth endpoints
  register: (name, email, password, role) => {
    return fetch(`${API_BASE}/auth/register`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ name, email, password, role })
    }).then(r => r.json());
  },

  login: (email, password) => {
    return fetch(`${API_BASE}/auth/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ email, password })
    }).then(r => r.json());
  },

  // Quizzes endpoints
  getQuizzes: () => {
    return fetch(`${API_BASE}/quizzes`)
      .then(r => r.json());
  },

  getQuiz: (quizId) => {
    return fetch(`${API_BASE}/quizzes/${quizId}`)
      .then(r => r.json());
  },

  getQuizQuestions: (quizId) => {
    return fetch(`${API_BASE}/quizzes/${quizId}/questions`)
      .then(r => r.json());
  },

  // Forums endpoints
  getForums: () => {
    return fetch(`${API_BASE}/forums`)
      .then(r => r.json());
  },

  getForumPosts: (forumId) => {
    return fetch(`${API_BASE}/forums/${forumId}/posts`)
      .then(r => r.json());
  },

  postMessage: (forumId, authorId, title, content) => {
    return fetch(`${API_BASE}/forums/${forumId}/posts`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ authorId, title, content })
    }).then(r => r.json());
  },

  // Videos endpoints
  getVideos: () => {
    return fetch(`${API_BASE}/videos`)
      .then(r => r.json());
  },

  // Courses endpoints
  getCourses: () => {
    return fetch(`${API_BASE}/courses`)
      .then(r => r.json());
  }
};

// Error handler with better logging
function handleApiError(error) {
  console.error('❌ API Error:', error);
  console.error('Error details:', {
    message: error.message,
    stack: error.stack
  });
  alert('An error occurred. Please check the browser console (F12) for details and try again.');
}
