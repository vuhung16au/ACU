# 01.RazorPagesPlaywrightDemo

## Overview

This project demonstrates a simple ASP.NET Core Razor Pages form that can be automated with Playwright.

The form collects:

1. name
2. email
3. favourite language

After the user clicks **Submit**, the page displays the submitted values.

## Learning Objectives

By working through this project, students will learn how to:

- build a basic Razor Pages form
- handle form submission with `OnPost()`
- display submitted values on the same page
- automate a browser workflow with Playwright
- capture screenshots from an automated test
- record a test session as a `.webm` video
- optionally convert the video into a `.gif`

## Project Structure

```text
01.RazorPagesPlaywrightDemo/
├── 01.RazorPagesPlaywrightDemo.csproj
├── Program.cs
├── Models/
├── Pages/
├── Properties/
├── wwwroot/
├── docs/
├── tests/
├── README.md
├── QUICKSTART.md
└── FRD.md
```

## Main Features

- simple Razor Pages form with three inputs
- server-side `OnPost()` handling
- confirmation section showing submitted values
- Playwright browser automation test
- screenshot capture after submission
- recorded browser session saved as `.webm`

## Test Artifacts

When the Playwright test runs, it stores generated files in the test project's `artifacts` folders:

- `artifacts/screenshots`
- `artifacts/videos`
- `artifacts/gifs`

The `.gif` file is created only if `ffmpeg` is available on the machine.

## Related Files

- [QUICKSTART.md](QUICKSTART.md) for setup and run steps
- [FRD.md](FRD.md) for functional requirements
- [docs/Key-Takeaways.md](docs/Key-Takeaways.md) for teaching notes
