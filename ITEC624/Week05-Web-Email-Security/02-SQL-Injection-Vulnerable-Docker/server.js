const express = require('express');
const sqlite3 = require('sqlite3').verbose();
const path = require('path');

const app = express();

app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

app.use(express.urlencoded({ extended: false }));
app.use(express.json());

// Initialize SQLite database with sample data
const db = new sqlite3.Database(':memory:');

// Create users table and insert sample data
db.serialize(() => {
  db.run(`CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL,
    role TEXT DEFAULT 'user'
  )`);

  // Insert sample users
  const users = [
    ['admin', 'admin@example.com', 'admin123', 'admin'],
    ['john_doe', 'john@example.com', 'password123', 'user'],
    ['jane_smith', 'jane@example.com', 'secret456', 'user'],
    ['bob_wilson', 'bob@example.com', 'mypassword', 'user']
  ];

  const stmt = db.prepare(`INSERT INTO users (username, email, password, role) VALUES (?, ?, ?, ?)`);
  users.forEach(user => {
    stmt.run(user);
  });
  stmt.finalize();
});

// Home page - shows vulnerable login form
app.get('/', (req, res) => {
  res.render('index', { 
    message: req.query.message || '',
    error: req.query.error || ''
  });
});

// VULNERABLE: SQL Injection in login
app.post('/login', (req, res) => {
  const { username, password } = req.body;
  
  // VULNERABLE: Direct string concatenation - SQL INJECTION VULNERABILITY!
  const query = `SELECT * FROM users WHERE username = '${username}' AND password = '${password}'`;
  
  console.log('Vulnerable query:', query);
  
  db.all(query, (err, rows) => {
    if (err) {
      console.error('Database error:', err);
      return res.redirect('/?error=' + encodeURIComponent('Database error occurred'));
    }
    
    if (rows.length > 0) {
      const user = rows[0];
      res.render('dashboard', { 
        user: user,
        message: `Welcome back, ${user.username}!`,
        query: query
      });
    } else {
      res.redirect('/?error=' + encodeURIComponent('Invalid username or password'));
    }
  });
});

// VULNERABLE: SQL Injection in search
app.get('/search', (req, res) => {
  const searchTerm = req.query.q || '';
  
  // VULNERABLE: Direct string concatenation - SQL INJECTION VULNERABILITY!
  const query = `SELECT * FROM users WHERE username LIKE '%${searchTerm}%' OR email LIKE '%${searchTerm}%'`;
  
  console.log('Vulnerable search query:', query);
  
  db.all(query, (err, rows) => {
    if (err) {
      console.error('Database error:', err);
      return res.render('search', { 
        users: [], 
        searchTerm: searchTerm,
        error: 'Database error occurred',
        query: query
      });
    }
    
    res.render('search', { 
      users: rows, 
      searchTerm: searchTerm,
      query: query
    });
  });
});

// SAFE: Parameterized queries (prepared statements)
app.post('/safe-login', (req, res) => {
  const { username, password } = req.body;
  
  // SAFE: Using parameterized queries
  const query = `SELECT * FROM users WHERE username = ? AND password = ?`;
  
  console.log('Safe query:', query, 'with params:', [username, password]);
  
  db.all(query, [username, password], (err, rows) => {
    if (err) {
      console.error('Database error:', err);
      return res.redirect('/?error=' + encodeURIComponent('Database error occurred'));
    }
    
    if (rows.length > 0) {
      const user = rows[0];
      res.render('dashboard', { 
        user: user,
        message: `Welcome back, ${user.username}! (Safe login)`,
        query: query + ' with parameters: [' + username + ', ' + password + ']'
      });
    } else {
      res.redirect('/?error=' + encodeURIComponent('Invalid username or password'));
    }
  });
});

// SAFE: Parameterized search
app.get('/safe-search', (req, res) => {
  const searchTerm = req.query.q || '';
  
  // SAFE: Using parameterized queries
  const query = `SELECT * FROM users WHERE username LIKE ? OR email LIKE ?`;
  const searchPattern = `%${searchTerm}%`;
  
  console.log('Safe search query:', query, 'with params:', [searchPattern, searchPattern]);
  
  db.all(query, [searchPattern, searchPattern], (err, rows) => {
    if (err) {
      console.error('Database error:', err);
      return res.render('search', { 
        users: [], 
        searchTerm: searchTerm,
        error: 'Database error occurred',
        query: query + ' with parameters: [' + searchPattern + ', ' + searchPattern + ']'
      });
    }
    
    res.render('search', { 
      users: rows, 
      searchTerm: searchTerm,
      query: query + ' with parameters: [' + searchPattern + ', ' + searchPattern + ']'
    });
  });
});

// Show database schema
app.get('/schema', (req, res) => {
  db.all("SELECT sql FROM sqlite_master WHERE type='table'", (err, rows) => {
    if (err) {
      return res.status(500).send('Error fetching schema');
    }
    res.render('schema', { tables: rows });
  });
});

const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`SQL Injection vulnerable app listening on http://localhost:${port}`);
  console.log('Sample users:');
  console.log('- admin / admin123 (admin role)');
  console.log('- john_doe / password123 (user role)');
  console.log('- jane_smith / secret456 (user role)');
  console.log('- bob_wilson / mypassword (user role)');
});
