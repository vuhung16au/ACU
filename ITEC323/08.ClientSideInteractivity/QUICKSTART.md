# Quick Start Guide
## Client-Side Interactivity Module

### Prerequisites

**Required:**
- ✅ .NET SDK 8.0 or later
- ✅ Visual Studio Code or Visual Studio 2022
- ✅ Modern web browser (Chrome, Edge, Firefox, Safari)
- ✅ Basic JavaScript knowledge
- ✅ Completed previous modules (01-07)

**For React/Vue Projects (07-08):**
- ✅ Node.js 18+ and NPM
- ✅ Basic command-line familiarity

### Installation Checks

```bash
# Check .NET version
dotnet --version
# Should show 8.0.x or 9.0.x

# Check Node.js (for React/Vue)
node --version
# Should show v18.x or higher

# Check NPM
npm --version
# Should show 9.x or higher
```

### Setup Steps

#### 1. Navigate to Module Folder

```bash
cd /path/to/ITEC323/08.ClientSideInteractivity
```

#### 2. Choose Your Starting Point

**Beginners:** Start with `01.VanillaJsBasics`  
**Some JS experience:** Jump to `03.AlpineJsComponents`  
**Framework curious:** Try `06.BlazorInteractive` or `07.ReactBasics`

#### 3. Follow Individual Project Instructions

Each project folder will contain:
- `README.md` - Project overview
- `QUICKSTART.md` - Specific setup steps (when needed)
- Source code files

### Running Projects

#### ASP.NET Projects (01-06, 09)

```bash
# Navigate to project folder
cd 01.VanillaJsBasics

# Restore dependencies
dotnet restore

# Build project
dotnet build

# Run application
dotnet run

# Open browser to:
https://localhost:5001
# or check terminal output for port
```

#### React Project (07)

```bash
cd 07.ReactBasics

# Install JavaScript dependencies
npm install

# Run .NET backend (terminal 1)
cd backend
dotnet run

# Run React frontend (terminal 2)
cd frontend
npm run dev

# Open browser to:
http://localhost:5173
```

#### Vue Project (08)

```bash
cd 08.VueBasics

# Install JavaScript dependencies
npm install

# Run .NET backend (terminal 1)
cd backend
dotnet run

# Run Vue frontend (terminal 2)
cd frontend
npm run dev

# Open browser to:
http://localhost:5173
```

### Recommended Learning Path

```
Day 1: 01.VanillaJsBasics → 02.PromisesAsyncAwait
       ↓
Day 2: 03.AlpineJsComponents → 04.HtmxPartialUpdates
       ↓
Day 3: 05.HtmxAdvanced
       ↓
Day 4: 06.BlazorInteractive → 07.ReactBasics OR 08.VueBasics
       ↓
Day 5: 09.ComprehensiveApp (brings it all together)
```

### Essential Reading

**Before Starting:**
1. [docs/WhyNotJQuery.md](docs/WhyNotJQuery.md) - Understand the "why"
2. [docs/ModernJavaScript.md](docs/ModernJavaScript.md) - Language refresher

**During Projects:**
- Reference guides as needed for specific technologies

**After Completing:**
- [docs/FrameworkComparison.md](docs/FrameworkComparison.md) - Make informed decisions

### Browser DevTools Setup

**Chrome/Edge:**
1. Press `F12` or `Ctrl+Shift+I` (Windows) / `Cmd+Option+I` (Mac)
2. Key tabs: **Console**, **Network**, **Elements**

**Firefox:**
1. Press `F12` or `Ctrl+Shift+I` (Windows) / `Cmd+Option+I` (Mac)
2. Similar tabs: **Console**, **Network**, **Inspector**

**What to Watch:**
- **Console:** JavaScript errors, `console.log()` output
- **Network:** XHR/Fetch requests, response data
- **Elements:** DOM changes, CSS debugging

### Troubleshooting

#### Port Already in Use

```bash
# Find and kill process on port 5001 (macOS/Linux)
lsof -ti:5001 | xargs kill -9

# Windows
netstat -ano | findstr :5001
taskkill /PID <PID> /F
```

#### CORS Errors (React/Vue)

Check that backend `Program.cs` includes:

```csharp
builder.Services.AddCors(options =>
{
    options.AddPolicy("AllowFrontend",
        policy => policy.WithOrigins("http://localhost:5173")
                       .AllowAnyMethod()
                       .AllowAnyHeader());
});

app.UseCors("AllowFrontend");
```

#### NPM Install Fails

```bash
# Clear cache and retry
npm cache clean --force
npm install
```

#### .NET Build Fails

```bash
# Clean and rebuild
dotnet clean
dotnet restore
dotnet build
```

### Getting Help

1. **Check the README:** Each project has specific instructions
2. **Review docs:** Guides in `docs/` folder
3. **Browser Console:** Check for JavaScript errors
4. **Network Tab:** Verify API calls are working
5. **Ask Questions:** Use your learning platform or instructor

### Tips for Success

✅ **Work Sequentially:** Each project builds on previous concepts  
✅ **Use DevTools:** Browser console is your best friend  
✅ **Experiment:** Change code, break things, see what happens  
✅ **Compare Approaches:** Try building the same feature multiple ways  
✅ **Read Comments:** Code comments explain the "why," not just "what"

### Next Steps

Once setup is complete:

1. Open [README.md](README.md) for module overview
2. Read [docs/WhyNotJQuery.md](docs/WhyNotJQuery.md) for context
3. Start with `01.VanillaJsBasics/README.md`
4. Code along with examples
5. Try the challenges at the end of each project

---

**Ready to begin?** Head to [01.VanillaJsBasics](01.VanillaJsBasics/) to start!
