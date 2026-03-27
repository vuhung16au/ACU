# Key Takeaways

## Razor Pages Form Flow

- `OnGet()` displays the page.
- `OnPost()` receives the submitted values.
- `BindProperty` connects form fields to the PageModel.
- Razor syntax displays the submitted values after the form is posted.

## Why Playwright Is Useful

- It tests the page through a real browser.
- It behaves more like a real user than a unit test.
- It helps students confirm both page behaviour and page content.

## Screenshots And Video

- `ScreenshotAsync()` captures a still image of the page.
- Playwright video recording captures the full browser session.
- The recorded file is useful when teaching, debugging, or demonstrating a workflow.

## About GIF Output

Playwright records video as `.webm`.

This sample optionally converts the recorded `.webm` file into a `.gif` by calling `ffmpeg` when it is available. This keeps the example practical while showing students an easy path to create shareable demo files.
