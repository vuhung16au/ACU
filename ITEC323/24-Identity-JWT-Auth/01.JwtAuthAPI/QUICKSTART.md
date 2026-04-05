# Quickstart

## Running the API
1. Open a terminal and navigate to this project:
   ```bash
   cd 01.JwtAuthAPI
   ```
2. Run the application:
   ```bash
   dotnet run
   ```
3. The API will start at `http://localhost:5000`. Navigate to this URL in your web browser to interact with the integrated JWT demonstration interface.

## Running Tests
Run backend integration tests:
```bash
dotnet test
```

## Running Automated Visual Demo (Playwright)
1. Open a terminal and navigate to the `scripts` directory:
   ```bash
   cd scripts
   ```
2. Install frontend testing dependencies and browser binaries (first-time only):
   ```bash
   npm install
   npx playwright install chromium
   ```
3. Execute the tests:
   ```bash
   node playwright-test.js
   ```

   ```bash
      Starting the .NET API server on an ephemeral port...
      API is successfully running at http://127.0.0.1:52587
      Launching browser...
      Navigating to API host at http://127.0.0.1:52587...
      Clicking Register...
      Clicking Login...
      Requesting Profile Data...
      Closing browser and finalizing video...
      Shutting down .NET API process...
      Playback artifacts generated! Check the 24-Identity-JWT-Auth/artifacts directory.
   ```
   **Note**: The script operates autonomously. It will start the .NET API automatically, extract its dynamic listening port, execute the UI interactions, and shut the server down once testing finishes.

4. View the generated visual workflow (screenshots and `.webm` videos) in the `artifacts` directory!
