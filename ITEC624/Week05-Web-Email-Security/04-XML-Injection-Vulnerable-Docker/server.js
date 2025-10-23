const express = require('express');
const path = require('path');
const fs = require('fs');
const xpath = require('xpath');
const { DOMParser } = require('xmldom');

const app = express();

app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

app.use(express.urlencoded({ extended: false }));

// Load XML document (in-memory for demo)
const usersXmlPath = path.join(__dirname, 'data', 'users.xml');
const usersXmlString = fs.readFileSync(usersXmlPath, 'utf8');
const usersDoc = new DOMParser({ locator: {}, errorHandler: { warning: null } }).parseFromString(usersXmlString);

// Home page with vulnerable XML/XPath injection
app.get('/', (req, res) => {
  res.render('index', { username: '', password: '', result: null, error: null });
});

// Vulnerable login: user input is concatenated directly into XPath expression
app.post('/login', (req, res) => {
  const username = req.body.username || '';
  const password = req.body.password || '';
  try {
    // Intentionally vulnerable construction
    const query = "//user[username='" + username + "' and password='" + password + "']";
    const nodes = xpath.select(query, usersDoc);
    const ok = nodes && nodes.length > 0;
    res.render('index', { username, password, result: ok ? 'Login successful (vulnerable path)' : 'Invalid credentials', error: null });
  } catch (e) {
    res.render('index', { username, password, result: null, error: 'XPath error: ' + String(e) });
  }
});

// Safe example using parameterization via xpath variables is not supported by this library,
// so demonstrate by validating/escaping inputs and comparing via DOM, not string-built XPath.
app.post('/safe-login', (req, res) => {
  const username = (req.body.username || '').replace(/['"<>\\]/g, '');
  const password = (req.body.password || '').replace(/['"<>\\]/g, '');
  try {
    // Safer: iterate and compare text contents without building dynamic XPath from user input
    const userNodes = xpath.select('//user', usersDoc) || [];
    let ok = false;
    for (const userNode of userNodes) {
      const unameNode = xpath.select1('string(username)', userNode);
      const pwdNode = xpath.select1('string(password)', userNode);
      if (String(unameNode) === username && String(pwdNode) === password) {
        ok = true;
        break;
      }
    }
    res.render('safe', { username, password, result: ok ? 'Login successful (safe path)' : 'Invalid credentials', error: null });
  } catch (e) {
    res.render('safe', { username, password, result: null, error: 'Error: ' + String(e) });
  }
});

const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`XML Injection lab listening on http://localhost:${port}`);
});


