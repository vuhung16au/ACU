const express = require('express');
const path = require('path');
const axios = require('axios');
const request = require('request');

const app = express();

app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

app.use(express.urlencoded({ extended: false }));
app.use(express.json());

// Internal services that should be protected
const internalServices = {
  admin: { port: 8080, endpoint: '/admin' },
  database: { port: 3306, endpoint: '/api/data' },
  redis: { port: 6379, endpoint: '/cache' },
  elasticsearch: { port: 9200, endpoint: '/search' }
};

// Simulate internal services for demonstration
app.get('/admin', (req, res) => {
  res.json({ 
    message: 'Admin panel accessed!', 
    sensitive_data: 'This should never be accessible from outside!',
    users: ['admin', 'user1', 'user2'],
    config: { secret_key: 'super-secret-key-123' }
  });
});

app.get('/api/data', (req, res) => {
  res.json({ 
    message: 'Database accessed!', 
    tables: ['users', 'orders', 'payments'],
    connection_string: 'mysql://admin:password@localhost:3306/db'
  });
});

app.get('/cache', (req, res) => {
  res.json({ 
    message: 'Redis cache accessed!', 
    keys: ['session:123', 'user:456', 'temp:789'],
    memory_usage: '256MB'
  });
});

app.get('/search', (req, res) => {
  res.json({ 
    message: 'Elasticsearch accessed!', 
    indices: ['logs', 'documents', 'analytics'],
    cluster_health: 'green'
  });
});

// VULNERABLE: Basic SSRF - No URL validation
app.get('/vulnerable', (req, res) => {
  const url = req.query.url;
  if (!url) {
    return res.render('vulnerable', { 
      url: '',
      error: null,
      result: null 
    });
  }

  // Intentionally vulnerable: Direct URL usage without validation
  axios.get(url)
    .then(response => {
      res.render('vulnerable', { 
        url: url,
        result: response.data,
        error: null 
      });
    })
    .catch(error => {
      res.render('vulnerable', { 
        url: url,
        result: null,
        error: error.message 
      });
    });
});

// VULNERABLE: SSRF with request library (different attack vector)
app.get('/vulnerable-request', (req, res) => {
  res.render('vulnerable-request', { 
    url: '',
    error: null,
    result: null 
  });
});

app.post('/vulnerable-request', (req, res) => {
  const { url } = req.body;
  
  if (!url) {
    return res.render('vulnerable-request', { 
      url: '',
      error: null,
      result: null 
    });
  }

  // Intentionally vulnerable: Using request library without validation
  request(url, (error, response, body) => {
    if (error) {
      return res.render('vulnerable-request', { 
        url: url,
        result: null,
        error: error.message 
      });
    }
    
    res.render('vulnerable-request', { 
      url: url,
      result: body,
      error: null 
    });
  });
});

// VULNERABLE: SSRF with file:// protocol
app.get('/vulnerable-file', (req, res) => {
  const url = req.query.url;
  
  if (!url) {
    return res.render('vulnerable-file', { 
      url: '',
      error: null,
      result: null 
    });
  }

  // Intentionally vulnerable: Allows file:// protocol
  axios.get(url)
    .then(response => {
      res.render('vulnerable-file', { 
        url: url,
        result: response.data,
        error: null 
      });
    })
    .catch(error => {
      res.render('vulnerable-file', { 
        url: url,
        result: null,
        error: error.message 
      });
    });
});

// VULNERABLE: SSRF with gopher:// protocol
app.get('/vulnerable-gopher', (req, res) => {
  const url = req.query.url;
  
  if (!url) {
    return res.render('vulnerable-gopher', { 
      url: '',
      error: null,
      result: null 
    });
  }

  // Intentionally vulnerable: Allows gopher:// protocol
  request(url, (error, response, body) => {
    if (error) {
      return res.render('vulnerable-gopher', { 
        url: url,
        result: null,
        error: error.message 
      });
    }
    
    res.render('vulnerable-gopher', { 
      url: url,
      result: body,
      error: null 
    });
  });
});

// SAFE: Protected endpoint with URL validation
app.get('/safe', (req, res) => {
  const url = req.query.url;
  
  if (!url) {
    return res.render('safe', { 
      url: '',
      error: null,
      result: null 
    });
  }

  // Safe implementation: URL validation and whitelist
  const allowedDomains = ['httpbin.org', 'jsonplaceholder.typicode.com', 'api.github.com'];
  const urlObj = new URL(url);
  
  if (!allowedDomains.includes(urlObj.hostname)) {
    return res.render('safe', { 
      url: url,
      result: null,
      error: 'Domain not allowed. Only external APIs are permitted.' 
    });
  }

  // Additional validation: Check for dangerous protocols
  if (!['http:', 'https:'].includes(urlObj.protocol)) {
    return res.render('safe', { 
      url: url,
      result: null,
      error: 'Only HTTP and HTTPS protocols are allowed.' 
    });
  }

  axios.get(url)
    .then(response => {
      res.render('safe', { 
        url: url,
        result: response.data,
        error: null 
      });
    })
    .catch(error => {
      res.render('safe', { 
        url: url,
        result: null,
        error: error.message 
      });
    });
});

// Home page
app.get('/', (req, res) => {
  res.render('index', { internalServices });
});

const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`SSRF vulnerable app listening on http://localhost:${port}`);
  console.log('Available internal services:');
  Object.entries(internalServices).forEach(([name, service]) => {
    console.log(`  - ${name}: http://localhost:${service.port}${service.endpoint}`);
  });
});
