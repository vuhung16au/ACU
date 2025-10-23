const express = require('express');
const fs = require('fs');
const path = require('path');

const app = express();
const PORT = 3000;

// Set EJS as template engine
app.set('view engine', 'ejs');
app.set('views', path.join(__dirname, 'views'));

// Middleware
app.use(express.urlencoded({ extended: true }));
app.use(express.json());

// VULNERABLE: Path Traversal - Direct file access without validation
app.get('/vulnerable', (req, res) => {
    const filename = req.query.file;
    
    if (!filename) {
        return res.render('index', { 
            error: 'Please provide a filename',
            title: 'Path Traversal Lab - Vulnerable'
        });
    }

    try {
        // VULNERABLE: Direct path construction without validation
        const filePath = path.join(__dirname, 'files', filename);
        const content = fs.readFileSync(filePath, 'utf8');
        
        res.render('file-display', {
            title: 'File Content',
            filename: filename,
            content: content,
            vulnerable: true
        });
    } catch (error) {
        res.render('index', {
            error: `Error reading file: ${error.message}`,
            title: 'Path Traversal Lab - Vulnerable'
        });
    }
});

// SAFE: Path Traversal - With proper validation and sanitization
app.get('/safe', (req, res) => {
    const filename = req.query.file;
    
    if (!filename) {
        return res.render('index', { 
            error: 'Please provide a filename',
            title: 'Path Traversal Lab - Safe'
        });
    }

    try {
        // SAFE: Validate and sanitize the filename
        if (filename.includes('..') || filename.includes('/') || filename.includes('\\')) {
            return res.render('index', {
                error: 'Invalid filename: Path traversal detected',
                title: 'Path Traversal Lab - Safe'
            });
        }

        // SAFE: Use path.resolve and check if file is within allowed directory
        const allowedDir = path.join(__dirname, 'files');
        const requestedPath = path.resolve(allowedDir, filename);
        
        // SAFE: Ensure the resolved path is within the allowed directory
        if (!requestedPath.startsWith(allowedDir)) {
            return res.render('index', {
                error: 'Access denied: File outside allowed directory',
                title: 'Path Traversal Lab - Safe'
            });
        }

        const content = fs.readFileSync(requestedPath, 'utf8');
        
        res.render('file-display', {
            title: 'File Content (Safe)',
            filename: filename,
            content: content,
            vulnerable: false
        });
    } catch (error) {
        res.render('index', {
            error: `Error reading file: ${error.message}`,
            title: 'Path Traversal Lab - Safe'
        });
    }
});

// Home page
app.get('/', (req, res) => {
    res.render('index', {
        title: 'Path Traversal Lab',
        error: null
    });
});

// List available files
app.get('/files', (req, res) => {
    try {
        const filesDir = path.join(__dirname, 'files');
        const files = fs.readdirSync(filesDir);
        
        res.render('file-list', {
            title: 'Available Files',
            files: files
        });
    } catch (error) {
        res.render('index', {
            error: `Error listing files: ${error.message}`,
            title: 'Path Traversal Lab'
        });
    }
});

// Create sample files on startup
function createSampleFiles() {
    const filesDir = path.join(__dirname, 'files');
    
    // Create files directory if it doesn't exist
    if (!fs.existsSync(filesDir)) {
        fs.mkdirSync(filesDir, { recursive: true });
    }

    // Create sample files
    const sampleFiles = {
        'readme.txt': 'This is a sample readme file.\nIt contains some basic information.',
        'config.txt': 'Database configuration:\nHost: localhost\nPort: 3306\nUser: admin',
        'secrets.txt': 'SECRET: This file contains sensitive information!\nAPI Key: abc123xyz789',
        'data.json': JSON.stringify({
            users: [
                { id: 1, name: 'John Doe', email: 'john@example.com' },
                { id: 2, name: 'Jane Smith', email: 'jane@example.com' }
            ]
        }, null, 2)
    };

    // Create a sensitive file outside the files directory
    const sensitiveFile = path.join(__dirname, 'sensitive-data.txt');
    fs.writeFileSync(sensitiveFile, 'CRITICAL: This file should never be accessible!\nAdmin password: super_secret_123\nSSH key: -----BEGIN PRIVATE KEY-----\nMIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC...\n-----END PRIVATE KEY-----');

    // Write sample files
    Object.entries(sampleFiles).forEach(([filename, content]) => {
        const filePath = path.join(filesDir, filename);
        fs.writeFileSync(filePath, content);
    });

    console.log('Sample files created successfully');
}

// Start server
app.listen(PORT, () => {
    console.log(`Path Traversal Lab running on http://localhost:${PORT}`);
    console.log('Available endpoints:');
    console.log('  / - Home page');
    console.log('  /vulnerable?file=<filename> - Vulnerable file access');
    console.log('  /safe?file=<filename> - Safe file access');
    console.log('  /files - List available files');
    createSampleFiles();
});
