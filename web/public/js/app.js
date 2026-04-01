// Main Application Logic
console.log('🚀 Education Platform Web App Loaded');

// Service Worker for offline support (optional)
if ('serviceWorker' in navigator) {
  navigator.serviceWorker.register('sw.js').catch(() => {
    // Service worker registration failed, app will still work online
  });
}

// Initialize app
document.addEventListener('DOMContentLoaded', () => {
  console.log('✅ App initialized');
  checkAuthStatus();
});

// Handle browser back button
window.addEventListener('popstate', () => {
  if (currentUser) {
    showPage('dashboard');
  }
});
