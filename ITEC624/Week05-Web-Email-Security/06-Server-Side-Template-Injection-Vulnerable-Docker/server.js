const express = require('express');
const path = require('path');
const Handlebars = require('handlebars');
const Mustache = require('mustache');
const nunjucks = require('nunjucks');

const app = express();

// Configure EJS as default view engine
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

// Configure Nunjucks
nunjucks.configure('views', {
  autoescape: false, // Intentionally vulnerable
  express: app
});

app.use(express.urlencoded({ extended: false }));
app.use(express.json());

// Home page with vulnerable EJS template injection
app.get('/', (req, res) => {
  const template = req.query.template || 'Hello World';
  // Intentionally vulnerable: direct template injection
  res.render('index', { template });
});

// Vulnerable EJS template injection endpoint
app.get('/ejs', (req, res) => {
  const userInput = req.query.input || '';
  // Intentionally vulnerable: unescaped template rendering
  res.render('vulnerable-ejs', { userInput });
});

// Vulnerable Handlebars template injection endpoint
app.get('/handlebars', (req, res) => {
  const userInput = req.query.input || '';
  res.render('vulnerable-handlebars', { userInput });
});

// Vulnerable Mustache template injection endpoint
app.get('/mustache', (req, res) => {
  const userInput = req.query.input || '';
  res.render('vulnerable-mustache', { userInput });
});

// Vulnerable Nunjucks template injection endpoint
app.get('/nunjucks', (req, res) => {
  const userInput = req.query.input || '';
  // Intentionally vulnerable: autoescape disabled
  res.render('vulnerable-nunjucks', { userInput });
});

// Advanced SSTI with file operations
app.get('/advanced', (req, res) => {
  const template = req.query.template || '';
  // Intentionally vulnerable: direct template execution
  res.render('advanced', { template });
});

// Safe example route showing proper template handling
app.get('/safe', (req, res) => {
  const userInput = req.query.input || '';
  // Safe: properly escaped template rendering
  res.render('safe', { userInput });
});

// File upload endpoint for template files (vulnerable)
app.post('/upload', (req, res) => {
  const templateContent = req.body.template || '';
  // Intentionally vulnerable: direct template execution
  res.render('upload-result', { templateContent });
});

// Template preview endpoint (vulnerable)
app.get('/preview', (req, res) => {
  const template = req.query.template || '';
  // Intentionally vulnerable: direct template rendering
  res.render('preview', { template });
});

// Admin panel with template injection
app.get('/admin', (req, res) => {
  const adminTemplate = req.query.template || 'Admin Panel';
  // Intentionally vulnerable: admin template injection
  res.render('admin', { adminTemplate });
});

// Search functionality with template injection
app.get('/search', (req, res) => {
  const searchQuery = req.query.q || '';
  const results = [
    { title: 'Document 1', content: 'Sample content 1' },
    { title: 'Document 2', content: 'Sample content 2' },
    { title: 'Document 3', content: 'Sample content 3' }
  ];
  // Intentionally vulnerable: search results with template injection
  res.render('search', { searchQuery, results });
});

// User profile with template injection
app.get('/profile', (req, res) => {
  const username = req.query.username || 'Guest';
  const bio = req.query.bio || 'No bio provided';
  // Intentionally vulnerable: user profile template injection
  res.render('profile', { username, bio });
});

// Error page with template injection
app.get('/error', (req, res) => {
  const errorMessage = req.query.message || 'An error occurred';
  // Intentionally vulnerable: error message template injection
  res.render('error', { errorMessage });
});

// Template compilation endpoint (highly vulnerable)
app.post('/compile', (req, res) => {
  const templateCode = req.body.template || '';
  try {
    // Intentionally vulnerable: direct template compilation and execution
    const result = eval(templateCode);
    res.json({ success: true, result });
  } catch (error) {
    res.json({ success: false, error: error.message });
  }
});

// Dynamic template endpoint
app.get('/dynamic/:templateName', (req, res) => {
  const templateName = req.params.templateName;
  const data = req.query;
  // Intentionally vulnerable: dynamic template loading
  res.render(templateName, data);
});

const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`SSTI vulnerable app listening on http://localhost:${port}`);
  console.log('Available endpoints:');
  console.log('  / - Basic EJS template injection');
  console.log('  /ejs - EJS template injection');
  console.log('  /handlebars - Handlebars template injection');
  console.log('  /mustache - Mustache template injection');
  console.log('  /nunjucks - Nunjucks template injection');
  console.log('  /advanced - Advanced SSTI with file operations');
  console.log('  /safe - Safe template handling example');
  console.log('  /upload - File upload with template injection');
  console.log('  /preview - Template preview with injection');
  console.log('  /admin - Admin panel with template injection');
  console.log('  /search - Search with template injection');
  console.log('  /profile - User profile with template injection');
  console.log('  /error - Error page with template injection');
  console.log('  /compile - Template compilation (highly vulnerable)');
  console.log('  /dynamic/:templateName - Dynamic template loading');
});
