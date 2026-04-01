# Education Platform Web Deployment Guide

Complete guide for deploying the Education Platform web application to browsers.

## Table of Contents

1. [Local Development](#local-development)
2. [Docker Deployment](#docker-deployment)
3. [Cloud Deployment](#cloud-deployment)
4. [Browser Compatibility](#browser-compatibility)
5. [Performance Optimization](#performance-optimization)
6. [Troubleshooting](#troubleshooting)

## Local Development

### Prerequisites

- **Node.js**: 18.0.0 or higher
- **npm**: 9.0.0 or higher
- **Git**: For version control

### Setup Steps

```bash
# 1. Navigate to web directory
cd web

# 2. Install dependencies
npm install

# 3. Start development server
npm start

# 4. Open browser
# http://localhost:3000

# 5. Test login
# Email: test@example.com
# Password: password123
```

### Development Commands

```bash
# Start server (production mode)
npm start

# Start with auto-reload (requires nodemon)
npm run dev
```

## Docker Deployment

### Quick Start with Docker

```bash
# 1. Build Docker image
docker build -t education-platform .

# 2. Run container
docker run -p 3000:3000 -d education-platform

# 3. Open browser
# http://localhost:3000

# 4. Stop container
docker stop <container-id>
```

### Docker Compose (Recommended)

```bash
# 1. Start all services
docker-compose up -d

# 2. View logs
docker-compose logs -f

# 3. Stop services
docker-compose down
```

### Docker Health Check

```bash
# Check if container is healthy
docker ps

# View detailed status
docker inspect <container-id> | grep Health -A 5
```

## Cloud Deployment

### Heroku Deployment

```bash
# 1. Install Heroku CLI
brew tap heroku/brew && brew install heroku

# 2. Login to Heroku
heroku login

# 3. Create Heroku app
heroku create education-platform

# 4. Deploy from GitHub
git push heroku main

# 5. View logs
heroku logs --tail

# 6. Open app
heroku open
```

### AWS ECS Deployment

```bash
# 1. Configure AWS credentials
aws configure

# 2. Create ECR repository
aws ecr create-repository --repository-name education-platform

# 3. Build and push Docker image
docker build -t education-platform .
docker tag education-platform:latest <account-id>.dkr.ecr.us-east-1.amazonaws.com/education-platform:latest
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <account-id>.dkr.ecr.us-east-1.amazonaws.com
docker push <account-id>.dkr.ecr.us-east-1.amazonaws.com/education-platform:latest

# 4. Create ECS cluster and task definition
# Follow AWS console or CloudFormation template

# 5. Deploy via ECS
aws ecs update-service --cluster education-cluster --service education-service --force-new-deployment
```

### DigitalOcean Deployment

```bash
# 1. Create Droplet (Ubuntu 22.04, 2GB RAM minimum)

# 2. SSH into Droplet
ssh root@<droplet-ip>

# 3. Install Docker
curl -fsSL https://get.docker.com -o get-docker.sh
bash get-docker.sh

# 4. Clone repository
git clone <your-repo> education-platform
cd education-platform/web

# 5. Deploy with Docker Compose
docker-compose up -d

# 6. Setup domain with Let's Encrypt
# Use Certbot or DigitalOcean's built-in SSL
```

### Vercel Deployment (Serverless)

```bash
# 1. Install Vercel CLI
npm install -g vercel

# 2. Login and deploy
vercel

# 3. Connect GitHub repository for auto-deployment
# https://vercel.com/dashboard/import

# 4. Set environment variables
# PORT=3000
# NODE_ENV=production
```

## Browser Compatibility

### Desktop Browsers

| Browser | Version | Status |
|---------|---------|--------|
| Chrome | 90+ | ✅ Full Support |
| Safari | 14+ | ✅ Full Support |
| Firefox | 88+ | ✅ Full Support |
| Edge | 90+ | ✅ Full Support |

### Mobile Browsers

| Browser | Device | Version | Status |
|---------|--------|---------|--------|
| Chrome | Android | 90+ | ✅ Full Support |
| Safari | iOS | 12+ | ✅ Full Support |
| Samsung Internet | Galaxy | 14+ | ✅ Full Support |
| Firefox | Android | 88+ | ✅ Full Support |
| Edge | iOS/Android | Latest | ✅ Full Support |

### Testing Browsers

Use [BrowserStack](https://www.browserstack.com) or [LambdaTest](https://www.lambdatest.com) for cross-browser testing.

## Performance Optimization

### Caching Strategy

```javascript
// Enable HTTP caching
Cache-Control: public, max-age=31536000, immutable  // Static assets
Cache-Control: public, max-age=3600                 // HTML
Cache-Control: no-cache                             // API responses
```

### CDN Setup

1. **CloudFlare**: Simple DNS change, auto-optimization
2. **AWS CloudFront**: Link to S3 bucket
3. **Fastly**: Premium DDoS protection
4. **Bunny CDN**: Affordable alternative

### Image Optimization

```html
<!-- Use WebP with fallback -->
<picture>
  <source srcset="image.webp" type="image/webp">
  <img src="image.jpg" alt="Description">
</picture>
```

### Minification

Built-in: Express serves minified CSS/JS automatically

### Lazy Loading

```html
<img src="image.jpg" loading="lazy" alt="Description">
```

## Troubleshooting

### Port 3000 Already in Use

```bash
# Find process using port 3000
lsof -i :3000

# Kill the process
kill -9 <process-id>

# Or use different port
PORT=3001 npm start
```

### Database Errors

```bash
# Reset database
rm education_platform.db
npm start

# Check database integrity
sqlite3 education_platform.db ".tables"
```

### CORS Issues

If frontend cannot reach backend:

```javascript
// Verify CORS headers in server.js
app.use(cors());

// Check browser console for error details
// Ensure API_BASE in api.js matches server URL
```

### Docker Won't Start

```bash
# Check logs
docker logs <container-id>

# Rebuild from scratch
docker-compose down
docker-compose build --no-cache
docker-compose up -d
```

### High Memory Usage

```bash
# Limit container memory
docker run -m 512m education-platform

# Monitor usage
docker stats
```

### SSL/HTTPS Issues

For production, use a reverse proxy:

```nginx
server {
  listen 443 ssl;
  ssl_certificate /path/to/cert.pem;
  ssl_certificate_key /path/to/key.pem;
  
  location / {
    proxy_pass http://localhost:3000;
    proxy_set_header X-Forwarded-For $remote_addr;
  }
}
```

## Performance Metrics

```
First Contentful Paint: < 1.5s
Largest Contentful Paint: < 2.5s
Time to Interactive: < 3.5s
Cumulative Layout Shift: < 0.1
```

## Security Checklist

- [ ] HTTPS/SSL certificate installed
- [ ] CORS properly configured
- [ ] Password hashing enabled (SHA256+)
- [ ] Database backups scheduled
- [ ] Environment variables secured
- [ ] Dependencies audited (`npm audit`)
- [ ] Security headers set
- [ ] Rate limiting enabled
- [ ] Input validation active
- [ ] Monitoring/alerting configured

## Support

- **Documentation**: See [README.md](README.md)
- **Issues**: Open GitHub issue
- **Discord**: Join community server
- **Email**: support@educationplatform.com

---

**Last Updated**: 2024
**Version**: 1.0.0
**Status**: Production Ready ✅
