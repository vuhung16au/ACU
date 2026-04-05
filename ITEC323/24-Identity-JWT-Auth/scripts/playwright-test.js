const { chromium } = require('playwright');
const path = require('path');
const fs = require('fs');
const { spawn } = require('child_process');

(async () => {
  const artifactsDir = path.join(__dirname, '..', 'artifacts');
  if (!fs.existsSync(artifactsDir)) {
    fs.mkdirSync(artifactsDir, { recursive: true });
  }

  const apiPath = path.join(__dirname, '..', '01.JwtAuthAPI');
  
  console.log('Starting the .NET API server on an ephemeral port...');
  
  // Start the .NET app on port 0 to get a dynamic port, avoiding any collisions
  const apiProcess = spawn('dotnet', ['run', '--urls', 'http://127.0.0.1:0'], { cwd: apiPath });
  
  let baseUrl = '';
  
  // Create a promise to wait for the server to output its listening URL
  const serverReady = new Promise((resolve, reject) => {
    const timeout = setTimeout(() => {
      reject(new Error('Timed out waiting for API to start.'));
    }, 20000);

    const onData = (data) => {
      const output = data.toString();
      // console.log(`[API]: ${output.trim()}`); // Uncomment to debug API logs
      // Look for "Now listening on: http://..."
      const match = output.match(/Now listening on:\s*(http:\/\/[^\s]+)/);
      if (match && !baseUrl) {
        baseUrl = match[1];
        clearTimeout(timeout);
        resolve(baseUrl);
      }
    };

    apiProcess.stdout.on('data', onData);
    // Sometimes logs are pushed to stderr in .NET
    apiProcess.stderr.on('data', (data) => {
      onData(data);
    });
    
    apiProcess.on('error', (err) => reject(err));
  });

  try {
    await serverReady;
    console.log(`API is successfully running at ${baseUrl}`);
  } catch (err) {
    console.error(err.message);
    if (!apiProcess.killed) apiProcess.kill('SIGKILL');
    process.exit(1);
  }

  console.log('Launching browser...');
  const browser = await chromium.launch({ headless: true });
  
  // Record video
  const context = await browser.newContext({
    recordVideo: {
      dir: artifactsDir,
      size: { width: 800, height: 600 }
    }
  });

  const page = await context.newPage();

  console.log(`Navigating to API host at ${baseUrl}...`);
  try {
      await page.goto(baseUrl);
  } catch (err) {
      console.error(`Failed to load page at ${baseUrl}`, err);
      await cleanUp(browser, context, apiProcess);
      process.exit(1);
  }

  try {
    // 1. Fill email and password for register
    await page.fill('#email', 'playwright@example.com');
    await page.fill('#password', 'password123!');
    
    console.log('Clicking Register...');
    await page.click('#btnRegister');
    await page.waitForTimeout(1000);
    await page.screenshot({ path: path.join(artifactsDir, '01-register-success.png') });

    // 2. Click Login
    console.log('Clicking Login...');
    await page.click('#btnLogin');
    await page.waitForTimeout(1000);
    await page.screenshot({ path: path.join(artifactsDir, '02-login-success.png') });

    // 3. Request Profile using the retrieved Token
    console.log('Requesting Profile Data...');
    await page.click('#btnProfile');
    await page.waitForTimeout(1000);
    await page.screenshot({ path: path.join(artifactsDir, '03-profile-accessed.png') });

  } catch(e) {
    console.error('Error during browser tests:', e);
  }

  console.log('Closing browser and finalizing video...');
  await cleanUp(browser, context, apiProcess);

  console.log('Playback artifacts generated! Check the 24-Identity-JWT-Auth/artifacts directory.');
})();

async function cleanUp(browser, context, apiProcess) {
  if (context) await context.close();
  if (browser) await browser.close();
  if (apiProcess) {
    console.log('Shutting down .NET API process...');
    // tree-kill the process if possible, but standard SIGTERM is usually enough
    apiProcess.kill('SIGTERM');
  }
}
