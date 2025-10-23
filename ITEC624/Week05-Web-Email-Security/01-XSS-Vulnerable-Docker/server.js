const express = require('express');
const path = require('path');

const app = express();

app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

app.use(express.urlencoded({ extended: false }));

// Home page with vulnerable reflected XSS rendering
app.get('/', (req, res) => {
  const userInput = req.query.q || '';
  // Intentionally vulnerable: unescaped output via EJS <%- %> in the template
  res.render('index', { userInput });
});

// Safe example route showing proper escaping
app.get('/safe', (req, res) => {
  const userInput = req.query.q || '';
  res.render('safe', { userInput });
});

const port = process.env.PORT || 3000;
app.listen(port, () => {
  console.log(`XSS vulnerable app listening on http://localhost:${port}`);
});


